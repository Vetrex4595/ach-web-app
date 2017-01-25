package com.firstdata.ach.pwmb.web.client;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.firstdata.ach.pwmb.web.client.util.JsonUtil;

@Controller
public class AppController {

	@RequestMapping("/")
	public String start(Model model, HttpServletRequest request) {

		
		// in case we're running in cloud foundry
		String name = null;
		String json = System.getenv("VCAP_APPLICATION");
		if (json != null) {
			
			System.out.println("Json is: " + json);
			
			HashMap map = JsonUtil.fromJson(json, HashMap.class);
			List urls = (List)map.get("application_uris");
			if (urls != null && urls.size() > 0) {
				name = (String)urls.get(0);
				System.out.println("Name is: " + name);
				// name = "https://" + name;
				name = request.getScheme() + "://" + name;
			} else {
				System.out.println("Defaulting to connect-pay-demo");
				name = "https://connect-pay-demo.cfapps.io";
			}
			
		} else {
			
			System.out.println("Not running in cloud foundry - using server name");
			name = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
			
		}
		
		
		request.setAttribute("name", name);
		
		return "pwmb_merchant";
	}

}