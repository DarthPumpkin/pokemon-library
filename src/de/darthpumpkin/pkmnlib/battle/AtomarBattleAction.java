package de.darthpumpkin.pkmnlib.battle;

import java.io.Serializable;

import de.darthpumpkin.pkmnlib.AtomarMove;

/**
 * AtomarBattleActions are the smallest pieces of actions that can happen in a
 * battle. A single move can cause multiple AtomarBattleActions, e.g. damage +
 * stat change + paralysis + ... In damage calculation, they are applied
 * successively
 * 
 * @author dominik
 * 
 */
@SuppressWarnings("serial")
public class AtomarBattleAction implements Serializable {

	/**
	 * 
	 * @param am What happened?
	 * @param value How much happened?
	 * @param causerId Who caused it?
	 * @param targetId To whom did it happen?
	 */
	public AtomarBattleAction(AtomarMove am, int value, int causerId,
			int targetId) {
		this.am = am;
		this.value = value;
		this.causerId = causerId;
		this.targetId = targetId;
	}
	private AtomarMove am;	//What happened?
	private int value;		//How much happened?
	private int causerId;	//Who caused it?
	private int targetId;	//To whom did it happen?
}
