package com.wannago.dust.service;

import com.wannago.config.ApplicationConfig;
import com.wannago.dust.dto.Dust;
import com.wannago.dust.dto.GpsValue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DustServiceTest {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        DustService dustService = ac.getBean(DustService.class);

        Dust dust = new Dust();
//        Point p1 = new Point();
//        Point p2 = new Point();
//        dust.setxLocationInfo(37.659844);
//        dust.setyLocationInfo(127.033804);
//       dust.setDust(100);
//        dust = dustService.addDust(dust);
   //     double d = Point.distanceSq(37.659844, 127.033804, 37.659326, 127.033665);
        //dust.setLocationInfo();
        GpsValue gpsValue  = new GpsValue();
        gpsValue.setX(37.659844);
        gpsValue.setY(127.033804);
        List<GpsValue> gpsValues = new ArrayList<GpsValue>();
        gpsValues.add(gpsValue);
        GpsValue gpsValue1  = new GpsValue();
        gpsValue1.setX(37.589167);
        gpsValue1.setY(127.090547);
        gpsValues.add(gpsValue1);
          List<Dust> result = dustService.getDusts(gpsValues);
      //  dust.setMeasureTime(new Date());
        System.out.println(result);
    }
}
