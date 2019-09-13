package com.wannago.user.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.wannago.user.dao.UserDao;
import com.wannago.user.dto.UserToken;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

@Service
@PropertySource("classpath:fcmSetting.properties")
public class PushService {
    @Value("${fcm.local.path}")
    String path;

    @Autowired
    UserDao userDao;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void sendFcm(){
        List<UserToken> userTokenList = userDao.selectUserToken();

        for (UserToken token : userTokenList) {
            try {
                String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
                String[] SCOPES = {MESSAGING_SCOPE};

                GoogleCredential googleCredential = GoogleCredential
                        .fromStream(new FileInputStream(path))
                        .createScoped(Arrays.asList(SCOPES));
                googleCredential.refreshToken();

                HttpHeaders headers = new HttpHeaders();
                headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
                headers.add("Authorization", "Bearer " + googleCredential.getAccessToken());

                JSONObject notification = new JSONObject();
                notification.put("body", "미세먼지 수치가 나쁩니다. 외출 시 마스크를 착용해주세요!");
                notification.put("title", "wannaGoOut");

                JSONObject message = new JSONObject();

                message.put("token", token.getUserToken());
                message.put("notification", notification);

                log.debug("token : {}", token);
                JSONObject jsonParams = new JSONObject();
                jsonParams.put("message", message);

                HttpEntity<JSONObject> httpEntity = new HttpEntity<JSONObject>(jsonParams, headers);
                RestTemplate rt = new RestTemplate();

                ResponseEntity<String> res = rt.exchange("https://fcm.googleapis.com/v1/projects/wannagoout/messages:send"
                        , HttpMethod.POST
                        , httpEntity
                        , String.class);

                if (res.getStatusCode() != HttpStatus.OK) {
                    log.debug("FCM-Exception");
                    log.debug(res.getStatusCode().toString());
                    log.debug(res.getHeaders().toString());
                    log.debug(res.getBody().toString());

                } else {
                    log.debug(res.getStatusCode().toString());
                    log.debug(res.getHeaders().toString());
                    log.debug(res.getBody().toLowerCase());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
