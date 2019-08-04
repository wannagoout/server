package com.wannago.measureStation.dao;

import com.wannago.measureStation.dto.MeasureStation;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static com.wannago.measureStation.dao.MeasureStationDaoSqls.*;

@Repository
public class MeasureStationDao {
    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<MeasureStation> rowMapper = BeanPropertyRowMapper.newInstance(MeasureStation.class);

    public MeasureStationDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
                            .withTableName("measurementStation")
                            .usingGeneratedKeyColumns("id");
    }

    public MeasureStation getMeasureStationLocation(String name){
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        try{
            return jdbc.queryForObject(SELECT_MEASURESTATION, params, rowMapper);
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    public Long insert(MeasureStation measureStation){
        SqlParameterSource params = new BeanPropertySqlParameterSource(measureStation);
        return insertAction.executeAndReturnKey(params).longValue();
    }
}
