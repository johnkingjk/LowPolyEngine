package engine.scene.light;

import engine.math.Vector3f;

/**
 * Copyright by michidk
 * Created: 26.12.2014.
 */
public class SpotLight extends Light {

    private float angle;
    private float range;

    public SpotLight(float intensity, Vector3f color) {
        super(intensity, color);
    }

}
