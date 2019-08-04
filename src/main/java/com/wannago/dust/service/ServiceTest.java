package com.wannago.dust.service;

import com.wannago.config.ApplicationConfig;
import com.wannago.dust.dto.Dust;
import com.wannago.measureStation.service.MeasureStationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class ServiceTest {
    public static void main(String args[]){
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        DustService dustService = ac.getBean(DustService.class);
        List<Dust> dustList =  dustService.getDustApi();
        for(Dust d : dustList){
            System.out.println(d);
        }
    }
}
