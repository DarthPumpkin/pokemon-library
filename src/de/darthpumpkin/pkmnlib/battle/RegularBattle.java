package de.darthpumpkin.pkmnlib.battle;

import java.util.Map;

import de.darthpumpkin.pkmnlib.Pokemon;

@SuppressWarnings("serial")
public class RegularBattle extends Battle {
	
	private Map<Player, Pokemon> activePokemons;

	public RegularBattle(Player[] players, Weather weather) {
		super(players, weather);
	}

	@Override
	public void doTurn(Map<Player, Turn> turns) {
		// TODO Auto-generated method stub

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
			//Exception handling
			if (activePokemons.get(player) == null) {
				throw new RuntimeException(player.toString() + " has no usable pkmns!");
			}
		}
		/*
		 * activate abilities
		 */
		for (Player p : players) {
			int ability = activePokemons.get(p).getAbility();
			switch (ability) {
			//TODO implement abilities
			default:
			}
		}
		/*
		 * log weather if not normal
		 */
		if (this.weather != Weather.NORMAL) {
			//TODO log
		}
	}

	private void sendPokemon(Player player, Pokemon p) {
		activePokemons.put(player, p);
		//TODO log
	}

}
