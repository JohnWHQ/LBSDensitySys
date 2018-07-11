package com.john.density_info.mode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserConfig {
    @Id
    @GeneratedValue
    int id;

    int uid;

    String token;

    double config_lng;
    double config_lat;

    long last_time;
    long login_time;

    public UserConfig(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getConfig_lng() {
        return config_lng;
    }

    public void setConfig_lng(double config_lng) {
        this.config_lng = config_lng;
    }

    public double getConfig_lat() {
        return config_lat;
    }

    public void setConfig_lat(double config_lat) {
        this.config_lat = config_lat;
    }

    public long getLast_time() {
        return last_time;
    }

    public void setLast_time(long last_time) {
        this.last_time = last_time;
    }

    public long getLogin_time() {
        return login_time;
    }

    public void setLogin_time(long login_time) {
        this.login_time = login_time;
    }
}
