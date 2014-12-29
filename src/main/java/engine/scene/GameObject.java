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
        components.add(component); //add component + call onApply
        component.init(this);
        try {
            component.onApply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setComponent(Component component) {
        if(component.gameObject() != null) { //check if component already in use
            new ComponentException("Component cannot be used for two GameObjects!").callSave();
            return;
        }
        Component other = getComponent(component.getClass());
        if(other != null) { //check if component of same type is already in component list and override it
            try {
                other.onRemove();
            } catch (Exception e) {
                e.printStackTrace();
            }
            components.remove(other);
            components.add(component);
            component.init(this);
            try {
                component.onApply();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        components.add(component); //add and init the component
        component.init(this);
        try {
            component.onApply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Component removeComponent(Class<? extends Component> component) {
        Component other = getComponent(component);
        if(other == null) {
            return null;
        }
        try {
            other.onRemove();
        } catch (Exception e) {
            e.printStackTrace();
        }
        components.remove(other);
        return other;
    }

    public Component getComponent(Class<? extends Component> component) {
        for(Component other : components) {
            if(other.getClass() == component) {
                return other;
            }
        }
        return null;
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
