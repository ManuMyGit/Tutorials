package org.mjjaenl.springdi.services;

import org.springframework.stereotype.Service;

@Service
public class ConstructorGreetingServiceImpl implements GreetingService {
	public static final String HELLO = "Hello everyone!!!";
	
	@Override
	public String sayGreeting() {
		return "Hello - I was injected via constructor";
	}
}
