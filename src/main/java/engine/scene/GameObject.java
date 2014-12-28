package engine.scene;

import engine.scene.light.Light;
import engine.transform.Transform;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright by michidk
 * Created: 27.12.2014.
 */
public class GameObject extends Transform {

    private List<Component> components = new ArrayList<>();

    public void addComponent(Component component) {
        if(components.contains(component)) {
            return;
        }
        component.init(this);
        components.add(component);
    }
}
