package com.firstdata.ach.pwmb.web.client;

import java.util.List;

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
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.ImmutableList;

@SpringBootApplication
public class Application {

	@Autowired
	Environment env;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate t = new RestTemplate(converterList());

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
	
	

	@SuppressWarnings("unchecked")
	private List<HttpMessageConverter<?>> converterList() {
		return ImmutableList.<HttpMessageConverter<?>>of(
				formHttpMessageConverter(),
				byteArrayHttpMessageConverter(),
				stringHttpMessageConverter(),
				resourceHttpMessageConverter(),
				sourceHttpMessageConverter(),
				allEncompassingFormHttpMessageConverter(),
				jaxb2RootElementHttpMessageConverter(),
				mappingJackson2HttpMessageConverter()
		);
	}

	@Bean
	public SourceHttpMessageConverter sourceHttpMessageConverter() {
		return new SourceHttpMessageConverter();
	}
	
	@Bean
	public FormHttpMessageConverter formHttpMessageConverter() {
		return new FormHttpMessageConverter();
	}

	@Bean
	public AllEncompassingFormHttpMessageConverter allEncompassingFormHttpMessageConverter() {
		return new AllEncompassingFormHttpMessageConverter();
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		return new MappingJackson2HttpMessageConverter();
	}

	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter c = new StringHttpMessageConverter();
		c.setWriteAcceptCharset(false);
		return c;
	}

	@Bean
	public Jaxb2RootElementHttpMessageConverter jaxb2RootElementHttpMessageConverter() {
		return new Jaxb2RootElementHttpMessageConverter();
	}

	@Bean
	public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
		return new ByteArrayHttpMessageConverter();
	}

	@Bean
	ResourceHttpMessageConverter resourceHttpMessageConverter() {
		return new ResourceHttpMessageConverter();
	}
	
	

}
