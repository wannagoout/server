package com.wannago.measureStation.service;

import com.wannago.dust.dto.Dust;
import com.wannago.dust.dto.GpsValue;
import com.wannago.measureStation.dao.MeasureStationDao;
import com.wannago.measureStation.dto.MeasureStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:xmlparsing.properties")
public class MeasureStationService {
    @Autowired
    MeasureStationDao measureStationDao;

    @Value("${openapi.measurement.address}")
    String endpoint;

    @Value("${openApi.serviceKey}")
    String serviceKey;

    @Transactional
    public MeasureStation getMeasureStationLocation(String name){
        System.out.println(name);
        MeasureStation measureStation = measureStationDao.getMeasureStationLocation(name);
        System.out.println(measureStation);
        return measureStation;
    }

    @Transactional(readOnly = false)
    public List<MeasureStation> getMeasureStationApi(){
        List<MeasureStation> stationList = null;
        try {
            String param = "";
            param += "&" + "numOfRows=500";
            param += "&" + "pageNo=1";

            String addr = endpoint + serviceKey + param;

            System.out.println(addr);

            URL url = new URL(addr);

            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();


            XmlPullParser parser = factory.newPullParser();
            parser.setInput(is,"UTF-8");

            stationList = new ArrayList<>();
            MeasureStation measureStation = null;
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.END_DOCUMENT: // 문서의 끝
                        break;
                    case XmlPullParser.START_DOCUMENT:
                        measureStation = new MeasureStation();
                        break;
                    case XmlPullParser.END_TAG: {
                        String tag = parser.getName();
                        if(tag.equals("item") && parser.getDepth() == 4) {
                            if(measureStation != null){
                                stationList.add(measureStation);
                                measureStation = null;
                            }
                            measureStation = new MeasureStation();
                        }
                    }
                    case XmlPullParser.START_TAG: {
                        String tag = parser.getName();
                        switch (tag) {
                            case "stationName":
                                measureStation.setName(parser.nextText());
                                break;
                            case "dmX":
                                String dmX = parser.nextText();
                                if(dmX == ""){
                                    measureStation = null;
                                    eventType = parser.next();
                                    continue;
                                }
                                 measureStation.setX_location_info(Double.parseDouble(dmX));
                                break;
                            case "dmY":
                                String dmY = parser.nextText();
                                if(dmY == ""){
                                    eventType = parser.next();
                                    continue;
                                }
                                measureStation.setY_location_info(Double.parseDouble(dmY));
                                break;
                            default :
                                break;

                        }
                    }
                }
                eventType = parser.next();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return stationList;
    }

    public MeasureStation addMeasureStation(MeasureStation newMeasureStation){
        Long id = measureStationDao.insert(newMeasureStation);
        newMeasureStation.setId(id);
        return newMeasureStation;
    }

}

