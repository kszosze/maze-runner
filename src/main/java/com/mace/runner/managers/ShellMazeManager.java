package com.mace.runner.managers;

import com.mace.runner.model.Head;
import com.mace.runner.model.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@ShellComponent
public class ShellMazeManager {

	private Head head;

	private Runner runner;

	public ShellMazeManager(Head head, Runner runner) {
		this.head = head;
		this.runner = runner;
	}

	@ShellMethod("Start Running.")
	public void startRunning() throws InterruptedException {

			final ExecutorService taskExecutor = Executors.newCachedThreadPool();
			taskExecutor.execute(runner);
			taskExecutor.execute(head);
			taskExecutor.shutdown();
			taskExecutor.awaitTermination(1, TimeUnit.MINUTES);
	}
}
