package engine;

import engine.input.InputManager;
import engine.math.Matrix4f;
import engine.math.Quaternion;
import engine.misc.Debug;
import engine.rendering.OpenGLLoader;
import engine.math.Vector3f;
import engine.rendering.Renderer;
import engine.rendering.model.Model;
import engine.rendering.model.ModelLoader;
import engine.rendering.shader.DefaultShader;
import engine.rendering.texture.Material;
import engine.rendering.texture.Texture;
import engine.scene.Terrain;
import engine.transform.Camera;
import engine.transform.Transform;
import org.lwjgl.opengl.*;

import java.util.logging.Level;

/**
 * Created by Marco on 22.12.2014.
 */
public class LowPolyEngine {

    public static void main(String[] args) {
        DisplayManager.create(
                new Window("LowPolyEngine Test",
                        960,
                        600,
                        false,
                        true,
                        60
                        )
        );


        new TestListener();

        OpenGLLoader loader = new OpenGLLoader();

        Camera camera = new Camera(new Vector3f(0, 300, -10), Quaternion.IDENTITY, Display.getWidth(), Display.getHeight(), 70, 0.1f, 1000.0f);
        Renderer renderer = new Renderer(camera.getProjection());
        ModelLoader modelLoader = new ModelLoader();

        Model mountain = modelLoader.readObjectFile("src/main/resources/models/mountain.obj", loader);
        Transform transform_mountain = new Transform();
        transform_mountain.setTranslation(new Vector3f(0, 0, 0));

        Model bigvalley = modelLoader.readObjectFile("src/main/resources/models/bigvalley2.obj", loader);
        Transform transform_bigvalley = new Transform();
        transform_bigvalley.setTranslation(new Vector3f(0, 0, 0));



        InputManager.init();

        DefaultShader shader = new DefaultShader();
        renderer.registerShader(shader);
        Terrain terrain = new Terrain(loader);
        Material mat = new Material("test", Vector3f.zero(), new Vector3f(51, 227, 16), Vector3f.zero(), (byte) 0, 0f, 1f, new Texture(0));

        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

        while(!Display.isCloseRequested()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            InputManager.update();

            renderer.processModel(mountain, transform_mountain);
            renderer.processModel(bigvalley, transform_bigvalley);

            shader.start();
            shader.setTransformationMatrix(new Matrix4f());
            shader.setupViewMatrix(camera.getViewMatrix());
            shader.setupMaterial(mat);
            terrain.render();
            shader.stop();

            camera.update();

            renderer.render(camera);

            DisplayManager.update();
        }

        loader.cleanUp();
        renderer.cleanUp();
        DisplayManager.destroy();
        InputManager.destroy();
    }
}
