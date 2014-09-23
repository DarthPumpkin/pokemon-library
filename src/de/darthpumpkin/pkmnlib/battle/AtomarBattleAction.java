package de.darthpumpkin.pkmnlib.battle;

import java.io.Serializable;

/**
 * AtomarBattleActions are the smallest pieces of actions that can happen in a
 * battle. A single move can cause multiple AtomarBattleActions, e.g. damage +
 * stat change + paralysis + ... In damage calculation, they are applied
 * successively
 * 
 * @author dominik
 * 
 */
public class AtomarBattleAction implements Serializable {

}
