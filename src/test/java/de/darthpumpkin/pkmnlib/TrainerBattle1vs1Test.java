/**
 * 
 */
package de.darthpumpkin.pkmnlib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
		PokemonInstanceBuilder b = new PokemonInstanceBuilder(bulbaSpecies)
				.setLevel(1);
		UniqueBoundedList<PokemonInstance> team1 = new UniqueBoundedList<>();
		team1.add(b.makePokemon());
		UniqueBoundedList<PokemonInstance> team2 = new UniqueBoundedList<>();
		team2.add(b.makePokemon());
		SingleBattlePlayer[] players = new DummyPlayer[] {
				new DummyPlayer(team1), new DummyPlayer(team2) };
		TrainerBattle1vs1 battle = new TrainerBattle1vs1(players,
				Weather.NORMAL);
		assertEquals(battle.getWeather(), Weather.NORMAL);

		battle.start();
		assertSame(battle.getActivePkmns()[0].getInstance(), team1.get(0));
		assertSame(battle.getActivePkmns()[1].getInstance(), team2.get(0));
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
	public void testDamageCalculation1() {
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
	public void testDamageCalculation2() {
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
	 * {@link de.darthpumpkin.pkmnlib.battle.TrainerBattle1vs1#doTurn()}. 3rd
	 * case: Two lvl 1 bulbasaurs at 1 HP each. First bulbasaur faints, battle
	 * ends
	 */
	@Test
	public void testBattleFinishesAfterFaint() {
		TrainerBattle1vs1 battle = makeEasyBulbaBattle(1);
		battle.start();
		SingleBattlePlayer[] players = (SingleBattlePlayer[]) battle
				.getPlayers();
		PokemonInstance weakPkmn = players[0].getActivePokemon().getInstance();
		weakPkmn.applyDamage(10);
		assertEquals(1, players[0].getTeam().get(0).getCurrentHp());
		HashMap<Player, Turn> turns = new HashMap<Player, Turn>();
		for (Player p : players) {
			turns.put(p, p.makeTurn());
		}
		battle.doTurn(turns);
		assertEquals(0, weakPkmn.getCurrentHp());
		assertFalse(weakPkmn.isUsable());
		assertFalse(battle.isActive());
	}

	/**
	 * Test method for
	 * {@link de.darthpumpkin.pkmnlib.battle.TrainerBattle1vs1#doTurn()}. 4th
	 * case: bulbasaur faints, new bulbasaur is sent in
	 */
	@Test
	public void testSendInNextPkmnAfterFaint() {
		TrainerBattle1vs1 battle = makeEasyBulbaBattle(1);
		SingleBattlePlayer[] players = battle.getPlayers();
		SingleBattlePlayer playerWith2Pkmn = battle.getPlayers()[0];
		SingleBattlePlayer playerWith1Pkmn = battle.getPlayers()[1];
		PokemonInstance scndBulba = new PokemonInstanceBuilder(bulbaSpecies)
				.setLevel(1).makePokemon();
		playerWith2Pkmn.getTeam().add(scndBulba);
		playerWith2Pkmn.getTeam().get(0).applyDamage(10);
		battle.start();
		Map<Player, Turn> turns = new HashMap<Player, Turn>();
		for (Player p : players) {
			turns.put(p, p.makeTurn());
		}
		battle.doTurn(turns);
		assertFalse(playerWith2Pkmn.getTeam().get(0).isUsable());
		assertSame(scndBulba, playerWith2Pkmn.getActivePokemon().getInstance());
		assertSame(playerWith1Pkmn.getTeam().get(0), playerWith1Pkmn
				.getActivePokemon().getInstance());
		assertEquals(11, scndBulba.getCurrentHp());
		assertTrue(battle.isActive());
	}

	/**
	 * Test method for
	 * {@link de.darthpumpkin.pkmnlib.battle.TrainerBattle1vs1#doTurn()}. Test
	 * that turns are executed in the order specified by
	 * {@link Turn#compareTo(Turn)}, additionally taking into account the
	 * pokemons' speed and the battle's state.
	 */
	@Test
	public void testTurnOrder() {
		fail("Not yet implemented");
	}

	// TODO more test cases for doTurn()

	private TrainerBattle1vs1 makeEasyBulbaBattle(int level) {
		PokemonInstanceBuilder b = new PokemonInstanceBuilder(bulbaSpecies)
				.setLevel(level);
		UniqueBoundedList<PokemonInstance> team1 = new UniqueBoundedList<>();
		team1.add(b.makePokemon());
		UniqueBoundedList<PokemonInstance> team2 = new UniqueBoundedList<>();
		team2.add(b.makePokemon());
		SingleBattlePlayer[] players = new DummyPlayer[] {
				new DummyPlayer(team1), new DummyPlayer(team2) };
		return new TrainerBattle1vs1(players, Weather.NORMAL);
	}
}
