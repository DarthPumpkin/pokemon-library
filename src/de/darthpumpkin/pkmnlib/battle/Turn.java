package de.darthpumpkin.pkmnlib.battle;

import java.io.Serializable;

import de.darthpumpkin.pkmnlib.Item;
import de.darthpumpkin.pkmnlib.Move;

public class Turn implements Serializable, Comparable<Turn> {

	/**
	 * RUN > SWAP_PKMN > USE_ITEM > FIGHT
	 */
	public enum TurnOption {
		FIGHT, USE_ITEM, SWAP_PKMN, RUN
	}

	private TurnOption option;
	private Move move;
	private Item item;
	private int targetId;
	private Player parent;

	/**
	 * first compares turn options. if equal, compares move priorities. Note:
	 * this does not respect the pkmn's speed or any other information related
	 * to the current state of the battle as a turn does not know about the
	 * battle.
	 */
	@Override
	public int compareTo(Turn otherTurn) {
		int optionComparison = this.option.compareTo(otherTurn.getOption());
		if (optionComparison != 0) {
			return optionComparison;
		}
		if (option == TurnOption.FIGHT) {
			return ((Integer) this.move.getPriority()).compareTo(otherTurn
					.getMove().getPriority());
		}
		return 0;
	}

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

	public Player getParent() {
		return parent;
	}

	public void setParent(Player parent) {
		this.parent = parent;
	}
}
