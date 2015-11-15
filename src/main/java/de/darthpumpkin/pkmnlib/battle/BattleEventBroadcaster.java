package de.darthpumpkin.pkmnlib.battle;

import java.util.Collection;

public interface BattleEventBroadcaster {

    /**
     * Returns a snapshot of the events that have happened so far in the battle.
     * Events happening subsequently will not make it into this collection. If
     * you wish to be updated on every event, please consider registering a
     * {@link BattleEventListener}.
     *
     * @return an immutable serializable collection containing the battle
     *         events. Its iterator is guaranteed to return the events in
     *         chronological order
     * @see #addEventListener(BattleEventListener)
     */
    Collection<BattleEvent> eventLog();

    /**
     * Registers a listener if it is not yet registered. The listener will
     * be notified when a {@link BattleEvent} occurs. Returns true if the
     * listener was not registered before.
     * occurs.
     *
     * @param l
     *            the listener to be registered
     * @throws NullPointerException
     *             if l == null
     * @return true if the listener was not registered before, false otherwise
     */
    boolean addEventListener(BattleEventListener l);

    /**
     * Unregisters a listener, if it is registered. The listener will not be
     * notified on any subsequent events. Returns true if the listener was
     * registered before.
     *
     * @param l
     *            the listener to be unregistered
     * @return true if the listener was registered before, false otherwise
     */
    boolean removeEventListener(BattleEventListener l);
}
