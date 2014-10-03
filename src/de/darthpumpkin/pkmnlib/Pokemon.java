package de.darthpumpkin.pkmnlib;

import java.io.Serializable;

/**
 * 
 * @author dominik
 * 
 */

@SuppressWarnings("serial")
public class Pokemon implements Serializable {

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

}
