package engine.core.model;

import engine.core.texture.Material;

/**
 * Created by Marco on 22.12.2014.
 */
public class ModelGroup {

    private Material material;
    private int indexStart;
    private int indexCount;

    public ModelGroup(Material material, int indexStart) {
        this.material = material;
        this.indexStart = indexStart;
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

    public void addTriangle() {
        indexCount += 3;
    }
}
