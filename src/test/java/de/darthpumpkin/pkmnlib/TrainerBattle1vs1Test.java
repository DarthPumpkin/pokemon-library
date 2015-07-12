/**
 * 
 */
package de.darthpumpkin.pkmnlib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
		TrainerBattle1vs1 battle = makeEasyBulbaBattle(1);
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

	/**
	 * Test method for
	 * {@link de.darthpumpkin.pkmnlib.battle.TrainerBattle1vs1#doTurn()}. 2nd
	 * case: Two lvl 12 bulbasaurs using tackle at full health (lvl 12 is the
	 * last one before forgetting tackle)
	 */
	@Test
	public void testDoTurn2() {
		TrainerBattle1vs1 battle = makeEasyBulbaBattle(10);
		battle.start();
		SingleBattlePlayer[] players = (SingleBattlePlayer[]) battle
				.getPlayers();
		HashMap<Player, Turn> turns = new HashMap<Player, Turn>();
		for (Player p : players) {
			turns.put(p, p.makeTurn());
		}
		battle.doTurn(turns);
		for (PokemonBattleInstance pbi : battle.getActivePkmns()) {
			assertEquals(22.0, pbi.getInstance().getCurrentHp(), 1.001);
		}
	}

	/**
	 * Test method for
	 * {@link de.darthpumpkin.pkmnlib.battle.TrainerBattle1vs1#doTurn()}. 
	 * 3rd case: first bulbasaur faints
	 */
	@Test
	public void testDoTurnFaint() {
		TrainerBattle1vs1 battle = makeEasyBulbaBattle(1);
		battle.start();
		SingleBattlePlayer[] players = (SingleBattlePlayer[]) battle.getPlayers();
		PokemonInstance weakPkmn = players[0].getActivePokemon().getInstance(); 
		weakPkmn.applyDamage(10);
		assertEquals(1, players[0].getTeam()[0].getCurrentHp());
		HashMap<Player, Turn> turns = new HashMap<Player, Turn>();
		for (Player p : players) {
			turns.put(p, p.makeTurn());
		}
		battle.doTurn(turns);
		assertEquals(0, weakPkmn.getCurrentHp());
		assertFalse(weakPkmn.isUsable());
		assertFalse(battle.isActive());
	}

	// TODO more test cases for doTurn()

	private TrainerBattle1vs1 makeEasyBulbaBattle(int level) {
		PokemonInstance[] team1 = new PokemonInstance[] { new PokemonInstanceBuilder(
				bulbaSpecies).setLevel(level).makePokemon() };
		PokemonInstance[] team2 = new PokemonInstance[] { new PokemonInstanceBuilder(
				bulbaSpecies).setLevel(level).makePokemon() };
		SingleBattlePlayer[] players = new DummyPlayer[] {
				new DummyPlayer(team1), new DummyPlayer(team2) };
		return new TrainerBattle1vs1(players, Weather.NORMAL);
	}

}
