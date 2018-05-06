package com.zhide.daily.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 用户pojo
 * @author wuchenxi
 * @date 2018/4/30
 */
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    @OneToMany(targetEntity = Daily.class,mappedBy = "user")
    private List<Daily> dailies;
    /**
     * 角色
     * 1：管理员（可查看所有用户的日报信息）
     * 2：普通用户（只能查看自己的日报信息）
     */
    private int role;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
}
