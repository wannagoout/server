package com.wannago.measureStation.service;

import com.wannago.config.ApplicationConfig;
import com.wannago.measureStation.dao.MeasureStationDao;
import com.wannago.measureStation.dto.MeasureStation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class ServiceTest {
    public static void main(String args[]){
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        MeasureStationService measureStationService = ac.getBean(MeasureStationService.class);
//       List<MeasureStation> measureStations = measureStationService.getMeasureStationApi();
//       for(MeasureStation m : measureStations){
//           System.out.println(m);
//       }
        MeasureStation m = measureStationService.getMeasureStationLocation("중구");
        System.out.println(m);
    }
}
