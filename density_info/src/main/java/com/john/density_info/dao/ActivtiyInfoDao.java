package com.john.density_info.dao;

import com.john.density_info.mode.BeanActivityInfo;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface ActivtiyInfoDao {
    String TABLE_NAME = "bean_activity_info";
    String INSERT_FIELDS = " id, uid, uname, content, ac_id, rq_id, create_time ";
    String SELECT_FIELDS = " id, uid, uname, content, ac_id, rq_id, create_time ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") Values" +
            "(#{id}, #{uid}, #{uname}, #{content}, #{ac_id}, #{rq_id}, #{create_time})"})
    int addActivtiy(BeanActivityInfo activityInfo);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where 1=1"})
    ArrayList<BeanActivityInfo> selectAll();

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    BeanActivityInfo selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where uid=#{uid}"})
    ArrayList<BeanActivityInfo> selectByUId(int uid);

    @Update({"update ", TABLE_NAME, " set content = #{content} where id=#{id}"})
    int updateInfo(BeanActivityInfo activityInfo);

    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    int deleteById(int id);
}
