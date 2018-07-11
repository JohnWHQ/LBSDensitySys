package com.john.density_info.mode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class MainEntity {
    @Id
    @GeneratedValue
    private Integer id;

    private String data;

    private String info;

    public MainEntity(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "MainEntity{" +
                "id=" + id +
                ", data='" + data + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
