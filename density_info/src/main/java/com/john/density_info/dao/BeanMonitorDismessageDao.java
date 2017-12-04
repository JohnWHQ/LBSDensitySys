package com.john.density_info.dao;

import com.john.density_info.mode.BeanMonitorDismessage;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface BeanMonitorDismessageDao {
    String TABLE_NAME = "bean_monitor_dismessage";
    String INSERT_FIELDS = " id, polygonid, uid, taskid, star, content, send_time , uname ";
    String SELECT_FIELDS = " id, polygonid, uid, taskid, star, content, send_time , uname ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") Values" +
            "(#{id}, #{polygonid}, #{uid}, #{taskid}, #{star}, #{content}, #{send_time}, #{uname})"})
    int addDismessage(BeanMonitorDismessage activityInfo);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where 1=1"})
    ArrayList<BeanMonitorDismessage> selectAll();

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    BeanMonitorDismessage selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where uid=#{uid}"})
    ArrayList<BeanMonitorDismessage> selectByUId(int uid);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where pid=#{polygonid}"})
    ArrayList<BeanMonitorDismessage> selectByPId(int pid);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where taskid=#{taskid}"})
    ArrayList<BeanMonitorDismessage> selectByTaskId(int taskid);

    @Update({"update ", TABLE_NAME, " set content = #{content} where id=#{id}"})
    int updateInfo(BeanMonitorDismessage beanMonitorDismessage);

    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    int deleteById(int id);
}
