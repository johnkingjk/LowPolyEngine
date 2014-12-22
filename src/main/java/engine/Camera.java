package engine;

import engine.math.Vector3f;

/**
 * Copyright by michidk
 * Created: 22.12.2014.
 */
public class Camera {

    private Vector3f position;
    private Vector3f pitch;
    private Vector3f yaw;

    public Camera(Vector3f position) {
        this.position = position;
        this.pitch = new Vector3f(0, 0, 0);
        this.yaw = new Vector3f(0, 0, 0);
    }

    public Camera(Vector3f position, Vector3f pitch, Vector3f yaw) {
        this.position = position;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getPitch() {
        return pitch;
    }

    public void setPitch(Vector3f pitch) {
        this.pitch = pitch;
    }

    public Vector3f getYaw() {
        return yaw;
    }

    public void setYaw(Vector3f yaw) {
        this.yaw = yaw;
    }
}
