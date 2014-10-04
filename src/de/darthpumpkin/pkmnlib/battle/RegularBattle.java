package de.darthpumpkin.pkmnlib.battle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import de.darthpumpkin.pkmnlib.Item;
import de.darthpumpkin.pkmnlib.Pokemon;
import de.darthpumpkin.pkmnlib.Pokemon.Stat;

@SuppressWarnings("serial")
public class RegularBattle extends Battle {

	private Map<Player, Pokemon> activePokemons;
	private boolean runningEnabled;

	public RegularBattle(Player[] players, Weather weather) {
		super(players, weather);
	}

	@Override
	public void doTurn(Map<Player, Turn> turns) {
		/*
		 * 1st: running
		 */
		for (Player player : players) {
			if (turns.get(player).getOption() == TurnOption.RUN) {
				if (runningEnabled) { // TODO there are moves and abilities that
										// can prevent escaping
					// TODO implement escape
				}
			}
		}
		/*
		 * 2nd: swapping
		 */
		for (Player player : players) {
			if (turns.get(player).getOption() == TurnOption.SWAP_PKMN) {
				if (true) { // TODO there are moves and abilities that can
							// prevent swapping
					Pokemon p = player.getTeam()[turns.get(player)
							.getTargetId()]; // targetId is the index of the
												// pokemon to be sent in
					if (!p.isUsable()) {
						throw new RuntimeException(p.toString()
								+ " is not usable.");
					}
					withdrawPokemon(player);
					sendPokemon(player, p);
				}
			}
		}
		/*
		 * 3rd: item usage
		 */
		for (Player player : players) {
			if (turns.get(player).getOption() == TurnOption.USE_ITEM) {
				Item item = turns.get(player).getItem();
				item.getParent().remove(item);
				// TODO implement item usage
			}
		}
		/*
		 * 4th: regular move
		 */
		List<Turn> turnsInOrder = new ArrayList<Turn>();
		turnsInOrder.addAll(turns.values());
		// decide who's first
		Collections.sort(turnsInOrder, new Comparator<Turn>() {
			@Override
			public int compare(Turn o0, Turn o1) {
				//first, compare priotities
				int[] prios = new int[] { o0.getMove().getPriority(),
						o1.getMove().getPriority() };
				if (prios[0] < prios[1]) {
					return -1;
				}
				if (prios[1] < prios[0]) {
					return 1;
				} else { //priorities are eaual, compare speed
					int result = 0;
					float[] speeds = new float[] {
							activePokemons.get(o0.getParent()).getCurrent(
									Stat.SPEED),
							activePokemons.get(o1.getParent()).getCurrent(
									Stat.SPEED) };
					if (speeds[0] < speeds[1]) {
						result = -1;
					}
					if (speeds[0] < speeds[1]) {
						result = 1;
					}
					if (trickRoomCounter != 0) {
						result = -result;
					}
					return result;
				}
			}

		});
	}

	@Override
	public void start() {
		/*
		 * select starting pkmns
		 */
		for (Player player : players) {
			for (Pokemon p : player.getTeam()) {
				if (p.isUsable()) {
					this.sendPokemon(player, p);
					break;
				}
			}
			// Exception handling
			if (activePokemons.get(player) == null) {
				throw new RuntimeException(player.toString()
						+ " has no usable pkmns!");
			}
		}
		/*
		 * activate abilities
		 */
		for (Player p : players) {
			int ability = activePokemons.get(p).getAbility();
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

	private void sendPokemon(Player player, Pokemon p) {
		activePokemons.put(player, p);
		// TODO log
	}

	private void withdrawPokemon(Player player) {
		activePokemons.put(player, null);
		// TODO log
	}
}
