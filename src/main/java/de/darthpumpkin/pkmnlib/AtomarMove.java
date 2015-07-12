package de.darthpumpkin.pkmnlib;


/**
 * An AtomarMove is an element in the Move tree (see diagram in the doc folder).
 * It is the smallest fragment a move can consist of.
 * 
 * @author dominik
 * 
 */
public class AtomarMove {

	private int probability;
	private int effectId;
	private AtomarMove failureElement;
	private AtomarMove successElement;

	/**
	 * The probability with which the effectId is applied and the successElement
	 * is called.
	 * 
	 * @return probability as a percentage (1 to 100).
	 */
	public int getProbability() {
		return probability;
	}

	/**
	 * The probability with which the effectId is applied and the successElement
	 * is called.
	 * 
	 * @param probability
	 *            probability as a percentage (1 to 100).
	 */
	public void setProbability(int probability) {
		this.probability = probability;
	}

	// TODO javadoc once it is clear which id is used
	public int getEffectId() {
		return effectId;
	}

	// TODO javadoc once it is clear which id is used
	public void setEffectId(int effectId) {
		this.effectId = effectId;
	}

	/**
	 * 
	 * @return the child in the binary tree to be called on failure.
	 */
	public AtomarMove getFailureElement() {
		return failureElement;
	}

	/**
	 * 
	 * @param failureElement
	 *            the child in the binary tree to be called on failure.
	 */
	public void setFailureElement(AtomarMove failureElement) {
		this.failureElement = failureElement;
	}

	/**
	 * 
	 * @return the child in the binary tree to be called on success.
	 */
	public AtomarMove getSuccessElement() {
		return successElement;
	}

	/**
	 * 
	 * @param successElement
	 *            the child in the binary tree to be called on success.
	 */
	public void setSuccessElement(AtomarMove successElement) {
		this.successElement = successElement;
	}
}
