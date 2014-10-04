package de.darthpumpkin.pkmnlib;

import java.io.Serializable;

public class Item implements Serializable{

	private ItemContainer parent;

	public ItemContainer getParent() {
		return parent;
	}
	public void setParent(ItemContainer parent) {
		this.parent = parent;
	}
}
