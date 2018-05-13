package com.ptcent.carrier.carrierapp.util;

import java.util.concurrent.Semaphore;

public class Synchronizer {
	Semaphore sema;

	public Synchronizer() {
		sema = new Semaphore(0);
	}

	public void wakeup() {
		sema.release();
	}

	public void await() {
		try {
			sema.acquire();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
