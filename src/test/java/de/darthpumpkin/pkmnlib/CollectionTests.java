package de.darthpumpkin.pkmnlib;

import static org.mockito.Mockito.mock;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Dominik Fay
 */
public class CollectionTests {
    public static <T> boolean isCollectionImmutable(Collection<T> log,
            Class<T> type) {

        T additionalElement = mock(type);
        try {
            log.addAll(Collections.singleton(additionalElement));
        } catch (UnsupportedOperationException e1) {
            try {
                log.add(additionalElement);
            } catch (UnsupportedOperationException e2) {
                try {
                    log.clear();
                } catch (UnsupportedOperationException e3) {
                    try {
                        log.remove(additionalElement);
                    } catch (UnsupportedOperationException e4) {
                        try {
                            log.removeAll(log);
                        } catch (UnsupportedOperationException e5) {
                            try {
                                log.retainAll(log);
                            } catch (UnsupportedOperationException e6) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
