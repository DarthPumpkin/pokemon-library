package de.darthpumpkin.pkmnlib.battle;

import java.io.Serializable;

import de.darthpumpkin.pkmnlib.Pokemon;

public interface Player extends Serializable {
	
	public Turn makeTurn();

	public Pokemon[] getTeam();

	/**
	 * Switch current pkmn. Is called when a pkmn faints
	 * @return the pkmn to be switched in or null if there aren't any left
	 */
	public Pokemon forceSwitch(Pokemon toBeSwitchedOut);
}
