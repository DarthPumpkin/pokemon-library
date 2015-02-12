package de.darthpumpkin.pkmnlib;

import java.io.Serializable;

// TODO javadoc is outdated, see usage example and ER diagram
/**
 * Use a {@link PokemonInstanceBuilder} to create a PokemonInstance from a
 * {@link PokemonSpecies}.</br></br> Represents an instance of a species, that
 * is one concrete living pokemon.
 * 
 * @author dominik
 * 
 */

@SuppressWarnings("serial")
public class PokemonInstance implements Serializable, ItemContainer {

	// TODO there are hidden abilities (...?)
	private int abilityId;
	private int[] deterValues;
	private int[] effortValues;
	private int experiencePoints; // those obtained since last level-up
	// TODO int or enum? check db
	private int gender;
	private int level;
	private Move[] moves;
	private String nickname;
	private PokemonSpecies species;
	private StatusProblem statusProblem; // null if there is none

	/**
	 * should only be called by {@link PokemonInstanceBuilder}.
	 * 
	 * @param species
	 */
	/* package */PokemonInstance(PokemonSpecies species) {
		this.species = species;
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

	@Override
	public void attach(Item item) {
		// TODO Auto-generated method stub

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

	@Override
	public void remove(Item item) {
		// TODO Auto-generated method stub

	}

	public int getAbilityId() {
		return abilityId;
	}

	/**
	 * those you would see in the game when checking the poke's info
	 * 
	 * @return
	 */
	public int[] getCurrentStats() {
		// TODO compute current stats
		return null;
	}

	public int[] getDeterValues() {
		return deterValues;
	}

	public int[] getEffortValues() {
		return effortValues;
	}

	/*
	 * getters and setters
	 */
	public int getExperiencePoints() {
		return experiencePoints;
	}

	public int getGender() {
		return gender;
	}

	public int getLevel() {
		return this.level;
	}

	public Move[] getMoves() {
		return moves;
	}

	public String getNickname() {
		return nickname;
	}

	public PokemonSpecies getSpecies() {
		return this.species;
	}

	/*
	 * getters and setters
	 */
	/**
	 * computes the current stats respecting the basic stats, level, eVs and dVs
	 * 
	 * @return
	 */
	public int[] getStats() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @return the status problem or null if there is none
	 */
	public StatusProblem getStatusProblem() {
		return statusProblem;
	}

	/**
	 * 
	 * @return true if it can be used in a battle. False if not (fainted, egg,
	 *         etc.)
	 */
	public boolean isUsable() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setAbilityId(int abilityId) {
		this.abilityId = abilityId;
	}

	public void setDeterValues(int[] deterValues) {
		this.deterValues = deterValues;
	}

	public void setEffortValues(int[] effortValues) {
		this.effortValues = effortValues;
	}

	public void setExperiencePoints(int experiencePoints) {
		this.experiencePoints = experiencePoints;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setMoves(Move[] moves) {
		this.moves = moves;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setSpecies(PokemonSpecies species) {
		this.species = species;
	}

	/**
	 * use null if the status problem is cured
	 * 
	 * @param statusProblem
	 */
	public void setStatusProblem(StatusProblem statusProblem) {
		this.statusProblem = statusProblem;
	}

}