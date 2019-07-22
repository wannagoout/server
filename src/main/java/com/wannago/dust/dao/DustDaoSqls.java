package com.wannago.dust.dao;

public class DustDaoSqls {
    public static final String SELECT_RECENT = "SELECT id," +
                                                    "x_location_info," +
                                                    "  y_location_info," +
                                                    " measure_time," +
                                                    " dust" +
                                              " FROM dustInfo" +
                                              " WHERE x_location_info = :x and y_location_info = :y ORDER BY measure_time desc limit 1";

}
