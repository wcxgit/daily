package com.zhide.daily.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 日报内容pojo
 *
 * @author wuchenxi
 * @date 2018/4/30
 */
@Data
@Entity
public class Daily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = User.class)
    private User user;

    /**
     * 事项，概述
     */
     private String matter;
    /**
     * 日报内容
     */
    private String content;
    /**
     * 完成度百分比
     */
    private Integer complete;

    /**
     * 备注
     */
    private String remark;

    /**
     * 保存状态
     * 0：草稿
     * 1：保存
     */
    private Integer status;

    /**
     * 日报日期
     */
    @Temporal(TemporalType.DATE)
    private Date workDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

}
