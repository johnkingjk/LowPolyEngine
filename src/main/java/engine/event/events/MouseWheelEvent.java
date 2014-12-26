package engine.event.events;

import engine.event.Event;
import engine.event.EventManager;
import engine.input.MouseWheelDirection;
import org.lwjgl.input.Mouse;

/**
 * Copyright by michidk
 * Created: 25.12.2014.
 */
public class MouseWheelEvent implements Event {

    private int delta;
    private MouseWheelDirection direction;

    public MouseWheelEvent(int delta) {
        this.delta = delta;
        if (delta > 0) {
            this.direction = MouseWheelDirection.UP;
        } else if (Mouse.getEventDWheel() < 0) {
            this.direction = MouseWheelDirection.DOWN;
        }
    }

    public MouseWheelDirection getDirection() {
        return direction;
    }
}
