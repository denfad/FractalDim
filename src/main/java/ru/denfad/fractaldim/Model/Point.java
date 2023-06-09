package ru.denfad.fractaldim.Model;

public class Point {
    protected float t;
    protected float x;

    public Point(float t, float x) {
        this.t = t;
        this.x = x;
    }

    public float getT() {
        return t;
    }

    public float getX() {
        return x;
    }

    public void setT(float t) {
        this.t = t;
    }

    public void setX(float x) {
        this.x = x;
    }
}

