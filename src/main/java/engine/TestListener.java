package engine;

import engine.event.Event;
import engine.event.EventManager;
import engine.event.Listener;
import engine.event.events.KeyPressedEvent;
import engine.event.events.MousePressedEvent;

/**
 * Copyright by michidk
 * Created: 25.12.2014.
 */
public class TestListener implements Listener {

    public TestListener() {
        EventManager.registerListener(this);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof MousePressedEvent) {
            MousePressedEvent mousePressed = (MousePressedEvent) event;

            //System.out.println(mousePressed.getId());
        }
    }
}
