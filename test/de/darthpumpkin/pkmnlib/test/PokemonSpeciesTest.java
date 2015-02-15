/**
 * 
 */
package de.darthpumpkin.pkmnlib.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.darthpumpkin.pkmnlib.DummySpeciesFactory;
import de.darthpumpkin.pkmnlib.PokemonSpecies;
import de.darthpumpkin.pkmnlib.PokemonSpeciesFactory;
import de.darthpumpkin.pkmnlib.Type;

/**
 * Provisional test for PokemonSpecies. Only tests Bulbasaurs, because
 * DummySpeciesFactory can only instanciate bulbasaur species.
 * 
 * @author dominik
 * 
 */
public class PokemonSpeciesTest {

	private static PokemonSpeciesFactory factory = null;
	private PokemonSpecies species = null;

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
		species = factory.getSpeciesById(1);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		species = null;
	}

	/**
	 * Test method for
	 * {@link de.darthpumpkin.pkmnlib.PokemonSpecies#isOfType(de.darthpumpkin.pkmnlib.Type)}
	 * .
	 */
	@Test
	public void testIsOfType() {
		Type grass = Type.GRASS;
		Type poison = Type.POISON;
		Type flying = Type.FLYING;
		assertTrue(species.isOfType(grass));
		assertTrue(species.isOfType(poison));
		assertFalse(species.isOfType(flying));
	}

	/**
	 * Tests
	 * {@link de.darthpumpkin.pkmnlib.PokemonSpecies#isOfType(de.darthpumpkin.pkmnlib.Type)}
	 * for the correct handling of null arguments
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsOfTypeNull() {
		species.isOfType(null);
	}

}
