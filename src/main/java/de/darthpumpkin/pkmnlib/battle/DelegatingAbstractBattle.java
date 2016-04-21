package de.darthpumpkin.pkmnlib.battle;

/**
 * <p>
 * This abstract layer splits battle implementation into an outer and an inner
 * part: The battle loop iterates over the turns and turn steps and the battle
 * delegate executes the actual logic. That is, this battle class can be used
 * together with any {@link BattleDelegate} implementation.
 * </p>
 * <p>
 * Any subclass must implement {@link #getBattleState()} since this depends on
 * the internal design of the data.
 * </p>
 *
 * @author Dominik Fay
 *
 */
public abstract class DelegatingAbstractBattle implements Battle {

    private final WritableBattleEventBroadcaster broadcaster;
    private final Thread loop;

    public DelegatingAbstractBattle(BattleDelegate delegate) {
        this.broadcaster = new WritableBattleEventBroadcaster();
        this.loop = new Thread(new BattleLoop(this, delegate));
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
