package de.darthpumpkin.pkmnlib.battle;

import static de.darthpumpkin.pkmnlib.battle.BattleState.FINISHABLE;
import static de.darthpumpkin.pkmnlib.battle.BattleState.PENDING;

/**
 * @author Dominik Fay
 */
public class BattleLoop implements Runnable {
    private final BattleDelegate producer;
    private final DelegatingAbstractBattle battle;

    public BattleLoop(DelegatingAbstractBattle battle,
            BattleDelegate producer) {
        this.battle = battle;
        this.producer = producer;
    }

    @Override
    public void run() {
        if (battle.getBattleState() != PENDING) {
            throw new IllegalStateException("battle was already started");
        }
        producer.init();
        while (battle.getBattleState() != FINISHABLE) {
            producer.preTurn();
            if (battle.getBattleState() == FINISHABLE)
                break;
            producer.midTurn();
            if (battle.getBattleState() == FINISHABLE)
                break;
            producer.postTurn();
        }
        producer.finish();
    }
}
