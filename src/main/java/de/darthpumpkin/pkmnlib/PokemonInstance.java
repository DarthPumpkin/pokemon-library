package de.darthpumpkin.pkmnlib;


/**
 * Use a {@link PokemonInstanceBuilder} to create a PokemonInstance from a
 * {@link PokemonSpecies}.</br></br> Represents an instance of a species, that
 * is one concrete living pokemon.
 * 
 * @author dominik
 * 
 */

public class PokemonInstance implements ItemContainer {

	private int abilityId;
	private int currentHp;
	private int[] deterValues;
	private int[] effortValues;
	private int experiencePoints; // those obtained since last level-up
	private Gender gender;
	private int level;
	private Move[] moves;
	private Nature nature; // from 1 to 25
	private String nickname; // null if not set
	private PokemonSpecies species;
	private StatusProblem statusProblem; // null if there is none

	/**
	 * Caution: Should only be called by {@link PokemonInstanceBuilder}. This
	 * creates a partially initialized object, with just its species set. All
	 * other attributes are unset. This is highly unsafe.
	 * 
	 * @param species
	 */
	PokemonInstance(PokemonSpecies species) {
		this.species = species;
	}

	/**
	 * Creates a PokemonInstance with all specified attributes, setting its
	 * current hp to its maximum hp. None of the attributes are checked for
	 * validity.<br>
	 * <br>
	 * Note that the more convenient and safe way of creating PokemonInstance
	 * objects is by using {@link PokemonBuilder}.
	 * 
	 * @param abilityId
	 * @param deterValues
	 * @param effortValues
	 * @param experiencePoints
	 * @param gender
	 * @param level
	 * @param moves
	 * @param nature
	 * @param nickname
	 * @param species
	 * @param statusProblem
	 */
	PokemonInstance(int abilityId, int[] deterValues, int[] effortValues,
			int experiencePoints, Gender gender, int level, Move[] moves,
			Nature nature, String nickname, PokemonSpecies species,
			StatusProblem statusProblem) {
		this.abilityId = abilityId;
		this.deterValues = deterValues;
		this.effortValues = effortValues;
		this.experiencePoints = experiencePoints;
		this.gender = gender;
		this.level = level;
		this.moves = moves;
		this.nature = nature;
		this.nickname = nickname;
		this.species = species;
		this.statusProblem = statusProblem;
		// Full HP as default
		this.currentHp = getStats()[Stat.HP.i()];
	}

	/**
	 * subtracts damage from this pkmn's current hp. Set to 0 if damage >
	 * {@link #getCurrentHp()}
	 * 
	 * @param damage
	 *            damage to be inflicted
	 */
	public void applyDamage(int damage) {
		currentHp -= damage;
		if (currentHp < 0) {
			currentHp = 0;
			statusProblem = null;
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

	/**
	 * 
	 * @return Amount of additional ep needed to proceed to next level.
	 */
	public int requiredExperiencePointsToNextLevel() {
		return this.species.requiredExperiencePointsForLevel(this.level + 1)
				- totalExperiencePoints();
	}

	/**
	 * 
	 * @return The total amount of experience points of this instance, that is
	 *         the sum of experience points required to reach the current level
	 *         and the experience points gathered since last level-up
	 */
	public int totalExperiencePoints() {
		return this.species.requiredExperiencePointsForLevel(this.level)
				+ this.experiencePoints;
	}

	/**
	 * increase the total amount of experience points by the specified value.
	 * Level-up if needed.
	 * 
	 * @param ep
	 */
	public void obtainExperiencePoints(int ep) {
		if (ep <= 0) {
			throw new IllegalArgumentException("negative amount is not valid");
		}
		this.experiencePoints += ep;
		// check if level-up
		while (requiredExperiencePointsToNextLevel() < 0 && this.level < 100) {
			// Syntactic sugar; lowers(!) the current xp by the difference
			// between the required xp for current and next level
			this.experiencePoints += this.species
					.requiredExperiencePointsForLevel(this.level++)
					- this.species.requiredExperiencePointsForLevel(this.level);
		}
		if (this.level == 100) {
			this.experiencePoints = 0;
		}
	}

	public int getAbilityId() {
		return abilityId;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public int[] getDeterValues() {
		return deterValues;
	}

	public int[] getEffortValues() {
		return effortValues;
	}

	public int getExperiencePoints() {
		return experiencePoints;
	}

	public Gender getGender() {
		return gender;
	}

	public int getLevel() {
		return this.level;
	}

	public Move[] getMoves() {
		return moves;
	}

	public Nature getNature() {
		return nature;
	}

	public String getNickname() {
		return nickname;
	}

	public PokemonSpecies getSpecies() {
		return this.species;
	}

	/**
	 * computes the current stats respecting the basic stats, level, eVs and
	 * dVs. For HP, this returns the maximum hp. For the actual current hp, see
	 * {@link #getCurrentHp()}
	 * 
	 * @return
	 */
	public int[] getStats() {
		int[] stats = new int[6];
		int hpI = Stat.HP.i();
		stats[0] = (int) ((getDeterValues()[hpI] + 2
				* species.getBaseStats()[hpI] + getEffortValues()[hpI] / 4 + 100)
				* getLevel() / 100 + 10);
		for (int i = 1; i < 6; i++) {
			stats[i] = (int) (((getDeterValues()[i] + 2
					* species.getBaseStats()[i] + getEffortValues()[i] / 4)
					* getLevel() / 100 + 5) * nature.getStatFactors()[i]);
		}
		return stats;
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
		return currentHp > 0;
	}

	public void setAbilityId(int abilityId) {
		this.abilityId = abilityId;
	}

	public void setCurrentHp(int currentHp) {
		this.currentHp = currentHp;
	}

	public void setDeterValues(int[] deterValues) {
		this.deterValues = deterValues;
	}

	public void setEffortValues(int[] effortValues) {
		this.effortValues = effortValues;
	}

	/**
	 * Caution: this method does not automatically level up, thus it may leave
	 * the PokemonInstance in an illegal state. This is not intended for regular
	 * xp gain. For obtaining experience points, see
	 * {@link PokemonInstance#obtainExperiencePoints(int)}
	 * 
	 * @param experiencePoints
	 */
	public void setExperiencePoints(int experiencePoints) {
		this.experiencePoints = experiencePoints;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setMoves(Move[] moves) {
		this.moves = moves;
	}

	public void setNature(Nature nature) {
		this.nature = nature;
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