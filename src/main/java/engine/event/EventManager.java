package engine.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright by michidk
 * Created: 25.12.2014.
 */
public class EventManager {

    private static List<Listener> listeners = new ArrayList<>();

    public static void callEvent(Event event) {
        for (Listener listener : listeners) {
            listener.onEvent(event);
        }
    }

    public static void registerListener(Listener listener) {
        listeners.add(listener);
    }

    public static void unregisterListener(Listener listener) {
        listeners.remove(listener);
    }

}
