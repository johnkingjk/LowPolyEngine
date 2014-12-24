package engine.shader;

import engine.core.Unloadable;
import engine.math.Matrix4f;
import engine.math.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

/**
 * Created by Marco on 27.11.2014.
 */
public abstract class ShaderProgram implements Unloadable {

    private int programID;
    private int vertexID;
    private int fragmentID;

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(String file) {
        vertexID = loadShader(file + ".vert", GL20.GL_VERTEX_SHADER);
        fragmentID = loadShader(file + ".frag", GL20.GL_FRAGMENT_SHADER);
        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexID);
        GL20.glAttachShader(programID, fragmentID);
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
        getAllUniformLocations();
    }

    public void start() {
        GL20.glUseProgram(programID);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    @Override
    public void cleanUp() {
        stop();
        GL20.glDetachShader(programID, vertexID);
        GL20.glDetachShader(programID, fragmentID);
        GL20.glDeleteShader(vertexID);
        GL20.glDeleteShader(fragmentID);
        GL20.glDeleteProgram(programID);
    }

    public abstract void bindAttributes();

    public abstract void getAllUniformLocations();

    protected int getUniformLocation(String variable) {
        return GL20.glGetUniformLocation(programID, variable);
    }

    protected void bindAttribute(int attribute, String variable) {
        GL20.glBindAttribLocation(programID, attribute, variable);
    }

    protected void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    protected void loadInteger(int location, int value) {
        GL20.glUniform1i(location, value);
    }

    protected void loadVector(int location, Vector3f vector) {
        GL20.glUniform3f(location, vector.getX(), vector.getY(), vector.getZ());
    }

    protected void loadBoolean(int location, boolean value) {
        float load = 0;
        if(value) {
            load = 1;
        }
        GL20.glUniform1f(location, load);
    }

    protected void loadMatrix(int location, Matrix4f matrix) {
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4(location, false, matrixBuffer);
    }

    private static int loadShader(String file, int type) {
        StringBuilder source = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                source.append(line).append('\n');
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, source);
        GL20.glCompileShader(shaderID);
        if(GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.exit(-1);
        }
        return shaderID;
    }
}
