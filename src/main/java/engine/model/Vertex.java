package engine.model;

import engine.math.Vector2f;
import engine.math.Vector3f;

/**
 * Created by Marco on 23.12.2014.
 */
public class Vertex {

    private Vector3f location;
    private Vector3f normal;
    private Vector2f texture;

    public Vertex(Vector3f location, Vector3f normal, Vector2f texture) {
        this.location = location;
        this.normal = normal;
        this.texture = texture;
    }

    public Vector3f getLocation() {
        return location;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public Vector2f getTexture() {
        return texture;
    }

    public boolean equals(Vector3f location, Vector3f normal, Vector2f texture) {
        if(this.location == location || (this.location != null && this.location.equals(location))) {
            if(this.normal == normal || (this.normal != null && this.normal.equals(normal))) {
                if(this.texture == texture || (this.texture != null && this.texture.equals(texture))) {
                    return true;
                }
            }
        }
        return false;
    }
}
