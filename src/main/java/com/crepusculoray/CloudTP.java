package com.crepusculoray;

public class CloudTP {
    private float radius, angle, size;

    public CloudTP(float radius, float angle, float size) {
        this.radius = radius;
        this.angle = angle;
        this.size = size;
    }

    public float getRadius() {
        return this.radius;
    }

    public float getAngle() {
        return this.angle;
    }
    public float getSize() {
        return this.size;
    }
}
