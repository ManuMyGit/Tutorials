package org.mjjaenl.springdi.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mjjaenl.springdi.controller.ConstructorInjectedController;
import org.mjjaenl.springdi.services.GreetingServiceImpl;

public class ConstructorInjectedControllerTest {
	private ConstructorInjectedController constructorInjectedController;
	
	@Before
	public void setUp() throws Exception {
		this.constructorInjectedController = new ConstructorInjectedController(new GreetingServiceImpl());
	}

	@Test
	public void test() {
		assertEquals(GreetingServiceImpl.HELLO, constructorInjectedController.sayHello());
	}
}
