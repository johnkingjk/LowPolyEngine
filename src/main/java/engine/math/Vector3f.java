package engine.math;

public class Vector3f {

    private float x;
    private float y;
    private float z;

    public Vector3f() {

    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3f zero() {
        return new Vector3f(0, 0, 0);
    }

    public static Vector3f fromColor(int r, int g, int b) {
        return new Vector3f((float) r / 255, (float) g / 255, (float) b / 255);
    }

    public float length() {
        return FastMath.sqrt(x * x + y * y + z * z);
    }

    public float dot(Vector3f r) {
        return x * r.getX() + y * r.getY() + z * r.getZ();
    }

    public Vector3f cross(Vector3f r) {
        float x_ = y * r.getZ() - z * r.getY();
        float y_ = z * r.getX() - x * r.getZ();
        float z_ = x * r.getY() - y * r.getX();

        return new Vector3f(x_, y_, z_);
    }

    public Vector3f normalize() {
        float length = length();

        x /= length;
        y /= length;
        z /= length;
        return this;
    }

    public Vector3f rotate(float angle, Vector3f axis) {
        Quaternion rotation = new Quaternion(angle, axis);
        Quaternion conjugate = rotation.conjugate();

        Quaternion w = rotation.clone().mul(this).mul(conjugate);

        x = w.getX();
        y = w.getY();
        z = w.getZ();

        return this;
    }

    public Vector3f negate() {
        x *= -1;
        y *= -1;
        z *= -1;
        return this;
    }

    public Vector3f add(Vector3f r) {
        x += r.getX();
        y += r.getY();
        z += r.getZ();
        return this;
    }

    public Vector3f add(float r) {
        x += r;
        y += r;
        z += r;
        return this;
    }

    public Vector3f sub(Vector3f r) {
        x -= r.getX();
        y -= r.getY();
        z -= r.getZ();
        return this;
    }

    public Vector3f sub(float r) {
        x -= r;
        y -= r;
        z -= r;
        return this;
    }

    public Vector3f mul(Vector3f r) {
        x *= r.getX();
        y *= r.getY();
        z *= r.getZ();
        return this;
    }

    public Vector3f mul(float r) {
        x *= r;
        y *= r;
        z *= r;
        return this;
    }

    public Vector3f div(Vector3f r) {
        x /= r.getX();
        y /= r.getY();
        z /= r.getZ();
        return this;
    }

    public Vector3f div(float r) {
        x /= r;
        y /= r;
        z /= r;
        return this;
    }

    public Vector3f abs() {
        x = FastMath.abs(x);
        y = FastMath.abs(y);
        z = FastMath.abs(z);
        return this;
    }

    public String toString() {
        return "(" + x + " " + y + " " + z + ")";
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

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public Vector3f clone() {
        return new Vector3f(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Vector3f)) {
            return false;
        }
        Vector3f other = (Vector3f) o;
        return other.x == this.x && other.y == this.y && other.z == z;
    }
}