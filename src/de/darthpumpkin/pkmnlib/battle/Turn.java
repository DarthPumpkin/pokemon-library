package de.darthpumpkin.pkmnlib.battle;

import java.io.Serializable;

import de.darthpumpkin.pkmnlib.Item;
import de.darthpumpkin.pkmnlib.Move;

public class Turn implements Serializable {
	private TurnOption option;
	private Move move;
	private Item item;
	private int targetId;
}

enum TurnOption {
	FIGHT, SWAP_PKMN, USE_ITEM, RUN
}