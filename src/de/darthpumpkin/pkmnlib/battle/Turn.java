package de.darthpumpkin.pkmnlib.battle;

import java.io.Serializable;

import de.darthpumpkin.pkmnlib.Item;
import de.darthpumpkin.pkmnlib.Move;

@SuppressWarnings("serial")
public class Turn implements Serializable {
	private TurnOption option;
	private Move move;
	private Item item;
	private int targetId;
	
	/*
	 * getters and setters
	 */
	public TurnOption getOption() {
		return option;
	}
	public void setOption(TurnOption option) {
		this.option = option;
	}
	public Move getMove() {
		return move;
	}
	public void setMove(Move move) {
		this.move = move;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public int getTargetId() {
		return targetId;
	}
	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}
}

enum TurnOption {
	FIGHT, SWAP_PKMN, USE_ITEM, RUN
}