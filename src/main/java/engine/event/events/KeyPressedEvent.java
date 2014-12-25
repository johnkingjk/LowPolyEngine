package engine.event.events;

import engine.event.Event;

/**
 * Copyright by michidk
 * Created: 25.12.2014.
 */
public class KeyPressedEvent implements Event {

    private int key;

    public KeyPressedEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
