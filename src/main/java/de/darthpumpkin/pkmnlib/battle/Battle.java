package de.darthpumpkin.pkmnlib.battle;

/**
 * The general interface all battle classes must implement; it defines the
 * general structure of a battle.<br/>
 * <br/>
 * A battle is an asynchronous ...
 *
 * @author dominik
 */
public interface Battle {

    /**
     * Initializes the battle and starts the main loop in a background thread.
     * This method is non-blocking and will return immediately.
     */
    void start();

    /**
     * Blocks until the battle has finished.
     *
     * @throws InterruptedException
     *             if the battle could not finish because it was interrupted.
     * @see Thread#join()
     */
    void waitForEnd() throws InterruptedException;

    /**
     * Returns the {@link BattleState} this battle is currently in.
     * 
     * @return the battle state.
     */
    BattleState getBattleState();

    /**
     * Returns the {@link BattleEventBroadcaster} that is concerned with
     * managing BattleEvent subscriptions for this battle. Use this broadcaster
     * to register or unregister from BattleEvents occurring in this battle.
     */
    BattleEventBroadcaster getBroadcaster();
}
