package com.wannago.dust.service;

import com.wannago.dust.dao.DustDao;
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

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@PropertySource("classpath:xmlparsing.properties")
public class DustService {
    @Autowired
    DustDao dustDao;

    @Autowired
    MeasureStationDao measureStationDao;

    @Value("${openapi.dust.address}")
    String endpoint;

    @Value("${openApi.serviceKey}")
    String serviceKey;

    @Transactional(readOnly = true)
    public List<Dust> getDusts(List<GpsValue> gpsValuesList){
        List<Dust> list = new ArrayList<Dust>();
        for(GpsValue gps : gpsValuesList){
            Dust dust = dustDao.selectRecent(gps.getX(), gps.getY());
            list.add(dust);
        }
        return list;
    }

    @Transactional
    public String addDust(Dust newDust){
        newDust.setMeasureTime(new Date());
        Long mId = measureStationDao.getMeasureStationLocationForGps(newDust.getxLocationInfo(), newDust.getyLocationInfo());
        if(mId == -1){
            MeasureStation newMeasureStation = new MeasureStation();
            newMeasureStation.setX_location_info(newDust.getxLocationInfo());
            newMeasureStation.setY_location_info(newDust.getyLocationInfo());
            newMeasureStation.setName("미세먼지 측정기기");
            mId = measureStationDao.insert(newMeasureStation);
        }
        newDust.setMeasurementId(mId);
        Long id = dustDao.insert(newDust);
        String result = "fail";
        if(id > 0) {
            result = "success";
        }
        return result;
    }

    @Transactional
    public void getDustApi(){
        String sidoNames[] = {"서울", "부산", "대구", "인천", "광주", "대전", "울산", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주", "세종"};
        try {
            for(String sido : sidoNames) {
                String param = "";
                param += "&" + "numOfRows=100";
                param += "&" + "pageNo=1";
                param += "&" + "sidoName=" + sido;
                param += "&" + "ver=1.3";

                String addr = endpoint + serviceKey + param;

                URL url = new URL(addr);

                InputStream is = url.openStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(is, "UTF-8");

                int eventType = parser.getEventType();
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Dust dust = null;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.END_DOCUMENT: // 문서의 끝
                            break;
                        case XmlPullParser.START_DOCUMENT:
                            dust = new Dust();
                            break;
                        case XmlPullParser.END_TAG: {
                            String tag = parser.getName();
                            if (tag.equals("item")) {
                                if(dust != null) {
                                    //dustList.add(dust);
                                    dustDao.insert(dust);
                                    dust = null;
                                }
                                dust = new Dust();
                            }
                        }
                        case XmlPullParser.START_TAG: {
                            String tag = parser.getName();
                            switch (tag) {
                                case "stationName":
                                    String name = parser.nextText();
                                    MeasureStation m = measureStationDao.getMeasureStationLocation(name);
                                    if(m != null) {
                                        dust.setxLocationInfo(m.getX_location_info());
                                        dust.setyLocationInfo(m.getY_location_info());
                                        dust.setMeasurementId(m.getId());
                                    }
                                    if(m == null) dust = null;
                                    break;
                                case "dataTime":
                                    if(dust != null) {
                                        dust.setMeasureTime(transFormat.parse(parser.nextText()));
                                    }
                                    break;
                                case "pm10Value":
                                    if(dust != null) {
                                        String pm10 = parser.nextText();
                                        if(pm10.equals("-")){
                                            dust = null;
                                            eventType = parser.next();
                                            continue;
                                        }
                                        dust.setDust(Double.parseDouble(pm10));
                                    }
                                    break;
                            }

                        }
                    }
                    eventType = parser.next();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
