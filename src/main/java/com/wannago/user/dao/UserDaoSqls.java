package com.wannago.user.dao;

public class UserDaoSqls {
    public static final String SELECT_ALL_USER = "SELECT id, usr_id," +
            " usr_ps," +
            " usr_token," +
            " usr_setting," +
            " usr_addr_x," +
            " usr_addr_y" +
            " FROM userTable";

    public static final String CHECK_DUST = "SELECT id, usr_token AS user_token" +
            " FROM(" +
            " SELECT" +
            " u.id," +
            " usr_token," +
            " (6371 * ACOS(COS(RADIANS(u.usr_addr_x)) * COS(RADIANS(x_location_info)) * COS(RADIANS(y_location_info) - RADIANS(u.usr_addr_y)) + SIN(RADIANS(u.usr_addr_x)) * SIN(RADIANS(x_location_info)))) AS distance" +
            " FROM" +
            " dustInfo d," +
            " userTable u" +
            " WHERE" +
            " measure_time > (NOW() - INTERVAL 30 MINUTE)" +
            " AND usr_setting < dust" +
            " HAVING distance <= 5" +
            " ORDER BY distance , measure_time DESC" +
            ")did" +
            " GROUP BY id";
}
