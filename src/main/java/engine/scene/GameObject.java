package engine.scene;

import engine.scene.light.Light;
import engine.transform.Transform;

import java.util.ArrayList;

/**
 * Copyright by michidk
 * Created: 27.12.2014.
 */
public final class GameObject extends Transform {

    private ArrayList<Component> components = new ArrayList<>(); //component represents a script

    private Light light;
    //standard attributes like rigidbody + meshrenderer + etc.

    public void addComponent(Component component) {
        if(components.contains(component)) {
            return;
        }
        component.init(this);
        components.add(component);
    }
}
