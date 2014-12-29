package engine.transform;

import engine.math.Matrix4f;
import engine.math.Quaternion;
import engine.math.Vector3f;

/**
 * Copyright by michidk
 * Created: 22.12.2014.
 */
public class Transform {

    protected Vector3f translation;
    protected Quaternion rotation;
    protected Vector3f scale;

    private Matrix4f matrix;

    public Transform() {
        translation = new Vector3f(0, 0, 0);
        rotation = Quaternion.IDENTITY;
        scale = new Vector3f(1, 1, 1);

        matrix = new Matrix4f();
    }

    public Transform(Vector3f translation, Quaternion rotation, Vector3f scale) {
        this.translation = translation;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Transform(Vector3f translation, Quaternion rotation) {
        this.translation = translation;
        this.rotation = rotation;
        this.scale = new Vector3f(1, 1, 1);
    }

    private void recalculateMatrix() {
        matrix.identity();
        matrix.translate(translation);
        matrix.rotate(getRotation());
        matrix.scale(scale);
    }

    public Matrix4f getTransformation() {
        return matrix;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
        recalculateMatrix();
    }

    public Quaternion getRotation() {
        return rotation;
    }

    public void setRotation(Quaternion rotation) {
        this.rotation = rotation;
        recalculateMatrix();
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
        recalculateMatrix();
    }
}
