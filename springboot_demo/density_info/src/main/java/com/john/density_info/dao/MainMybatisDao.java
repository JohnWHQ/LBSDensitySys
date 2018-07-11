package com.john.density_info.dao;

import com.john.density_info.mode.MainEntity;
import org.apache.ibatis.annotations.*;

/**
 * mode Mybatis DAO
 */
@Mapper
public interface MainMybatisDao {
    String TABLE_NAME = "main_entity";
    String INSERT_FIELDS = " id, data, info ";
    String SELECT_FIELDS = " id, data, info ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") Values" +
            "(#{id}, #{data}, #{info})"})
    int addEntity(MainEntity entity);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    MainEntity selectById(int id);

    @Update({"update ", TABLE_NAME, " set data = #{data} , info = #{info} where id=#{id}"})
    int updateInfo(MainEntity entity);

    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    int deleteById(int id);
}
