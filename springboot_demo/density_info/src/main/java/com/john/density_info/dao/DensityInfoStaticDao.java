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
    String INSERT_FIELDS = " id, pname, uid, n, s, e, grade, safer_info, creat_time, update_time, lng_s, lat_s ";
    String SELECT_FIELDS = " id, pname, uid, n, s, e, grade, safer_info, creat_time, update_time, lng_s, lat_s ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") Values" +
            "(#{id}, #{pname}, #{uid}, #{n}, #{s}, #{e}, #{grade}, #{safer_info}, #{creat_time}, #{update_time}, #{lng_s}, #{lat_s})"})
    int addPolygon(DensityInfoStatic polygon);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where 1=1"})
    ArrayList<DensityInfoStatic> selectAll();

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    DensityInfoStatic selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where uid=#{uid}"})
    ArrayList<DensityInfoStatic> selectByUId(int uid);

    @Update({"update ", TABLE_NAME, " set pname =  #{pname}, uid = #{uid}, n = #{n}, s = #{s}, e = #{e}, " +
            "grade = #{grade}, safer_info = #{safer_info}, " +
            "creat_time = #{creat_time}, update_time = #{update_time}, lng_s = #{lng_s}, lat_s = #{lat_s} " +
            "where id=#{id}"})
    int updateInfo(DensityInfoStatic polygon);

    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    int deleteById(int id);
}
