package engine.scene.light;

import engine.math.Quaternion;
import engine.math.Vector3f;

/**
 * Copyright by michidk
 * Created: 26.12.2014.
 */
public class DirectionalLight extends Light {

    public DirectionalLight(Vector3f translation, Quaternion rotation, float intensity, Vector3f color) {
        super(translation, rotation, intensity, color);
    }

}
