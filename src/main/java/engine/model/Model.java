package engine.model;

/**
 * Created by Marco on 22.12.2014.
 */
public class Model {

    private int vaoID;
    private ModelGroup[] parts;

    public Model(int vaoID, ModelGroup[] parts) {
        this.vaoID = vaoID;
        this.parts = parts;
    }

    public int getVaoID() {
        return vaoID;
    }

    public ModelGroup[] getParts() {
        return parts;
    }
}
