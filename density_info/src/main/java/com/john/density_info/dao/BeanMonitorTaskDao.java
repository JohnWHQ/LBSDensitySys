package com.john.density_info.dao;

import com.john.density_info.mode.BeanMonitorTask;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface BeanMonitorTaskDao {
    String TABLE_NAME = "bean_monitor_task";
    String INSERT_FIELDS = " id, taskname, pid, uid, status, lat, lng , create_time, otherinfo ";
    String SELECT_FIELDS = " id, taskname, pid, uid, status, lat, lng , create_time, otherinfo ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") Values" +
            "(#{id}, #{taskname}, #{pid}, #{uid}, #{status}, #{lat}, #{lng}, #{create_time}, #{otherinfo})"})
    int addTask(BeanMonitorTask task);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where 1=1"})
    ArrayList<BeanMonitorTask> selectAll();

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    BeanMonitorTask selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where uid=#{uid}"})
    ArrayList<BeanMonitorTask> selectByUId(int uid);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where pid=#{polygonid}"})
    ArrayList<BeanMonitorTask> selectByPId(int pid);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where taskid=#{taskid}"})
    ArrayList<BeanMonitorTask> selectByTaskId(int taskid);

    @Update({"update ", TABLE_NAME, " set taskname = #{taskname} where id=#{id}"})
    int updateInfo(BeanMonitorTask beanMonitorTask);

    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    int deleteById(int id);
}
