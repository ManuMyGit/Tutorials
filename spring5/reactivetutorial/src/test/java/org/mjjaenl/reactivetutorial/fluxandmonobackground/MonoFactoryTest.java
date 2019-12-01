package org.mjjaenl.reactivetutorial.fluxandmonobackground;


import java.util.function.Supplier;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MonoFactoryTest {
	@Test
	public void test001ShouldCreateMonoUsingJustOrEmpty() {
		System.out.println("TEST test001ShouldCreateMonoUsingJustOrEmpty ******");
		Mono<String> monoFlux = Mono.<String>justOrEmpty(null).log();
		StepVerifier.create(monoFlux)
			.verifyComplete();
	}
	
	@Test
	public void test002ShouldCreateMonoUsingSupplier() {
		System.out.println("\n\nTEST test002ShouldCreateMonoUsingSupplier ******");
		Supplier<String> stringSupplier = () -> "Manu";
		
		Mono<String> monoFlux = Mono.fromSupplier(stringSupplier).log();
		StepVerifier.create(monoFlux)
			.expectNext(stringSupplier.get())
			.verifyComplete();
	}
}
