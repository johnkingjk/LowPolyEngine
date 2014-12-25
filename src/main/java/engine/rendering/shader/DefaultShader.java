package engine.rendering.shader;

import engine.math.Matrix4f;
import engine.math.Vector3f;
import engine.rendering.RenderGroup;
import engine.rendering.model.ModelGroup;
import engine.transform.Transform;

/**
 * Created by Marco on 24.12.2014.
 */
public class DefaultShader extends ShaderProgram {

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_colour;

    public DefaultShader() {
        super("src/main/resources/shader/default");
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "texture");
        super.bindAttribute(2, "normal");
    }

    @Override
    public void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_colour = super.getUniformLocation("colour");
    }

    @Override
    public void setupProjection(Matrix4f projectionMatrix) {
        this.setProjectionMatrix(projectionMatrix);
    }

    @Override
    public void setupTransform(Matrix4f transformationMatrix, Matrix4f viewMatrix) {
        this.setViewMatrix(viewMatrix);
        this.setTransformationMatrix(transformationMatrix);
    }

    @Override
    public void setupGroup(RenderGroup group) {
        this.setColour(group.getMaterial().getDiffuseColor());
    }

    public void setColour(Vector3f colour) {
        super.loadVector(location_colour, colour);
    }

    public void setProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    public void setTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void setViewMatrix(Matrix4f matrix) {
        super.loadMatrix(location_viewMatrix, matrix);
    }
}
