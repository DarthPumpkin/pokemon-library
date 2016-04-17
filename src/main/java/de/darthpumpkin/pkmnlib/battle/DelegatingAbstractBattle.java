package de.darthpumpkin.pkmnlib.battle;

/**
 * <p>
 * This abstract layer splits battle implementation into a producing part and a
 * consuming part: The producer takes care of the battle logic and decides when
 * new battle events are triggered. The consumer ("applier") is the core part of
 * the engine: It changes the internal state of the battle, e.g. damage
 * infliction, accoring to the battle events received from the producer.
 * </p>
 * <p>
 * Therefore, the producer only needs read access to the internal battle state.
 * It is the responsibility of the implementing class to initialize both the
 * producer and the applier with the neccessary access to the internal battle
 * state.
 * </p>
 * <p>
 * Furthermore, it must implement {@link #getBattleState()} since this depends
 * on the internal design of the data.
 * </p>
 * <p>
 * Control flow is inverted by handing it over to an external {@link BattleLoop}
 * .
 * </p>
 *
 * @author Dominik Fay
 *
 */
public abstract class DelegatingAbstractBattle implements Battle {

    private final WritableBattleEventBroadcaster broadcaster = new WritableBattleEventBroadcaster();
    private final Thread loop;

    public DelegatingAbstractBattle(BattleEventProducer producer,
            BattleEventApplier applier) {
        this.loop = new Thread(new BattleLoop(this, producer));
        broadcaster.addEventListener(applier);
    }

    @Override
    public void start() {
        loop.start();
    }

    @Override
    public void waitForEnd() throws InterruptedException {
        loop.join();
    }

    @Override
    public BattleEventBroadcaster getBroadcaster() {
        return broadcaster.asReadOnly();
    }
}
