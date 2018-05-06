package com.zhide.daily.dao;

import com.zhide.daily.pojo.Daily;
import com.zhide.daily.pojo.User;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 日报相关操作dao
 * @author wuchenxi
 * @date 2018/4/30
 */
@Repository
public interface DailyDao extends JpaRepository<Daily,Long>,JpaSpecificationExecutor<Daily>{

    /**
     * 根据日报提交时间降序查找所有日报信息
     * @param pageable  分页查询条件
     * @return
     */
    @Query(value = "select d from Daily d where d.status = 1")
    List<Daily> findAllOrderByCreateTimeDesc(Pageable pageable);

    /**
     * 获取保存状态下的日报总数量
     * @return
     */
    @Query(value = "select count(d.id) from Daily d where d.status = 1")
    Long getTotal();

    /**
     * 获取指定用户的日报信息
     * @param uId   指定的用户id
     * @return
     */
    @Query(value = "select count(d.id) from Daily d where d.user = ?1")
    Long getTotal(User user);

    @Query(value = "select d from Daily d where d.user=?1 ")
    List<Daily> findByUId(User user, Pageable pageable);

    /**
     * 更新日报状态
     * @param user
     * @param id
     */
    @Modifying
    @Query(value = "update Daily d set d.status = 1 where d.user=?1 and d.id=?2")
    void updateDailyById(User user, Long id);
}
