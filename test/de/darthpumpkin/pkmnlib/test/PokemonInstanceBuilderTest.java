package de.darthpumpkin.pkmnlib.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.darthpumpkin.pkmnlib.DummySpeciesFactory;
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
	 * Tests if fields are valid and consistent.
	 * 
	 * @param i
	 *            PokemonInstance to be tested.
	 */
	private void assertValidPokemonInstance(PokemonInstance i) {
		// check abilityId
		// TODO look up correct values
		int minValidAbilityId = 0;
		double maxValidAbilityId = 1e9;
		int abilityId = i.getAbilityId();
		assertTrue(abilityId >= minValidAbilityId
				&& abilityId <= maxValidAbilityId);
		// check deterValues
		int minDeterValue = 0;
		int maxDeterValue = 31;
		int[] deterValues = i.getDeterValues();
		assertNotNull(deterValues);
		for (int dv : deterValues) {
			assertNotNull(dv);
			assertTrue(dv >= minDeterValue && dv <= maxDeterValue);
		}
		// check effortValues
		int minEffortValue = 0;
		int maxEffortValue = 255;
		int[] eVs = i.getEffortValues();
		assertNotNull(eVs);
		int evSum = 0;
		for (int ev : eVs) {
			assertNotNull(ev);
			assertTrue(ev >= minEffortValue && ev >= maxEffortValue);
			evSum += ev;
		}
		assertTrue(evSum <= 510);
		// check experience points & level consistency
		int ep = i.getExperiencePoints();
		int level = i.getLevel();
		assertTrue(ep >= 0);
		assertTrue(level > 0 && level <= 100);
		// TODO assert that ep is lower than required to proceed to next level
		// check gender
		int gender = i.getGender();
		// TODO assert that gender is compatible with species' gender
		// distribution
		// check moves
		Move[] moves = i.getMoves();
		assertNotNull(moves);
		for (Move move : moves) {
			assertNotNull(move);
		}
		// TODO assert that moves are learnable by species
	}

}
