package com.john.density_info.mode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 密度监控区域信息实体
 */
@Entity
public class DensityInfoStatic {
    @Id
    @GeneratedValue
    Integer id;

    // 区域自定义名称
    String pname;

    // 所属用户
    Integer uid;
    // poi数量
    Integer n;

    String lng_s;
    String lat_s;

    // 面积
    Double s;
    // 密度
    Double e;
    // 等级
    Character grade;
    // 监控信息
    String safer_info;

    // polygen创建时间
    Long creat_time;
    // polygen更新时间
    Long update_time;

    public DensityInfoStatic(){
    }


    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Double getS() {
        return s;
    }

    public void setS(Double s) {
        this.s = s;
    }

    public Double getE() {
        return e;
    }

    public void setE(Double e) {
        this.e = e;
    }

    public Character getGrade() {
        return grade;
    }

    public void setGrade(Character grade) {
        this.grade = grade;
    }

    public String getSafer_info() {
        return safer_info;
    }

    public void setSafer_info(String safer_info) {
        this.safer_info = safer_info;
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

    public String getLng_s() {
        return lng_s;
    }

    public void setLng_s(String lng_s) {
        this.lng_s = lng_s;
    }

    public String getLat_s() {
        return lat_s;
    }

    public void setLat_s(String lat_s) {
        this.lat_s = lat_s;
    }
}
