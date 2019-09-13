package com.wannago.dust.dao;

import com.wannago.dust.dto.Dust;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.wannago.dust.dao.DustDaoSqls.*;

@Repository
public class DustDao {
    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<Dust> rowMapper = BeanPropertyRowMapper.newInstance(Dust.class);

    public DustDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("dustInfo")
                .usingGeneratedKeyColumns("id");
    }

    public Dust selectRecent(Double x, Double y){
        Map<String, Double> params = new HashMap<>();
        params.put("x", x);
        params.put("y", y);
        try {
            return jdbc.queryForObject(SELECT_RECENT, params, rowMapper);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Long insert(Dust dust){
        SqlParameterSource params = new BeanPropertySqlParameterSource(dust);
        return insertAction.executeAndReturnKey(params).longValue();
    }

    public int checkMeasureStation(String dustName){
        Map<String, String> params = Collections.singletonMap("name", dustName);
        try {
            return jdbc.queryForObject(CHECK_MEASUREMENT, params, int.class);
        }catch (EmptyResultDataAccessException e){
            return 0;
        }
    }
}
