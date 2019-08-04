package com.wannago.dust.service;

import com.wannago.dust.dao.DustDao;
import com.wannago.dust.dto.Dust;
import com.wannago.dust.dto.GpsValue;
import com.wannago.measureStation.dao.MeasureStationDao;
import com.wannago.measureStation.dto.MeasureStation;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DustService {
    @Autowired
    DustDao dustDao;

    @Autowired
    MeasureStationDao measureStationDao;

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

    @Transactional
    public List<Dust> getDustApi(){
        List<Dust> dustList = new ArrayList<>();
        String sidoNames[] = {"서울", "부산", "대구", "인천", "광주", "대전", "울산", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주", "세종"};
        try {
            for(String sido : sidoNames) {
                String addr = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?ServiceKey=";
                String serviceKey = "QQxRvaJKsukgQAB45gm82oPr1immQe3oMaLcNS5EJ8OQkxTJJ4lmA%2FkLnYlcJC8%2BvI42HKa4vrRFGzo%2BjcJpcw%3D%3D";
                String param = "";
                param += "&" + "numOfRows=100";
                param += "&" + "pageNo=1";
                param += "&" + "sidoName=" + sido;
                param += "&" + "ver=1.3";

                addr = addr + serviceKey + param;

                System.out.println(addr);

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
                                    dustDao.insert(dust);
                                    System.out.println(dust);
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
                                    System.out.println(name);
                                    MeasureStation m = measureStationDao.getMeasureStationLocation(name);
                                    //System.out.println(m);
                                    if(m != null) {
                                        dust.setxLocationInfo(m.getX_location_info());
                                        dust.setyLocationInfo(m.getY_location_info());
                                    }
                                    if(m == null) dust = null;
                                    break;
                                case "dataTime":
                                    System.out.println(tag);
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
        return dustList;
    }

}
