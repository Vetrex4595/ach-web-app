package com.firstdata.ach.pwmb.web.client;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.firstdata.ach.pwmb.web.client.payload.Address;
import com.firstdata.ach.pwmb.web.client.payload.EstablishRequest;
import com.firstdata.ach.pwmb.web.client.payload.EstablishResponse;
import com.firstdata.ach.pwmb.web.client.payload.TokenRequest;
import com.firstdata.ach.pwmb.web.client.payload.TokenResponse;
import com.firstdata.ach.pwmb.web.client.payload.ValidateRequest;
import com.firstdata.ach.pwmb.web.client.payload.ValidateResponse;
import com.firstdata.ach.pwmb.web.client.util.HmacConstants;
import com.firstdata.ach.pwmb.web.client.util.HmacUtil;
import com.firstdata.ach.pwmb.web.client.util.JsonUtil;

@Controller
public class TCKValidationController {

	@Autowired Environment env;
	@Autowired RestTemplate restTemplate;
	
	@RequestMapping(value="/establish", method = RequestMethod.POST, headers="Content-Type=application/json")
	public ResponseEntity<EstablishResponse> establish(@RequestBody String strRequest, HttpServletRequest servletRequest) {
	
		
		try {
		
			String url = env.getProperty("payeezy.url");
			url = url + "/establish";
			
			String fdoaToken = env.getProperty("token");
			String apikey = env.getProperty("apikey");
			String secret = env.getProperty("apisecret");
			
			
			// String str = convertMultipartToJson(strRequest);
			
			String str = strRequest;
			
			
			EstablishRequest establishRequest = JsonUtil.fromJson(str, EstablishRequest.class);
			
			// establishRequest.setReturnUrl("http://localhost:8080/validate");
			
			String payload = JsonUtil.getJSONObject(establishRequest);
			
	    	Map<String, String> map = HmacUtil.getSecurityKeys(apikey, secret, fdoaToken, payload);
			
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("token", fdoaToken);
			headers.set("apikey", apikey);

			headers.set(HmacConstants.AUTHORIZATION, map.get(HmacConstants.AUTHORIZATION));
			headers.set(HmacConstants.NONCE, map.get(HmacConstants.NONCE));
			headers.set(HmacConstants.TIMESTAMP, map.get(HmacConstants.TIMESTAMP));
			
			
			HttpEntity<String> entity = new HttpEntity<String>(payload, headers);
			
			
			String resp = restTemplate.postForObject(url, entity, String.class);
			
			EstablishResponse response = JsonUtil.fromJson(resp, EstablishResponse.class);
			

			return new ResponseEntity<>(response, HttpStatus.OK);
			
					
		} catch (Exception e) {
			
			
			EstablishResponse response = new EstablishResponse();
			response.setError(e.getMessage());
			
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
			
		}
				
		
	}
	
	
	
	@RequestMapping(value="/validate", method = RequestMethod.POST)
	public ResponseEntity<ValidateResponse> validate(@RequestBody String strRequest, HttpServletRequest servletRequest) {
	
		
		try {
		
			String url = env.getProperty("payeezy.url");
			url = url + "/validate";
			
			String fdoaToken = env.getProperty("token");
			String apikey = env.getProperty("apikey");
			String secret = env.getProperty("apisecret");
			
			// String str = convertMultipartToJson(strRequest);
			String str = strRequest;
			
			ValidateRequest validateRequest = JsonUtil.fromJson(str, ValidateRequest.class);
			validateRequest.setCallType("pwmb.getAll");
			
			String payload = JsonUtil.getJSONObject(validateRequest);
			
	    	Map<String, String> map = HmacUtil.getSecurityKeys(apikey, secret, fdoaToken, payload);

			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("token", fdoaToken);
			headers.set("apikey", apikey);

			headers.set(HmacConstants.AUTHORIZATION, map.get(HmacConstants.AUTHORIZATION));
			headers.set(HmacConstants.NONCE, map.get(HmacConstants.NONCE));
			headers.set(HmacConstants.TIMESTAMP, map.get(HmacConstants.TIMESTAMP));
			
			
			HttpEntity<String> entity = new HttpEntity<String>(payload, headers);
			
			
			String resp = restTemplate.postForObject(url, entity, String.class);
			
			ValidateResponse response = JsonUtil.fromJson(resp, ValidateResponse.class);
			

			return new ResponseEntity<>(response, HttpStatus.OK);
			
					
		} catch (Exception e) {
			
			
			ValidateResponse response = new ValidateResponse();
			response.setError(e.getMessage());
			
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
			
		}
				
		
	}
	
	
	
