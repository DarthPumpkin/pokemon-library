package de.darthpumpkin.pkmnlib;

/**
 * Thrown to indicate that a {@link Team} has been expanded above its allowed
 * maximum size.
 * 
 * @author dominik
 * 
 */
public class MaximumSizeExceededException extends RuntimeException {

	private final Integer maxSize;
	private final Integer previousSize;
	private final Integer newElements;

	/**
	 * Constructs a MaximumSizeExceededException with a message telling what was
	 * the allowed maximum size.
	 * 
	 * @param maxSize
	 *            the exceeded maximum size.
	 */
	public MaximumSizeExceededException(Integer maxSize) {
		super("Maximum size of " + maxSize + " exceeded");
		this.maxSize = maxSize;
		this.previousSize = null;
		this.newElements = null;
	}

	/**
	 * Constructs a MaximumSizeExceededException with a message telling what was
	 * the allowed maximum size and how many elements were added to the team as
	 * well as how large the team was before the addition.
	 * 
	 * @param maxSize
	 *            the exceeded maximum size.
	 * @param previousSize
	 *            the size before the insertion.
	 * @param newElements
	 *            the amount of new elements added to the team.
	 */
	public MaximumSizeExceededException(Integer maxSize, Integer previousSize,
			Integer newElements) {
		super("Maximum size of " + maxSize + " exceeded when trying to add "
				+ newElements + (newElements == 1 ? " element" : " elements")
				+ " to a team of size " + previousSize);
		this.maxSize = maxSize;
		this.previousSize = previousSize;
		this.newElements = newElements;
	}

	/**
	 * Returns the maximum size of the Collection whose size was exceeded.
	 * 
	 * @return the maximum size
	 */
	public Integer maxSize() {
		return this.maxSize;
	}

	/**
	 * Returns the amount of elements in the Collection before the attempt to
	 * perform the manipulation that caused this Exception.
	 * 
	 * @return the amount of elements in the Collection or null if it has not
	 *         been set
	 */
	public Integer previousSize() {
		return this.previousSize;
	}

	/**
	 * Returns the amount of elements that were attempted to insert into the
	 * Collection that cauesed this Exception.
	 * 
	 * @return the amount of elements attempted to insert or null if it has not
	 *         been set
	 */
	public Integer newElements() {
		return this.newElements;
	}
}
