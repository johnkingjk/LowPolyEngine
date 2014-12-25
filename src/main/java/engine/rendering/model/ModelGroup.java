package engine.rendering.model;

import engine.rendering.RenderGroup;
import engine.rendering.texture.Material;

/**
 * Created by Marco on 22.12.2014.
 */
public class ModelGroup implements RenderGroup{

    private Material material;
    private int indexStart;
    private int indexCount;

    public ModelGroup(Material material, int indexStart) {
        this.material = material;
        this.indexStart = indexStart;
    }

    @Override
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
