package com.wannago.user.dao;

import com.wannago.user.dto.User;
import com.wannago.user.dto.UserToken;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

import static com.wannago.user.dao.UserDaoSqls.*;

@Repository
public class UserDao {
    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
    private RowMapper<UserToken> userTokenRowMapper = BeanPropertyRowMapper.newInstance(UserToken.class);

    public UserDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
                                .withTableName("userTable")
                                .usingGeneratedKeyColumns("id");

    }

    public List<User> selectAllUser(){
        return jdbc.query(SELECT_ALL_USER, Collections.emptyMap(), rowMapper);
    }

    public Long insertUser(User newUser){
        SqlParameterSource params = new BeanPropertySqlParameterSource(newUser);
        return insertAction.executeAndReturnKey(params).longValue();
    }

    public List<UserToken> selectUserToken(){
        return jdbc.query(CHECK_DUST, Collections.EMPTY_MAP, userTokenRowMapper);
    }
}
