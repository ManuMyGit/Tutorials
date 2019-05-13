package org.mjjaenl.springdi.services;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService {
	public static final String HELLO = "Hello everyone!!!";
	
	@Override
	public String sayGreeting() {
		return HELLO;
	}
}
