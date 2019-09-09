package com.wannago.user.dto;

public class User {
    private Long id;
    private String usrId;
    private String usrPs;
    private String usrToken;
    private Long usrSetting;
    private Double usrAddrX;
    private Double usrAddrY;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getUsrPs() {
        return usrPs;
    }

    public void setUsrPs(String usrPs) {
        this.usrPs = usrPs;
    }

    public String getUsrToken() {
        return usrToken;
    }

    public void setUsrToken(String usrToken) {
        this.usrToken = usrToken;
    }

    public Long getUsrSetting() {
        return usrSetting;
    }

    public void setUsrSetting(Long usrSetting) {
        this.usrSetting = usrSetting;
    }

    public Double getUsrAddrX() {
        return usrAddrX;
    }

    public void setUsrAddrX(Double usrAddrX) {
        this.usrAddrX = usrAddrX;
    }

    public Double getUsrAddrY() {
        return usrAddrY;
    }

    public void setUsrAddrY(Double usrAddrY) {
        this.usrAddrY = usrAddrY;
    }
}
