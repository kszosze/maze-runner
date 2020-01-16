package com.mace.runner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.mace.runner.model.Maze;
import com.mace.runner.model.Position;

@RunWith(MockitoJUnitRunner.class)
public class MazeManagerTest {

	@Mock
	private RestTemplate restTemplate;

	private IMazeManager mazeManager;
	
	@Before
	public void setup() {
		mazeManager = new MazeManager(restTemplate);
	}
	
	@Test
	public void getMapTest() {
		final ResponseEntity<String> result = new ResponseEntity<>("O O O X O O X\nX X O X O X X\nX O O X O X X\nX O X X O O X\nX O O O O X X\nX O X X O O O\nX X X X X X O",
				HttpStatus.OK);
		when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class),any(Class.class))).thenReturn(result);
		Maze maze = mazeManager.getMap();
		assertThat(maze).isNotNull();
		assertThat(maze.getHeight()).isEqualTo(7);
		assertThat(maze.getWidth()).isEqualTo(7);
		assertThat(maze.compute(new Position(6,6), new Position(0,0)).size()).isEqualTo(15);
	}
	
	@Test
	public void moveTest() {
		final ResponseEntity<String> result = new ResponseEntity<>(HttpStatus.OK);
		when(restTemplate.postForEntity(any(String.class), any(Position.class), any(Class.class))).thenReturn(result);
		assertThat(mazeManager.move(new Position())).isEqualTo(Boolean.TRUE);
	}
	
	@Test
	public void isOutTest() {
		final ResponseEntity<Boolean> result = new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
		when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenReturn(result);
		assertThat(mazeManager.isOut()).isEqualTo(Boolean.TRUE);
	}
	
	@Test
	public void getPathTest() {
		final Position point1 = new Position();
		final Position point2 = new Position();
		
		when(restTemplate.getForObject(any(String.class), any(Class.class))).thenReturn(new Position[]{point1, point2});
		assertThat(mazeManager.getPath()).isEqualTo(Arrays.asList(point1, point2));
	}
}
