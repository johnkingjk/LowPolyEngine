package engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import java.io.File;

/**
 * Created by Marco on 22.12.2014.
 */
public class DisplayManager {

    private static Window window;

    public static void create(Window window) {
        if(DisplayManager.window != null) {
            //TODO : throw exception
            return;
        }
        System.setProperty("org.lwjgl.librarypath", new File("src/main/resources/natives").getAbsolutePath());
        try {
            ContextAttribs attributes = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);

            Display.setDisplayMode(new DisplayMode(window.getWidth(), window.getHeight()));
            Display.setTitle(window.getTitle());
            Display.setVSyncEnabled(window.isUseVSync());
            Display.setResizable(window.isResizeable());
            Display.create(new PixelFormat(), attributes);

            GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
        } catch (LWJGLException e) {
            e.printStackTrace();
            return;
        }
        DisplayManager.window = window;
    }

    public static void update() {
        Display.sync(window.getFpsCap());
        Display.update();
    }

    public static void destroy() {
        if(DisplayManager.window == null) {
            //TODO : throw exception
            return;
        }
        Display.destroy();
        DisplayManager.window = null;
    }
}
