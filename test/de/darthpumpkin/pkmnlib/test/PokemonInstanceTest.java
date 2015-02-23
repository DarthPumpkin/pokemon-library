package de.darthpumpkin.pkmnlib.test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.darthpumpkin.pkmnlib.DummySpeciesFactory;
import de.darthpumpkin.pkmnlib.PokemonInstanceBuilder;
import de.darthpumpkin.pkmnlib.PokemonSpeciesFactory;

public class PokemonInstanceTest {
	
	private static PokemonSpeciesFactory factory;
	private PokemonInstanceBuilder builder;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		factory = new DummySpeciesFactory();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		factory = null;
	}

	@Before
	public void setUp() throws Exception {
		builder = new PokemonInstanceBuilder(factory.getSpeciesById(001));
	}

	@After
	public void tearDown() throws Exception {
		builder = null;
	}

	@Test
	public void testApplyDamage() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStats() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsUsable() {
		fail("Not yet implemented");
	}

	@Test
	public void testRequiredExperiencePointsToNextLevel() {
		fail("Not yet implemented");
	}

	@Test
	public void testTotalExperiencePoints() {
		fail("Not yet implemented");
	}

	@Test
	public void testObtainExperiencePoints() {
		fail("Not yet implemented");
	}

}
