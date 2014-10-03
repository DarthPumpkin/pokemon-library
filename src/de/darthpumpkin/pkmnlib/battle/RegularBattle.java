package de.darthpumpkin.pkmnlib.battle;

import java.util.Map;

import de.darthpumpkin.pkmnlib.Pokemon;

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
					Pokemon p = player.getTeam()[turns.get(player).getTargetId()];
					if (!p.isUsable()) {
						throw new RuntimeException(p.toString() + " is not usable.");
					}
					withdrawPokemon(player);
					sendPokemon(player, p);
				}
			}
		}
		/*
		 * 3rd: item usage
		 */
		
		/*
		 * 4th: regular move
		 */
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
