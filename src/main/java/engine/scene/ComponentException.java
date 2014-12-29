package engine.scene;

/**
 * Created by Marco on 29.12.2014.
 */
public class ComponentException extends Exception {

    static final long serialVersionUID = 1348907560459874801L;

    public ComponentException() {
        super();
    }

    public ComponentException(String message) {
        super(message);
    }

    public ComponentException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ComponentException(Throwable throwable) {
        super(throwable);
    }

    public void callSave() {
        try {
            throw this;
        } catch (ComponentException e) {
            e.printStackTrace();
        }
    }
}
