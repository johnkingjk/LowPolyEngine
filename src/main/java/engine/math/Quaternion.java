package engine.math;

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
    public Quaternion(float xAngle, float yAngle, float zAngle) {
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

    public Quaternion(float angle, Vector3f axis) {
        float halfAngle = 0.5f * angle;
        float sin = FastMath.sin(halfAngle);

        x = sin * axis.getX();
        y = sin * axis.getY();
        z = sin * axis.getZ();
        w = FastMath.cos(halfAngle);
    }

    public Quaternion(Vector3f xAxis, Vector3f yAxis, Vector3f zAxis) {
        float t = xAxis.getX() + yAxis.getY() + zAxis.getZ();

        if(t >= 0) {
            float s = FastMath.sqrt(t + 1);
            w = 0.5f * s;
            s = 0.5f / s;
            x = (yAxis.getZ() - zAxis.getY()) * s;
            y = (zAxis.getX() - xAxis.getZ()) * s;
            z = (xAxis.getY() - yAxis.getX()) * s;
        } else if (xAxis.getX() > yAxis.getY() && xAxis.getX() > zAxis.getZ()) {
            float s = FastMath.sqrt(1.0f + xAxis.getX() - yAxis.getY() - zAxis.getZ());
            x = s * 0.5f;
            s = 0.5f / s;
            y = (xAxis.getY() + yAxis.getX()) * s;
            z = (zAxis.getX() + xAxis.getZ()) * s;
            w = (yAxis.getZ() - zAxis.getY()) * s;
        } else if (yAxis.getY() > zAxis.getZ()) {
            float s = FastMath.sqrt(1.0f + yAxis.getY() - xAxis.getX() - zAxis.getZ());
            y = s * 0.5f;
            s = 0.5f / s;
            x = (xAxis.getY() + yAxis.getX()) * s;
            z = (yAxis.getZ() + zAxis.getY()) * s;
            w = (zAxis.getX() - xAxis.getZ()) * s;
        } else {
            float s = FastMath.sqrt(1.0f + zAxis.getZ() - xAxis.getX() - yAxis.getY());
            z = s * 0.5f;
            s = 0.5f / s;
            x = (zAxis.getX() + xAxis.getZ()) * s;
            y = (yAxis.getZ() + zAxis.getY()) * s;
            w = (xAxis.getY() - yAxis.getX()) * s;
        }
    }

    public float[] toAngles() {
        float[] result = new float[3];

        float sqw = w * w;
        float sqx = x * x;
        float sqy = y * y;
        float sqz = z * z;
        float unit = sqx + sqy + sqz + sqw;

        float test = x * y + z * w;
        if (test > 0.499 * unit) {
            result[1] = 2.0f * FastMath.atan2(x, w);
            result[2] = FastMath.PI / 2.0f;
        } else if (test < -0.499 * unit) {
            result[1] = -2.0f * FastMath.atan2(x, w);
            result[2] = -FastMath.PI / 2.0f;
        } else {
            result[0] = FastMath.atan2(2 * x * w - 2 * y * z, -sqx + sqy - sqz + sqw);
            result[1] = FastMath.atan2(2 * y * w - 2 * x * z, sqx - sqy - sqz + sqw);
            result[2] = FastMath.asin(2 * test / unit);
        }
        return result;
    }

    public Vector3f getAxis(int axis) {
        Vector3f result = new Vector3f();

        float tn2 = 1.0f / length() * 2.0f;

        switch (axis) {
            case 0 :
                result.setX(1 - (y * y + z * z) * tn2);
                result.setY((x * y + z * w) * tn2);
                result.setZ((x * z - y * w) * tn2);
                break;
            case 1 :
                result.setX((x * y - z * w) * tn2);
                result.setY(1 - (x * x + z * z) * tn2);
                result.setZ((y * z + x * w) * tn2);
                break;
            case 2 :
                result.setX((x * z + y * w) * tn2);
                result.setY((y * z - x * w) * tn2);
                result.setZ(1 - (x * x + y * y) * tn2);
        }
        return result;
    }

    public float length() {
        return FastMath.sqrt(x * x + y * y + z * z + w * w);
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

    public Quaternion inverse() {
        return normalize().conjugate();
    }

    public Quaternion add(Quaternion q) {
        return add(q.getX(), q.getY(), q.getZ(), q.getW());
    }

    public Quaternion add(float x, float y, float z, float w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;

        return this;
    }

    public Quaternion sub(Quaternion q) {
        return sub(q.getX(), q.getY(), q.getZ(), q.getW());
    }

    public Quaternion sub(float x, float y, float z, float w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;

        return this;
    }

    public Quaternion mul(Quaternion r) {
        float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
        float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
        float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();
        float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();

        return new Quaternion(x_, y_, z_, w_);
    }

    public Quaternion mul(Vector3f r) {
        float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
        float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
        float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
        float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

        return new Quaternion(x_, y_, z_, w_);
    }

    public float dot(Quaternion q) {
        return x * q.x + y * q.y + z * q.z + w * q.w;
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

    @Override
    public Quaternion clone() {
        return new Quaternion(x, y, z, w);
    }
}