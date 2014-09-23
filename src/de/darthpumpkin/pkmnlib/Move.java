package de.darthpumpkin.pkmnlib;

import java.io.Serializable;
import java.util.List;

import de.darthpumpkin.pkmnlib.battle.AtomarMove;

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
	private int pp;
	private int accuracy;
	private int priority;
	private int target;
}

/*
 * can't enumerate everything; which enums make sense?
 */
enum DamageClass {
	STATUS, PHYSICAL, SPECIAL
}
