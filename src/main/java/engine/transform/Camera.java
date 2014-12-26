package engine.transform;

import engine.input.InputManager;
import engine.input.MouseButton;
import engine.math.Matrix4f;
import engine.math.Quaternion;
import engine.math.Vector3f;
import engine.misc.Debug;
import org.lwjgl.Sys;
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
    private Matrix4f projection;

    public Camera(Vector3f translation, Quaternion rotation, float width, float height, float fov, float zNear, float zFar) {
        super(translation, rotation);
        this.width = width;
        this.height = height;
        this.fov = fov;
        this.zNear = zNear;
        this.zFar = zFar;

        recalculateViewMatrix();
        recalculateProjection();
    }

    public void update() {
        if(Mouse.isButtonDown(MouseButton.LEFT) || Mouse.isButtonDown(MouseButton.RIGHT)) {
            Quaternion rot = getRotation();
            setRotation(rot.mul(new Quaternion(new Vector3f((float)Math.toDegrees(InputManager.getMouseDeltaPos().getX()), (float)Math.toDegrees(InputManager.getMouseDeltaPos().getY()), 0))));
            Debug.Log(getRotation());
            /*
            Vector3f rot = getRotation().toAngles();
            float pitch = rot.getX();
            float yaw = rot.getY();

            pitch -= ;
            pitch %= 360;
            yaw += ;
            yaw %= 360;
            */
        }
    }

    public void recalculateViewMatrix() {
        Matrix4f m = new Matrix4f();
        m.translate(getTranslation().negate());
        m.rotate(getRotation());

        viewMatrix = m;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    private void recalculateProjection() {
        float aspectRatio = width / height;
        float y_scale = (float) (1f / Math.tan(Math.toRadians(fov / 2))) * aspectRatio;
        float x_scale = y_scale /aspectRatio;
        float frustum_length = zFar - zNear;

        Matrix4f m = new Matrix4f();
        m.set(0, 0, x_scale);
        m.set(1, 1, y_scale);
        m.set(2, 2, -((zFar + zNear) / frustum_length));
        m.set(2, 3, -1);
        m.set(3, 2, -(2 * zNear * zFar / frustum_length));
        m.set(3, 3, 0);

        projection = m;
    }

    public Matrix4f getProjection() {
        return projection;
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
