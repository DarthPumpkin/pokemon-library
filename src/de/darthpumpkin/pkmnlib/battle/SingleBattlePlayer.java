package de.darthpumpkin.pkmnlib.battle;

import de.darthpumpkin.pkmnlib.PokemonBattleInstance;

/**
 * Interface for all players in battles with exactly one active pokemon per
 * player at a time.
 * 
 * @author dominik
 * 
 */
public interface SingleBattlePlayer extends Player {

	/**
	 * get PokemonBattleInstance that is currently in battle.
	 * 
	 * @return
	 */
	public PokemonBattleInstance getActivePokemon();

	/**
	 * set PokemonBattleInstance that is currently in battle.
	 * 
	 * @param pbi
	 *            the Battle instance. Must be a battle instance of a usable
	 *            PokemonInstance from the player's team.
	 */
	public void setActivePokemon(PokemonBattleInstance pbi);

}
