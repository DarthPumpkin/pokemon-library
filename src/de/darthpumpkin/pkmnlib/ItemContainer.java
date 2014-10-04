package de.darthpumpkin.pkmnlib;

/**
 * everything that can hold an item must implement this interface
 * @author dominik
 *
 */
public interface ItemContainer {
	
	public void remove(Item item);
	public void attach(Item item);

}
