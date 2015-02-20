package de.darthpumpkin.pkmnlib.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.darthpumpkin.pkmnlib.DummySpeciesFactory;
import de.darthpumpkin.pkmnlib.Gender;
import de.darthpumpkin.pkmnlib.Move;
import de.darthpumpkin.pkmnlib.PokemonInstance;
import de.darthpumpkin.pkmnlib.PokemonInstanceBuilder;
import de.darthpumpkin.pkmnlib.PokemonSpeciesFactory;

/**
 * Small test for {@link PokemonInstanceBuilder}. Only tests Bulbasaurs.
 * 
 * @author dominik
 * 
 */
public class PokemonInstanceBuilderTest {

	private static PokemonSpeciesFactory factory = null;
	private PokemonInstanceBuilder bulbaBuilder = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		factory = new DummySpeciesFactory();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bulbaBuilder = new PokemonInstanceBuilder(factory.getSpeciesById(1));
	}

	@After
	public void tearDown() throws Exception {
		bulbaBuilder = null;
	}

	/**
	 * Tests if {@link PokemonInstanceBuilder#makePokemon()} returns a fully
	 * functional {@link PokemonInstance}, even if attributes haven't been set.
	 */
	@Test
	public void testMakePokemonEmpty() {
		assertValidPokemonInstance(bulbaBuilder.makePokemon());
	}

	/**
	 * Tests if {@link PokemonInstanceBuilder#makePokemon()} returns a fully
	 * functional {@link PokemonInstance} when using all setters with valid
	 * values.
	 */
	@Test
	public void testMakePokemonAllRegular() {
		int abilityId = 65;
		bulbaBuilder.setAbilityId(abilityId); // overgrow
		int[] dVs = new int[] { 14, 15, 16, 17, 18, 19 };
		bulbaBuilder.setDeterminantValues(dVs);
		int[] eVs = new int[] { 50, 60, 70, 80, 90, 100 };
		bulbaBuilder.setEffortValues(eVs);
		int level = 14;
		bulbaBuilder.setLevel(level);
		// Moves are not fully implemented yet
		// bulbaBuilder.setMoves(moves)
		PokemonInstance i = bulbaBuilder.makePokemon();
		assertTrue(i.getAbilityId() == abilityId);
		assertTrue(i.getDeterValues().equals(dVs));
		assertTrue(i.getEffortValues().equals(eVs));
		assertTrue(i.getLevel() == level);
		assertValidPokemonInstance(i);
	}

	/**
	 * Tests if default moves are the four latest learnable ones. (level 1)
	 */
	@Test
	public void testDefaultMoves1() {
		bulbaBuilder.setLevel(1);
		PokemonInstance i = bulbaBuilder.makePokemon();
		assertValidPokemonInstance(i);
		Move[] moves = i.getMoves();
		List<Integer> indicesForNotNullMove = new ArrayList<Integer>();
		for (int j = 0; j < 4; j++) {
			if (moves[j] != null) {
				indicesForNotNullMove.add(j);
			}
		}
		int numOfNotNullEntries = indicesForNotNullMove.size();
		assertTrue("There are " + numOfNotNullEntries
				+ " not-null moves instead of " + 1, numOfNotNullEntries == 1);
		assertTrue(moves[indicesForNotNullMove.get(0)].getId() == 33);
	}

	/**
	 * Tests if default moves are the four latest learnable ones. (level 2)
	 */
	@Test
	public void testDefaultMoves2() {
		bulbaBuilder.setLevel(2);
		PokemonInstance i = bulbaBuilder.makePokemon();
		assertValidPokemonInstance(i);
		Move[] moves = i.getMoves();
		List<Integer> indicesForNotNullMove = new ArrayList<Integer>();
		for (int j = 0; j < 4; j++) {
			if (moves[j] != null) {
				indicesForNotNullMove.add(j);
			}
		}
		int numOfNotNullEntries = indicesForNotNullMove.size();
		assertTrue("There are " + numOfNotNullEntries
				+ " not-null moves instead of " + 1, numOfNotNullEntries == 1);
		assertTrue(moves[indicesForNotNullMove.get(0)].getId() == 33);
	}

	/**
	 * Tests if default moves are the four latest learnable ones. (level 3)
	 */
	@Test
	public void testDefaultMoves3() {
		bulbaBuilder.setLevel(3);
		PokemonInstance i = bulbaBuilder.makePokemon();
		assertValidPokemonInstance(i);
		Move[] moves = i.getMoves();
		List<Integer> indicesForNotNullMove = new ArrayList<Integer>();
		for (int j = 0; j < 4; j++) {
			if (moves[j] != null) {
				indicesForNotNullMove.add(j);
			}
		}
		int numOfNotNullEntries = indicesForNotNullMove.size();
		assertTrue("There are " + numOfNotNullEntries
				+ " not-null moves instead of " + 2, numOfNotNullEntries == 2);
		assertTrue(moves[indicesForNotNullMove.get(0)].getId() == 33
				|| moves[indicesForNotNullMove.get(0)].getId() == 45
				&& (moves[indicesForNotNullMove.get(1)].getId() == 33 || moves[indicesForNotNullMove
						.get(1)].getId() == 45));
	}

	/**
	 * Tests if default moves are the four latest learnable ones. (level 13,
	 * that is two new moves)
	 */
	@Test
	public void testDefaultMovesTwoAtOnce() {
		bulbaBuilder.setLevel(13);
		PokemonInstance i = bulbaBuilder.makePokemon();
		assertValidPokemonInstance(i);
		Move[] moves = i.getMoves();
		ArrayList<Integer> moveIds = new ArrayList<Integer>();
		for (Move m : moves) {
			moveIds.add(m.getId());
		}
		assertTrue(moveIds.contains(79));
		assertTrue(moveIds.contains(77));
		assertTrue(moveIds.contains(73));
		assertTrue(moveIds.contains(22));
	}

	/**
	 * Tests if fields are valid and consistent.
	 * 
	 * @param i
	 *            PokemonInstance to be tested.
	 */
	private void assertValidPokemonInstance(PokemonInstance i) {
		// check abilityId
		int[] abilityIds = i.getSpecies().getAbilities().clone();
		Arrays.sort(abilityIds);
		int abilityId = i.getAbilityId();
		assertTrue(Arrays.binarySearch(abilityIds, abilityId) >= 0);
		// check deterValues
		int minDeterValue = 0;
		int maxDeterValue = 31;
		int[] deterValues = i.getDeterValues();
		assertNotNull(deterValues);
		assertTrue(deterValues.length == 6);
		for (int dv : deterValues) {
			assertTrue(dv >= minDeterValue && dv <= maxDeterValue);
		}
		// check effortValues
		int minEffortValue = 0;
		int maxEffortValue = 255;
		int[] eVs = i.getEffortValues();
		assertNotNull(eVs);
		assertTrue(eVs.length == 6);
		int evSum = 0;
		for (int ev : eVs) {
			assertTrue(ev >= minEffortValue && ev <= maxEffortValue);
			evSum += ev;
		}
		assertTrue(evSum <= 510);
		// check experience points & level consistency
		int ep = i.getExperiencePoints();
		int level = i.getLevel();
		int epForNextLevel = i.getSpecies().requiredExperiencePointsForLevel(
				level + 1)
				- i.getSpecies().requiredExperiencePointsForLevel(level);
		assertTrue(ep >= 0);
		assertTrue(level > 0 && level <= 100);
		assertTrue(ep + " >= " + epForNextLevel + " (required for next level)",
				ep < epForNextLevel);
		// check gender
		Gender gender = i.getGender();
		int genderRate = i.getSpecies().getGenderRate();
		int[] validGenderRates;
		switch (gender) {
		case FEMALE:
			validGenderRates = new int[] { 1, 2, 4, 6, 7, 8 };
			break;
		case MALE:
			validGenderRates = new int[] { 0, 1, 2, 4, 6, 7 };
			break;
		case NEUTRAL:
			validGenderRates = new int[] { -1 };
			break;
		default:
			validGenderRates = new int[] {};
		}
		assertTrue(gender + " is not valid for genderRate " + genderRate,
				Arrays.binarySearch(validGenderRates, genderRate) >= 0);
		// distribution
		// check moves
		Move[] moves = i.getMoves();
		assertNotNull(moves);
		assertTrue(moves.length == 4);
		// TODO assert that moves are learnable by species
	}
}
