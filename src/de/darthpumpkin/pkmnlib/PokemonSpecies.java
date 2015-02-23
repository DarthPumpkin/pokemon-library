package de.darthpumpkin.pkmnlib;

import java.io.Serializable;

import static java.lang.Math.pow;

/**
 * Use a {@link PokemonSpeciesFactory} to create a PokemonSpecies
 * object.</br></br> Represents a pokemon species (Bulbasaur, Rattata, ...).
 * Attributes are those who are equal for all instances of the species.
 * 
 * @author dominik
 * 
 */
public class PokemonSpecies implements Serializable {

	private int[] abilities; // 0-1: regular, 2-3: hidden
	private int baseFriendship;
	private int[] baseStats;
	private int catchRate;
	private int[] eggGroups;
	private int[] evYield; // how many EV gets the defeater of this pkmn
	// see table pokemon_species, column gender_rate
	private int genderRate;
	// from 1 to 6, see table growth_rates and growth_rate_prose as well as
	// bulbapedia.net or pokewiki.de
	private int growthRate;
	private int height;
	private int[] levelsForMovesLearnableByLevel;
	private Move[] movesLearnableByLevel;
	private int speciesId;
	private Type[] types;
	private int weight;

	/* package */PokemonSpecies() {

	}

	public int[] getAbilities() {
		return abilities;
	}

	public void setAbilities(int[] abilities) {
		this.abilities = abilities;
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

	public int[] getLevelsForMovesLearnableByLevel() {
		return levelsForMovesLearnableByLevel;
	}

	public void setLevelsForMovesLearnableByLevel(
			int[] levelsForMovesLearnableByLevel) {
		this.levelsForMovesLearnableByLevel = levelsForMovesLearnableByLevel;
	}

	public Move[] getMovesLearnableByLevel() {
		return movesLearnableByLevel;
	}

	public void setMovesLearnableByLevel(Move[] movesLearnableByLevel) {
		this.movesLearnableByLevel = movesLearnableByLevel;
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

	/**
	 * Calulates the absolute amount of experience points an instance of this
	 * species must have collected to reach specified level.
	 * 
	 * @param level
	 *            Level for which the experience points should be calculated
	 * @return Absolute amount
	 */
	public int requiredExperiencePointsForLevel(int level) {
		int n = level;
		double epForNextLevel = 0;
		switch (growthRate) {
		case 1:
			epForNextLevel = 5.0 / 4 * pow(n,3);
			break;
		case 2:
			epForNextLevel = pow(n,3);
			break;
		case 3:
			epForNextLevel = 4.0 / 5 * pow(n,3);
			break;
		case 4:
			epForNextLevel = (n == 1) ? 0 : (n == 2) ? 9 : (n == 3) ? 57
					: (n == 4) ? 96 : 1.2 * pow(n,3) - 15 * pow(n,2) + 100 * n
							- 140;
			break;
		case 5:
			epForNextLevel = (n <= 50) ? pow(n,3) * (100 - n) / 50.0
					: (n <= 68) ? pow(n,3) * (150 - n) / 100.0
							: (n <= 98) ? pow(n,3) / 500.0
									* (int) Math.floor((1911 - 10 * n) / 3.0)
									: pow(n,3) * (160 - n) / 100.0;
			break;
		case 6:
			epForNextLevel = (n <= 15) ? pow(n,3)
					* ((int) Math.floor((n + 1) / 3.0) + 24) / 50.0
					: (n <= 36) ? pow(n,3) * (n + 14) / 50.0 : pow(n,3)
							* (Math.floor(n / 2.0) + 32) / 50.0;
			break;
		default:
			throw new RuntimeException(
					"Calculation of experience points failed. Probably illegal growth rate or error in algorithm");
		}
		return (int) epForNextLevel;
	}
}
