package engine.math;

/**
 * @author Gugu42
 */
public class Quaternion {

    public static final Quaternion IDENTITY = new Quaternion();

    private float x;
    private float y;
    private float z;
    private float w;

    public Quaternion() {
        x = 0;
        y = 0;
        z = 0;
        w = 1;
    }

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * @author jmonkeyengine
     */
    public Quaternion(Vector3f rotation) {
        float xAngle = rotation.getX();
        float yAngle = rotation.getY();
        float zAngle = rotation.getZ();

        float angle;
        float sinY, sinZ, sinX, cosY, cosZ, cosX;
        angle = zAngle * 0.5f;
        sinZ = FastMath.sin(angle);
        cosZ = FastMath.cos(angle);
        angle = yAngle * 0.5f;
        sinY = FastMath.sin(angle);
        cosY = FastMath.cos(angle);
        angle = xAngle * 0.5f;
        sinX = FastMath.sin(angle);
        cosX = FastMath.cos(angle);

        // variables used to reduce multiplication calls.
        float cosYxCosZ = cosY * cosZ;
        float sinYxSinZ = sinY * sinZ;
        float cosYxSinZ = cosY * sinZ;
        float sinYxCosZ = sinY * cosZ;

        w = (cosYxCosZ * cosX - sinYxSinZ * sinX);
        x = (cosYxCosZ * sinX + sinYxSinZ * cosX);
        y = (sinYxCosZ * cosX + cosYxSinZ * sinX);
        z = (cosYxSinZ * cosX - sinYxCosZ * sinX);

        normalize();
    }

    public Vector3f toAngles() {
        Vector3f result = new Vector3f(0, 0, 0);

        float sqw = w * w;
        float sqx = x * x;
        float sqy = y * y;
        float sqz = z * z;
        float unit = sqx + sqy + sqz + sqw;

        float test = x * y + z * w;
        if (test > 0.499 * unit) {
            result.setX(0);
            result.setY(2.0f * FastMath.atan2(x, w));
            result.setZ(FastMath.PI / 2.0f);
        } else if (test < -0.499 * unit) {
            result.setX(0);
            result.setY(-2.0f * FastMath.atan2(x, w));
            result.setZ(-FastMath.PI / 2.0f);
        } else {
            result.setX(FastMath.atan2(2 * x * w - 2 * y * z, -sqx + sqy - sqz + sqw));
            result.setY(FastMath.atan2(2 * y * w - 2 * x * z, sqx - sqy - sqz + sqw));
            result.setZ(FastMath.asin(2 * test / unit));
        }
        return result;
    }

    public Quaternion rotate(Quaternion quaternion) {
        //TODO: make dat shit @johnking
        return this;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public Quaternion normalize() {
        float length = length();

        x /= length;
        y /= length;
        z /= length;
        w /= length;

        return this;
    }

    public Quaternion conjugate() {
        return new Quaternion(-x, -y, -z, w);
    }

    public Quaternion negate() {
        x *= -1;
        y *= -1;
        z *= -1;
        w *= -1;

        return this;
    }

    public Quaternion mul(Quaternion r) {
        float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
        float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
        float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
        float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

        return new Quaternion(x_, y_, z_, w_);
    }

    public Quaternion mul(Vector3f r) {
        float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
        float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
        float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
        float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

        return new Quaternion(x_, y_, z_, w_);
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

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    @Override
    public String toString() {
        return "Quaternion{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }
}