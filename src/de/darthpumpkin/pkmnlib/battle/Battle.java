package de.darthpumpkin.pkmnlib.battle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Superclass for all battle implementations
 * @author dominik
 *
 */
@SuppressWarnings("serial")
public abstract class Battle implements Serializable {
	
	protected final Player[] players;
	protected Weather weather;
	protected List<AtomarBattleAction> actionLog;
	
	public Battle(Player[] players) {
		this.players = players;
	}
	
	/**
	 * executes the turns submitted by the players and applies the results
	 * @param turns the submitted turns mapped to their respective player
	 */
	public abstract void doTurn(Map<Player, Turn> turns);
	
	/**
	 * clear and return the actionLog
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

enum Weather {
	NORMAL, SUN, RAIN, HAIL, SAND
}