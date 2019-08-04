package com.wannago.measureStation.dao;

public class MeasureStationDaoSqls {
    public static final String SELECT_MEASURESTATION = "SELECT id," +
                                                       " x_location_info," +
                                                       " y_location_info," +
                                                       " name" +
                                                       " FROM measurementStation" +
                                                       " WHERE name = :name";
}
