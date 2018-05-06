package com.zhide.daily.service;

import com.zhide.daily.utils.easyui.EasyUIDataGridResult;
import com.zhide.daily.utils.server.ServerResponse;
import org.json.JSONException;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;

/**
 * 日报操作service
 *
 * @author wuchenxi
 * @date 2018/4/30
 */
public interface DailyService {
    /**
     * 保存日报
     *
     * @param param
     * @param flag  保存状态：0.草稿，1.保存
     * @return
     */
    ServerResponse<String> saveDaily(HttpSession session, String param, Integer flag, Long id) throws JSONException, ParseException;

    /**
     * 获取日报信息
     *
     *
     * @param userId
     * @param workDate
     *@param session  @return
     */
    EasyUIDataGridResult getDailies(Long userId, Date workDate, HttpSession session, Integer page, Integer rows);

    /**
     * 草稿状态下内容不做修改直接保存执行此处代码
     * @param session   当前登录人员信息
     * @param id     通过日志预览修改草稿状态日志的id
     * @return
     */
    ServerResponse<String> updateDaily(HttpSession session, Long id);

    /**
     * 删除指定日报信息
     * @param session
     * @param id
     * @return
     */
    ServerResponse<String> deleteDailyById(HttpSession session, Long id);
}
