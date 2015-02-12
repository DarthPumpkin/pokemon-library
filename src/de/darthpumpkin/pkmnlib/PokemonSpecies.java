package de.darthpumpkin.pkmnlib;

import java.io.Serializable;

/**
 * use a PokemonSpeciesFactory to create a PokemonSpecies object.
 * 
 * @author dominik
 * 
 */
public class PokemonSpecies implements Serializable {

	private int baseFriendship;
	private int[] baseStats;
	private int catchRate;
	private int[] eggGroups;
	private int[] evYield; // how many EV gets the defeater of this pkmn
	// from 1 to 6, see table growth_rates and growth_rate_prose as well as
	// bulbapedia.net or pokewiki.de
	private int genderRate;
	private int growthRate;
	private int height;
	private int speciesId;
	private Type[] types;
	private int weight;

	/* package */PokemonSpecies() {

	}

	public int getBaseFriendship() {
		return baseFriendship;
	}

	public void setBaseFriendship(int baseFriendship) {
		this.baseFriendship = baseFriendship;
	}

	public int[] getBaseStats() {
		return baseStats;
	}

	public void setBaseStats(int[] baseStats) {
		this.baseStats = baseStats;
	}

	public int getCatchRate() {
		return catchRate;
	}

	public void setCatchRate(int catchRate) {
		this.catchRate = catchRate;
	}

	public int[] getEvYield() {
		return evYield;
	}

	public void setEvYield(int[] evYield) {
		this.evYield = evYield;
	}

	public int[] getEggGroups() {
		return eggGroups;
	}

	public void setEggGroups(int[] eggGroups) {
		this.eggGroups = eggGroups;
	}

	public int getGenderRate() {
		return genderRate;
	}

	public void setGenderRate(int genderRate) {
		this.genderRate = genderRate;
	}

	public int getGrowthRate() {
		return growthRate;
	}

	public void setGrowthRate(int growthRate) {
		this.growthRate = growthRate;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getSpeciesId() {
		return speciesId;
	}

	public void setSpeciesId(int speciesId) {
		this.speciesId = speciesId;
	}

	public Type[] getTypes() {
		return types;
	}

	public void setTypes(Type[] types) {
		this.types = types;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * 
	 * @param type
	 * @return true if one of the pkmn's types equals the Type passed as
	 *         argument.
	 * @throws IllegalArgumentException
	 *             if type == null. Do not use this method to detect whether the
	 *             species is a dual-type.
	 */
	public boolean isOfType(Type type) {
		if (type == null) {
			throw new IllegalArgumentException("null is not accepted as Type");
		}
		for (Type t : types) {
			if (t == type) {
				return true;
			}
		}
		return false;
	}
}
