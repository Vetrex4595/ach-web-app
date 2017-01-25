package com.firstdata.ach.pwmb.web.client.util;



import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class HmacUtil {
	private static final Logger log = LoggerFactory.getLogger(HmacUtil.class);

	public static Map<String, String> getSecurityKeys(String appId, String secureId, String token, String payLoad) throws Exception {

		Map<String, String> returnMap = new HashMap<String, String>();
		long nonce;
		try {

			nonce = Math.abs(SecureRandom.getInstance("SHA1PRNG").nextLong());
			log.debug("HMacUtil :: getSecurityKeys :: SecureRandom nonce:{}", nonce);
			returnMap.put(HmacConstants.NONCE, Long.toString(nonce));
			returnMap.put(HmacConstants.APIKEY, appId);
			returnMap.put(HmacConstants.TIMESTAMP, Long.toString(System.currentTimeMillis()));
			returnMap.put(HmacConstants.TOKEN, token);
			returnMap.put(HmacConstants.APISECRET, secureId);
			returnMap.put(HmacConstants.PAYLOAD, payLoad);
			returnMap.put(HmacConstants.AUTHORIZE, getMacValue(returnMap));
			return returnMap;

		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private static String getMacValue(Map<String, String> data) throws Exception {
		Mac mac = Mac.getInstance("HmacSHA256");
		String apiSecret = data.get(HmacConstants.APISECRET);
		log.debug("HMacUtil :: getSecurityKeys :: API_SECRET:{}", apiSecret);		
		SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
		mac.init(secret_key);
		StringBuilder buff = new StringBuilder();
		buff.append(data.get(HmacConstants.APIKEY)).append(data.get(HmacConstants.NONCE)).append(data.get(HmacConstants.TIMESTAMP));
		if (data.get(HmacConstants.TOKEN) != null)
			buff.append(data.get(HmacConstants.TOKEN));
		if (data.get(HmacConstants.PAYLOAD) != null)
			buff.append(data.get(HmacConstants.PAYLOAD));

		log.info(buff.toString());
		byte[] macHash = mac.doFinal(buff.toString().getBytes("UTF-8"));
		StringBuffer hash = new StringBuffer();
		for (int i = 0; i < macHash.length; i++) {
			String hex = Integer.toHexString(0xFF & macHash[i]);
			if (hex.length() == 1) {
				hash.append('0');
			}
			hash.append(hex);
		}
		String digest = hash.toString();
		String authorizeString = new String(Base64.encodeBase64(digest.getBytes()));
		log.debug("HMacUtil :: getMacValue :: Authorize: {}", authorizeString);
		return authorizeString;
	}

	private static byte[] toHex(byte[] arr) {
		String hex = Hex.encodeHexString(arr);
		log.debug("HMacUtil :: toHex :: common value:{}", hex);
		return hex.getBytes();
	}
	
}