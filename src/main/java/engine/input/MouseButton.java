package engine.input;

/**
 * Copyright by michidk
 * Created: 25.12.2014.
 */
public enum MouseButton {
    LEFT(0),
    RIGHT(1),
    MIDDLE(2);

    private int id;

    MouseButton(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