	private TokenResponse enroll(ValidateResponse validateResponse) throws Exception {
		
		
		String url = env.getProperty("payeezy.url");
		url = url + "/consumer/enrollment/pwmb";
		
		String fdoaToken = env.getProperty("token");
		String apikey = env.getProperty("apikey");
		String secret = env.getProperty("apisecret");
		
		ValidateResponse.TCKObj obj = validateResponse.getTckObj();
		if (obj == null) return null;
		
		TokenRequest tr = new TokenRequest();
		tr.setPwmbTransactionId(obj.getTransactionId());
		
		if (obj.getName() != null) {
			String[] names = obj.getName().split(" ");
			if (names.length > 0) {
				tr.setFirstName(names[0]);
			}

			if (names.length > 1) {
				tr.setLastName(names[1]);
			}
		}
		
		if (obj.getAddress1() != null) {
			Address address = new Address();
			tr.setAddress(address);
			address.setAddressLine1(obj.getAddress1());
			address.setAddressLine2(obj.getAddress2());
			address.setState(obj.getState());
			address.setCity(obj.getCity());
			address.setCountry("0840");
			address.setEmail(obj.getEmail());
			address.setPhone(new Address.Phone());
			address.getPhone().setNumber(obj.getPhone());
			address.getPhone().setType("MOBILE");
			address.setZip(obj.getZip());			
		}
		
		String payload = JsonUtil.getJSONObject(tr);
		
		
    	Map<String, String> map = HmacUtil.getSecurityKeys(apikey, secret, fdoaToken, payload);

		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("token", fdoaToken);
		headers.set("apikey", apikey);

		headers.set(HmacConstants.AUTHORIZATION, map.get(HmacConstants.AUTHORIZATION));
		headers.set(HmacConstants.NONCE, map.get(HmacConstants.NONCE));
		headers.set(HmacConstants.TIMESTAMP, map.get(HmacConstants.TIMESTAMP));
		
		HttpEntity<String> entity = new HttpEntity<String>(payload, headers);
		
		
		String resp = restTemplate.postForObject(url, entity, String.class);
		 
		TokenResponse response = JsonUtil.fromJson(resp, TokenResponse.class);
		
		return response;
		
		
	}
	
	
	
	
	
	@RequestMapping(value="/enroll", method = RequestMethod.POST)
	public ResponseEntity<TokenResponse> enroll(@RequestBody String strRequest, HttpServletRequest servletRequest) {
	
		
		try {
		
			
			HashMap map = JsonUtil.fromJson(strRequest, HashMap.class);
			ValidateResponse resp = new ValidateResponse();
			ValidateResponse.TCKObj obj = resp.new TCKObj();
			resp.setTckObj(obj);
			obj.setTransactionId((String)map.get("transactionId"));
			obj.setName((String)map.get("name"));
			obj.setBank_rt((String)map.get("bank_rt"));
			obj.setBank_acct((String)map.get("bank_acct"));
			obj.setCity((String)map.get("city"));
			obj.setState((String)map.get("state"));
			obj.setZip((String)map.get("zip"));
			obj.setPhone((String)map.get("phone"));
			obj.setEmail((String)map.get("email"));
			obj.setAddress1((String)map.get("address1"));
			obj.setAddress2((String)map.get("address2"));
			
			
			
			TokenResponse response = enroll(resp);
			
			
			

			return new ResponseEntity<>(response, HttpStatus.OK);
			
					
		} catch (Exception e) {
			
			
			TokenResponse response = new TokenResponse();
			response.setDisplayText(e.getMessage());
			
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
			
		}
		
	}
	
	
	
	
	
	
	
	
	@RequestMapping(value="/validate", method = RequestMethod.GET)
	public ResponseEntity<String> validateGet(HttpServletRequest req) {
	
		
		try {
		
			String url = env.getProperty("payeezy.url");
			url = url + "/validate";
			
			String fdoaToken = env.getProperty("token");
			String apikey = env.getProperty("apikey");
			String secret = env.getProperty("apisecret");
			
			String transactionId = req.getParameter("transactionId");
			String transactionType = req.getParameter("transactionType");
			String merchantReference = req.getParameter("merchantReference");
			String status = req.getParameter("status");
			String ppType = req.getParameter("payment.paymentType");
			String pprovType = req.getParameter("payment.paymentProvider.type");
			String verified = req.getParameter("payment.account.verified");
			String panel = req.getParameter("panel");
			String requestSignature = req.getParameter("requestSignature");
			
			ValidateRequest validateRequest = new ValidateRequest();
			validateRequest.setCallType("pwmb.getAll");
			validateRequest.setTransactionId(transactionId);
			validateRequest.setTransactionType(transactionType);
			validateRequest.setMerchantReference(merchantReference);
			validateRequest.setStatus(status);
			validateRequest.setPaymentType(ppType);
			validateRequest.setPaymentProviderType(pprovType);
			validateRequest.setPaymentAccountVerified(verified);
			validateRequest.setPanel(panel);
			validateRequest.setRequestSignature(requestSignature);
			
			
			String payload = JsonUtil.getJSONObject(validateRequest);
			
	    	Map<String, String> map = HmacUtil.getSecurityKeys(apikey, secret, fdoaToken, payload);

			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("token", fdoaToken);
			headers.set("apikey", apikey);

			headers.set(HmacConstants.AUTHORIZATION, map.get(HmacConstants.AUTHORIZATION));
			headers.set(HmacConstants.NONCE, map.get(HmacConstants.NONCE));
			headers.set(HmacConstants.TIMESTAMP, map.get(HmacConstants.TIMESTAMP));
			
			
			HttpEntity<String> entity = new HttpEntity<String>(payload, headers);
			
			
			String response = restTemplate.postForObject(url, entity, String.class);
			

			return new ResponseEntity<>(response, HttpStatus.OK);
			
			
			
		} catch (HttpClientErrorException e) {
			
			return new ResponseEntity<>(e.getMessage() + " - " + e.getResponseBodyAsString(), HttpStatus.OK);
			
					
		} catch (Exception e) {
			
			
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
			
			
		}
				
		
	}
	
	
	
	

	
	
}
