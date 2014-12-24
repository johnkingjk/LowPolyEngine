package engine.math;

/**
 * @author johnking
 */
public class Matrix4f {

    private float[][] m;

    public Matrix4f() {
        m = new float[4][4];
        this.identity();
    }

    public Matrix4f identity() {
        m[0][0] = 1;
        m[0][1] = 0;
        m[0][2] = 0;
        m[0][3] = 0;
        m[1][0] = 0;
        m[1][1] = 1;
        m[1][2] = 0;
        m[1][3] = 0;
        m[2][0] = 0;
        m[2][1] = 0;
        m[2][2] = 1;
        m[2][3] = 0;
        m[3][0] = 0;
        m[3][1] = 0;
        m[3][2] = 0;
        m[3][3] = 1;

        return this;
    }

    public Matrix4f translate(Vector3f translation) {
        m[3][0] += m[0][0] * translation.getX() + m[1][0] * translation.getY() + m[2][0] * translation.getZ();
        m[3][0] += m[0][1] * translation.getX() + m[1][1] * translation.getY() + m[2][1] * translation.getZ();
        m[3][0] += m[0][2] * translation.getX() + m[1][2] * translation.getY() + m[2][2] * translation.getZ();
        m[3][0] += m[0][3] * translation.getX() + m[1][3] * translation.getY() + m[2][3] * translation.getZ();

        return this;
    }

    public Matrix4f rotate(float rx, float ry, float rz) {
        float sinX = (float) Math.sin(rx);
        float cosX = (float) Math.cos(rx);
        float sinY = (float) Math.sin(ry);
        float cosY = (float) Math.cos(ry);
        float sinZ = (float) Math.sin(rz);
        float cosZ = (float) Math.cos(rz);

        float[][] t = new float[4][4];

        //x rotation
        this.copyTo(t);
        m[1][0] = t[1][0] * cosX + t[2][0] * sinX;
        m[1][1] = t[1][1] * cosX + t[2][1] * sinX;
        m[1][2] = t[1][2] * cosX + t[2][2] * sinX;
        m[1][3] = t[1][3] * cosX + t[2][3] * sinX;
        m[2][0] = t[1][0] * (-sinX) + t[2][0] * cosX;
        m[2][1] = t[1][1] * (-sinX) + t[2][1] * cosX;
        m[2][2] = t[1][2] * (-sinX) + t[2][2] * cosX;
        m[2][3] = t[1][3] * (-sinX) + t[2][3] * cosX;

        //y rotation
        this.copyTo(t);
        m[0][0] = t[0][0] * cosY + t[2][0] * (-sinY);
        m[0][1] = t[0][1] * cosY + t[2][1] * (-sinY);
        m[0][2] = t[0][2] * cosY + t[2][2] * (-sinY);
        m[0][3] = t[0][3] * cosY + t[2][3] * (-sinY);
        m[2][0] = t[0][0] * sinY + t[2][0] * cosY;
        m[2][1] = t[0][1] * sinY + t[2][1] * cosY;
        m[2][2] = t[0][2] * sinY + t[2][2] * cosY;
        m[2][3] = t[0][3] * sinY + t[2][3] * cosY;

        //z rotation
        this.copyTo(t);
        m[0][0] = t[0][0] * cosZ + t[1][0] * sinZ;
        m[0][1] = t[0][1] * cosZ + t[1][1] * sinZ;
        m[0][2] = t[0][2] * cosZ + t[1][2] * sinZ;
        m[0][3] = t[0][3] * cosZ + t[1][3] * sinZ;
        m[1][0] = t[0][0] * (-sinZ) + t[1][0] * cosZ;
        m[1][1] = t[0][1] * (-sinZ) + t[1][1] * cosZ;
        m[1][2] = t[0][2] * (-sinZ) + t[1][2] * cosZ;
        m[1][3] = t[0][3] * (-sinZ) + t[1][3] * cosZ;

        return this;
    }

    public void copyTo(float[][] matrix) {
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 4; y++) {
                matrix[x][y] = m[x][y];
            }
        }
    }

    public Matrix4f scale(Vector3f scale) {
        m[0][0] *= scale.getX();
        m[0][1] *= scale.getX();
        m[0][2] *= scale.getX();
        m[0][3] *= scale.getX();

        m[1][0] *= scale.getY();
        m[1][1] *= scale.getY();
        m[1][2] *= scale.getY();
        m[1][3] *= scale.getY();

        m[2][0] *= scale.getZ();
        m[2][1] *= scale.getZ();
        m[2][2] *= scale.getZ();
        m[2][3] *= scale.getZ();

        return this;
    }

    public Matrix4f setProjection(float fov, float width, float height, float zNear, float zFar) {
        float ar = width / height;
        float tanHalfFOV = (float) Math.tan(Math.toRadians(fov / 2));
        float zRange = zNear - zFar;

        m[0][0] = 1.0f / (tanHalfFOV * ar);
        m[0][1] = 0;
        m[0][2] = 0;
        m[0][3] = 0;
        m[1][0] = 0;
        m[1][1] = 1.0f / tanHalfFOV;
        m[1][2] = 0;
        m[1][3] = 0;
        m[2][0] = 0;
        m[2][1] = 0;
        m[2][2] = (-zNear - zFar) / zRange;
        m[2][3] = 2 * zFar * zNear / zRange;
        m[3][0] = 0;
        m[3][1] = 0;
        m[3][2] = 1;
        m[3][3] = 0;

        return this;
    }

    public Matrix4f setCamera(Vector3f forward, Vector3f up) {
        Vector3f f = forward;
        f.normalized();

        Vector3f r = up;
        r.normalized();
        r = r.cross(f);

        Vector3f u = f.cross(r);

        m[0][0] = r.getX();
        m[0][1] = r.getY();
        m[0][2] = r.getZ();
        m[0][3] = 0;
        m[1][0] = u.getX();
        m[1][1] = u.getY();
        m[1][2] = u.getZ();
        m[1][3] = 0;
        m[2][0] = f.getX();
        m[2][1] = f.getY();
        m[2][2] = f.getZ();
        m[2][3] = 0;
        m[3][0] = 0;
        m[3][1] = 0;
        m[3][2] = 0;
        m[3][3] = 1;

        return this;
    }

    public Matrix4f mul(Matrix4f r) {
        Matrix4f res = new Matrix4f();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                res.set(i, j, m[i][0] * r.get(0, j) + m[i][1] * r.get(1, j)
                        + m[i][2] * r.get(2, j) + m[i][3] * r.get(3, j));
            }
        }

        return res;
    }

    public float[][] getM() {
        return m;
    }

    public float get(int x, int y) {
        return m[x][y];
    }

    public void setM(float[][] m) {
        this.m = m;
    }

    public void set(int x, int y, float value) {
        m[x][y] = value;
    }
}
