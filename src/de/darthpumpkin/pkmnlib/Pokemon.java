package de.darthpumpkin.pkmnlib;

import java.io.Serializable;

import de.darthpumpkin.pkmnlib.Pokemon.Stat;

/**
 * 
 * @author dominik
 * 
 */

@SuppressWarnings("serial")
public class Pokemon implements Serializable, ItemContainer {
	
	public enum Stat {
		HP(0), ATK(1), DEF(2), SPATK(3), SPDEF(4), SPEED(5), ACCURACY(6), EVASION(7);
		
		private int index;
		Stat(int index) {
			this.index = index;
		}
		public int i() {
			return index;
		}
	}

	private int[] baseStats;
	private int[] currentStats; //those you would see in the game
	private int[] temporaryStatModifiers; //from -6 to 6; reset after switch-out
	
	/**
	 * 
	 * @return true if it can be used in a battle. False if not (fainted, egg,
	 *         etc.)
	 */
	public boolean isUsable() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getAbility() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void remove(Item item) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void attach(Item item) {
		// TODO Auto-generated method stub
		
	}

	public int[] getBaseStats() {
		return baseStats;
	}

	public void setBaseStats(int[] baseStats) {
		this.baseStats = baseStats;
	}

	public int[] getCurrentStats() {
		return currentStats;
	}

	public void setCurrentStats(int[] currentStats) {
		this.currentStats = currentStats;
	}

	public int[] getTemporaryStatModifiers() {
		return temporaryStatModifiers;
	}

	public void setTemporaryStatModifiers(int[] temporaryStatModifiers) {
		this.temporaryStatModifiers = temporaryStatModifiers;
	}

	public float getCurrent(Stat stat) {
		int c = currentStats[stat.i()];
		int t = temporaryStatModifiers[stat.i()];
		if (t >= 0) {
			return c*(2+t)/2;
		}
		return c*2/(2-t);
	}

}