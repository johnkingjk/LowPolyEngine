package engine.event.events;

import engine.event.Event;

/**
 * Copyright by michidk
 * Created: 25.12.2014.
 */
public class MessageEvent implements Event {

    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
