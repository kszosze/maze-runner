package com.mace.runner.model;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mace.runner.managers.IMazeManager;

/**
 * The Class Runner.
 */
public class Runner implements Runnable {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(Runner.class);

  /** The messages queue. */
  private final BlockingQueue<Message> messagesQueue;

  /** The answer queue. */
  private final BlockingQueue<Message> answerQueue;

  /** The maze manager. */
  private final IMazeManager mazeManager;

  /**
   * Instantiates a new runner.
   *
   * @param messagesQueue the messages queue
   * @param answerQueue the answer queue
   * @param mazeManager the maze manager
   */
  public Runner(final BlockingQueue<Message> messagesQueue, final BlockingQueue<Message> answerQueue, IMazeManager mazeManager) {
      this.messagesQueue = messagesQueue;
      this.mazeManager = mazeManager;
      this.answerQueue = answerQueue;
  }

  /* (non-Javadoc)
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run() {

    Boolean isOut = false;
    log.info("Runner is ready to run faster!");
    try {
      while (!isOut) {
        final Message message = messagesQueue.take();
        log.info("  Yes my Lord! Got your command! {}", message);
        isOut = message.getFinish();
        if (!isOut) {
          log.info(" My Lord! I'm moving to ({}, {})", message.getPoint().getX(), message.getPoint().getY());
					final Boolean result = mazeManager.move(message.getPoint());
					answerQueue.add(new Message(message.getPoint(), result));
					if (!result) {
						log.error("Still in the Maze ");
					} else {
						log.info(" My Lord! We are free!!");
					}
        }
      }
      log.info("Freeeeee!!!");
    } catch (final InterruptedException e) {
      log.info("I had been interrupted");
    }
  }
}