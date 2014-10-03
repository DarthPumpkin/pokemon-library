package de.darthpumpkin.pkmnlib.battle;

import java.io.Serializable;

import de.darthpumpkin.pkmnlib.Pokemon;

public interface Player extends Serializable {
	
	public Turn makeTurn();

	public Pokemon[] getTeam();
}
