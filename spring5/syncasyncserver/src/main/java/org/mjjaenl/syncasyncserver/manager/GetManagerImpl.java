package org.mjjaenl.syncasyncserver.manager;

import org.springframework.stereotype.Component;

@Component
public class GetManagerImpl implements GetManager {
	public void doNothingAndWait() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
