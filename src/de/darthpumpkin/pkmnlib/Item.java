package de.darthpumpkin.pkmnlib;

import java.io.Serializable;

// TODO this class is probably unneccessary and could be replaced with a single int.
public class Item implements Serializable {

	private ItemContainer parent;

	public ItemContainer getParent() {
		return parent;
	}

	public void setParent(ItemContainer parent) {
		this.parent = parent;
	}
}
