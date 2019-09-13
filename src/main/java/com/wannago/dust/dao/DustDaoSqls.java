package com.wannago.dust.dao;

public class DustDaoSqls {
    //위치 먼저 끊고, 시간에서 찾음
    //distance : 5km 이내
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
            " HAVING distance <= 5" +
            " )recent" +
            " WHERE measure_time > (NOW() - INTERVAL 80 MINUTE)" +
            " ORDER BY distance, measure_time desc limit 1";

    public static final String CHECK_MEASUREMENT = "SELECT count(*)" +
            " FROM dustInfo" +
            " INNER JOIN measurementStation m ON m.id = measurement_id" +
            " WHERE m.name = :name and now() - measure_time < 10000";
}
