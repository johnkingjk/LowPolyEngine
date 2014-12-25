package engine.rendering;

import engine.rendering.model.Model;
import engine.rendering.shader.DefaultShader;
import engine.transform.Transform;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Marco on 25.12.2014.
 */
public class Renderer {

    private static final DefaultShader DEFAULT_SHADER = new DefaultShader();
    private HashMap<Model, ArrayList<Transform>> models = new HashMap<>();

    public void processModel(Model model, Transform transform) {
        ArrayList<Transform> transforms = models.get(model);
        if(transforms == null) {
            models.put(model, (transforms = new ArrayList<>()));
        }
        transforms.add(transform);
    }

    public void render() {

    }
}
