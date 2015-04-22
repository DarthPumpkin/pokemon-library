/**
 * 
 */
package de.darthpumpkin.pkmnlib.battle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import de.darthpumpkin.pkmnlib.AtomarMove;
import de.darthpumpkin.pkmnlib.PokemonBattleInstance;
import de.darthpumpkin.pkmnlib.PokemonInstance;
import de.darthpumpkin.pkmnlib.Stat;

/**
 * @author dominik
 * 
 */
public class TrainerBattle1vs1 extends AbstractBattle {

	/*
	 * Indexes matching players.
	 */
	private PokemonBattleInstance[] activePkmns;
	private boolean active;

	public TrainerBattle1vs1(SingleBattlePlayer[] players, Weather weather) {
		super(players, weather);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.darthpumpkin.pkmnlib.battle.AbstractBattle#start()
	 */
	@Override
	public void start() {
		/*
		 * send pkmns in
		 */
		for (int i = 0; i < 2; i++) {
			for (PokemonInstance p : players[i].getTeam()) {
				if (p.isUsable()) {
					activePkmns[i] = new PokemonBattleInstance(p);
					break;
				}
			}
			// Exception handling
			if (activePkmns[i] == null) {
				throw new RuntimeException(players[i].toString()
						+ " has no usable pkmns!");
			}
		}
		/*
		 * activate abilities
		 */
		for (int i = 0; i < 2; i++) {
			int ability = activePkmns[i].getInstance().getAbilityId();
			switch (ability) {
			// TODO implement abilities
			default:
			}
		}
		/*
		 * log weather if not normal
		 */
		if (this.weather != Weather.NORMAL) {
			// TODO log
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.darthpumpkin.pkmnlib.battle.AbstractBattle#doTurn(java.util.Map)
	 */
	@Override
	public void doTurn(Map<Player, Turn> turns) {
		List<Turn> turnsInOrder = new ArrayList<Turn>();
		turnsInOrder.addAll(turns.values());

		// decide who's first
		Collections.sort(turnsInOrder, new Comparator<Turn>() {
			@Override
			public int compare(Turn o1, Turn o2) {
				int naturalTurnComparison = o1.compareTo(o2);
				if (naturalTurnComparison != 0) {
					return naturalTurnComparison;
				}
				// if we're here, both players chose the same TurnOption
				if (o1.getOption() == Turn.TurnOption.FIGHT) {
					// if we're here, both moves have the same priority
					// therefore, we compare the pkmns' speed
					return ((Float) activePkmns[Arrays.binarySearch(players,
							o1.getParent())].getCurrent(Stat.SPEED))
							.compareTo(activePkmns[Arrays.binarySearch(players,
									o2.getParent())].getCurrent(Stat.SPEED));
				}
				// no criteria left => random
				return 0;
			}
		});
		// now that we've determined order, let's execute the turns
		for (Turn turn : turnsInOrder) {
			int attIdx = Arrays.asList(players).indexOf(turn.getParent());
			PokemonBattleInstance attackingPkmn = this.activePkmns[attIdx];
			PokemonBattleInstance defendingPkmn = this.activePkmns[(attIdx + 1) % 2];

			switch (turn.getOption()) {
			case FIGHT:
				// TODO check if move is valid
				// TODO check if opponent used 'Protect'
				// TODO check if opponent's type has immunity for move's type
				// TODO what else can prevent the move?
				if (!attackingPkmn.getInstance().isUsable()) {
					// pkmn fainted before it could attack
					continue;
				}
				/*
				 * If we're here, the move was not prevented; will be executed
				 */
				// TODO check if attack hits (accuracy, evasion...)
				boolean hit = false;
				AtomarMove tree = null;
				if (hit) {
					/*
					 * if we're here, the attack was successful; traverse the
					 * left tree
					 */
					tree = turn.getMove().getSuccessElement();
				} else {
					/*
					 * if we're here, the attack was unsuccessful; traverse the
					 * right tree.
					 */
					// TODO log miss
					tree = turn.getMove().getFailureElement();
				}
				/*
				 * traverse now.
				 */
				while (tree != null) {
					boolean success = false;
					// TODO determine if successful
					if (success) {
						// continue with left subtree
						tree = tree.getSuccessElement();
						switch (tree.getEffectId()) {
						// TODO implement atomar effects here
						default:
							throw new RuntimeException("Unknown effect id: "
									+ tree.getEffectId());
						}
					} else {
						// continue with right subtree
						tree = tree.getFailureElement();
					}
				}
				break;
			// TODO implement other turn options
			default:
			}
		}
		// TODO continue
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.darthpumpkin.pkmnlib.battle.AbstractBattle#isActive()
	 */
	@Override
	public boolean isActive() {
		return this.active;
	}

}
