package com.firstdata.ach.pwmb.web.client.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidateRequest {




	private String transactionId;
	private String callType;
	private String subscrId;
	private String requestUrl;
	private String requestSignature;
	private String transactionType;
	private String merchantReference;
	private String status;
	private String returnUrl;
	
	@JsonProperty("payment.paymentType")
	private String paymentType;
	
	@JsonProperty("payment.paymentProvider.type")
	private String paymentProviderType;
	
	@JsonProperty("payment.account.verified")
	private String paymentAccountVerified;
	
	private String panel;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getSubscrId() {
		return subscrId;
	}

	public void setSubscrId(String subscrId) {
		this.subscrId = subscrId;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestSignature() {
		return requestSignature;
	}

	public void setRequestSignature(String requestSignature) {
		this.requestSignature = requestSignature;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getMerchantReference() {
		return merchantReference;
	}

	public void setMerchantReference(String merchantReference) {
		this.merchantReference = merchantReference;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentProviderType() {
		return paymentProviderType;
	}

	public void setPaymentProviderType(String paymentProviderType) {
		this.paymentProviderType = paymentProviderType;
	}

	public String getPaymentAccountVerified() {
		return paymentAccountVerified;
	}

	public void setPaymentAccountVerified(String paymentAccountVerified) {
		this.paymentAccountVerified = paymentAccountVerified;
	}

	public String getPanel() {
		return panel;
	}

	public void setPanel(String panel) {
		this.panel = panel;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	
	
	
}
