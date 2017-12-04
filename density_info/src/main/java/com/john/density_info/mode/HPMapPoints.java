package com.john.density_info.mode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class HPMapPoints {

    @Id
    @GeneratedValue
    Integer id;

    // 标识用户id
    Integer uid;
    // 标识所属监控管理者id
    Integer fid;


    // 外部api所需属性
    Double lng;
    Double lat;
    Integer count;

    // poi创建时间
    Long creat_time;
    // poi更新时间
    Long update_time;


    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Long getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(Long creat_time) {
        this.creat_time = creat_time;
    }

    public Long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Long update_time) {
        this.update_time = update_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HPMapPoints(){
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
