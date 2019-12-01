package org.mjjaenl.reactivetutorial.fluxandmonobackground;


import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;


public class FluxTransformTest {
	@Test
	public void test001ShouldCreateAndTransformFluxUsingMap() {
		System.out.println("\n\nTEST test001ShouldCreateAndTransformFluxUsingMap ******");
		List<String> names = Arrays.asList("Ana", "Pedro", "Trini", "Manu");
		Flux<String> stringFlux = Flux.fromIterable(names).log()
				.map(s -> s.toUpperCase()).log();
		StepVerifier.create(stringFlux)
			.expectNext("ANA", "PEDRO", "TRINI", "MANU")
			.verifyComplete();
	}
	
	@Test
	public void test002ShouldCreateAndTransformFluxUsingMap() {
		System.out.println("\n\nTEST test002ShouldCreateAndTransformFluxUsingMap ******");
		List<String> names = Arrays.asList("Ana", "Pedro", "Trini", "Manu");
		Flux<Integer> stringFlux = Flux.fromIterable(names).log()
				.map(s -> s.length()).log();
		StepVerifier.create(stringFlux)
			.expectNext(3, 5, 5, 4)
			.verifyComplete();
	}
	
	@Test
	public void test003ShouldCreateAndTransformFluxUsingMapAndRepeat() {
		System.out.println("\n\nTEST test003ShouldCreateAndTransformFluxUsingMapAndRepeat ******");
		List<String> names = Arrays.asList("Ana", "Pedro", "Trini", "Manu");
		Flux<Integer> stringFlux = Flux.fromIterable(names).log()
				.map(s -> s.length()).log()
				.repeat(1)
				.log();
		StepVerifier.create(stringFlux)
			.expectNext(3, 5, 5, 4)
			.expectNext(3, 5, 5, 4)
			.verifyComplete();
	}
	
	public void test004ShouldCreateAndTransformAndFilterFluxUsingMapAndRepeat() {
		System.out.println("\n\nTEST test004ShouldCreateAndTransformAndFilterFluxUsingMapAndRepeat ******");
		List<String> names = Arrays.asList("Ana", "Pedro", "Trini", "Manu");
		Flux<Integer> stringFlux = Flux.fromIterable(names).log()
				.map(s -> s.length()).log()
				.repeat(1)
				.log()
				.filter(s -> s == 3)
				.log();
		StepVerifier.create(stringFlux)
			.expectNext(3)
			.expectNext(3)
			.verifyComplete();
	}
	
	@Test
	public void test005ShouldCreateAndTransformFluxUsingFlatMap() {
		System.out.println("\n\nTEST test005ShouldCreateAndTransformFluxUsingFlatMap ******");
		List<String> names = Arrays.asList("A", "B", "C", "D", "E", "F");
		Flux<String> stringFlux = Flux.fromIterable(names).log()
				.flatMap(s -> {
					return Flux.fromIterable(convertToList(s));
				}).log(); //DB or external call that returns a flux since you're having a flux inside a flux
		StepVerifier.create(stringFlux)
			.expectNextCount(12)
			.verifyComplete();
	}
	
	private List<String> convertToList(String s) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Arrays.<String>asList(s, "newValue");
	}
}
