package com.mace.runner.model;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mace.runner.IMazeManager;

/**
 * The Class Head.
 */
public class Head implements Runnable {
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(Head.class);
	
    /** The messages queue. */
    private final BlockingQueue<Message> messagesQueue;
    
    /** The answer queue. */
    private final BlockingQueue<Message> answerQueue;
    
    /** The maze manager. */
    private final IMazeManager mazeManager;

    /**
     * Instantiates a new head.
     *
     * @param messagesQueue the messages queue
     * @param answerQueue the answer queue
     * @param mazeManager the maze manager
     */
    public Head(final BlockingQueue<Message> messagesQueue, final BlockingQueue<Message> answerQueue, final IMazeManager mazeManager) {
        this.messagesQueue = messagesQueue;
        this.mazeManager = mazeManager;
        this.answerQueue = answerQueue;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
    	log.info("Head is prepare to think");

    	final Maze maze = mazeManager.getMap();
    	final List<Position> path = maze.compute(new Position(maze.getHeight()-1, maze.getWidth()-1), new Position(0, 0));
    	final Iterator<Position> pathIt = path.iterator();
    	log.info("	Hey got a path!");
    	try {
    		pathIt.next();
			while (pathIt.hasNext()) {
				final Position position = pathIt.next();
				log.info("Head say move to ({}, {})", position.getX(), position.getY());
				messagesQueue.add(new Message(position, false));
				Message message = answerQueue.take();
				if (message.getFinish() && mazeManager.isOut()) {
					log.info("Got it, we are out!");
					messagesQueue.add(new Message(null, true));
				}				
			}
		} catch (InterruptedException e) {
			log.error("I had been interrupted!");
		}
    }
}