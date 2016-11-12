package com.mace.runner;

import java.util.List;

import com.mace.runner.model.Maze;
import com.mace.runner.model.Position;

/**
 * The Interface IMazeManager.
 */
public interface IMazeManager {

	/**
	 * Gets the Maze map.
	 *
	 * @return the Maze representation
	 */
	Maze getMap();

	/**
	 * Move the runner to that position.
	 *
	 * @param position the position
	 * @return true if the movement has been successful
	 */
	Boolean move(Position position);

	/**
	 * Checks if runner is out.
	 *
	 * @return true if the runner is out of the Maze, false otherwise
	 */
	Boolean isOut();

	/**
	 * Gets the path to get out the Maze.
	 *
	 * @return the path as a list of {@link com.mace.runner.model.Position)
	 */
	List<Position> getPath();

}