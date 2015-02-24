package de.darthpumpkin.pkmnlib;

public enum Nature {
	// TODO Test
	/*
	 * Order is equivalent to table 'natures' but ids are corrected(?)
	 */
	// TODO should be upper-case
	hardy(1), bold(2), modest(3), calm(4), timid(5), lonely(6), docile(7), mild(
			8), gentle(9), hasty(10), adamant(11), impish(12), bashful(13), careful(
			14), rash(18), jolly(15), naughty(16), lax(17), quirky(19), naive(
			20), brave(21), relaxed(22), quiet(23), sassy(24), serious(25);

	/*
	 * [increasedStatId, decreasedStatId] //zero-based
	 */
	private int[] indices;

	private Nature(int id) {
		// integer division (floor), modulo
		this.indices = new int[] { id % 5, id / 5 };
	}

	public float[] getStatFactors() {
		float[] factors = new float[] { 1f, 1f, 1f, 1f, 1f, 1f };
		factors[indices[0]] += 0.1;
		factors[indices[1]] -= 0.1;
		return factors;
	}
}
