package com.wannago.dust.service;

import com.wannago.dust.dao.DustDao;
import com.wannago.dust.dto.Dust;
import com.wannago.dust.dto.GpsValue;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DustService {
    @Autowired
    DustDao dustDao;

    @Transactional
    public List<Dust> getDusts(List<GpsValue> gpsValuesList){
        List<Dust> list = new ArrayList<Dust>();
        for(GpsValue gps : gpsValuesList){
            Dust dust = dustDao.selectRecent(gps.getX(), gps.getY());
            list.add(dust);
        }
        return list;
    }

    @Transactional(readOnly = false)
    public Dust addDust(Dust newDust){
        newDust.setMeasureTime(new Date());
        Long id = dustDao.insert(newDust);
        newDust.setId(id);
        return newDust;
    }
}
