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
                                                        " r.dust" +
                                                        " FROM measurementStation m" +
                                                        " Left OUTER JOIN (" +
                                                        " SELECT max_time, d.dust, d.measurement_id" +
                                                        " FROM dustInfo d," +
                                                        " (" +
                                                        " SELECT max(measure_time) as max_time, measurement_id" +
                                                        " FROM dustInfo" +
                                                        " WHERE measure_time > (NOW() - INTERVAL 80 Minute)" +
                                                        " GROUP BY measurement_id" +
                                                        " ) as t" +
                                                        " WHERE d.measure_time = t.max_time AND t.measurement_id = d.measurement_id" +
                                                        ")r ON m.id = r.measurement_id";

    public static final String SELECT_MEASURESTATION_GPS = "SELECT id" +
                                                            " FROM measurementStation" +
                                                            " WHERE x_location_info = :x and y_location_info = :y";
}
