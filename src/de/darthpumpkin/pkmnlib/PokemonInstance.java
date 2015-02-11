package de.darthpumpkin.pkmnlib;

import java.io.Serializable;

// TODO javadoc is outdated, see usage example and ER diagram
/**
 * To make a pokemon, use a PokemonSpeciesFactory implementation to create a
 * PokemonInstanceBuilder. Use latter to specify the desired attributes, then call
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
	private int level;
	private int experiencePoints; // those obtained since last level-up
	private StatusProblem statusProblem; // null if there is none
	// TODO there are hidden abilities (...?)
	private int abilityId;
	private int[] effortValues;
	private int[] deterValues;
	// TODO int or enum? check db
	private int gender;
	private String nickname;
	private Move[] moves;

	/**
	 * should only be called by PokemonInstanceBuilder
	 * @param species
	 */
	/*package*/ PokemonInstance(PokemonSpecies species) {
		this.species = species;
	}

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
		int hpStat = this.getCurrentStats()[Stat.HP.i()];
		hpStat -= damage;
		if (hpStat < 0) {
			hpStat = 0;
		}
	}

	/*
	 * getters and setters
	 */
	public int getExperiencePoints() {
		return experiencePoints;
	}

	public void setExperiencePoints(int experiencePoints) {
		this.experiencePoints = experiencePoints;
	}

	public int[] getEffortValues() {
		return effortValues;
	}

	public void setEffortValues(int[] effortValues) {
		this.effortValues = effortValues;
	}

	public int[] getDeterValues() {
		return deterValues;
	}

	public void setDeterValues(int[] deterValues) {
		this.deterValues = deterValues;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Move[] getMoves() {
		return moves;
	}

	public void setMoves(Move[] moves) {
		this.moves = moves;
	}

	public void setSpecies(PokemonSpecies species) {
		this.species = species;
	}

	public PokemonSpecies getSpecies() {
		return this.species;
	}

	/**
	 * those you would see in the game when cheking the poke's info
	 * 
	 * @return
	 */
	public int[] getCurrentStats() {
		// TODO compute current stats
		return null;
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