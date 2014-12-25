package engine.input;

import engine.math.Vector2i;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * Copyright by michidk
 * Created: 25.12.2014.
 */
public class InputManager {

    private static Vector2i mousePos = Vector2i.zero();
    private static Vector2i mouseDeltaPos = Vector2i.zero();

    private static int mouseWeel = 0;

    public static void init() {
        try {
            Keyboard.create();
            Mouse.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public static void destroy() {
        Keyboard.destroy();
        Mouse.destroy();
    }

    public static void update() {
        mousePos.setX(Mouse.getX());
        mousePos.setY(Mouse.getY());

        mouseDeltaPos.setX(Mouse.getDX());
        mouseDeltaPos.setY(Mouse.getDY());

        mouseWeel = Mouse.getDWheel();
    }

    public static boolean isKeyDown(int keyCode) {
        return Keyboard.isKeyDown(keyCode);
    }

    public static boolean isKeyUp(int keyCode) {
        return Keyboard.isKeyDown(keyCode);
    }

    public static boolean isMouseDown(int mouseBtn) {
        return Mouse.isButtonDown(mouseBtn);
    }

    public static boolean isMouseDown(MouseButton mouseBtn) {
        return isMouseDown(mouseBtn.getId());
    }

    public static boolean isMouseUp(int mouseBtn) {
        return !Mouse.isButtonDown(mouseBtn);
    }

    public static boolean isMouseUp(MouseButton mouseBtn) {
        return isMouseUp(mouseBtn.getId());
    }

    public static void setMousePos(Vector2i mousePos) {
        Mouse.setCursorPosition(mousePos.getX(), mousePos.getX());
    }

    public static boolean isMouseInsideWindow() {
        return Mouse.isInsideWindow();
    }

    public static Vector2i getMousePos() {
        return mousePos;
    }

    public static Vector2i getMouseDeltaPos() {
        return mouseDeltaPos;
    }

    public static int getMouseWeel() {
        return mouseWeel;
    }

}
