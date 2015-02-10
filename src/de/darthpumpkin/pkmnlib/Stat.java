package de.darthpumpkin.pkmnlib;

public enum Stat {
	HP(0), ATK(1), DEF(2), SPATK(3), SPDEF(4), SPEED(5), ACCURACY(6), EVASION(
			7);
	private int index;

	Stat(int index) {
		this.index = index;
	}

	public int i() {
		return index;
	}
}