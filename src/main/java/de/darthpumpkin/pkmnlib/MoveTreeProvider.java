package de.darthpumpkin.pkmnlib;

/**
 * Interface for attaching the move tree to a {@link Move}. A MoveTreeProvider
 * is called after a move has been instanciated. The MoveTreeProvider takes the
 * effectId from the move and equips the move with the corresponding move tree.
 * E.g. the move tree can be read from XML files.
 * 
 * @author dominik
 * 
 */
public interface MoveTreeProvider {

	/**
	 * 
	 * @param move
	 *            the {@link Move} to which a move tree shall be attached.
	 */
	public void setMove(Move move);

	/**
	 * Attaches a binary tree of {@link AtomarMove}s to the {@link Move}, i.e.
	 * this method initializes the succcessElement and failureElement returned
	 * by {@link Move#getSuccessElement()} and {@link Move#getFailureElement()}.
	 * The move tree is chosen so that it matches the id returned by
	 * {@link Move#getId()}.
	 */
	public void attachMoveTree();

}
