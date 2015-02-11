package de.darthpumpkin.pkmnlib;

import java.io.Serializable;

public class PokemonSpecies implements Serializable {

	private int[] baseStats;
	private Type[] types;
	// from 1 to 6, see table growth_rates and growth_rate_prose as well as
	// bulbapedia.net or pokewiki.de
	private int growthRate;
	private int catchRate;

	public int[] getBaseStats() {
		return baseStats;
	}

	public void setBaseStats(int[] baseStats) {
		this.baseStats = baseStats;
	}

	public Type[] getTypes() {
		return types;
	}

	public void setTypes(Type[] types) {
		this.types = types;
	}

	public int getGrowthRate() {
		return growthRate;
	}

	public void setGrowthRate(int growthRate) {
		this.growthRate = growthRate;
	}

	public int getCatchRate() {
		return catchRate;
	}

	public void setCatchRate(int catchRate) {
		this.catchRate = catchRate;
	}

	/**
	 * 
	 * @param type
	 * @return true if one of the pkmn's types equals the Type passed as
	 *         argument.
	 */
	public boolean isOfType(Type type) {
		for (Type t : types) {
			if (t == type) {
				return true;
			}
		}
		return false;
	}
}
