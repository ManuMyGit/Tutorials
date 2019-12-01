package org.mjjaenl.reactivetutorial.fluxandmonobackground;

import java.time.Duration;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ColdAndHotPublisherTest {
	@Test
	public void test001ColdPublisherTest() throws InterruptedException {
		System.out.println("TEST test001ColdPublisherTest ******");
		Flux<String> flux = Flux.just("A", "B", "C", "D", "E", "F")
				.delayElements(Duration.ofSeconds(1)).log();
		//Emits the value from the beginning
		flux.subscribe(s -> System.out.println("Subscriber 1: " + s));
		Thread.sleep(2000);
		//Emits the value from the beginning
		flux.subscribe(s -> System.out.println("Subscriber 2: " + s));
		//Every time we subscribe to the publisher, the publisher will emit the value from the beginning.
		//This is called cold publisher
		Thread.sleep(10000);
	}
	
	@Test
	public void test002HotPublisherTest() throws InterruptedException {
		//Basically, hot subscriber is the opposite to cold subscriber.
		//In the moment there is a new subscription, the subscriber will get the last value emitted to the 1st subscriber.
		//This second subscriber won't get the values from the beginning, the publisher won't start sending the values from the beginning with every subscriber.
		System.out.println("\n\nTEST test002HotPublisherTest ******");
		Flux<String> flux = Flux.just("A", "B", "C", "D", "E", "F")
				.delayElements(Duration.ofSeconds(1));
		ConnectableFlux<String> connectableFlux = flux.publish();
		connectableFlux.connect();
		connectableFlux.subscribe(s -> System.out.println("Subscriber 1: " + s));
		Thread.sleep(3000);
		connectableFlux.subscribe(s -> System.out.println("Subscriber 2: " + s));
		Thread.sleep(4000);
	}
}
