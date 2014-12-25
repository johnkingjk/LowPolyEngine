package engine.transform;

import engine.math.Matrix4f;
import engine.math.Vector3f;

/**
 * Copyright by michidk
 * Created: 22.12.2014.
 */
public class Transform {

    private Vector3f translation;
    private Vector3f rotation;
    private Vector3f scale;

    private Matrix4f matrix;
    private boolean changed;

    public Transform() {
        translation = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
        scale = new Vector3f(1, 1, 1);

        matrix = new Matrix4f();
        changed = true;
    }

    public Matrix4f getTransformation() {
        if(changed) {
            matrix.identity();
            matrix.translate(translation);
            matrix.rotate(rotation.getX(), rotation.getY(), rotation.getZ());
            matrix.scale(scale);

            changed = false;
        }
        return matrix;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
        this.changed = true;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
        this.changed = true;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
        this.changed = true;
    }
}
