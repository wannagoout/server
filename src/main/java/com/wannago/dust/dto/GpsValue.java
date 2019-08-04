package com.wannago.dust.dto;

import java.util.List;

public class GpsValue {
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "GpsValue{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
