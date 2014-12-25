package engine.rendering.model;

import engine.rendering.shader.ShaderProgram;

import java.util.ArrayList;

/**
 * Created by Marco on 25.12.2014.
 */
public class ModelPart {

    private String name;
    private ArrayList<ModelGroup> groups;
    private ShaderProgram program = null;
    private boolean smooth;

    public ModelPart(String name) {
        this.name = name;
        this.groups = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<ModelGroup> getGroups() {
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
        for(ModelGroup group : groups) {
            if(group.getIndexCount() != 0) {
                return false;
            }
        }
        return true;
    }
}
