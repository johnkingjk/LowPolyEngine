package engine.rendering.model;

import engine.rendering.shader.ShaderProgram;

import java.util.ArrayList;

/**
 * Created by Marco on 25.12.2014.
 */
public class ModelPart {

    private String name;
    private ArrayList<RenderGroup> groups;
    private ShaderProgram program = null;
    private boolean smooth;

    public ModelPart(String name) {
        this.name = name;
        this.groups = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<RenderGroup> getGroups() {
        return groups;
    }

    public ShaderProgram getProgram() {
        return program;
    }

    public void setProgram(ShaderProgram program) {
        this.program = program;
    }

    public boolean isSmooth() {
        return smooth;
    }

    public void setSmooth(boolean smooth) {
        this.smooth = smooth;
    }

    public boolean isEmpty() {
        for(RenderGroup group : groups) {
            if(group.getIndexCount() != 0) {
                return false;
            }
        }
        return true;
    }
}
