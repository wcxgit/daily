package com.zhide.daily.dao;

import com.zhide.daily.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author wuchenxi
 * @date 2018/4/30
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @Query("select u from User u where u.name=?1 and u.password=?2")
    User getUserByNameAndPassword(String username, String password);
}
