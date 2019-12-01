package org.mjjaenl.reactivetutorial.utils;

import org.springframework.http.MediaType;

public class MyMediaType extends MediaType {	
	private static final long serialVersionUID = 6182800755172147612L;
	
	public static final MediaType APPLICATION_STREAM_JSON_UTF8;
	public static final String APPLICATION_STREAM_JSON_UTF8_VALUE = "application/stream+json;charset=UTF-8";
	
	static {
		APPLICATION_STREAM_JSON_UTF8 = valueOf(APPLICATION_STREAM_JSON_UTF8_VALUE);
	}
	
	public MyMediaType(String type) {
		super(type);
	}
}
