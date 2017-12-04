package com.john.density_info.dao;

import com.john.density_info.mode.MainEntity;
import com.john.density_info.mode.UserInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginDao {

    String TABLE_NAME = "user_info";
    String INSERT_FIELDS = " name, password, email, info, nickname ";
    String SELECT_FIELDS = " id, name, password, email, info, nickname ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") Values" +
            "(#{name}, #{password}, #{email}, #{info}, #{nickname})"})
    int addEntity(UserInfo userInfo);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    UserInfo selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}"})
    UserInfo selectByName(String name);


    @Update({"update ", TABLE_NAME, " set password = #{password} where id=#{id}"})
    int updateInfo(MainEntity entity);

    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    int deleteById(int id);
}
