package com.john.density_info.dao;


import com.john.density_info.mode.MainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * mode JPA hibernate DAO
 */
public interface MainJPACurdDao extends JpaRepository<MainEntity, Integer> {
    // 根据 data 查询
    public List<MainEntity> findByData(String data);
    // 根据 info 查询
    public List<MainEntity> findByInfo(String info);
}
