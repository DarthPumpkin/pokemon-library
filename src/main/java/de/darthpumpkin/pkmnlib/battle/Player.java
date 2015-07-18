/**
 * 
 */
package de.darthpumpkin.pkmnlib.battle;

import de.darthpumpkin.pkmnlib.PokemonInstance;
import de.darthpumpkin.pkmnlib.UniqueBoundedList;

/**
 * @author dominik
 *
 */
public interface Player {

	/**
	 * Make a turn.
	 * 
	 * @return
	 */
	public Turn makeTurn();

	/**
	 * get list of PokemonInstances that are in the player's team.
	 * 
	 * @return
	 */
	public UniqueBoundedList<PokemonInstance> getTeam();
	
	/**
	 * Switch current pkmn. Is called when a pkmn faints
	 * 
	 * @return the pkmn to be switched in or null if there aren't any left
	 */
	public PokemonInstance forceSwitch(PokemonInstance toBeSwitchedOut);
}
