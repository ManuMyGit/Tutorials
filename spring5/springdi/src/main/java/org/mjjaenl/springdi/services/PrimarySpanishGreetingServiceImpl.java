package org.mjjaenl.springdi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Primary
@Profile("es")
public class PrimarySpanishGreetingServiceImpl implements GreetingService {
	private GreetingRepository greetingRepository;
	
	@Autowired
	public PrimarySpanishGreetingServiceImpl(GreetingRepository greetingRepository) {
		super();
		this.greetingRepository = greetingRepository;
	}
	
	@Override
	public String sayGreeting() {
		return greetingRepository.getSpanishGreeting();
	}
}
