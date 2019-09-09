package com.wannago.measureStation.dao;

public class MeasureStationDaoSqls {
    public static final String SELECT_MEASURESTATION = "SELECT id," +
                                                       " x_location_info," +
                                                       " y_location_info," +
                                                       " name" +
                                                       " FROM measurementStation" +
                                                       " WHERE name = :name";

    public static final String SELECT_All_MEASURESTATIONS = "SELECT" +
                                                            " m.id," +
                                                            " m.name," +
                                                            " m.x_location_info," +
                                                            " m.y_location_info," +
                                                            " max_time," +
                                                            " d.dust" +
                                                            " FROM measurementStation m" +
                                                            " INNER JOIN dustInfo d ON d.measurement_id = m.id" +
                                                            " INNER JOIN (" +
                                                            " SELECT" +
                                                            " max(measure_time) as max_time," +
                                                            " measurement_id from dustInfo" +
                                                            " WHERE measure_time > (NOW() - interval 120 minute)" +
                                                            " GROUP BY measurement_id" +
                                                            ")recent ON recent.max_time = d.measure_time and recent.measurement_id = m.id;";

    public static final String SELECT_MEASURESTATION_GPS = "SELECT id" +
                                                            " FROM measurementStation" +
                                                            " WHERE x_location_info = :x and y_location_info = :y";
}
