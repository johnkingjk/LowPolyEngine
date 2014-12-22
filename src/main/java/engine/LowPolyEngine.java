package engine;

import org.lwjgl.opengl.Display;

/**
 * Created by Marco on 22.12.2014.
 */
public class LowPolyEngine {

    public static void main(String[] args) {
        DisplayManager.create(
                new Window("LowPolyEngine Test",
                        960,
                        600,
                        true,
                        true,
                        60
                        )
        );

        while(!Display.isCloseRequested()) {
            DisplayManager.update();
        }

        DisplayManager.destroy();
    }
}
