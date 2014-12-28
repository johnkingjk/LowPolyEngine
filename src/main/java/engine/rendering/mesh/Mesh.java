package engine.rendering.mesh;

/**
 * Created by Marco on 22.12.2014.
 */
public class Mesh {

    private int vaoID;
    private MeshPart[] parts;

    public Mesh(int vaoID, MeshPart[] parts) {
        this.vaoID = vaoID;
        this.parts = parts;
    }

    public int getVaoID() {
        return vaoID;
    }

    public MeshPart[] getParts() {
        return parts;
    }
}
