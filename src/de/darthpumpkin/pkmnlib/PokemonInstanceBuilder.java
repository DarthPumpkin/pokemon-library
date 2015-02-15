package de.darthpumpkin.pkmnlib;

import java.util.Arrays;

/**
 * Builder class for conveniently and safely generating a
 * {@link PokemonInstance}. All set methods check the passed argument on
 * validity and throw a {@link IllegalArgumentException} if not. Offers the
 * possibility to set attributes in a chain.
 * 
 * @author DarthPumpkin
 * 
 */
public class PokemonInstanceBuilder {

	private PokemonInstance p;

	public PokemonInstanceBuilder(PokemonSpecies species) {
		this.p = new PokemonInstance(species);
		// DV & EV
		p.setDeterValues(new int[] { 0, 0, 0, 0, 0, 0 });
		p.setEffortValues(new int[] { 0, 0, 0, 0, 0, 0 });
		// abilitiy: choose randomly between non-hidden
		int[] sAbilities = species.getAbilities();
		int n = (sAbilities[1] == 0) ? 1 : 2;
		int i = (int) Math.floor(Math.random() * n);
		p.setAbilityId(sAbilities[i]);
		p.setExperiencePoints(0);
		p.setLevel(5);

		// TODO add more default values
	}

	/**
	 * Default: random between all non-hidden abilities
	 * 
	 * @param id
	 *            The integer value that represents the ability
	 */
	public PokemonInstanceBuilder setAbilityId(int id) {
		int[] possibleAbilities = p.getSpecies().getAbilities();
		boolean validAbility = false;
		for (int ability : possibleAbilities) {
			if (id == ability) {
				validAbility = true;
			}
		}
		if (!validAbility) {
			throw new IllegalArgumentException("Ability id " + id
					+ " is not possible for species "
					+ p.getSpecies().getSpeciesId());
		}
		p.setAbilityId(id);
		return this;
	}

	public PokemonInstanceBuilder setDeterminantValues(int[] dVs) {
		for (int dv : dVs) {
			if (dv > 31 || dv < 0) {
				throw new IllegalArgumentException(dv + " is not a valid DV");
			}
		}
		p.setDeterValues(dVs);
		return this;
	}

	public PokemonInstanceBuilder setEffortValues(int[] eVs) {
		int evSum = 0;
		for (int ev : eVs) {
			if (ev < 0 || ev > 255) {
				throw new IllegalArgumentException(ev + " is not a valid EV");
			}
			evSum += ev;
		}
		if (evSum > 510) {
			throw new IllegalArgumentException("Sum of EVs is over 510");
		}
		p.setEffortValues(eVs);
		return this;
	}

	public PokemonInstanceBuilder setLevel(int level) {
		if (level <= 0 || level > 100) {
			throw new IllegalArgumentException(level + " is not a valid level");
		}
		p.setLevel(level);
		return this;
	}

	public PokemonInstanceBuilder setMoves(Move[] moves) {
		if (moves == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		p.setMoves(moves);
		return this;
	}

	/**
	 * call this method to generate the PokemonInstance. If one or more
	 * attributes haven't been set, this method will choose valid default values
	 * based on the species
	 * 
	 * @return the PokemonInstance represented by this builder
	 */
	public PokemonInstance makePokemon() {
		PokemonSpecies s = p.getSpecies();
		// ability
		if (p.getAbilityId() == 0) {
			// need to set id -> choose randomly between non-hidden abilities
		}
		// moves
		if (p.getMoves() == null) {
			// determine four latest (by level) learnable moves
			// TODO Test this, probably white-box!
			Move[] moves;
			int currentLevel = p.getLevel();
			int[] levels = p.getSpecies().getLevelsForMovesLearnableByLevel();
			// index of the latest learnable move
			int index = Arrays.binarySearch(levels, currentLevel);
			if (index < 0) {
				// no new move at this level, see javadoc of
				// Arrays.binarySearch(..)
				index = -index - 1;
			} else {
				// new move(s) at this level
				index++;
				// in case there are muliple new moves at this level and binary
				// search did not find the latest one
				while (levels[index] == levels[index - 1]) {
					// increment index until we've reached the last new move
					index++;
				}
			}
			Move[] allMoves = p.getSpecies().getMovesLearnableByLevel();
			moves = Arrays.copyOfRange(allMoves, (index >= 4) ? index - 4 : 0,
					index);
			moves = Arrays.copyOf(moves, 4); // in case index < 3
			p.setMoves(moves);
		}
		return p;
	}
}
