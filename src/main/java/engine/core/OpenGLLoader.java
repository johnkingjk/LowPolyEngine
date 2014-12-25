package engine.core;

import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.model.Vertex;
import engine.texture.Texture;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

/**
 * Created by Marco on 23.12.2014.
 */
public class OpenGLLoader implements Unloadable {

    private final ArrayList<Integer> imageIDs = new ArrayList<>();
    private final ArrayList<Integer> vaoIDs = new ArrayList<>();
    private final ArrayList<Integer> vboIDs = new ArrayList<>();

    public Texture loadTexture (String fileName, boolean useAlpha) {
        int bytesPerPixel = useAlpha ? 4 : 3;
        try {
            BufferedImage image = ImageIO.read(new File(fileName));
            int[] pixels = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

            //create byte buffer
            ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * bytesPerPixel);
            for(int y = 0; y < image.getHeight(); y++) {
                for(int x = 0; x < image.getWidth(); x++) {
                    int pixel = pixels[y * image.getWidth() + x];
                    buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red
                    buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green
                    buffer.put((byte) (pixel & 0xFF));             // Blue
                    if (useAlpha) {
                        buffer.put((byte) ((pixel >> 24) & 0xFF));     // Alpha
                    }
                }
            }
            buffer.flip();

            //send image to OpenGL
            int textureID = GL11.glGenTextures();
            imageIDs.add(textureID);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

            int mode = useAlpha ? GL11.GL_RGBA : GL11.GL_RGB;
            int byteMode = useAlpha ? GL11.GL_RGBA8 : GL11.GL_RGB8;
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, byteMode, image.getWidth(), image.getHeight(), 0, mode, GL11.GL_UNSIGNED_BYTE, buffer);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

            return new Texture(textureID);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int loadToVAO(Vertex[] vertices, int[] indices) {
        int vaoID = createVAO();
        float[] positions = extractPositions(vertices);
        float[] textures = extractTextures(vertices);
        float[] normals = extractNormals(vertices);
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textures);
        storeDataInAttributeList(2, 3, normals);
        GL30.glBindVertexArray(0);
        return vaoID;
    }

    private float[] extractPositions(Vertex[] vertices) {
        float[] positions = new float[vertices.length * 3];
        for(int i = 0; i < vertices.length; i++) {
            if(vertices[i] == null) {
                System.out.println("missing vertex " + i + " " + vertices.length);
                continue;
            }
            Vector3f position = vertices[i].getPosition();
            positions[i * 3] = position.getX();
            positions[i * 3 + 1] = position.getY();
            positions[i * 3 + 2] = position.getZ();
        }
        return positions;
    }

    private float[] extractTextures(Vertex[] vertices) {
        float[] textures = new float[vertices.length * 2];
        for(int i = 0; i < vertices.length; i++) {
            if(vertices[i] == null) {
                continue;
            }
            Vector2f texture = vertices[i].getTexture() == null ? new Vector2f(0, 0) : vertices[i].getTexture();
            textures[i * 2] = texture.getX();
            textures[i * 2 + 1] = texture.getY();
        }
        return textures;
    }

    private float[] extractNormals(Vertex[] vertices) {
        float[] normals = new float[vertices.length * 3];
        for(int i = 0; i < vertices.length; i++) {
            if(vertices[i] == null) {
                continue;
            }
            Vector3f normal = vertices[i].getNormal() == null ? new Vector3f(0, 0, 0) : vertices[i].getNormal();
            normals[i * 3] = normal.getX();
            normals[i * 3 + 1] = normal.getY();
            normals[i * 3 + 2] = normal.getZ();
        }
        return normals;
    }

    private int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        vaoIDs.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList(int attributeNumber, int size, float[] data) {
        int vboID = GL15.glGenBuffers();
        vboIDs.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = asFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void bindIndicesBuffer(int[] indices) {
        int vboID = GL15.glGenBuffers();
        vboIDs.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = asIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private IntBuffer asIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer asFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    @Override
    public void cleanUp() {
        for(int vao : vaoIDs) {
            GL30.glDeleteVertexArrays(vao);
        }
        for(int vbo : vboIDs) {
            GL15.glDeleteBuffers(vbo);
        }
        for(int textureID : imageIDs) {
            GL11.glDeleteTextures(textureID);
        }
    }
}
