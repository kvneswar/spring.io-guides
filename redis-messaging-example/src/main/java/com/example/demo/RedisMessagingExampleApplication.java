package com.example.demo;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.example.demo.redis.Receiver;

@SpringBootApplication
public class RedisMessagingExampleApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisMessagingExampleApplication.class);

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext applicationContext = SpringApplication.run(RedisMessagingExampleApplication.class, args);
		
		StringRedisTemplate template = applicationContext.getBean(StringRedisTemplate.class);
		//CountDownLatch latch = applicationContext.getBean(CountDownLatch.class);
		
		template.convertAndSend("chat", "Hello from Redis!");
		template.convertAndSend("chat", "Hello from Eswar!!!");
		
		//latch.await();
		Thread.sleep(100000);

		System.exit(0);
		
}
	
	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter messageListenerAdapter) {
		RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
		redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
		redisMessageListenerContainer.addMessageListener(messageListenerAdapter, new PatternTopic("chat"));
		return redisMessageListenerContainer;
	}
	
	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Bean
	Receiver receiver(/*CountDownLatch latch*/) {
		return new Receiver(/*latch*/);
	}

	/*@Bean
	CountDownLatch latch() {
		return new CountDownLatch(1);
	}*/
	
	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}
}
