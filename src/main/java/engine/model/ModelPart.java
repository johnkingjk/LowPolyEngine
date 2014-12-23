package engine.model;

import engine.texture.Material;

/**
 * Created by Marco on 22.12.2014.
 */
public class ModelPart {

    private Material material;
    private int indexStart;
    private int indexCount;

    public ModelPart(Material material, int indexStart, int indexCount) {
        this.material = material;
        this.indexStart = indexStart;
        this.indexCount = indexCount;
    }

    public Material getMaterial() {
        return material;
    }

    public int getIndexStart() {
        return indexStart;
    }

    public int getIndexCount() {
        return indexCount;
    }
}
