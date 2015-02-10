package de.darthpumpkin.pkmnlib;

import java.io.Serializable;

public class PokemonSpecies implements Serializable {

	private int[] baseStats;
	private Type[] types;
	
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
