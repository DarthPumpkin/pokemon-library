package de.darthpumpkin.pkmnlib;

import java.util.HashMap;
import java.util.Map;

public enum Type {
	PLANT, GRASS, FLYING; // TODO add all types

	// [attacking, defending] : effectiveness
	private static final Map<Type[], Float> effectivenesses;
	static {
		effectivenesses = new HashMap<Type[], Float>();
		// TODO add effectivenesses
	}

	/**
	 * calculates the effectiveness of an attack of this type over the type
	 * passed as argument
	 * 
	 * @param defending
	 *            the types of the defending pkmn
	 * @return effectiveness as float in the interval [0.25, 2]
	 */
	public float getEffectivenessOver(Type[] defending) {
		return effectivenesses.get(new Type[] { this, defending[0] })
				* effectivenesses.get(new Type[] { this, defending[1] });
	}

	/**
	 * calculates the effectiveness of an attack of this type over the pkmn
	 * passed as argument
	 * 
	 * @param defending
	 *            the defending pkmn
	 * @return effectiveness as float in the interval [0.25, 2]
	 */
	public float getEffectivenessOver(PokemonSpecies defendingPkmn) {
		return getEffectivenessOver(defendingPkmn.getTypes());
	}

	/**
	 * calculates the same-type-attack-bonus a.k.a. STAB. A STAB of 50% is
	 * granted when a pkmn's type equals the move's type
	 * 
	 * @param attacking
	 *            the types of the attacking PokemonInstance
	 * @return 1.5f if stab, else 1
	 */
	public float getStab(Type[] attacking) {
		return (this == attacking[0] || this == attacking[1]) ? 1.5f : 1;
	}

	/**
	 * calculates the same-type-attack-bonus a.k.a. STAB. A STAB of 50% is
	 * granted when a pkmn's type equals the move's type
	 * 
	 * @param attacking
	 *            the attacking PokemonInstance
	 * @return 1.5f if stab, else 1
	 */
	public float getStab(PokemonSpecies attacking) {
		return getStab(attacking.getTypes());
	}

}
