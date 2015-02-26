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
	
	public int getProbability() {
		return probability;
	}
	public void setProbability(int probability) {
		this.probability = probability;
	}
	public int getEffectId() {
		return effectId;
	}
	public void setEffectId(int effectId) {
		this.effectId = effectId;
	}
	public AtomarMove getFailureElement() {
		return failureElement;
	}
	public void setFailureElement(AtomarMove failureElement) {
		this.failureElement = failureElement;
	}
	public AtomarMove getSuccessElement() {
		return successElement;
	}
	public void setSuccessElement(AtomarMove successElement) {
		this.successElement = successElement;
	}
}
