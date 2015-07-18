/**
 * 
 */
package de.darthpumpkin.pkmnlib.battle;

import de.darthpumpkin.pkmnlib.PokemonBattleInstance;
import de.darthpumpkin.pkmnlib.PokemonInstance;
import de.darthpumpkin.pkmnlib.UniqueBoundedList;

/**
 * @author dominik
 *
 */
public class DummyPlayer implements SingleBattlePlayer {

	private UniqueBoundedList<PokemonInstance> team;
	private PokemonBattleInstance activePokemon;

	public DummyPlayer(UniqueBoundedList<PokemonInstance> team) {
		this.team = team;
	}

	@Override
	public Turn makeTurn() {
		Turn t = new Turn();
		t.setOption(Turn.TurnOption.FIGHT);
		t.setMove(activePokemon.getInstance().getMoves()[0]);
		t.setParent(this);
		// t.setTargetId(targetId);
		return t;
	}

	@Override
	public UniqueBoundedList<PokemonInstance> getTeam() {
		return team;
	}

	@Override
	public PokemonInstance forceSwitch(PokemonInstance toBeSwitchedOut) {
		for (PokemonInstance p : team) {
			if (p.isUsable()) {
				return p;
			}
		}
		return null;
	}

	@Override
	public PokemonBattleInstance getActivePokemon() {
		return activePokemon;
	}

	@Override
	public void setActivePokemon(PokemonBattleInstance pbi) {
		this.activePokemon = pbi;
	}

}
