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
import de.darthpumpkin.pkmnlib.Move;
import de.darthpumpkin.pkmnlib.PokemonBattleInstance;
import de.darthpumpkin.pkmnlib.PokemonInstance;
import de.darthpumpkin.pkmnlib.Stat;
import de.darthpumpkin.pkmnlib.StatusProblem;

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
		this.activePkmns = new PokemonBattleInstance[2];
		this.active = false;
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
					((SingleBattlePlayer[]) players)[i]
							.setActivePokemon(activePkmns[i]);
					break;
				}
			}
			// Exception handling
			if (activePkmns[i] == null) {
				throw new RuntimeException(players[i].toString()
						+ " has no usable pkmns!");
			}
		}
		this.active = true;
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
					return ((Float) activePkmns[Arrays.asList(players).indexOf(
							o1.getParent())].getCurrent(Stat.SPEED))
							.compareTo(activePkmns[Arrays.asList(players)
									.indexOf(o2.getParent())]
									.getCurrent(Stat.SPEED));
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
				boolean hit = true;
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
					switch (tree.getEffectId()) {
					// TODO implement atomar effects here
					case 1:
						// see
						// http://www.smogon.com/dp/articles/damage_formula
						/*
						 * level
						 */
						// int division will automatically floor
						int level = (attackingPkmn.getInstance().getLevel() * 2) / 5 + 2;

						/*
						 * base power
						 */
						int power = turn.getMove().getPower();
						double helpingHand = 1d; // not relevant in single
													// battle
						// see
						// http://www.smogon.com/dp/articles/damage_formula#bp_items
						double itemMultiplier = 1d;
						int charge = 1; // TODO 2 if the last move was
										// charge and the move's type is
										// electric
						double sport = 1d; // TODO modificator for mud sport
											// and water sport
						// TODO see
						// http://www.smogon.com/dp/articles/damage_formula#bp_abilities_user
						// and
						// http://www.smogon.com/dp/articles/damage_formula#bp_abilities_foe
						double abilityMultiplier = 1d;

						// multiplying all beforementioned values with
						// flooring after each step for the final basePower
						// value
						double basePower = Math.floor(Math.floor(Math
								.floor(Math.floor(Math.floor(helpingHand
										* power)
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

						// multiplying all beforementioned values with
						// flooring after each step for the final atk value
						double atk = Math.floor(Math.floor(Math.floor(atkStat
								* abilityModifier)
								* itemModifier));

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

						// multiplying all beforementioned values with
						// flooring after each step for the final def value
						double def = Math.floor(Math.floor(defStat * sx) * mod);

						/*
						 * MOD1
						 */
						// TODO brn is always 1 if the attacker's ability is
						// 'guts'
						double brn = (turn.getMove().getDamageClass() == Move.DamageClass.PHYSICAL && attackingPkmn
								.getInstance().getStatusProblem() == StatusProblem.BURN) ? 0.5
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

						// multiplying all beforementioned values for the
						// final mod1
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
						// TODO stab is 2 if the attaacker's ability is
						// adaptability
						double stab = attackingPkmn.getInstance().getSpecies().getTypeSet().getStab(turn
								.getMove()
								.getType());

						/*
						 * TYPE1 and TYPE2
						 */
						double eff = defendingPkmn.getInstance()
								.getSpecies().getTypeSet().getModifier(turn.getMove().getType()); 
								
						/*
						 * MOD3
						 */
						// TODO see
						// http://www.smogon.com/dp/articles/damage_formula#mod3
						double mod3 = 1d;

						/*
						 * now, the final formula
						 */
						int damage = (int) Math.floor(Math.floor((Math
								.floor(Math.floor(Math.floor(level * basePower
										* atk / 50)
										/ def)
										* mod1) + 2)
								* critical * mod2)
								* r / 100);
//						System.out.println("level " + level + ", basePower " + basePower + ", atk " + atk + ", def " + def + ", r " + r);
						defendingPkmn.getInstance().applyDamage(damage);
						// TODO log!
						break;
					case 101:
						// TODO implement
					case 1001:
						// TODO implement
					default:
						throw new RuntimeException("Unknown effect id: "
								+ tree.getEffectId());
					}
					boolean success = true;
					// TODO determine if successful
					if (success) {
						// continue with left subtree
						tree = tree.getSuccessElement();
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

	public PokemonBattleInstance[] getActivePkmns() {
		return activePkmns;
	}

}
