package engine.math;

/**
 * Created by Marco on 26.12.2014.
 */
public class FastMath {

    public static final float PI = 3.141592653589793f;

    public static float sin(float angle) {
        return (float) Math.sin(angle);
    }

    public static float cos(float angle) {
        return (float) Math.cos(angle);
    }

    public static float atan2(float x, float y) {
        return (float) Math.atan2(x, y);
    }

    public static float asin(float x) {
        return (float) Math.asin(x);
    }

    public static float sqrt(float value) {
        return (float) Math.sqrt(value);
    }

    public static float toRadians(float value) {
        return value / 180.0f * PI;
    }

    public static float toDegree(float value) {
        return value / PI * 180.0f;
    }
}
