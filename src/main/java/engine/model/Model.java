package engine.model;

/**
 * Created by Marco on 22.12.2014.
 */
public class Model {

    private int vaoID;
    private ModelPart[] parts;

    public Model(int vaoID, ModelPart[] parts) {
        this.vaoID = vaoID;
        this.parts = parts;
    }

    public int getVaoID() {
        return vaoID;
    }

    public ModelPart[] getParts() {
        return parts;
    }
}
