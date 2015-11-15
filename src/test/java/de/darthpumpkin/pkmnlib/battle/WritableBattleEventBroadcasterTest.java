package de.darthpumpkin.pkmnlib.battle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import de.darthpumpkin.pkmnlib.CollectionTests;

// TODO test thread safety

/**
 * @author Dominik Fay
 */
public class WritableBattleEventBroadcasterTest {

    private WritableBattleEventBroadcaster b;

    @Before
    public void setUp() throws Exception {
        b = new WritableBattleEventBroadcaster();
    }

    @Test
    public void isEventLogImmutable() throws Exception {
        // test with the empty log
        Collection<BattleEvent> log = b.eventLog();
        assertTrue(log.isEmpty());
        assertTrue(
                CollectionTests.isCollectionImmutable(log, BattleEvent.class));

        // test with a non-empty log
        BattleEvent e = mock(BattleEvent.class);
        b.receiveEvent(e);
        log = b.eventLog();
        assertEquals(Arrays.asList(e), new ArrayList<>(log));
        assertTrue(
                CollectionTests.isCollectionImmutable(log, BattleEvent.class));
    }

    @Test
    public void areListenersNotified() throws Exception {
        BattleEventListenerStub listener = new BattleEventListenerStub();
        b.addEventListener(listener);
        BattleEvent e = mock(BattleEvent.class);
        b.receiveEvent(e);
        assertEquals(listener.lastBattleEvent, e);
    }
}


class BattleEventListenerStub implements BattleEventListener {

    public BattleEvent lastBattleEvent;

    @Override
    public void onBattleEvent(BattleEvent e) {
        lastBattleEvent = e;
    }
}
