package com.mace.runner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.mace.runner.model.Maze;
import com.mace.runner.model.Position;

@RunWith(MockitoJUnitRunner.class)
public class MazeTest {
	
	private Maze maze;
	
	private final static List<String> rawMaze = Arrays.asList("O O O X O O X","X X O X O X X","X O O X O X X","X O X X O O X","X O O O O X X","X O X X O O O","X X X X X X O");
 
	@Test
	public void createMazeTest() {
		maze = new Maze(rawMaze);
		assertThat(maze.getHeight()).isEqualTo(7);
		assertThat(maze.getWidth()).isEqualTo(7);
	}

	@Test
	public void calculatePathTest() {
		maze = new Maze(rawMaze);
		final Position initPosition = new Position(6,6);
		final Position endPosition = new Position(0,0);
		final List<Position> path = maze.compute(new Position(6,6), new Position(0,0));
		
		assertThat(path.size()).isEqualTo(15);
		assertThat(path.get(0)).isEqualTo(initPosition);
		assertThat(path.get(14)).isEqualTo(endPosition);
	}
}
