package com.firstdata.ach.pwmb.web.client.util;

import java.util.Properties;

import org.springframework.core.env.Environment;

public class PropertyGetter {

	
	public static Properties getProperties(Environment env) {
		
		
		String url = env.getProperty("payeezy.url");
		int index = url.indexOf("v1/ach");
		if (index > -1) {
			url = url.substring(0, index);
		}
		
		String fdoaToken = env.getProperty("token");
		String apikey = env.getProperty("apikey");
		String secret = env.getProperty("apisecret");
		
        Properties props = new Properties();
        props.put("url", url);
        props.put("token", fdoaToken);
        props.put("pzsecret", secret);
        props.put("apikey", apikey);
        
        // in case running on first data network
        String proxyHost = env.getProperty("proxy.server");
        String proxyPort = env.getProperty("proxy.port");
        if (proxyHost != null && proxyPort != null) {        
        	props.put("proxyHost", proxyHost);
        	props.put("proxyPort", proxyPort);
        }
        
        return props;
		
	}
	
}
