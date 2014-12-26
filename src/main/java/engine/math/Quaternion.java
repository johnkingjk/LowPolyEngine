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
        float pitch = rotation.getX();
        float yaw = rotation.getY();
        float roll = rotation.getZ();

        float angle;
        float sinRoll, sinPitch, sinYaw, cosRoll, cosPitch, cosYaw;
        angle = pitch * 0.5f;
        sinPitch = (float) Math.sin(angle);
        cosPitch = (float) Math.cos(angle);
        angle = roll * 0.5f;
        sinRoll = (float) Math.sin(angle);
        cosRoll = (float) Math.cos(angle);
        angle = yaw * 0.5f;
        sinYaw = (float) Math.sin(angle);
        cosYaw = (float) Math.cos(angle);

        // variables used to reduce multiplication calls.
        float cosRollXcosPitch = cosRoll * cosPitch;
        float sinRollXsinPitch = sinRoll * sinPitch;
        float cosRollXsinPitch = cosRoll * sinPitch;
        float sinRollXcosPitch = sinRoll * cosPitch;

        w = (cosRollXcosPitch * cosYaw - sinRollXsinPitch * sinYaw);
        x = (cosRollXcosPitch * sinYaw + sinRollXsinPitch * cosYaw);
        y = (sinRollXcosPitch * cosYaw + cosRollXsinPitch * sinYaw);
        z = (cosRollXsinPitch * cosYaw - sinRollXcosPitch * sinYaw);

        normalize();
    }

    public Vector3f toAngles() {
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

}