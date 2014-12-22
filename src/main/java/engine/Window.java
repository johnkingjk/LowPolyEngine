package engine;

/**
 * Created by Marco on 22.12.2014.
 */
public class Window {

    private String title;
    private int width;
    private int height;
    private boolean resizeable;
    private boolean useVSync;
    private int fpsCap;

    public Window(String title, int width, int height, int fpsCap) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.fpsCap = fpsCap;
    }

    public Window(String title, int width, int height, boolean resizeable, boolean useVSync, int fpsCap) {
        this.width = width;
        this.title = title;
        this.height = height;
        this.resizeable = resizeable;
        this.useVSync = useVSync;
        this.fpsCap = fpsCap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isResizeable() {
        return resizeable;
    }

    public void setResizeable(boolean resizeable) {
        this.resizeable = resizeable;
    }

    public boolean isUseVSync() {
        return useVSync;
    }

    public void setUseVSync(boolean useVSync) {
        this.useVSync = useVSync;
    }

    public int getFpsCap() {
        return fpsCap;
    }

    public void setFpsCap(int fpsCap) {
        this.fpsCap = fpsCap;
    }
}
