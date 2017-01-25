	// var url = "http://localhost:8080/";
    var url = merchantUrl + "/";
	var xmlhttp_establish = createHTTPRequest('POST', url + "establish");
	var xmlhttp_validate = createHTTPRequest('POST', url + "validate");
	var xmlhttp_enroll = createHTTPRequest('POST', url + "enroll");

	function TCKValidationService(callType) {
		
		var obj = new Object();
		obj.callType = callType;
		obj.returnUrl = merchantUrl;
		
		var data = JSON.stringify(obj);
		
		xmlhttp_establish.send(data);
	}
	
	

	
	window.onload = function() {		
		var QueryString = getRequests();
		var transactionId = QueryString["transactionId"];
		var pwmb_status = QueryString["status"];
		
		if(transactionId != null && pwmb_status == 2) {
			var data = new FormData();
			data.append("transactionId", QueryString["transactionId"]);
			data.append("requestSignature", QueryString["requestSignature"]);
			data.append("transactionType", QueryString["transactionType"]);
			data.append("merchantReference", QueryString["merchantReference"]);
			data.append("status", QueryString["status"]);
			data.append("payment.paymentType", QueryString["payment.paymentType"]);
			data.append("payment.paymentProvider.type", QueryString["payment.paymentProvider.type"]);
			data.append("payment.account.verified", QueryString["payment.account.verified"]);
			data.append("panel", QueryString["panel"]);
			data.append("returnUrl", merchantUrl);
			
			var entries = data.entries();
		    var obj = {};
		    for(var pair of data.entries()){
		         obj[pair[0]] = pair[1];
		    }

		    var dataJson = JSON.stringify(obj);
			
			xmlhttp_validate.send(dataJson);
			
		}
	}
	
	xmlhttp_establish.onreadystatechange = function() {
		if (xmlhttp_establish.readyState == 4 && xmlhttp_establish.status == 200) {
			var response = JSON.parse(xmlhttp_establish.responseText);
// alert(xmlhttp_establish.responseText);
                        if(response.merchantReference != null && response.merchantReference != "") {
				PayWithMyBank.establish(response);
			}
			else
				alert("This feature is not available at this time");
		}
        }

        xmlhttp_validate.onreadystatechange = function() {
                if (xmlhttp_validate.readyState == 4 && xmlhttp_validate.status == 200) {
                        var response = JSON.parse(xmlhttp_validate.responseText);
			var QueryString = getRequests();
			var transactionId = QueryString["transactionId"];
			var pwmb_status = QueryString["status"];
// alert(xmlhttp_validate.responseText);

			if(transactionId != null && pwmb_status == 2) {
				document.getElementById("username").value = response.TCKObj.name;
				document.getElementById("bank_routing").value = response.TCKObj.bank_rt;
				document.getElementById("bank_acct").value = response.TCKObj.bank_acct;
				document.getElementById("transactionID").value = response.TCKObj.transactionId;
				document.getElementById("address").value = response.TCKObj.address1;
				document.getElementById("address2").value = response.TCKObj.address2;
				document.getElementById("city").value = response.TCKObj.city;
				document.getElementById("state").value = response.TCKObj.state;
				document.getElementById("zip").value = response.TCKObj.zip;
				document.getElementById("phone").value = response.TCKObj.phone;
				document.getElementById("email").value = response.TCKObj.email;
				
				// document.getElementById("enrollmentID").value = response.enrollmentId;
			}
		}
	}
        
        
    	xmlhttp_enroll.onreadystatechange = function() {
    		if (xmlhttp_enroll.readyState == 4 && xmlhttp_enroll.status == 200) {
    			var response = JSON.parse(xmlhttp_enroll.responseText);
    // alert(xmlhttp_enroll.responseText);
                if(response.token != null && response.token != "") {
                	document.getElementById("enrollmentID").value = response.token;
    			}
    			else
    				alert("This feature is not available at this time");
    		}
            }
        
	
	function getRequests() {
		var s1 = location.search.substring(1, location.search.length).split('&'),
			r = {}, s2, i;
		for (i = 0; i < s1.length; i += 1) {
			s2 = s1[i].split('=');
			r[decodeURIComponent(s2[0])] = decodeURIComponent(s2[1]);
		}
		return r;
	};
	
	function createHTTPRequest(method, url) {
	  var xhr = new XMLHttpRequest();
	  
	  if ("withCredentials" in xhr) {
		xhr.open(method, url, true);
	  } 
	  else if (typeof XDomainRequest != "undefined") {
		xhr = new XDomainRequest();
		xhr.open(method, url);
	  } 
	  else {
		xhr = null;
	  }
	  

	  xhr.setRequestHeader("Content-Type", "application/json");
	  
	  // xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	  // xhr.setRequestHeader("Content-Type", "multipart/form-data");
	  
	  return xhr;
	};
	
	

	
	
	
