package engine.model;

import engine.core.OpenGLLoader;
import engine.math.Vector3f;
import engine.texture.Material;
import engine.texture.Texture;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Marco on 22.12.2014.
 */
public class ModelLoader {

    private static final boolean USE_ALPHA_MATERIALS = false;
    private static final Material DEFAULT_MATERIAL = new Material("default", new Vector3f(0, 0, 0), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0, 0, 0), (byte) 0, 0, 0, new Texture(0));

    /*
    reads the .obj file
     */
    public Model readObjectFile(String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            Material current = DEFAULT_MATERIAL;
            ArrayList<Material> materials = null;
            while((line = reader.readLine()) != null) {

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    /*
    reads the .mtl file
     */
    public ArrayList<Material> readMaterialFile(String file, OpenGLLoader loader) {
        ArrayList<Material> result = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            Material current = null;
            while((line = reader.readLine()) != null) {
                String[] data = line.split(" ");
                if(data[0].length() == 0) {
                    continue;
                }
                if(data[0].equals("newmtl")) {
                    current = new Material(data[1]); //create new Material and read name
                    result.add(current);
                }
                if (current == null) {
                    continue;
                }
                switch(data[0]) {
                    case "Ka" :
                        Vector3f ambient = new Vector3f(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3]));
                        current.setAmbientColor(ambient);
                        continue;
                    case "Kd" :
                        Vector3f diffuse = new Vector3f(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3]));
                        current.setDiffuseColor(diffuse);
                        continue;
                    case "Ks" :
                        Vector3f specular = new Vector3f(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3]));
                        current.setAmbientColor(specular);
                        continue;
                    case "Ns" :
                        float shininess = Float.parseFloat(data[1]);
                        current.setShininess(shininess);
                        continue;
                    case "d" :
                        float transparency = Float.parseFloat(data[1]);
                        current.setTransparency(transparency);
                        continue;
                    case "illum" :
                        byte illumination = Byte.parseByte(data[1]);
                        current.setIllumination(illumination);
                        continue;
                    case "map_Kd" :
                        String textureFile = file.substring(0, file.lastIndexOf("/") + 1) + data[1];
                        Texture texture = loader.loadTexture(textureFile, USE_ALPHA_MATERIALS);
                        current.setTexture(texture);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
