package engine.math;

/**
 * Created by Marco on 27.12.2014.
 */
public class Matrix3f {

    private float[][] m;

    public Matrix3f() {
        m = new float[3][3];
        this.identity();
    }

    public Matrix3f(float angle, Vector3f axis) {
        m = new float[3][3];
        axis = axis.normalized();

        float cos = FastMath.cos(angle);
        float sin = FastMath.sin(angle);
        float oneMinusCos = 1.0f - cos;
        float xx = axis.getX() * axis.getX();
        float yy = axis.getY() * axis.getY();
        float zz = axis.getZ() * axis.getZ();
        float xym = axis.getX() * axis.getY() * oneMinusCos;
        float xzm = axis.getX() * axis.getZ() * oneMinusCos;
        float yzm = axis.getY() * axis.getZ() * oneMinusCos;
        float xs = axis.getX() * sin;
        float ys = axis.getY() * sin;
        float zs = axis.getZ() * sin;

        m[0][0] = xx * oneMinusCos + cos;
        m[0][1] = xym - zs;
        m[0][2] = xzm + ys;
        m[1][0] = xym + zs;
        m[1][1] = yy * oneMinusCos + cos;
        m[1][2] = yzm - xs;
        m[2][0] = xzm - ys;
        m[2][1] = yzm + xs;
        m[2][2] = zz * oneMinusCos + cos;
    }

    public void identity() {
        m[0][0] = 1; m[0][1] = 0; m[0][2] = 0;
        m[1][0] = 0; m[1][1] = 1; m[1][2] = 0;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = 1;
    }

    public Vector3f mul(Vector3f vector) {
        Vector3f result = new Vector3f();

        result.setX(m[0][0] * vector.getX() + m[0][1] * vector.getY() + m[0][2] * vector.getZ());
        result.setY(m[1][0] * vector.getX() + m[1][1] * vector.getY() + m[1][2] * vector.getZ());
        result.setZ(m[2][0] * vector.getX() + m[2][1] * vector.getY() + m[2][2] * vector.getZ());

        return result;
    }
}
