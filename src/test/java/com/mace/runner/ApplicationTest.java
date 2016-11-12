package com.mace.runner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.mace.runner.model.Head;
import com.mace.runner.model.Runner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MazeManager mazeManager;
	
	@Autowired
	private Head head;
	
	@Autowired
	private Runner runner;
	
	@Test
	public void contextLoads() {
		assertThat(restTemplate).isNotNull();
		assertThat(mazeManager).isNotNull();
		assertThat(head).isNotNull();
		assertThat(runner).isNotNull();
	}

}
