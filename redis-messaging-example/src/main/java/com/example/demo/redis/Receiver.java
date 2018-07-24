package com.example.demo.redis;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Receiver {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
	
	//private CountDownLatch countDownLatch;
	
	@Autowired
	public Receiver(/*CountDownLatch countDownLatch*/) {
		//this.countDownLatch = countDownLatch;
	}
	
	public void receiveMessage(String message) {
		LOGGER.info("Received: {}", message);
		//countDownLatch.countDown();
	}

}
