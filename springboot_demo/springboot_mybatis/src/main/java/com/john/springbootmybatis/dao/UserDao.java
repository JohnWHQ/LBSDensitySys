package com.john.springbootmybatis.dao;

import com.john.springbootmybatis.domain.User;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface UserDao {

    String TABLE_NAME = "user_info";
    String INSERT_FIELDS = " id, name, password, otherInfo ";
    String SELECT_FIELDS = " id, name, password, otherInfo ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") Values" +
            "(#{id}, #{name}, #{password}, #{otherInfo})"})
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    @Update({"update ", TABLE_NAME, " set password = #{password} where id=#{id}"})
    int updatePassword(User user);

    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    int deleteById(int id);

    List<User> selectByLimit(@Param("id") Integer id, @Param("limitN") Integer limitN);
}
