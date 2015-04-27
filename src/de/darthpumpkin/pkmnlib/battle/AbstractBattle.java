package de.darthpumpkin.pkmnlib.battle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Superclass for all battle implementations
 * 
 * @author dominik
 * 
 */
public abstract class AbstractBattle implements Serializable {

	protected final Player[] players;
	protected Weather weather;
	protected int trickRoomCounter; // remaining turns where trick room is
									// active
	protected List<AtomarBattleAction> actionLog;

	protected void log(AtomarBattleAction action) {
		actionLog.add(action);
	}

	public AbstractBattle(Player[] players, Weather weather) {
		this.players = players;
		this.weather = weather;
		this.trickRoomCounter = 0;
		this.actionLog = new ArrayList<AtomarBattleAction>();
	}

	/**
	 * start the battle.
	 */
	public abstract void start();

	/**
	 * executes the turns submitted by the players and applies the results
	 * 
	 * @param turns
	 *            the submitted turns mapped to their respective player
	 */
	public abstract void doTurn(Map<Player, Turn> turns);

	/**
	 * determines whether the battle is over
	 * 
	 * @return false if the battle is over, true if not
	 */
	public abstract boolean isActive();

	/**
	 * clear and return the actionLog
	 * 
	 * @return
	 */
	public List<AtomarBattleAction> dumpActionLog() {
		List<AtomarBattleAction> latestLog = actionLog;
		this.actionLog = new ArrayList<AtomarBattleAction>();
		return latestLog;
	}

	public Player[] getPlayers() {
		return players;
	}

	public Weather getWeather() {
		return weather;
	}
}