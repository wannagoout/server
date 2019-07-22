package com.wannago.dust.dao;

public class DustDaoSqls {
    //distance : 10km 이내 
    public static final String SELECT_RECENT = "SELECT" +
            " id," +
            " dust," +
            " x_location_info," +
            " y_location_info," +
            " measure_time," +
            " (6371*acos(cos(radians(:x))*cos(radians(x_location_info))*cos(radians(y_location_info)" +
            " -radians(:y))+sin(radians(:x))*sin(radians(x_location_info))))" +
            " AS distance" +
            " FROM (" +
            " SELECT" +
            " id, " +
            " dust, " +
            " x_location_info, " +
            " y_location_info, " +
            " measure_time" +
            " from dustInfo" +
            " where measure_time > (NOW() - INTERVAL 1 HOUR)" +
            " )recent" +
            " HAVING distance <= 10" +
            " ORDER BY distance limit 1";
}
