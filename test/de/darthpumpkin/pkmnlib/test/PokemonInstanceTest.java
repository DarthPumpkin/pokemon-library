package de.darthpumpkin.pkmnlib.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.darthpumpkin.pkmnlib.DummySpeciesFactory;
import de.darthpumpkin.pkmnlib.Nature;
import de.darthpumpkin.pkmnlib.PokemonInstance;
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

	/**
	 * test {@link PokemonInstance#applyDamage(int)}
	 */
	@Test
	public void testApplyDamage() {
		PokemonInstance i = builder.makePokemon();
		int originalHp = i.getCurrentHp();
		i.applyDamage(1);
		assertTrue(i.getCurrentHp() == originalHp - 1);
		i.applyDamage(originalHp);
		assertTrue(i.getCurrentHp() == 0);
	}

	/**
	 * test {@link PokemonInstance#getStats()}. Reference data gathered from <a
	 * href="http://www.psypokes.com/dex/stats.php">psypokes.com</a>
	 */
	@Test
	public void testGetStats() {
		//[level, dvs] : stats
		PokemonInstance i = builder.makePokemon();
		assertArrayEquals(new int[]{19,9,9,11,11,9}, i.getStats());
		i.setLevel(1);
		assertArrayEquals(new int[]{11,5,5,6,6,5}, i.getStats());
		i.setLevel(78);
		assertArrayEquals(new int[]{158,81,81,106,106,75}, i.getStats());
		i.setLevel(100);
		assertArrayEquals(new int[]{200,103,103,135,135,95}, i.getStats());
		i.setDeterValues(new int[] {0,1,2,3,4,5});
		assertArrayEquals(new int[]{200,104,105,138,139,100}, i.getStats());
		i.setDeterValues(new int[]{31,31,31,31,31,31});
		assertArrayEquals(new int[]{231,134,134,166,166,126}, i.getStats());
		i.setEffortValues(new int[]{0,1,2,3,4,5});
		assertArrayEquals(new int[]{231,134,134,166,167,127}, i.getStats());
		i.setEffortValues(new int[]{252,252,6,0,0,0});
		assertArrayEquals(new int[]{294,197,135,166,166,126}, i.getStats());
		i.setEffortValues(new int[]{0,0,0,6,252,252});
		assertArrayEquals(new int[]{231,134,134,167,229,189}, i.getStats());
		i.setNature(Nature.timid);
		assertArrayEquals(new int[]{231,120,134,167,229,207}, i.getStats());
		i.setNature(Nature.adamant);
		assertArrayEquals(new int[]{231,147,134,150,229,189}, i.getStats());
	}

	@Test
	public void testIsUsable() {
		PokemonInstance i = builder.makePokemon();
		assertTrue(i.isUsable());
		i.setCurrentHp(0);
		assertTrue(!i.isUsable());
	}

	@Test
	public void testRequiredExperiencePointsToNextLevel() {
		PokemonInstance i = builder.makePokemon();
		assertEquals(44, i.requiredExperiencePointsToNextLevel());
		i.setExperiencePoints(20);
		assertEquals(24, i.requiredExperiencePointsToNextLevel());
		i.setLevel(30);
		i.setExperiencePoints(2000);
		assertEquals(534, i.requiredExperiencePointsToNextLevel());
	}

	@Test
	public void testTotalExperiencePoints() {
		PokemonInstance i = builder.makePokemon();
		assertEquals(135, i.totalExperiencePoints());
		i.setExperiencePoints(20);
		assertEquals(155, i.totalExperiencePoints());
		i.setLevel(30);
		i.setExperiencePoints(2000);
		assertEquals(23760, i.totalExperiencePoints());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testObtainExperiencePointsIllegal() {
		PokemonInstance i = builder.makePokemon();
		i.obtainExperiencePoints(-1);
	}
	
	@Test
	public void testObtainExperiencePoints() {
		PokemonInstance i = builder.makePokemon();
		// without level gain
		i.obtainExperiencePoints(1);
		assertEquals(5, i.getLevel());
		assertEquals(1, i.getExperiencePoints());
		// level-up
		i.obtainExperiencePoints(44);
		assertEquals(6, i.getLevel());
		assertEquals(1, i.getExperiencePoints());
		// multiple level-ups
		i.obtainExperiencePoints(240);
		assertEquals(9, i.getLevel());
		assertEquals(1, i.getExperiencePoints());
		// 0 xp at level 100
		i.obtainExperiencePoints(2000000);
		assertEquals(100, i.getLevel());
		assertEquals(0, i.getExperiencePoints());
		// no xp gain if already at 100
		i.obtainExperiencePoints(2000000);
		assertEquals(100, i.getLevel());
		assertEquals(0, i.getExperiencePoints());
	}

}
