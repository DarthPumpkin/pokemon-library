package de.darthpumpkin.pkmnlib;

/**
 * everything that can hold an item must implement this interface
 * 
 * @author dominik
 * 
 */
public interface ItemContainer {

	public void remove(Item item);

	public void attach(Item item);

	public boolean holdsItem(Item item);

	/**
	 * checks whether this ItemContainer holds an item corresponding to the
	 * specified id.
	 * 
	 * @param id
	 *            The id to be checked
	 * @return true if specified item is held
	 */
	public boolean holdsItemInstanceOf(int id);

}
