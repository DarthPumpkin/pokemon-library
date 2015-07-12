package de.darthpumpkin.pkmnlib;


// TODO this class is probably unneccessary and could be replaced with a single int.
public class Item {

	private ItemContainer parent;

	public ItemContainer getParent() {
		return parent;
	}

	public void setParent(ItemContainer parent) {
		this.parent = parent;
	}
}
