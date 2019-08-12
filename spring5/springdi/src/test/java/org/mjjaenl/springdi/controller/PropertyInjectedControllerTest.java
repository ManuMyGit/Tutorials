package org.mjjaenl.springdi.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mjjaenl.springdi.services.GreetingServiceImpl;

public class PropertyInjectedControllerTest {
	private PropertyInjectedController propertyInjectedController;
	
	@Before
	public void setUp() throws Exception {
		this.propertyInjectedController = new PropertyInjectedController();
		this.propertyInjectedController.greetingServiceImpl = new GreetingServiceImpl();
	}

	@Test
	public void test() {
		assertEquals(GreetingServiceImpl.HELLO, propertyInjectedController.sayHello());
	}

}
