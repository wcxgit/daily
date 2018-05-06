package com.zhide.daily.service;

import com.zhide.daily.utils.easyui.EasyUIComboboxResult;
import com.zhide.daily.utils.server.ServerResponse;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户相关service
 * @author wuchenxi
 * @date 2018/4/30
 */
public interface UserService {

    /**
     * 用户登录
     * @param session
     * @param username
     * @param password
     * @return
     */
    ServerResponse<String> login(HttpSession session, String username, String password);

    /**
     * 获取所有普通人员姓名
     * @return
     */
    List<EasyUIComboboxResult> getUsers();
}
