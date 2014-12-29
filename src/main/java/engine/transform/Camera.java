package engine.transform;

import engine.input.InputManager;
import engine.input.MouseButton;
import engine.math.*;
import org.lwjgl.input.Mouse;

/**
 * Copyright by michidk
 * Created: 22.12.2014.
 */
public class Camera extends Transform {

    private float width;
    private float height;
    private float fov;
    private float zNear;
    private float zFar;

    private Matrix4f viewMatrix;
    private Matrix4f projectionMatrix;
    private Matrix4f projectionViewMatrix;

    public Camera(Vector3f translation, Quaternion rotation, float width, float height, float fov, float zNear, float zFar) {
        super(translation, rotation);
        this.width = width;
        this.height = height;
        this.fov = fov;
        this.zNear = zNear;
        this.zFar = zFar;

        projectionMatrix = new Matrix4f();
        projectionViewMatrix = new Matrix4f();
        recalculateViewMatrix();
        recalculateProjection();
    }

    public void update() {
        if(Mouse.isButtonDown(MouseButton.LEFT) || Mouse.isButtonDown(MouseButton.RIGHT)) {
            rotateCamera(FastMath.toRadians(-InputManager.getMouseDeltaPos().getY()), new Vector3f(1, 0, 0));
            rotateCamera(FastMath.toRadians(InputManager.getMouseDeltaPos().getX()), rotation.getAxis(1));
        }
    }

    public void rotateCamera(float angle, Vector3f axis) {
        Matrix3f matrix = new Matrix3f(angle, axis);

        Vector3f up = rotation.getAxis(1);
        Vector3f left = rotation.getAxis(0);
        Vector3f direction = rotation.getAxis(2);

        matrix.mul(up);
        matrix.mul(left);
        matrix.mul(direction);

        Quaternion quaternion = new Quaternion(left, up, direction);
        quaternion.normalize();

        this.rotation = quaternion;
        recalculateViewMatrix();
    }

    private void recalculateViewMatrix() {
        viewMatrix = new Matrix4f(this.translation, this.rotation.getAxis(2), this.rotation.getAxis(1));

        recalculateProjectionViewMatrix();
    }

    private void recalculateProjection() {
        float aspectRatio = width / height;
        float y_scale = 1f / FastMath.tan(FastMath.toRadians(fov / 2)) * aspectRatio;
        float x_scale = y_scale /aspectRatio;
        float frustum_length = zFar - zNear;

        projectionMatrix.identity();
        projectionMatrix.set(0, 0, x_scale);
        projectionMatrix.set(1, 1, y_scale);
        projectionMatrix.set(2, 2, -((zFar + zNear) / frustum_length));
        projectionMatrix.set(2, 3, -1);
        projectionMatrix.set(3, 2, -(2 * zNear * zFar / frustum_length));
        projectionMatrix.set(3, 3, 0);

        recalculateProjectionViewMatrix();
    }

    private void recalculateProjectionViewMatrix() {
        projectionViewMatrix.identity();
        projectionViewMatrix.mul(projectionMatrix).mul(viewMatrix);
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getProjectionViewMatrix() {
        return projectionViewMatrix;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
        recalculateProjection();
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
        recalculateProjection();
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
        recalculateProjection();
    }

    public float getzNear() {
        return zNear;
    }

    public void setzNear(float zNear) {
        this.zNear = zNear;
        recalculateProjection();
    }

    public float getzFar() {
        return zFar;
    }

    public void setzFar(float zFar) {
        this.zFar = zFar;
        recalculateProjection();
    }

    @Override
    public void setRotation(Quaternion rotation) {
        super.setRotation(rotation);

        recalculateViewMatrix();
    }

    @Override
    public void setTranslation(Vector3f translation) {
        super.setTranslation(translation);

        recalculateViewMatrix();
    }
}
