package engine.transform;

import engine.math.Vector3f;

/**
 * Copyright by michidk
 * Created: 22.12.2014.
 */
public class Camera {

    private Vector3f position;
    private float pitch;
    private float yaw;

    public Camera(Vector3f position) {
        this.position = position;
        this.pitch = 0;
        this.yaw = 0;
    }

    public Camera(Vector3f position, float pitch, float yaw) {
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

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
}
