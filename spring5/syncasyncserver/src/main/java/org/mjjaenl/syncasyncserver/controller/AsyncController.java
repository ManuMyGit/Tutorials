package org.mjjaenl.syncasyncserver.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mjjaenl.syncasyncserver.manager.GetManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController {
	private static final Logger logger = LogManager.getLogger(SyncController.class);
	@Autowired
	private Executor executor;

	@Autowired
	private GetManager getManager;
	
	@GetMapping("/async")
	public CompletableFuture<ResponseEntity<String>> getController() {
		long init = System.currentTimeMillis();
		logger.info("Beginning: " + init);
		CompletableFuture<ResponseEntity<String>> response = CompletableFuture.supplyAsync(() -> {
			getManager.doNothingAndWait();
			return ResponseEntity.ok("");
		}, executor);
		long end = System.currentTimeMillis();
		logger.info("Time needed: " + (end - init));
		return response;
	}
}
