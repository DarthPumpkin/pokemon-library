package de.darthpumpkin.pkmnlib.battle;

import java.util.Collection;
import java.util.Collections;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Thread-safe implementation of the {@link BattleEventBroadcaster} interface
 * that can receive events via the {@link #receiveEvent(BattleEvent)} method.
 * <p>
 * Thread-satety in this context means that this class will never fail due to
 * concurrent usage and no external synchronization is needed. If listeners are
 * being registered or unregistered while calling
 * {@link #receiveEvent(BattleEvent)}, then those listeners may or may not be
 * notified of the broadcast event.
 * </p>
 *
 * @author Dominik Fay
 */
public class WritableBattleEventBroadcaster implements BattleEventBroadcaster {

    private final Queue<BattleEvent> eventLog = new ConcurrentLinkedQueue<>();
    private Set<BattleEventListener> listeners = Collections.newSetFromMap(
            new ConcurrentHashMap<BattleEventListener, Boolean>());

    @Override
    public Collection<BattleEvent> eventLog() {
        return Collections.unmodifiableCollection(eventLog);
    }

    @Override
    public boolean addEventListener(BattleEventListener l) {
        return listeners.add(l);
    }

    @Override
    public boolean removeEventListener(BattleEventListener l) {
        return listeners.remove(l);
    }

    /**
     * Receive a new battle event. Broadcast it to all listeners and insert it
     * into the event log. The bradcasting process is synchronous, i.e. this
     * method will iteratively block until all registered listeners have
     * finished their {@link BattleEventListener#onBattleEvent(BattleEvent)}
     * method.
     *
     * @param e
     *            the event
     */
    public void receiveEvent(BattleEvent e) {
        eventLog.add(e);
        notifyListeners(e);
    }

    private void notifyListeners(BattleEvent e) {
        for (BattleEventListener listener : listeners) {
            listener.onBattleEvent(e);
        }
    }

}
