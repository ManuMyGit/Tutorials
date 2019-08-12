package org.mjjaenl.springdi.services;

import org.springframework.stereotype.Repository;

@Repository
public class GreetingRepositoryImpl implements GreetingRepository {

	@Override
	public String getEnglishGreeting() {
		return "Hello - English and default Profile Primary Greeting Service";
	}

	@Override
	public String getSpanishGreeting() {
		return "Hola - Primary Spaninsh Greeting Service using profiles";
	}

}
