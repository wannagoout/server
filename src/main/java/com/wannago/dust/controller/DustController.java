package com.wannago.dust.controller;

import com.wannago.dust.dto.Dust;
import com.wannago.dust.dto.GpsList;
import com.wannago.dust.dto.GpsValue;
import com.wannago.dust.service.DustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/api/dust")
public class DustController {
    @Autowired
    DustService dustService;

    @PostMapping
    public Map<String, Object> getDustList(@RequestBody GpsList gpsList, HttpServletRequest request){
        List<Dust> items = dustService.getDusts(gpsList.getGpsList());
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("gpsList", items);
        return map;
    }

    @PostMapping(path = "/add")
    public String addNewDust(@RequestBody Dust dust,
                           HttpServletRequest request){
        return dustService.addDust(dust);

    }

    @Scheduled(cron = "0 20,40 * * * ?")
    public void checkForBatch(){
        dustService.getDustApi();
    }

//    @GetMapping(path = "/test")
//    public void testGetApi(){
//        dustService.getDustApi();
//    }
}
