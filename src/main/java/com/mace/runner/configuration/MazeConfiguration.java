package com.mace.runner.configuration;

import com.mace.runner.IMazeManager;
import com.mace.runner.MazeManager;
import com.mace.runner.model.Head;
import com.mace.runner.model.Message;
import com.mace.runner.model.Runner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@Configuration
public class MazeConfiguration {

	private final BlockingQueue<Message> messagesQueue = new LinkedBlockingDeque<>();
	private final BlockingQueue<Message> answerQueue = new LinkedBlockingDeque<>();

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public IMazeManager createManager(RestTemplate restTemplate) {
		return new MazeManager(restTemplate);
	}

	@Bean
	public Head createHead(final IMazeManager mazeManager) {
		return new Head(messagesQueue, answerQueue, mazeManager);
	}

	@Bean
	public Runner createRunner(final IMazeManager mazeManager) {
		return new Runner(messagesQueue, answerQueue, mazeManager);
	}
}
