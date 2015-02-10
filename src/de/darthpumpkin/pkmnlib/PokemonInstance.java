package de.darthpumpkin.pkmnlib;

import java.io.Serializable;

// TODO javadoc is outdated, see usage example
/**
 * To make a pokemon, use a PokemonFactory implementation to create a
 * PokemonBuilder. Use latter to specify the desired attributes, then call
 * makePokemon().
 * 
 * @author dominik
 * 
 */

@SuppressWarnings("serial")
public class PokemonInstance implements Serializable, ItemContainer {

	/*
	 * fields
	 */
	private PokemonSpecies species;
	private int[] currentStats; // TODO compute when neccessary
	private int level;
	private int experiencePoints;	// those obtained since last level-up
	private StatusProblem statusProblem; // null if there is none
	// TODO there are hidden abilities (...?)
	private int abilityId;

	/*
	 * methods
	 */
	/**
	 * 
	 * @return true if it can be used in a battle. False if not (fainted, egg,
	 *         etc.)
	 */
	public boolean isUsable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void remove(Item item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attach(Item item) {
		// TODO Auto-generated method stub

	}

	/**
	 * subtracts damage from this pkmn's current hp. Set to 0 if damage > hp
	 * 
	 * @param damage
	 *            damage to be inflicted
	 */
	public void applyDamage(int damage) {
		int hpStat = this.currentStats[Stat.HP.i()];
		hpStat -= damage;
		if (hpStat < 0) {
			hpStat = 0;
		}
	}

	/*
	 * getters and setters
	 */
	public PokemonSpecies getSpecies() {
		return this.species;
	}

	/**
	 * those you would see in the game when cheking the poke's info
	 * 
	 * @return
	 */
	public int[] getCurrentStats() {
		return currentStats;
	}

	/**
	 * those you would see in the game when cheking the poke's info
	 * 
	 * @return
	 */
	public void setCurrentStats(int[] currentStats) {
		this.currentStats = currentStats;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * 
	 * @return the status problem or null if there is none
	 */
	public StatusProblem getStatusProblem() {
		return statusProblem;
	}

	/**
	 * use null if the status problem is cured
	 * 
	 * @param statusProblem
	 */
	public void setStatusProblem(StatusProblem statusProblem) {
		this.statusProblem = statusProblem;
	}

	public int getAbilityId() {
		return abilityId;
	}

	public void setAbilityId(int abilityId) {
		this.abilityId = abilityId;
	}

	@Override
	public boolean holdsItem(Item item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean holdsItemInstanceOf(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * computes the current stats respecting the basic stats, level, eVs and dVs
	 * 
	 * @return 
	 */
	public int[] getStats() {
		// TODO Auto-generated method stub
		return null;
	}

}