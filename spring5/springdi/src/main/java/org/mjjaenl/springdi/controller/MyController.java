package org.mjjaenl.springdi.controller;

import org.mjjaenl.springdi.services.GreetingService;
import org.springframework.stereotype.Controller;

@Controller
public class MyController {
	private GreetingService greetingService;
	
	public MyController(GreetingService greetingService) {
		super();
		this.greetingService = greetingService;
	}

	public String hello() {
		System.out.println("Hello!!!!");
		return greetingService.sayGreeting();
	}
}
