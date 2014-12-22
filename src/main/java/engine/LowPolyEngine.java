package engine;

/**
 * Created by Marco on 22.12.2014.
 */
public class LowPolyEngine {

    public static void main(String[] args) {
        DisplayManager.create(
                new Window("LowPolyEngine Test",
                        960,
                        600,
                        true,
                        true,
                        60
                        )
        );

        try {
            Thread.sleep(10 * 60L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
