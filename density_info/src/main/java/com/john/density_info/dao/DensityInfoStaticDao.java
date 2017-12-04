package com.john.density_info.dao;

import com.john.density_info.mode.DensityInfoStatic;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

/**
 * 密度监控区域操作
 */
@Mapper
public interface DensityInfoStaticDao {
    String TABLE_NAME = "density_info_static";
    String INSERT_FIELDS = " id, pname, uid, n, s, e, grade, safer_info, creat_time, update_time ";
    String SELECT_FIELDS = " id, pname, uid, n, s, e, grade, safer_info, creat_time, update_time ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") Values" +
            "(#{id}, #{pname}, #{uid}, #{s}, #{e}, #{n}, #{grade}, #{safer_info}, #{creat_time}, #{update_time})"})
    int addPolygon(DensityInfoStatic polygon);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where 1=1"})
    ArrayList<DensityInfoStatic> selectAll();

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    DensityInfoStatic selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where uid=#{uid}"})
    ArrayList<DensityInfoStatic> selectByUId(int uid);

    @Update({"update ", TABLE_NAME, " set update_time = #{update_time} where id=#{id}"})
    int updateInfo(DensityInfoStatic polygon);

    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    int deleteById(int id);
}
