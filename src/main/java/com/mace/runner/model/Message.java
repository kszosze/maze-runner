package com.mace.runner.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Class Message.
 */
public class Message implements Serializable {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6286369338777883038L;

	/** The point. */
	private Position point;

    /** The finish. */
    private Boolean finish;

    /**
     * Instantiates a new message.
     *
     * @param point the point
     * @param finish the finish
     */
    public Message(final Position point, final Boolean finish) {
        this.point = point;
        this.finish = finish;
    }

    /**
     * Gets the point.
     *
     * @return the point
     */
    public Position getPoint() {
        return point;
    }

    /**
     * Gets the finish.
     *
     * @return the finish
     */
    public Boolean getFinish() {
        return finish;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Message message = (Message) o;
        return Objects.equals(point, message.point) &&
                Objects.equals(finish, message.finish);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(point, finish);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Message{" +
                "point=" + point +
                ", finish=" + finish +
                '}';
    }
}
