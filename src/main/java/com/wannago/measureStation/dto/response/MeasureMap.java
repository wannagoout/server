package com.wannago.measureStation.dto.response;

public class MeasureMap {
    private String name;
    private double x_location_info;
    private double y_location_info;
    private double dust;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX_location_info() {
        return x_location_info;
    }

    public void setX_location_info(double x_location_info) {
        this.x_location_info = x_location_info;
    }

    public double getY_location_info() {
        return y_location_info;
    }

    public void setY_location_info(double y_location_info) {
        this.y_location_info = y_location_info;
    }

    public double getDust() {
        return dust;
    }

    public void setDust(double dust) {
        this.dust = dust;
    }
}
