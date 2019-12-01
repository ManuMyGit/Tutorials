package org.mjjaenl.reactivetutorial.fluxandmonobackground;

import java.time.Duration;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FluxCombineTest {
	@Test
	public void test001CombineFluxUsingMerge() {
		System.out.println("TEST test001CombineFluxUsingMerge ******");
		Flux<String> flux1 = Flux.just("A", "B", "C");
		Flux<String> flux2 = Flux.just("D", "E", "F");
		Flux<String> mergedFlux = Flux.merge(flux1, flux2);
		
		StepVerifier.create(mergedFlux.log())
			.expectSubscription()
			.expectNext("A", "B", "C", "D", "E", "F")
			.verifyComplete();
	}
	
	@Test
	public void test002CombineFluxUsingMergeWithDelay() {
		System.out.println("\n\nTEST test001CombineFluxUsingMergeWithDelay ******");
		Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2 = Flux.just("D", "E", "F");
		Flux<String> mergedFlux = Flux.merge(flux1, flux2);
		
		StepVerifier.create(mergedFlux.log())
			.expectSubscription()
			.expectNextCount(6)
			//.expectNext("A", "B", "C", "D", "E", "F") It fails because the events/streams take 1 second to arribe for flux1
			.verifyComplete();
	}
	
	@Test
	public void test003CombineFluxUsingConcat() {
		System.out.println("\n\nTEST test003CombineFluxUsingConcat ******");
		Flux<String> flux1 = Flux.just("A", "B", "C");
		Flux<String> flux2 = Flux.just("D", "E", "F");
		Flux<String> mergedFlux = Flux.concat(flux1, flux2);
		
		StepVerifier.create(mergedFlux.log())
			.expectSubscription()
			.expectNext("A", "B", "C", "D", "E", "F")
			.verifyComplete();
	}
	
	@Test
	public void test004CombineFluxUsingConcatWithDelay() {
		System.out.println("\n\nTEST test004CombineFluxUsingConcatWithDelay ******");
		Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2 = Flux.just("D", "E", "F");
		
		//In contrast to merge, concat waits for the arrival of the flux1 to concat it with flux2. Concat ensures the order. Flux2 will start emiting the values when flux1 finishes.
		Flux<String> mergedFlux = Flux.concat(flux1, flux2);
		
		StepVerifier.create(mergedFlux.log())
			.expectSubscription()
			.expectNext("A", "B", "C", "D", "E", "F")
			.verifyComplete();
	}
	
	@Test
	public void test005CombineFluxUsingZip() {
		System.out.println("\n\nTEST test005CombineFluxUsingZip ******");
		Flux<String> flux1 = Flux.just("A", "B", "C");
		Flux<String> flux2 = Flux.just("D", "E", "F");
		Flux<String> mergedFlux = Flux.zip(flux1, flux2, (t1, t2) -> {
			return t1.concat(t2);
		}); //A, D: B, E: C: F
		
		StepVerifier.create(mergedFlux.log())
			.expectSubscription()
			.expectNext("AD", "BE", "CF")
			.verifyComplete();
	}
}
