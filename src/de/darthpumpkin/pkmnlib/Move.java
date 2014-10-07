package de.darthpumpkin.pkmnlib;

import java.io.Serializable;

/**
 * This move model is designed after veekun's db, especially the table 'moves'
 * 
 * @author dominik
 * 
 */

/*
 * maybe only use effectId and effectChance as high-level interface, but
 * internally split it into AtomarMoves for easier processing?
 */
@SuppressWarnings("serial")
public class Move implements Serializable {

	private String name;
	private DamageClass damageClass;
	private Type type;

	private int effectId;
	private int effectChance;

	private int power;
	private int currentPp;
	private int maximumPp;	//equals basePp if not raised by Ap-Plus etc.
	private int basePp;
	private int accuracy;
	private int priority;
	private int target;
	
	
	public int getPriority() {
		return priority;
	}

	public int getEffectId() {
		return effectId;
	}

	public int getCurrentPp() {
		return currentPp;
	}
}

/*
 * can't enumerate everything; which enums make sense?
 */
enum DamageClass {
	STATUS, PHYSICAL, SPECIAL
}
