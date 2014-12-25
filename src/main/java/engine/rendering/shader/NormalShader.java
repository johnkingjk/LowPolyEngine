package engine.rendering.shader;

import engine.math.Matrix4f;
import engine.math.Vector3f;

/**
 * Created by Marco on 24.12.2014.
 */
public class NormalShader extends ShaderProgram {

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_colour;

    public NormalShader() {
        super("src/main/resources/shader/normal");
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
