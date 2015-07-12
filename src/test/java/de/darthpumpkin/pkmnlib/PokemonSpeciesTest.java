/**
 * 
 */
package de.darthpumpkin.pkmnlib;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import net.alexmack.poketypes.Poketype;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.darthpumpkin.pkmnlib.DummySpeciesFactory;
import de.darthpumpkin.pkmnlib.PokemonSpecies;
import de.darthpumpkin.pkmnlib.PokemonSpeciesFactory;

/**
 * Provisional test for PokemonSpecies. Only tests Bulbasaurs, because
 * DummySpeciesFactory can only instanciate bulbasaur s.
 * 
 * @author dominik
 * 
 */
public class PokemonSpeciesTest {

	private static PokemonSpeciesFactory factory = null;
	private PokemonSpecies s = null;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		factory = new DummySpeciesFactory();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		s = factory.getSpeciesById(1);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		s = null;
	}

	/**
	 * Test method for
	 * {@link de.darthpumpkin.pkmnlib.PokemonSpecies#isOfType(de.darthpumpkin.pkmnlib.Type)}
	 * .
	 */
	@Test
	public void testIsOfType() {
		Poketype grass = Poketype.GRASS;
		Poketype poison = Poketype.POISON;
		Poketype flying = Poketype.FLYING;
		assertTrue(s.isOfType(grass));
		assertTrue(s.isOfType(poison));
		assertFalse(s.isOfType(flying));
	}

	/**
	 * Tests
	 * {@link de.darthpumpkin.pkmnlib.PokemonSpecies#isOfType(de.darthpumpkin.pkmnlib.Type)}
	 * for the correct handling of null arguments
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsOfTypeNull() {
		s.isOfType(null);
	}

	/**
	 * Tests
	 * {@link de.darthpumpkin.pkmnlib.PokemonSpecies#requiredExperiencePointsForLevel(int)}
	 * for correctness (white-box, see actual method). Reference values are
	 * copied from <a href=
	 * "http://bulbapedia.bulbagarden.net/wiki/Experience#Experience_at_each_level"
	 * >here</a>.
	 */
	@Test
	public void testRequiredExperiencePointsForLevel() {
		HashMap<Integer, Integer> epMap = new HashMap<Integer, Integer>();
		epMap.put(1, 0);
		epMap.put(2, 9);
		epMap.put(3, 57);
		epMap.put(4, 96);
		epMap.put(5, 135);
		epMap.put(6, 179);
		epMap.put(14, 1612);
		epMap.put(15, 2035);
		epMap.put(16, 2535);
		epMap.put(35, 36435);
		epMap.put(36, 40007);
		epMap.put(37, 43808);
		epMap.put(49, 109923);
		epMap.put(50, 117360);
		epMap.put(51, 125126);
		epMap.put(67, 300140);
		epMap.put(68, 314618);
		epMap.put(69, 329555);
		epMap.put(97, 963632);
		epMap.put(98, 995030);
		epMap.put(99, 1027103);
		epMap.put(100, 1059860);
		for (int lvl : epMap.keySet()) {
			int neededXp = epMap.get(lvl);
			int actualXp = s.requiredExperiencePointsForLevel(lvl);
			assertTrue("Level " + lvl + " requires " + neededXp
					+ " xp instead of " + actualXp, neededXp == actualXp);
		}
	}

}
