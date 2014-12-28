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
        if(getComponent(component.getClass()) != null) {
            return;
        }
        Class<?> singleton = getSingletonClass(component);
        for(Component other : components) {
            if(singleton.isInstance(other)) {
                return;
            }
        }
        components.add(component);
        try {
            component.onApply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setComponent(Component component) {
        Component other = getComponent(component.getClass());
        if(other != null) {
            try {
                other.onRemove();
            } catch (Exception e) {
                e.printStackTrace();
            }
            components.remove(other);
            components.add(component);
            try {
                component.onApply();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        Class<?> singleton = getSingletonClass(component);
        Iterator<Component> iterator = components.iterator();
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
        components.add(component);
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
