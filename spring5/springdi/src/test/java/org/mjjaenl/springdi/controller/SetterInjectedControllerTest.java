package org.mjjaenl.springdi.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mjjaenl.springdi.services.GreetingServiceImpl;

public class SetterInjectedControllerTest {
	private SetterInjectedController setterInjectedController;
	
	@Before
	public void setUp() throws Exception {
		this.setterInjectedController = new SetterInjectedController();
		this.setterInjectedController.setGreetingService(new GreetingServiceImpl());
	}

	@Test
	public void test() {
		assertEquals(GreetingServiceImpl.HELLO, setterInjectedController.sayHello());
	}
}
