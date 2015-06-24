package de.darthpumpkin.pkmnlib;

import java.util.Arrays;

// TODO builder.makePokemon() should return a new PokemonInstance on every call.
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

	private int abilityId;
	private int currentHp; // unset
	private int[] deterValues;
	private int[] effortValues;
	private int experiencePoints; // unset
	private Gender gender;
	private int level;
	private Move[] moves;
	private Nature nature;
	private String nickname; // unset
	private PokemonSpecies species;
	private StatusProblem statusProblem; // unset

	public PokemonInstanceBuilder(PokemonSpecies species) {
		this.species = species;
		// DV & EV
		this.deterValues = new int[] { 0, 0, 0, 0, 0, 0 };
		this.effortValues = new int[] { 0, 0, 0, 0, 0, 0 };
		// abilitiy: choose randomly between non-hidden
		int[] sAbilities = species.getAbilities();
		int n = (sAbilities[1] == 0) ? 1 : 2;
		int i = (int) Math.floor(Math.random() * n);
		this.abilityId = sAbilities[i];
		this.experiencePoints = 0;
		this.level = 5;
		// gender
		int gr = species.getGenderRate();
		if (gr == -1) {
			this.gender = Gender.NEUTRAL;
		} else {
			float threshold = gr / 8f;
			if (Math.random() < threshold) {
				this.gender = Gender.FEMALE;
			} else {
				this.gender = Gender.MALE;
			}
		}
		// nature
		// int nature = (int) Math.ceil(Math.random() * 25);
		Nature nature = Nature.hardy;
		// TODO set nature randomly
		this.nature = nature;

		// TODO add more default values
	}

	/**
	 * Default: random among all non-hidden abilities
	 * 
	 * @param id
	 *            The integer value that represents the ability
	 */
	public PokemonInstanceBuilder setAbilityId(int id) {
		int[] possibleAbilities = this.species.getAbilities();
		boolean validAbility = false;
		for (int ability : possibleAbilities) {
			if (id == ability) {
				validAbility = true;
			}
		}
		if (!validAbility) {
			throw new IllegalArgumentException("Ability id " + id
					+ " is not possible for species "
					+ this.species.getSpeciesId());
		}
		this.abilityId = id;
		return this;
	}

	public PokemonInstanceBuilder setDeterminantValues(int[] dVs) {
		for (int dv : dVs) {
			if (dv > 31 || dv < 0) {
				throw new IllegalArgumentException(dv + " is not a valid DV");
			}
		}
		this.deterValues = dVs;
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
		this.effortValues = eVs;
		return this;
	}

	public void setGender(Gender gender) {
		if (gender == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		int gr = species.getGenderRate();
		if (gender != Gender.NEUTRAL && gr == -1) {
			throw new IllegalArgumentException("This species cannot be neutral");
		}
		if (gender != Gender.MALE && gr == 0) {
			throw new IllegalArgumentException("This species cannot be male");
		}
		if (gender != Gender.FEMALE && gr == 8) {
			throw new IllegalArgumentException("This species cannot be female");
		}
		this.gender = gender;
	}

	public PokemonInstanceBuilder setLevel(int level) {
		if (level <= 0 || level > 100) {
			throw new IllegalArgumentException(level + " is not a valid level");
		}
		this.level = level;
		return this;
	}

	public PokemonInstanceBuilder setMoves(Move[] moves) {
		if (moves == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.moves = moves;
		return this;
	}

	public PokemonInstanceBuilder setNature(Nature nature) {
		if (nature == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.nature = nature;
		return this;
	}

	/**
	 * Creates a new PokemonInstance with previously specified attributes. If
	 * one or more attributes haven't been set, this method will choose valid
	 * default values based on the species' properites. Each subsequent call to
	 * this method will return a newly created PokemonInstance, even if no
	 * attributes have been modified since the last call.
	 * 
	 * @return the PokemonInstance represented by this builder
	 */
	public PokemonInstance makePokemon() {
		// ability
		if (this.abilityId == 0) {
			// need to set id -> choose randomly between non-hidden abilities
		}
		// moves
		if (this.moves == null) {
			// determine four latest (by level) learnable moves
			// TODO Test this, probably white-box!
			Move[] moves;
			int[] levels = species.getLevelsForMovesLearnableByLevel();
			// index of the latest learnable move
			int index = Arrays.binarySearch(levels, this.level);
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
			Move[] allMoves = species.getMovesLearnableByLevel();
			moves = Arrays.copyOfRange(allMoves, (index >= 4) ? index - 4 : 0,
					index);
			moves = Arrays.copyOf(moves, 4); // in case index < 3
			this.moves = moves;
		}
		PokemonInstance pi = new PokemonInstance(abilityId, deterValues,
				effortValues, experiencePoints, gender, level, moves, nature,
				nickname, species, statusProblem);
		return pi;
	}
}
