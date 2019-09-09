package com.wannago.measureStation.dao;

import com.wannago.measureStation.dto.MeasureStation;
import com.wannago.measureStation.dto.response.MeasureMap;
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
import java.util.List;
import java.util.Map;

import static com.wannago.measureStation.dao.MeasureStationDaoSqls.*;

@Repository
public class MeasureStationDao {
    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<MeasureStation> rowMapperStation = BeanPropertyRowMapper.newInstance(MeasureStation.class);
    private RowMapper<MeasureMap> rowMapperMap = BeanPropertyRowMapper.newInstance(MeasureMap.class);

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
            return jdbc.queryForObject(SELECT_MEASURESTATION, params, rowMapperStation);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Long getMeasureStationLocationForGps(Double x, Double y){
        Map<String, Double> params = new HashMap<>();
        params.put("x", x);
        params.put("y", y);
        try{
            return jdbc.queryForObject(SELECT_MEASURESTATION_GPS, params, Long.class);
        }catch (EmptyResultDataAccessException e){
            return Long.valueOf(-1);
        }
    }

    public List<MeasureMap> getAllMeasurStationLocations(){
        return jdbc.query(SELECT_All_MEASURESTATIONS, Collections.emptyMap(), rowMapperMap);
    }

    public Long insert(MeasureStation measureStation){
        SqlParameterSource params = new BeanPropertySqlParameterSource(measureStation);
        return insertAction.executeAndReturnKey(params).longValue();
    }
}
