package engine.scene;

/**
 * Copyright by michidk
 * Created: 28.12.2014.
 */
@ComponentInfo(singleton = false)
public abstract class Component {

    private GameObject gameObject;

    public void onStart() {}

    public void onStop() {}

    public void onUpdate() {}

    public void onFixedUpdate() {}

    public void onRender() {}

    public void onPause() {}

    public void onResume() {}

    public void onApply() {}

    public void onRemove() {}

    void init(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public GameObject gameObject() {
        return gameObject;
    }

}
