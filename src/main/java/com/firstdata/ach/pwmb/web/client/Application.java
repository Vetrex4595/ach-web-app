package com.firstdata.ach.pwmb.web.client;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

	@Autowired
	Environment env;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate t = new RestTemplate();

		System.out.println("Use proxy is: " + true);
		String json = System.getenv("VCAP_APPLICATION");
		if (json != null) {
			// don't want to use it in cloud foundry
			return t;
		}

		String proxyServer = env.getProperty("proxy.server");
		int proxyPort = Integer.parseInt(env.getProperty("proxy.port"));

		SSLContext sslContext = null;

		try {
			sslContext = SSLContext.getDefault();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(
				sslContext, new String[] { "TLSv1.2" }, null,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		HttpHost proxy = null;
		String useProxy = env.getProperty("useProxy");
		if (useProxy != null && useProxy.equalsIgnoreCase("true")) {
			proxy = new HttpHost(proxyServer, proxyPort);
		}

		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLSocketFactory(csf).setProxy(proxy).build();

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

		requestFactory.setHttpClient(httpClient);

		t.setRequestFactory(requestFactory);

		return t;
	}

}
