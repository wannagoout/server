package com.wannago.dust.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class Dust {
   private double xLocationInfo; //위도
   private double yLocationInfo; //경도
   private double dust;    //미세먼지 수치
   private Long id;
   private double distance;
   @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "Asia/Seoul")
   private Date measureTime;

    public double getxLocationInfo() {
        return xLocationInfo;
    }

    public void setxLocationInfo(double xLocationInfo) {
        this.xLocationInfo = xLocationInfo;
    }

    public double getyLocationInfo() {
        return yLocationInfo;
    }

    public void setyLocationInfo(double yLocationInfo) {
        this.yLocationInfo = yLocationInfo;
    }

    public double getDust() {
        return dust;
    }

    public void setDust(double dust) {
        this.dust = dust;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(Date measureTime) {
        this.measureTime = measureTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Dust{" +
                "xLocationInfo=" + xLocationInfo +
                ", yLocationInfo=" + yLocationInfo +
                ", dust=" + dust +
                ", id=" + id +
                ", distance=" + distance +
                ", measureTime=" + measureTime +
                '}';
    }
}
