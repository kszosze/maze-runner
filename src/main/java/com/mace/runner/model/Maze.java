package com.mace.runner.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

/**
 * The Class Maze.
 */
public class Maze {

	/** The Constant OPEN. */
	private static final String OPEN = "O";
	
	/** The maze. */
	private final boolean[][] maze;

	/** The visited. */
	private boolean[][] visited;
	
	/** The parents. */
	private final Map<Position, Position> parents;

	/**
	 * Instantiates a new maze.
	 *
	 * @param maze the maze
	 */
	public Maze(final List<String> maze) {
		parents = new HashMap<>();
		final int numberOfRows = maze.size();

		if (numberOfRows == 0) {
			throw new IllegalArgumentException("The input maze is empty.");
		}

		int numberOfColumns = 0;

		numberOfColumns = maze.get(0).split(" ").length;

		this.maze = new boolean[numberOfRows][numberOfColumns];
		this.visited = new boolean[numberOfRows][numberOfColumns];
		for (int i = 0; i < maze.size(); i++) {
			final String[] row = maze.get(i).split(" ");
			for (int j = 0; j < row.length; j++) {
				this.maze[i][j] = OPEN.equals(row[j]);
				this.visited[i][j] = Boolean.FALSE;
			}
		}
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return maze[0].length;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return maze.length;
	}

	/**
	 * Cell is free.
	 *
	 * @param p the p
	 * @return true, if successful
	 */
	public boolean cellIsFree(final Position p) {
		return cellIsFree(p.getX(), p.getY());
	}

	/**
	 * Cell is within maze.
	 *
	 * @param p the p
	 * @return true, if successful
	 */
	public boolean cellIsWithinMaze(final Position p) {
		return p.getX() >= 0 && p.getX() < getWidth() && p.getY() >= 0 && p.getY() < getHeight();
	}

	/**
	 * Cell is traversible.
	 *
	 * @param p the p
	 * @return true, if successful
	 */
	public boolean cellIsTraversible(final Position p) {
		return cellIsWithinMaze(p) && cellIsFree(p);
	}

	/**
	 * Cell is free.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if successful
	 */
	public boolean cellIsFree(final int x, final int y) {
		checkXCoordinate(x);
		checkYCoordinate(y);
		return maze[y][x] != Boolean.FALSE;
	}

	/**
	 * Check X coordinate.
	 *
	 * @param x the x
	 */
	private void checkXCoordinate(final int x) {
		if (x < 0) {
			throw new IndexOutOfBoundsException("The x-coordinate is negative: " + x + ".");
		}

		if (x >= maze[0].length) {
			throw new IndexOutOfBoundsException("The x-coordinate is too large (" + x
					+ "). The amount of columns in this maze is " + maze[0].length + ".");
		}
	}

	/**
	 * Check Y coordinate.
	 *
	 * @param y the y
	 */
	private void checkYCoordinate(final int y) {
		if (y < 0) {
			throw new IndexOutOfBoundsException("The y-coordinate is negative: " + y + ".");
		}

		if (y >= maze.length) {
			throw new IndexOutOfBoundsException("The y-coordinate is too large (" + y
					+ "). The amount of rows in this maze is " + maze.length + ".");
		}
	}

	/**
	 * Compute.
	 *
	 * @param source the source
	 * @param exit the exit
	 * @return the list
	 */
	public List<Position> compute(final Position source, final Position exit) {
		final Queue<Position> queue = new ArrayDeque<>();
		//final Map<Position, Integer> distances = new HashMap<>();
		parents.put(source, null);
		queue.add(source);
	//	distances.put(source, 0);

		while (!queue.isEmpty()) {
			final Position current = queue.remove();
			if (current.equals(exit)) {
				return constructPath(exit, source);
			}

			for (final Position child : generateChildren(current)) {
				if (!parents.containsKey(child)) {
					parents.put(child, current);
					queue.add(child);
					visited[child.getX()][child.getY()] = Boolean.TRUE;
				}
			}
		}
		return null;
	}

	/**
	 * Construct path.
	 *
	 * @param exit the exit
	 * @param init the init
	 * @return the list
	 */
	private List<Position> constructPath(final Position exit, final Position init) {
		Position current = exit;
		final List<Position> path = new ArrayList<>();

		while (Objects.nonNull(current)) {
			path.add(current);
			current = parents.get(current);
		}

		Collections.reverse(path);
		return path;
	}

	/**
	 * Generate children.
	 *
	 * @param current the current
	 * @return the iterable
	 */
	private Iterable<Position> generateChildren(final Position current) {
		final Position north = new Position(current.getX(), current.getY() - 1);
		final Position south = new Position(current.getX(), current.getY() + 1);
		final Position west = new Position(current.getX() - 1, current.getY());
		final Position east = new Position(current.getX() + 1, current.getY());

		final List<Position> childList = new ArrayList<>(4);

		if (cellIsTraversible(north) && !hasBeenVisited(north)) {
			childList.add(north);
		}

		if (cellIsTraversible(south) && !hasBeenVisited(south)) {
			childList.add(south);
		}

		if (cellIsTraversible(west) && !hasBeenVisited(west)) {
			childList.add(west);
		}

		if (cellIsTraversible(east) && !hasBeenVisited(east)) {
			childList.add(east);
		}

		return childList;
	}

	/**
	 * Checks for been visited.
	 *
	 * @param node the node
	 * @return the boolean
	 */
	private Boolean hasBeenVisited(final Position node) {
		return visited[node.getX()][node.getY()];
	}

}
