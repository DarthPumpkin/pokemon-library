package de.darthpumpkin.pkmnlib.battle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import de.darthpumpkin.pkmnlib.*;

@SuppressWarnings("serial")
public class RegularBattle extends AbstractBattle {

	private Map<Player, PokemonInstance> activePokemons;
	private boolean runningEnabled;
	private Map<Player, Boolean> expEnabled;
	private boolean active;

	public RegularBattle(Player[] players, Weather weather) {
		super(players, weather);
	}

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
					return ((Float) activePokemons.get(o1.getParent())
							.getCurrent(Stat.SPEED)).compareTo(activePokemons
							.get(o2.getParent()).getCurrent(Stat.SPEED));
				}
				// no criteria left => random
				return 0;
			}
		});
		// now that we've determined order, let's execute the turns
		for (Turn turn : turnsInOrder) {
			PokemonInstance defendingPkmn = (turn.getParent() == players[0]) ? activePokemons
					.get(players[1]) : activePokemons.get(players[0]);
			PokemonInstance attackingPkmn = activePokemons.get(turn.getParent());
			Player player = turn.getParent();

			switch (turn.getOption()) {
			case RUN:
				if (runningEnabled) { // TODO there are moves that can prevent
					// escaping
					// see http://www.serebii.net/games/escape.shtml
					if (defendingPkmn.getAbilityId() != 71
							|| attackingPkmn.isOfType(Type.FLYING)
							|| attackingPkmn.isFlying()
							|| attackingPkmn.holdsItemInstanceOf(272)) {
						// 71 == 'arena trap'; but still works when flying-type
						// or in the air
						// 272 == 'shed shell'
						// TODO implement escape
					} else {
						// TODO log running is not possible because of arena
						// trap 
					}
				} else {
					// TODO log running is not possible (trainer battle etc.)
				}
				break;
			case SWAP_PKMN:
				if (defendingPkmn.getAbilityId() != 71
						|| attackingPkmn.isOfType(Type.FLYING)
						|| attackingPkmn.isFlying()
						|| attackingPkmn.holdsItemInstanceOf(272)) {
					// TODO there are moves that can prevent swapping
					// see http://www.serebii.net/games/escape.shtml
					PokemonInstance p = player.getTeam()[turns.get(player)
							.getTargetId()]; // targetId is the index of the
												// pokemon to be sent in
					if (!p.isUsable()) {
						throw new RuntimeException(p.toString()
								+ " is not usable.");
					}
					withdrawPokemon(player);
					sendPokemon(player, p);
				} else {
					// TODO log swapping is not possible because of arena trap
				}
				break;
			case USE_ITEM:
				Item item = turns.get(player).getItem();
				item.getParent().remove(item);
				// TODO implement item usage
				break;
			case FIGHT:
				if (!attackingPkmn.isUsable()) {
					// pkmn fainted before it could attack
					continue;
				}
				// TODO check if attack hits (accuracy, evasion...)

				// if we're here, the attack was successful; now apply the
				// effects
				switch (turn.getMove().getEffectId()) {
				case 1: // regular damage, see
						// http://www.smogon.com/dp/articles/damage_formula
					/*
					 * level
					 */
					// int division will automatically floor
					int level = attackingPkmn.getLevel() * 2 / 5 + 2;

					/*
					 * base power
					 */
					int power = turn.getMove().getPower();
					double helpingHand = 1d; // not relevant in single battle
					// see
					// http://www.smogon.com/dp/articles/damage_formula#bp_items
					double itemMultiplier = 1d;
					int charge = 1; // TODO 2 if the last move was charge and
									// the
									// move's type is electric
					double sport = 1d; // TODO modificator for mud sport and
										// water
										// sport
					// TODO see
					// http://www.smogon.com/dp/articles/damage_formula#bp_abilities_user
					// and
					// http://www.smogon.com/dp/articles/damage_formula#bp_abilities_foe
					double abilityMultiplier = 1d;

					// multiplying all beforementioned values with flooring
					// after
					// each step for the final basePower value
					double basePower = Math.floor(Math.floor(Math.floor(Math
							.floor(Math.floor(helpingHand * power)
									* itemMultiplier)
							* charge)
							* sport)
							* abilityMultiplier);
					/*
					 * [Sp]Atk
					 */
					double atkStat = (turn.getMove().getDamageClass() == Move.DamageClass.PHYSICAL) ? attackingPkmn
							.getCurrent(Stat.ATK) : attackingPkmn
							.getCurrent(Stat.SPATK);
					// TODO see
					// http://www.smogon.com/dp/articles/damage_formula#atk_abilities
					double abilityModifier = 1d;
					// TODO see
					// http://www.smogon.com/dp/articles/damage_formula#atk_items
					double itemModifier = 1d;

					// multiplying all beforementioned values with flooring
					// after
					// each step for the final atk value
					double atk = Math.floor(Math.floor(Math.floor(atkStat
							* abilityModifier)
							* itemModifier) / 50);

					/*
					 * [Sp]Def
					 */
					double defStat = (turn.getMove().getDamageClass() == Move.DamageClass.PHYSICAL) ? defendingPkmn
							.getCurrent(Stat.DEF) : defendingPkmn
							.getCurrent(Stat.SPDEF);
					// TODO see
					// http://www.smogon.com/dp/articles/damage_formula#defense
					double sx = 1d;
					// TODO see
					// http://www.smogon.com/dp/articles/damage_formula#defense
					double mod = 1d;

					// multiplying all beforementioned values with flooring
					// after
					// each step for the final def value
					double def = Math.floor(Math.floor(defStat * sx) * mod);

					/*
					 * MOD1
					 */
					// TODO brn is always 1 if the attacker's ability is 'guts'
					double brn = (turn.getMove().getDamageClass() == Move.DamageClass.PHYSICAL && attackingPkmn
							.getStatusProblem() == StatusProblem.BURN) ? 0.5
							: 1;
					// TODO see
					// http://www.smogon.com/dp/articles/damage_formula#mod1
					double rl = 1d;
					// TODO see
					// http://www.smogon.com/dp/articles/damage_formula#mod1
					double weatherModifier = 1d;
					// TODO see
					// http://www.smogon.com/dp/articles/damage_formula#mod1
					double ff = 1d;

					// multiplying all beforementioned values for the final mod1
					// value
					double mod1 = brn * rl * weatherModifier * ff;

					/*
					 * Critical hit
					 */
					double critical = 1d; // TODO critical hits

					/*
					 * MOD2
					 */
					// TODO see
					// http://www.smogon.com/dp/articles/damage_formula#mod2
					double mod2 = 1d;

					/*
					 * R
					 */
					double r = 100 - (15 * Math.random()); // [85, 100]

					/*
					 * STAB
					 */
					// TODO stab is 2 if the attaacker's ability is adaptability
					double stab = turn.getMove().getType()
							.getStab(attackingPkmn);

					/*
					 * TYPE1 and TYPE2
					 */
					double eff = turn.getMove().getType()
							.getEffectivenessOver(defendingPkmn);

					/*
					 * MOD3
					 */
					// TODO see
					// http://www.smogon.com/dp/articles/damage_formula#mod3
					double mod3 = 1d;

					/*
					 * now, the final formula
					 */
					int damage = (int) Math.floor(Math.floor((Math.floor(Math
							.floor(Math.floor(level * basePower * atk / 50)
									/ def)
							* mod1) + 2)
							* critical * mod2)
							* r / 100);
					defendingPkmn.applyDamage(damage);
					// TODO log!
					break;
				// TODO implement other effectIds
				default:
					throw new RuntimeException("Unknown effect id");
				}
				break;
			}
		}
	}

	@Override
	public void start() {
		/*
		 * select starting pkmns
		 */
		for (Player player : players) {
			for (PokemonInstance p : player.getTeam()) {
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
			int ability = activePokemons.get(p).getAbilityId();
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

	private void sendPokemon(Player player, PokemonInstance p) {
		activePokemons.put(player, p);
		// TODO log
	}

	private void withdrawPokemon(Player player) {
		// reset temporary stat modifiers
		java.util.Arrays.fill(activePokemons.get(player)
				.getTemporaryStatModifiers(), 0);
		activePokemons.put(player, null);
		// TODO log
	}

	@Override
	public boolean isActive() {
		return active;
	}
}
