package com.mace.runner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class Position.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Position {

    /** The x. */
    private int x;
    
    /** The y. */
    private int y;

    
    
    /**
     * Instantiates a new position.
     */
    public Position() {
    	this.x = 0;
		this.y = 0;
	}

	/**
	 * Instantiates a new position.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the value of the x property.
	 *
	 * @return the x
	 */
    public int getX() {
        return x;
    }

    /**
     * Sets the value of the x property.
     *
     * @param value the new x
     */
    public void setX(int value) {
        this.x = value;
    }

    /**
     * Gets the value of the y property.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the value of the y property.
     *
     * @param value the new y
     */
    public void setY(int value) {
        this.y = value;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}

}
