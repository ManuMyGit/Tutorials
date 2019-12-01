package org.mjjaenl.reactivetutorial.fluxandmonobackground;

import java.util.Arrays;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FluxFilteringTest {
	@Test
	public void test001ShouldCreateAndFilterFluxUsingIterable() {
		System.out.println("TEST test001ShouldCreateFluxUsingIterable ******");
		List<String> names = Arrays.asList("Ana", "Pedro", "Trini", "Manu");
		Flux<String> stringFlux = Flux.fromIterable(names).log()
				.filter(e -> e.contains("A")).log();
		StepVerifier.create(stringFlux)
			.expectNext("Ana")
			.verifyComplete();
	}
}
