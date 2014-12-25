package engine.rendering.texture;

import engine.math.Vector3f;

/**
 * Copyright by michidk
 * Created: 22.12.2014.
 */
public class Material {

    private String name;
    private Vector3f ambientColor;
    private Vector3f diffuseColor;
    private Vector3f specularColor;
    private byte illumination;
    private float shininess;
    private float transparency;
    private Texture texture;

    public Material(String name) {
        this.name = name;
    }

    public Material(String name, Vector3f ambientColor, Vector3f diffuseColor, Vector3f specularColor, byte illumination, float shininess, float transparency, Texture texture) {
        this.name = name;
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.illumination = illumination;
        this.shininess = shininess;
        this.transparency = transparency;
        this.texture = texture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector3f getAmbientColor() {
        return ambientColor;
    }

    public void setAmbientColor(Vector3f ambientColor) {
        this.ambientColor = ambientColor;
    }

    public Vector3f getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Vector3f diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public Vector3f getSpecularColor() {
        return specularColor;
    }

    public void setSpecularColor(Vector3f specularColor) {
        this.specularColor = specularColor;
    }

    public byte getIllumination() {
        return illumination;
    }

    public void setIllumination(byte illumination) {
        this.illumination = illumination;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public float getTransparency() {
        return transparency;
    }

    public void setTransparency(float transparency) {
        this.transparency = transparency;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
