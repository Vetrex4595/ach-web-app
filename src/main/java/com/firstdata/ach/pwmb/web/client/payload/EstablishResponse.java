package com.firstdata.ach.pwmb.web.client.payload;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonAutoDetect(getterVisibility=Visibility.NONE,setterVisibility=Visibility.NONE,fieldVisibility=Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class EstablishResponse {


	
	private String accessId;
	private String merchantId;
	private String description;
	private String currency;
	private String amount;
	private String merchantReference;
	private String paymentType;
	private String returnUrl;
	private String cancelUrl;
	private String requestSignature;
	
	@JsonProperty("ReturnCode")
	private String ReturnCode;
	
	@JsonProperty("Error")
	private String Error;
	
	
	public String getAccessId() {
		return accessId;
	}
	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getMerchantReference() {
		return merchantReference;
	}
	public void setMerchantReference(String merchantReference) {
		this.merchantReference = merchantReference;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getCancelUrl() {
		return cancelUrl;
	}
	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}
	public String getRequestSignature() {
		return requestSignature;
	}
	public void setRequestSignature(String requestSignature) {
		this.requestSignature = requestSignature;
	}
	public String getReturnCode() {
		return ReturnCode;
	}
	public void setReturnCode(String returnCode) {
		ReturnCode = returnCode;
	}
	
	public boolean hasErrors() {
		
		if (this.getError() != null) {
			return true;
		}
		
		return this.ReturnCode != null && !ReturnCode.equals("0");
	}
	public String getError() {
		return Error;
	}
	public void setError(String error) {
		this.Error = error;
	}

	
	
	
}
