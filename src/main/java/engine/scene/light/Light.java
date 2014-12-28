package engine.scene.light;

import engine.math.Vector3f;
import engine.scene.Component;

/**
 * Created by Marco on 26.12.2014.
 */
public class Light extends Component {

    private float intensity;
    private Vector3f color;

    public Light(float intensity, Vector3f color) {
        this.intensity = intensity;
        this.color = color;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
