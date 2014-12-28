package engine.rendering.mesh;

import engine.rendering.shader.ShaderProgram;

import java.util.ArrayList;

/**
 * Created by Marco on 25.12.2014.
 */
public class MeshPart {

    private String name;
    private ArrayList<MaterialGroup> groups;
    private ShaderProgram program = null;
    private boolean smooth;

    public MeshPart(String name) {
        this.name = name;
        this.groups = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<MaterialGroup> getGroups() {
        return groups;
    }

    public ShaderProgram getShader() {
        return program;
    }

    public void setShader(ShaderProgram program) {
        this.program = program;
    }

    public boolean isSmooth() {
        return smooth;
    }

    public void setSmooth(boolean smooth) {
        this.smooth = smooth;
    }

    public boolean isEmpty() {
        for(MaterialGroup group : groups) {
            if(group.getIndexCount() != 0) {
                return false;
            }
        }
        return true;
    }
}
