package com.mace.runner;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.mace.runner.model.Head;
import com.mace.runner.model.Message;
import com.mace.runner.model.Runner;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private final BlockingQueue<Message> messagesQueue = new LinkedBlockingDeque<>();
	private final BlockingQueue<Message> answerQueue = new LinkedBlockingDeque<>();

	@Autowired
	private Head head;

	@Autowired
	private Runner runner;

	public static void main(String args[]) {
		SpringApplication.run(Application.class);

	}

	@Override
	public void run(String... args) throws Exception {
		final ExecutorService taskExecutor = Executors.newCachedThreadPool();
		taskExecutor.execute(runner);
		taskExecutor.execute(head);
		taskExecutor.shutdown();
		taskExecutor.awaitTermination(1, TimeUnit.MINUTES);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public IMazeManager createManager(RestTemplate restTemplate) throws Exception {
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