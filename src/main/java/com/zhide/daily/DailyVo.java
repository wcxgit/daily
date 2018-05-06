package com.zhide.daily;

import com.zhide.daily.pojo.User;
import lombok.Data;

import java.util.Date;

/**
 * @author wuchenxi
 * @date 2018/4/30
 */
@Data
public class DailyVo {
    private Long id;
    private String  opUser;

    /**
     * 事项，概述
     */
    private String matter;
    /**
     * 日报内容
     */
    private String content;
    /**
     * e
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
    private String state;

    /**
     * 预计完成时间
     */
    private Date workDate;

    private Date createTime;
}
