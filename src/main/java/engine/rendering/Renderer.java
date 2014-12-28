package engine.rendering;

import engine.math.Matrix4f;
import engine.misc.Unloadable;
import engine.rendering.mesh.Mesh;
import engine.rendering.mesh.MaterialGroup;
import engine.rendering.mesh.MeshPart;
import engine.rendering.shader.DefaultShader;
import engine.rendering.shader.ShaderProgram;
import engine.transform.Camera;
import engine.transform.Transform;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marco on 25.12.2014.
 */
public class Renderer implements Unloadable{

    private final HashMap<Mesh, ArrayList<Transform>> models = new HashMap<>();
    private final ArrayList<ShaderProgram> shaderPrograms = new ArrayList<>();
    private final DefaultShader defaultShader;
    private final Matrix4f projectionMatrix;

    public Renderer(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
        defaultShader = new DefaultShader();
        registerShader(defaultShader);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClearColor(0.5f, 0.5f, 0.5f, 1);
    }

    public void registerShader(ShaderProgram shader) {
        shader.init();
        shader.start();
        shader.setupProjection(projectionMatrix);
        shader.stop();
        shaderPrograms.add(shader);
    }

    public void processModel(Mesh mesh, Transform transform) {
        ArrayList<Transform> transforms = models.get(mesh);
        if(transforms == null) {
            models.put(mesh, (transforms = new ArrayList<>()));
        }
        transforms.add(transform);
    }

    public void render(Camera camera) {
        for(Map.Entry<Mesh, ArrayList<Transform>> entry : models.entrySet()) {
            Mesh mesh = entry.getKey();
            ShaderProgram currentShader = defaultShader;
            Matrix4f viewMatrix = camera.getViewMatrix();
            prepareModel(mesh);

            currentShader.start();
            currentShader.setupViewMatrix(viewMatrix);
            for(MeshPart part : mesh.getParts()) {
                //switch shader
                ShaderProgram targetShader = part.getShader() == null ? defaultShader : part.getShader();
                if(targetShader != currentShader) {
                    currentShader = targetShader;
                    currentShader.start();
                    currentShader.setupViewMatrix(viewMatrix);
                }

                ShaderProgram shader = part.getShader() != null ? part.getShader() : defaultShader;
                for(Transform transform : entry.getValue()) {
                    render(part, transform, shader);
                }
            }
            currentShader.stop();

            unbindModel();
        }
        models.clear();
    }

    private void render(MeshPart part, Transform transform, ShaderProgram shader) {
        shader.setupTransform(transform.getTransformation());
        for(MaterialGroup group : part.getGroups()) {
            shader.setupMaterial(group.getMaterial());
            GL11.glDrawElements(GL11.GL_TRIANGLES, group.getIndexCount(), GL11.GL_UNSIGNED_INT, group.getIndexStart() * 4);
        }
    }

    private void prepareModel(Mesh mesh) {
        GL30.glBindVertexArray(mesh.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
    }

    private void unbindModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    @Override
    public void cleanUp() {
        for(ShaderProgram shader : shaderPrograms) {
            shader.cleanUp();
        }
    }
}
