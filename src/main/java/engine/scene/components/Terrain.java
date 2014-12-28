package engine.scene.components;

import engine.math.Vector3f;
import engine.rendering.OpenGLLoader;
import engine.rendering.mesh.Vertex;
import engine.transform.Transform;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Copyright by michidk
 * Created: 26.12.2014.
 */
public class Terrain extends Transform {

    private int width = 200;
    private int height = 200;
    private int indicies = ((2*height) * width);
    private int vertices = indicies * 3;

    private static float[][] data;
    private int[] indis = new int[indicies];
    private Vertex[] verts = new Vertex[indicies];

    private OpenGLLoader openGLLoader;
    private int vao;

    public Terrain(OpenGLLoader openGLLoader) {
        loadHeightmap();
        initVertices();

        vao = openGLLoader.loadToVAO(verts, indis);
    }

    private void loadHeightmap() {
        try {
            BufferedImage heightmap = ImageIO.read(new File("src/main/resources/heightmap.png"));
            data = new float[heightmap.getWidth()][heightmap.getHeight()];
            Color color;

            for (int x = 0; x < data.length; x++) {
                for (int z = 0; z < data[x].length; z++) {
                    color = new Color(heightmap.getRGB(x, z));
                    data[z][x] = color.getRed();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initVertices() {
        int c = 0;
        for (int z = 0; z < data.length - 1; z++) {
            for (int x = 0; x < data[z].length; x++) {
                verts[c] = new Vertex(new Vector3f(x, data[z][x], z), null, null);
                c++;
                verts[c] = new Vertex(new Vector3f(x, data[z + 1][x], z + 1), null, null);
                c++;
            }
        }

        for (int i = 0; i < indicies; i++) {
            indis[i] = i;
        }
    }

    public void render() {
        GL30.glBindVertexArray(vao);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL11.glDrawElements(GL11.GL_TRIANGLE_STRIP, indicies, GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }


}
