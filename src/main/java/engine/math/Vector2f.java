package engine.math;

/**
 * @author Gugu42
 */
public class Vector2f {

    private float x;
    private float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2f zero() {
        return new Vector2f(0, 0);
    }

    public float length() {
        return (float) FastMath.sqrt(x * x + y * y);
    }

    public float dot(Vector2f r) {
        return x * r.getX() + y * r.getY();
    }

    public Vector2f normalize() {
        float length = length();

        x /= length;
        y /= length;

        return this;
    }

    public Vector2f rotate(float angle) {
        float rad = FastMath.toRadians(angle);
        float cos = FastMath.cos(rad);
        float sin = FastMath.sin(rad);

        return new Vector2f(x * cos - y * sin, x * sin + y * cos);
    }

    public Vector2f add(Vector2f r) {
        x += r.getX();
        y += r.getY();
        return this;
    }

    public Vector2f add(float r) {
        x += r;
        y += r;
        return this;
    }

    public Vector2f sub(Vector2f r) {
        x -= r.getX();
        y -= r.getY();
        return this;
    }

    public Vector2f sub(float r) {
        x -= r;
        y -= r;
        return this;
    }

    public Vector2f mul(Vector2f r) {
        x *= r.getX();
        y *= r.getY();
        return this;
    }

    public Vector2f mul(float r) {
        x *= r;
        y *= r;
        return this;
    }

    public Vector2f div(Vector2f r) {
        x /= r.getX();
        y /= r.getY();
        return this;
    }

    public Vector2f div(float r) {
        x /= r;
        y /= r;
        return this;
    }

    public String toString() {
        return "(" + x + " ; " + y + ")";
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public Vector2f clone() {
        return new Vector2f(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Vector2f)) {
            return false;
        }
        Vector2f other = (Vector2f) o;
        return other.x == this.x && other.y == this.y;
    }
}