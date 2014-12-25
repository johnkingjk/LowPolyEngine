package engine.event.events;

import engine.event.Event;
import engine.input.MouseButton;

/**
 * Copyright by michidk
 * Created: 25.12.2014.
 */
public class MousePressedEvent implements Event {

    private int id;

    public MousePressedEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
