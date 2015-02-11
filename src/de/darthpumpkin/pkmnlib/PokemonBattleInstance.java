package de.darthpumpkin.pkmnlib;

import java.io.Serializable;

public class PokemonBattleInstance implements Serializable, ItemContainer {

	private PokemonInstance instance;
	private int[] temporaryStatModifiers; // from -6 to 6; reset after
											// switch-out
	private boolean flying; // in the air by using the move 'fly'
	private boolean confused;
	
	public PokemonBattleInstance(PokemonInstance instance) {
		this.instance = instance;
	}

	public PokemonInstance getInstance() {
		return instance;
	}

	public void setInstance(PokemonInstance instance) {
		this.instance = instance;
	}

	public int[] getTemporaryStatModifiers() {
		return temporaryStatModifiers;
	}

	public void setTemporaryStatModifiers(int[] temporaryStatModifiers) {
		this.temporaryStatModifiers = temporaryStatModifiers;
	}

	public boolean isFlying() {
		return flying;
	}

	public void setFlying(boolean flying) {
		this.flying = flying;
	}
	
	public boolean isConfused() {
		return confused;
	}

	public void setConfused(boolean confused) {
		this.confused = confused;
	}

	@Override
	public void remove(Item item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attach(Item item) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean holdsItem(Item item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean holdsItemInstanceOf(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * the actual value of the stat right wihtin the battle, respecting all
	 * temporary changes including +2 from swords dance etc.
	 * 
	 * @param stat
	 *            the stat you want computed
	 * @return
	 */
	public float getCurrent(Stat stat) {
		int c = instance.getStats()[stat.i()];
		int t = temporaryStatModifiers[stat.i()];
		if (t >= 0) {
			return c * (2 + t) / 2;
		}
		return c * 2 / (2 - t);
	}

	
}
