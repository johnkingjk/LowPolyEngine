package engine.event.events;

import engine.event.Event;
import engine.input.MouseWheelDirection;

/**
 * Copyright by michidk
 * Created: 25.12.2014.
 */
public class MouseWheelEvent implements Event {

    private MouseWheelDirection direction;

    public MouseWheelEvent(MouseWheelDirection direction) {
        this.direction = direction;
    }

    public MouseWheelDirection getDirection() {
        return direction;
    }
}
