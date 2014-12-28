package engine.scene;

/**
 * Copyright by michidk
 * Created: 28.12.2014.
 */
public abstract class Component {

    private GameObject gameObject;

    public abstract void onUpdate();

    void init(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public GameObject gameObject() {
        return gameObject;
    }

}
