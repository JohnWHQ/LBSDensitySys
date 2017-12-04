package com.john.density_info.dao;

import com.john.density_info.mode.HPMapPoints;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

/**
 * 热力图点信息DB操作类
 */
@Mapper
public interface HPMapPointsDao {

    String TABLE_NAME = "hpmap_points";
    String INSERT_FIELDS = " id, uid, fid, lng, lat, count, creat_time, update_time ";
    String SELECT_FIELDS = " id, uid, fid, lng, lat, count, creat_time, update_time ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") Values" +
            "(#{id}, #{uid}, #{fid}, #{lng}, #{lat}, #{count}, #{creat_time}, #{update_time})"})
    int addPoints(HPMapPoints points);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where 1=1"})
    ArrayList<HPMapPoints> selectAll();

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    HPMapPoints selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    ArrayList<HPMapPoints> selectByUid(int uid);


    @Update({"update ", TABLE_NAME, " set uid = #{uid} where id=#{id}"})
    int updateInfo(HPMapPoints points);

    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    int deleteById(int id);
}
