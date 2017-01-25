package com.firstdata.ach.pwmb.web.client.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidateResponse {

	
	



	@JsonProperty("TCKObj")
	private TCKObj tckObj;
	
	
	@JsonProperty("Error")
	private String error;
	
	@JsonProperty("ReturnCode")
	private Integer returnCode;
	
	private String enrollmentId;
	
	
	
	
	

	
	public String getEnrollmentId() {
		return enrollmentId;
	}


	public void setEnrollmentId(String enrollmentId) {
		this.enrollmentId = enrollmentId;
	}


	public Integer getReturnCode() {
		return returnCode;
	}


	public void setReturnCode(Integer returnCode) {
		this.returnCode = returnCode;
	}


	public String getError() {
		return error;
	}


	public void setError(String error) {
		this.error = error;
	}


	public TCKObj getTckObj() {
		return tckObj;
	}


	public void setTckObj(TCKObj tckObj) {
		this.tckObj = tckObj;
	}
	
	
	public boolean hasErrors() {
		return tckObj == null || error != null;
	}




	public class TCKObj {

		private String transactionId;
		private String name;
		private String phone;
		private String email;
		private String address1;
		private String address2;
		private String city;
		private String state;
		private String zip;
		private String bank_rt;
		private String bank_acct;
		public String getTransactionId() {
			return transactionId;
		}
		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getAddress1() {
			return address1;
		}
		public void setAddress1(String address1) {
			this.address1 = address1;
		}
		public String getAddress2() {
			return address2;
		}
		public void setAddress2(String address2) {
			this.address2 = address2;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getZip() {
			return zip;
		}
		public void setZip(String zip) {
			this.zip = zip;
		}
		public String getBank_rt() {
			return bank_rt;
		}
		public void setBank_rt(String bank_rt) {
			this.bank_rt = bank_rt;
		}
		public String getBank_acct() {
			return bank_acct;
		}
		public void setBank_acct(String bank_acct) {
			this.bank_acct = bank_acct;
		}

	}



	
}
