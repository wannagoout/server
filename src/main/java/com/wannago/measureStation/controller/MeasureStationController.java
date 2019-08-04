package com.wannago.measureStation.controller;

import com.wannago.dust.dto.GpsValue;
import com.wannago.measureStation.dto.MeasureStation;
import com.wannago.measureStation.service.MeasureStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/station")
public class MeasureStationController {
    @Autowired
    MeasureStationService measureStationService;

    @GetMapping("/add")
    public void addApiStation(){
        List<MeasureStation> measureStationList = measureStationService.getMeasureStationApi();
        for(MeasureStation m : measureStationList) {
            measureStationService.addMeasureStation(m);

        }
    }

    @GetMapping
    public Map getStation(@RequestParam String name){
        Map<String, Object> stationLoaction = new HashMap<>();
        stationLoaction.put("location", measureStationService.getMeasureStationLocation(name));
        return stationLoaction;
    }
}
