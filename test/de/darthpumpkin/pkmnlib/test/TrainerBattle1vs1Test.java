/**
 * 
 */
package de.darthpumpkin.pkmnlib.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.darthpumpkin.pkmnlib.DummySpeciesFactory;
import de.darthpumpkin.pkmnlib.PokemonBattleInstance;
import de.darthpumpkin.pkmnlib.PokemonInstance;
import de.darthpumpkin.pkmnlib.PokemonInstanceBuilder;
import de.darthpumpkin.pkmnlib.PokemonSpecies;
import de.darthpumpkin.pkmnlib.battle.DummyPlayer;
import de.darthpumpkin.pkmnlib.battle.Player;
import de.darthpumpkin.pkmnlib.battle.SingleBattlePlayer;
import de.darthpumpkin.pkmnlib.battle.TrainerBattle1vs1;
import de.darthpumpkin.pkmnlib.battle.Turn;
import de.darthpumpkin.pkmnlib.battle.Weather;

/**
 * @author dominik
 * 
 */
public class TrainerBattle1vs1Test {

	private static PokemonSpecies bulbaSpecies;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		bulbaSpecies = new DummySpeciesFactory().getSpeciesById(1);
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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link de.darthpumpkin.pkmnlib.battle.TrainerBattle1vs1#start()}.
	 */
	@Test
	public void testStart() {
		PokemonInstance[] team1 = new PokemonInstance[] { new PokemonInstanceBuilder(
				bulbaSpecies).setLevel(1).makePokemon() };
		PokemonInstance[] team2 = new PokemonInstance[] { new PokemonInstanceBuilder(
				bulbaSpecies).setLevel(1).makePokemon() };
		SingleBattlePlayer[] players = new DummyPlayer[] {
				new DummyPlayer(team1), new DummyPlayer(team2) };
		TrainerBattle1vs1 battle = new TrainerBattle1vs1(players,
				Weather.NORMAL);
		assertEquals(battle.getWeather(), Weather.NORMAL);

		battle.start();
		assertSame(battle.getActivePkmns()[0].getInstance(), team1[0]);
		assertSame(battle.getActivePkmns()[1].getInstance(), team2[0]);
		assertTrue(battle.isActive());
	}

	// TODO test case for start() with multiple pkmns; cheking if the first pkmn
	// is sent out

	/**
	 * Test method for
	 * {@link de.darthpumpkin.pkmnlib.battle.TrainerBattle1vs1#doTurn()}.
	 * Easiest case: Two lvl 1 bulbasaurs using tackle at full health
	 */
	@Test
	public void testDoTurn1() {
		TrainerBattle1vs1 battle = makeEasyBulbaBattle();
		battle.start();
		SingleBattlePlayer[] players = (SingleBattlePlayer[]) battle
				.getPlayers();
		HashMap<Player, Turn> turns = new HashMap<Player, Turn>();
		for (Player p : players) {
			turns.put(p, p.makeTurn());
		}
		battle.doTurn(turns);
		for (PokemonBattleInstance pbi : battle.getActivePkmns()) {
			assertEquals(8, pbi.getInstance().getCurrentHp());
		}
	}
	
	// TODO more test cases for doTurn()

	private TrainerBattle1vs1 makeEasyBulbaBattle() {
		PokemonInstance[] team1 = new PokemonInstance[] { new PokemonInstanceBuilder(
				bulbaSpecies).setLevel(1).makePokemon() };
		PokemonInstance[] team2 = new PokemonInstance[] { new PokemonInstanceBuilder(
				bulbaSpecies).setLevel(1).makePokemon() };
		SingleBattlePlayer[] players = new DummyPlayer[] {
				new DummyPlayer(team1), new DummyPlayer(team2) };
		return new TrainerBattle1vs1(players, Weather.NORMAL);
	}

}
