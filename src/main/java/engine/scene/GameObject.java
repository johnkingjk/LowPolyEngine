package engine.scene;

import engine.transform.Transform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Copyright by michidk
 * Created: 27.12.2014.
 */
public class GameObject extends Transform {

    private List<Component> components = new ArrayList<>();

    public void addComponent(Component component) {
        if(component.gameObject() != null) { //check if component is already in use
            new ComponentException("Component cannot be used for two GameObjects!").callSave();
            return;
        }
        if(getComponent(component.getClass()) != null) { //check if component of same type is already added
            new ComponentException("A Component one type can only be added once!").callSave();
            return;
        }
        Class<?> singleton = getSingletonClass(component); //check if component can only be added once
        if(singleton != null) {
            for(Component other : components) {
                if(singleton.isInstance(other)) {
                    new ComponentException("There is already " + other.getClass() + " to handle your components function").callSave();
                    return;
                }
            }
        }
        _addComponent(component);
    }

    public void setComponent(Component component) {
        if(component.gameObject() != null) { //check if component already in use
            new ComponentException("Component cannot be used for two GameObjects!").callSave();
            return;
        }
        Component other = getComponent(component.getClass());
        if(other != null) { //check if component of same type is already in component list and override it
            _removeComponent(other);
            _addComponent(other);
            return;
        }

        Class<?> singleton = getSingletonClass(component);
        Iterator<Component> iterator = components.iterator(); //check if there is already a component which can only be added once and remove it
        while(iterator.hasNext()) {
            Component current = iterator.next();
            if(singleton.isInstance(current)) {
                try {
                    current.onRemove();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                iterator.remove();
            }
        }
        _addComponent(component);
    }

    public Component removeComponent(Class<? extends Component> component) {
        Component other = getComponent(component);
        if(other == null) {
            return null;
        }
        _removeComponent(other);
        return other;
    }

    public void removeComponent(Component component) {
        if(!components.contains(component)) {
            return;
        }
        _removeComponent(component);
    }

    public Component getComponent(Class<? extends Component> component) {
        for(Component other : components) {
            if(other.getClass() == component) {
                return other;
            }
        }
        return null;
    }

    //TODO : fill methods
    void onStart() {

    }

    void onStop() {

    }

    void onUpdate() {

    }

    void onFixedUpdate() {

    }

    void onRender() {

    }

    void onPause() {

    }

    void onResume() {

    }

    private void _addComponent(Component component) { //adds component to list sorted by its priority
        int priority = getPriority(component);
        for(int i = 0; i < components.size(); i++) {
            Component current = components.get(i);
            if(getPriority(current) < priority) {
                components.add(i, component);
                break;
            }
        }
        component.init(this);
        try {
            component.onApply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void _removeComponent(Component component) {
        try {
            component.onRemove();
        } catch (Exception e) {
            e.printStackTrace();
        }
        components.remove(component);
    }

    private int getPriority(Component component) {
        Class<?> current = component.getClass();
        while(current != null) {
            ComponentInfo info = current.getAnnotation(ComponentInfo.class);
            if(info != null) {
                return info.priority();
            }
        }
        return 0;
    }

    private Class<?> getSingletonClass(Component component) {
        Class<?> current = component.getClass();
        Class<?> singleton = null;
        while(current != null) {
            ComponentInfo info = current.getAnnotation(ComponentInfo.class);
            if(info != null && info.singleton()) {
                singleton = current;
            }
            current = current.getSuperclass();
        }
        return singleton;
    }
}
