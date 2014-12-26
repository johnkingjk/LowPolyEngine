package engine.math;

import engine.misc.FastMath;

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
        float cosYXcosZ = cosY * cosZ;
        float sinYXsinZ = sinY * sinZ;
        float cosYXsinZ = cosY * sinZ;
        float sinYXcosZ = sinY * cosZ;

        w = (cosYXcosZ * cosX - sinYXsinZ * sinX);
        x = (cosYXcosZ * sinX + sinYXsinZ * cosX);
        y = (sinYXcosZ * cosX + cosYXsinZ * sinX);
        z = (cosYXsinZ * cosX - sinYXcosZ * sinX);

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
            result.setY(2.0f * (float) Math.atan2(x, w));
            result.setZ(FastMath.PI / 2.0f);
        } else if (test < -0.499 * unit) {
            result.setX(0);
            result.setY(-2.0f * (float) Math.atan2(x, w));
            result.setZ(-FastMath.PI / 2.0f);
        } else {
            result.setX((float) Math.atan2(2 * x * w - 2 * y * z, -sqx + sqy - sqz + sqw));
            result.setY((float) Math.atan2(2 * y * w - 2 * x * z, sqx - sqy - sqz + sqw));
            result.setZ((float) Math.asin(2 * test / unit));
        }
        return result;

        /*
        normalize();

        float test = x*y + z*w;
        if (test > 0.499) { // singularity at north pole
            return new Vector3f(2.0f * (float) Math.atan2(x,w), FastMath.PI / 2.0f, 0.0f);
        }
        if (test < -0.499) { // singularity at south pole
            return new Vector3f(-2.0f * (float) Math.atan2(x,w), -FastMath.PI / 2.0f, 0.0f);
        }
        double sqx = x*x;
        double sqy = y*y;
        double sqz = z*z;
        return new Vector3f((float) Math.atan2(2*y*w - 2*x*z, 1 - 2*sqy - 2*sqz), (float) Math.asin(2*test), (float) Math.atan2(2*x*w - 2*y*z , 1 - 2*sqx - 2*sqz));
        */
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