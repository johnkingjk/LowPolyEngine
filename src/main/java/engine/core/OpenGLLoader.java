package engine.core;

import engine.texture.Texture;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by Marco on 23.12.2014.
 */
public class OpenGLLoader implements Unloadable {

    private static final ArrayList<Integer> imageIDs = new ArrayList<>();

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

    @Override
    public void cleanUp() {
        for(int textureID : imageIDs) {
            GL11.glDeleteTextures(textureID);
        }
    }
}
