package com.wannago.user.dao;

public class UserDaoSqls {
    public static final String SELECT_ALL_USER = "SELECT id, usr_id," +
            " usr_ps," +
            " usr_token," +
            " usr_setting," +
            " usr_addr_x," +
            " usr_addr_y" +
            " FROM userTable";
}
