package engine.core.model;

import engine.core.OpenGLLoader;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.math.Vector3i;
import engine.core.texture.Material;
import engine.core.texture.Texture;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Marco on 22.12.2014.
 */
public class ModelLoader {

    private static final boolean USE_ALPHA_MATERIALS = false;
    private static final Material DEFAULT_MATERIAL = new Material("default", new Vector3f(0, 0, 0), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0, 0, 0), (byte) 0, 0, 0, new Texture(0));

    /*
    reads the .obj file
     */
    public Model readObjectFile(String file, OpenGLLoader loader) {
        ArrayList<ModelGroup> groups = new ArrayList<>();
        Vector<Vertex> vertices = new Vector<>();
        ArrayList<Integer> indices = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            //setup temp save
            ModelGroup currentGroup = new ModelGroup(DEFAULT_MATERIAL, 0);
            groups.add(currentGroup);
            ArrayList<Vector3f> tempLocations = new ArrayList<>();
            ArrayList<Vector3f> tempNormals = new ArrayList<>();
            ArrayList<Vector2f> tempTextures = new ArrayList<>();
            ArrayList<Vector3i> tempIndices = new ArrayList<>();
            ArrayList<Material> materials = null;
            //HashMap<Integer, Vertex> duplicates = new HashMap<>();

            //read file
            while((line = reader.readLine()) != null) {
                String[] data = line.split(" ");

                //handle material lib
                if(data[0].equals("mtllib")) {
                    String materialFile = file.substring(0, file.lastIndexOf("/") + 1) + data[1];
                    materials = readMaterialFile(materialFile, loader);
                } else if (data[0].equals("usemtl")) { //activate model part
                    Material material = getMaterial(materials, data[1]);

                    //check if old model part is useless
                    if(currentGroup.getIndexCount() == 0) {
                        groups.remove(currentGroup);
                    }
                    currentGroup = new ModelGroup(material == null ? DEFAULT_MATERIAL : material, currentGroup.getIndexStart() + currentGroup.getIndexCount()); //set new material and use default if no material is found
                    groups.add(currentGroup);
                }

                //load data
                switch (data[0]) {
                    case "v" : //handle vertex
                        Vector3f location = new Vector3f(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3]));
                        tempLocations.add(location);
                        continue;
                    case "vn" : //handle texture
                        Vector3f normal = new Vector3f(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3]));
                        tempNormals.add(normal);
                        continue;
                    case "vt" : //handle normal
                        Vector2f texture = new Vector2f(Float.parseFloat(data[1]), Float.parseFloat(data[2]));
                        tempTextures.add(texture);
                        continue;
                    case "f" : //handle index

                        Vector3i baseIndex = readIndex(data[1]);
                        for(int i = 0; i < data.length - 3; i++) {
                            tempIndices.add(baseIndex);
                            tempIndices.add(readIndex(data[i + 2]));
                            tempIndices.add(readIndex(data[i + 3]));
                            currentGroup.addTriangle();
                        }

                        /*
                        for(int i = 0; i < 3; i++) {
                            String[] indexData = data[i + 1].split("/");
                            int locationIndex = Integer.parseInt(indexData[0]) - 1; //- 1 because the obj index are starting at 1
                            int textureIndex = (indexData.length > 1 && indexData[1] != null && !indexData[1].equals("")) ? Integer.parseInt(indexData[1]) - 1 : -1;
                            int normalIndex = (indexData.length > 2 && indexData[2] != null && !indexData[2].equals("")) ? Integer.parseInt(indexData[2]) - 1 : -1;
                            tempIndices.add(new Vector3i(locationIndex, textureIndex, normalIndex));
                            currentGroup.addIndex();
                        }
                        */

                        /*
                        for(int i = 0; i < 3; i++) {
                            String[] indexData = data[i + 1].split("/");
                            int vertexIndex = Integer.parseInt(indexData[0]) - 1;
                            Vector3f vertexLocation = locations.get(vertexIndex);
                            Vector3f vertexNormal = null;
                            Vector2f vertexTexture = null;

                            //check vertex
                            vertices.ensureCapacity(vertexIndex);
                            Vertex storedVertex = vertices.get(vertexIndex);

                            //check index format
                            if (indexData.length == 3 && indexData[1] == null || indexData[1].equals("")) { //has location and normal index : 1//1
                                vertexNormal = normals.get(Integer.parseInt(indexData[2]) - 1);
                            } else if (indexData.length == 2) { //has location and texture index : 1/1 or 1/1/
                                vertexTexture = textures.get(Integer.parseInt(indexData[1]) - 1);
                            } else { //has location, texture and normal index : 1/2/3
                                vertexTexture = textures.get(Integer.parseInt(indexData[1]) - 1);
                                vertexNormal = normals.get(Integer.parseInt(indexData[2]) - 1);
                            }

                            indices.add(vertexIndex);
                            if(storedVertex == null) { //place new vertex in buffer + add index
                                vertices.set(vertexIndex, new Vertex(vertexLocation, vertexNormal, vertexTexture));
                            } else if (!storedVertex.equals(vertexLocation, vertexNormal, vertexTexture)) { //place vertex in map
                                duplicates.put(indices.size() - 1, new Vertex(vertexLocation, vertexNormal, vertexTexture));
                            }

                            //add index to material group
                            currentGroup.addIndex();
                        }
                        */
                }
            }
            reader.close();

            //transform data into vertex format
            int nextVertexIndex = tempLocations.size();
            vertices.setSize(nextVertexIndex);

            for(Vector3i index : tempIndices) {
                Vector3f location = tempLocations.get(index.getX());
                Vector2f texture = index.getY() != -1 ? tempTextures.get(index.getY()) : null;
                Vector3f normal = index.getZ() != -1 ? tempNormals.get(index.getZ()) : null;

                //check vertex
                Vertex stored = vertices.get(index.getX());
                if (stored == null) {
                    indices.add(index.getX());
                    vertices.set(index.getX(), new Vertex(location, normal, texture));
                } else if (stored.equals(location, normal, texture)) {
                    indices.add(index.getX());
                } else {
                    vertices.setSize(nextVertexIndex + 1);
                    vertices.set(nextVertexIndex, new Vertex(location, normal, texture));
                    indices.add(nextVertexIndex++);
                }
            }

            /*
            for(Map.Entry<Integer, Vertex> duplicate : duplicates.entrySet()) {
                int newIndex = vertices.size();
                indices.set(duplicate.getKey(), newIndex);
                vertices.addElement(duplicate.getValue());
            }
            */
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        int[] buffer = new int[indices.size()];
        for(int i = 0; i < indices.size(); i ++) {
            buffer[i] = indices.get(i);
        }
        int vaoID = loader.loadToVAO(vertices.toArray(new Vertex[vertices.size()]), buffer);
        return new Model(vaoID, groups.toArray(new ModelGroup[groups.size()]));
    }

    private Material getMaterial(ArrayList<Material> materials, String name) {
        for(Material material : materials) {
            if(material.getName().equals(name)) {
                return material;
            }
        }
        return null;
    }

    private Vector3i readIndex(String index) {
        String[] indexData = index.split("/");
        int locationIndex = Integer.parseInt(indexData[0]) - 1; //- 1 because the obj index are starting at 1
        int textureIndex = (indexData.length > 1 && indexData[1] != null && !indexData[1].equals("")) ? Integer.parseInt(indexData[1]) - 1 : -1;
        int normalIndex = (indexData.length > 2 && indexData[2] != null && !indexData[2].equals("")) ? Integer.parseInt(indexData[2]) - 1 : -1;
        return new Vector3i(locationIndex, textureIndex, normalIndex);
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
                    case "Ka" : //ambient color
                        Vector3f ambient = new Vector3f(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3]));
                        current.setAmbientColor(ambient);
                        continue;
                    case "Kd" : //diffuse color
                        Vector3f diffuse = new Vector3f(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3]));
                        current.setDiffuseColor(diffuse);
                        continue;
                    case "Ks" : //specular color
                        Vector3f specular = new Vector3f(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3]));
                        current.setAmbientColor(specular);
                        continue;
                    case "Ns" : //shininess
                        float shininess = Float.parseFloat(data[1]);
                        current.setShininess(shininess);
                        continue;
                    case "d" : //transparency
                        float transparency = Float.parseFloat(data[1]);
                        current.setTransparency(transparency);
                        continue;
                    case "illum" : //illumination mode (0 = diffuse; 1 = f(diffuse, ambient); 2 = f(diffuse, ambient, specular))
                        byte illumination = Byte.parseByte(data[1]);
                        current.setIllumination(illumination);
                        continue;
                    case "map_Kd" : //find the texture file
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
