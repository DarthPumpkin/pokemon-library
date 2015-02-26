package de.darthpumpkin.pkmnlib;

import java.io.Serializable;
import java.util.EnumSet;

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

	public enum DamageClass {
		PHYSICAL, SPECIAL, STATUS
	}

	public enum MoveFlag {
		authentic, ballistics, bite, charge, contact, defrost, distance, gravity, heal, mental, mirror, nonSkyBattle, powder, protect, pulse, punch, recharge, reflectable, snatch, sound
	}

	private int accuracy;
	private int basePp;
	private int currentPp;
	private DamageClass damageClass;
	private int effectChance;
	private int effectId;
	private EnumSet<MoveFlag> flags;
	private int id;
	private int maximumPp; // equals basePp if not raised by Ap-Plus etc.
	private int power;
	private int priority;
	// private int target;
	private Type type;

	/**
	 * Provisional constructor
	 * 
	 * @param id
	 */
	public Move(int id) {
		this.setId(id);
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public int getBasePp() {
		return basePp;
	}

	public void setBasePp(int basePp) {
		this.basePp = basePp;
	}

	public int getCurrentPp() {
		return currentPp;
	}

	public void setCurrentPp(int currentPp) {
		this.currentPp = currentPp;
	}

	public DamageClass getDamageClass() {
		return damageClass;
	}

	public void setDamageClass(DamageClass damageClass) {
		this.damageClass = damageClass;
	}

	public int getEffectChance() {
		return effectChance;
	}

	public void setEffectChance(int effectChance) {
		this.effectChance = effectChance;
	}

	public int getEffectId() {
		return effectId;
	}

	public void setEffectId(int effectId) {
		this.effectId = effectId;
	}

	public EnumSet<MoveFlag> getFlags() {
		return flags;
	}

	public void setFlags(EnumSet<MoveFlag> flags) {
		this.flags = flags;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMaximumPp() {
		return maximumPp;
	}

	public void setMaximumPp(int maximumPp) {
		this.maximumPp = maximumPp;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
