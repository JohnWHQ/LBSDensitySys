package com.john.density_info.dao;

import com.john.density_info.mode.UserConfig;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface UserConfigDao {

    String TABLE_NAME = "user_config";
    String INSERT_FIELDS = " id, uid, token, config_lng, config_lat, last_time, login_time";
    String SELECT_FIELDS = " id, uid, token, config_lng, config_lat, last_time, login_time";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") Values" +
            "(#{id}, #{uid}, #{token}, #{config_lng}, #{config_lat}, #{last_time}, #{login_time})"})
    int addConfig(UserConfig config);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where 1=1"})
    ArrayList<UserConfig> selectAll();


    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    UserConfig selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where uid=#{uid}"})
    UserConfig selectByUid(int uid);


    @Update({"update ", TABLE_NAME, " set token = #{token}, config_lng = #{config_lng}, config_lat = #{config_lat}, last_time = #{last_time}, login_time = #{login_time}  where uid=#{uid}"})
    int updateConfig(UserConfig config);

    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    int deleteById(int id);
}
