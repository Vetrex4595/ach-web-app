package com.firstdata.ach.pwmb.web.client.payload;

public class EstablishRequest {

	private String callType;
	private String subscrId;
	private String returnUrl;
	
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
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
}