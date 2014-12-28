package engine.scene;

/**
 * Copyright by michidk
 * Created: 28.12.2014.
 */
public abstract class Component {

    private GameObject gameObject;

    public void onEnable() {}

    public void onDisable() {}

    public void onUpdate() {}

    public void onFixedUpdate() {}

    public void onRender() {}

    public void onPause() {}

    public void onResume() {}

    void init(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public GameObject gameObject() {
        return gameObject;
    }

}
