package de.darthpumpkin.pkmnlib;

import java.util.EnumSet;

import net.alexmack.poketypes.Poketype;

/**
 * The meta data is similar to the table 'moves'. However, instead of effectId
 * and effectChance, Move contains a binary tree of {@link AtomarMove} instances
 * that can possibly be stored as an XML file (see diagram in the doc folder). I
 * consider this a better option than having a giant switch in the Battle
 * classes with all 396 effectIds.
 * 
 * @author dominik
 * 
 */

public class Move {

	public enum DamageClass {
		PHYSICAL, SPECIAL, STATUS
	}

	// TODO Upper-Case
	public enum MoveFlag {
		authentic, ballistics, bite, charge, contact, defrost, distance, gravity, heal, mental, mirror, nonSkyBattle, powder, protect, pulse, punch, recharge, reflectable, snatch, sound
	}

	private int accuracy;
	private int basePp;
	private int currentPp;
	private DamageClass damageClass;
	private EnumSet<MoveFlag> flags;
	private int id;
	private int maximumPp; // equals basePp if not raised by Ap-Plus etc.
	private int power;
	private int priority;
	// private AtomarMove rootElement;
	private AtomarMove successElement;
	private AtomarMove failureElement;
	// private int target;
	private Poketype type;

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

	public AtomarMove getFailureElement() {
		return failureElement;
	}

	public void setFailureElement(AtomarMove failureElement) {
		this.failureElement = failureElement;
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

	// public AtomarMove getRootElement() {
	// return rootElement;
	// }
	//
	// public void setRootElement(AtomarMove rootElement) {
	// this.rootElement = rootElement;
	// }

	public AtomarMove getSuccessElement() {
		return successElement;
	}

	public void setSuccessElement(AtomarMove successElement) {
		this.successElement = successElement;
	}

	public Poketype getType() {
		return type;
	}

	public void setType(Poketype type) {
		this.type = type;
	}

}
