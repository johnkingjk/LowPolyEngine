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

    public Matrix3f(float[][] m) {
        this.m = m;
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

    public Matrix3f identity() {
        m[0][0] = 1;
        m[0][1] = 0;
        m[0][2] = 0;
        m[1][0] = 0;
        m[1][1] = 1;
        m[1][2] = 0;
        m[2][0] = 0;
        m[2][1] = 0;
        m[2][2] = 1;

        return this;
    }

    public Vector3f mul(Vector3f v) {
        float x = v.getX();
        float y = v.getY();
        float z = v.getZ();
        v.setX(m[0][0] * x + m[0][1] * y + m[0][2] * z);
        v.setY(m[1][0] * x + m[1][1] * y + m[1][2] * z);
        v.setZ(m[2][0] * x + m[2][1] * y + m[2][2] * z);
        return v;
    }

    public Matrix3f mul(Matrix3f r) {
        float[][] t = new float[3][3];
        this.copyTo(t);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.set(i, j, t[i][0] * r.get(0, j) + t[i][1] * r.get(1, j)
                        + t[i][2] * r.get(2, j));
            }
        }

        return this;
    }

    public void copyTo(float[][] matrix) {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                matrix[x][y] = m[x][y];
            }
        }
    }

    public float[][] getM() {
        return m;
    }

    public void setM(float[][] m) {
        this.m = m;
    }

    public float get(int x, int y) {
        return m[x][y];
    }

    public void set(int x, int y, float value) {
        m[x][y] = value;
    }

    @Override
    public Matrix3f clone() {
        return new Matrix3f(m);
    }
}
