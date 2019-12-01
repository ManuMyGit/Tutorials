package org.mjjaenl.reactivetutorial.fluxandmonobackground;

import java.util.Arrays;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FluxFactoryTest {
	@Test
	public void test001ShouldCreateFluxUsingIterable() {
		System.out.println("TEST test001ShouldCreateFluxUsingIterable ******");
		List<String> names = Arrays.asList("Ana", "Pedro", "Trini", "Manu");
		Flux<String> stringFlux = Flux.fromIterable(names).log();
		StepVerifier.create(stringFlux)
			.expectNext("Ana", "Pedro", "Trini", "Manu")
			.verifyComplete();
	}
	
	@Test
	public void test002ShouldCreateFluxUsingArray() {
		System.out.println("\n\nTEST test002ShouldCreateFluxUsingArray ******");
		String[] names = new String[]{"Ana", "Pedro", "Trini", "Manu"};
		Flux<String> stringFlux = Flux.fromArray(names).log();
		StepVerifier.create(stringFlux)
			.expectNext("Ana", "Pedro", "Trini", "Manu")
			.verifyComplete();
	}
	
	@Test
	public void test003ShouldCreateFluxUsingStream() {
		System.out.println("\n\nTEST test003ShouldCreateFluxUsingStream ******");
		List<String> names = Arrays.asList("Ana", "Pedro", "Trini", "Manu");
		Flux<String> stringFlux = Flux.fromStream(names.stream()).log();
		StepVerifier.create(stringFlux)
			.expectNext("Ana", "Pedro", "Trini", "Manu")
			.verifyComplete();
	}
	
	@Test
	public void test004ShouldCreateFluxUsingRange() {
		System.out.println("\n\nTEST test004ShouldCreateFluxUsingRange ******");
		Flux<Integer> rangeFlux = Flux.range(1, 5).log();
		StepVerifier.create(rangeFlux)
			.expectNext(1, 2, 3, 4, 5)
			.verifyComplete();
	}
}
