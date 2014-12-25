package engine;

import engine.rendering.OpenGLLoader;
import engine.math.Matrix4f;
import engine.math.Vector3f;
import engine.rendering.model.Model;
import engine.rendering.model.ModelGroup;
import engine.rendering.model.ModelLoader;
import engine.rendering.shader.NormalShader;
import engine.transform.Camera;
import engine.transform.Transform;
import org.lwjgl.opengl.*;

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

        OpenGLLoader loader = new OpenGLLoader();
        NormalShader shader = new NormalShader();
        ModelLoader modelLoader = new ModelLoader();

        Model mountain = modelLoader.readObjectFile("src/main/resources/models/mountain.obj", loader);
        Transform transform_mountain = new Transform();
        transform_mountain.setTranslation(new Vector3f(0, 0, 0));

        Model bigvalley = modelLoader.readObjectFile("src/main/resources/models/bigvalley2.obj", loader);
        Transform transform_bigvalley = new Transform();
        transform_bigvalley.setTranslation(new Vector3f(0, 0, 0));
        transform_bigvalley.setRotation(new Vector3f(0, 0, 0));

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClearColor(0.5f, 0.5f, 0.5f, 1);

        Camera camera = new Camera(new Vector3f(0, 5, -10), 0, 0);

        Matrix4f projectionMatrix = new Matrix4f().setProjection(70, Display.getWidth(), Display.getHeight(), 0.1f, 1000.0f);
        shader.start();
        shader.setProjectionMatrix(projectionMatrix);
        shader.stop();

        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

        while(!Display.isCloseRequested()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            camera.update();

            shader.start();
            shader.setTransformationMatrix(transform_mountain.getTransformation());
            shader.setViewMatrix(camera.getViewMatrix());
            GL30.glBindVertexArray(mountain.getVaoID());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glEnableVertexAttribArray(2);

            for(ModelGroup group : mountain.getParts()) {
                shader.setColour(group.getMaterial().getDiffuseColor());
                GL32.glDrawElementsBaseVertex(GL11.GL_TRIANGLES, group.getIndexCount(), GL11.GL_UNSIGNED_INT, group.getIndexStart() * 4, 0);
            }

            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(2);
            GL30.glBindVertexArray(0);

            shader.setTransformationMatrix(transform_bigvalley.getTransformation());
            GL30.glBindVertexArray(bigvalley.getVaoID());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glEnableVertexAttribArray(2);

            for(ModelGroup group : bigvalley.getParts()) {
                shader.setColour(group.getMaterial().getDiffuseColor());
                GL32.glDrawElementsBaseVertex(GL11.GL_TRIANGLES, group.getIndexCount(), GL11.GL_UNSIGNED_INT, group.getIndexStart() * 4, 0);
            }

            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(2);
            GL30.glBindVertexArray(0);

            shader.stop();

            DisplayManager.update();
        }

        loader.cleanUp();
        shader.cleanUp();
        DisplayManager.destroy();
    }
}
