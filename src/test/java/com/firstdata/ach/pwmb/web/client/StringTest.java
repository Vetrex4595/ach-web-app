package com.firstdata.ach.pwmb.web.client;

import org.junit.Test;

public class StringTest {

	@Test
	public void test() {
		
		
		byte[] arr = new byte[]{123, 34, 99, 111, 100, 101, 34, 58, 34, 52, 48, 49, 34, 44, 32, 34, 109, 101, 115, 115, 97, 103, 101, 34, 58, 34, 73, 110, 118, 97, 108, 105, 100, 32, 65, 112, 105, 32, 75, 101, 121, 34, 125};
		
		String str = new String(arr);
		
		System.out.println(str);
	}
	
}
