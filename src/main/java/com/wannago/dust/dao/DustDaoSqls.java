package com.wannago.dust.dao;

public class DustDaoSqls {
    //위치 먼저 끊고, 시간에서 찾음
    //distance : 10km 이내 
    public static final String SELECT_RECENT = "SELECT" +
            " id," +
            " dust," +
            " x_location_info," +
            " y_location_info," +
            " measurement_id,"  +
            " measure_time," +
            " distance" +
            " FROM (" +
            " SELECT" +
            " id, " +
            " dust, " +
            " x_location_info, " +
            " y_location_info, " +
            " measurement_id, " +
            " measure_time," +
            " (6371*acos(cos(radians(:x))*cos(radians(x_location_info))*cos(radians(y_location_info)" +
            " -radians(:y))+sin(radians(:x))*sin(radians(x_location_info))))" +
            " AS distance" +
            " FROM dustInfo" +
            " HAVING distance <= 10" +
            " )recent" +
            " WHERE measure_time > (NOW() - INTERVAL 70 MINUTE)" +
            " ORDER BY distance, measure_time desc limit 1";
}
