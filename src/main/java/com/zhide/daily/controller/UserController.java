package com.zhide.daily.controller;

import com.zhide.daily.service.UserService;
import com.zhide.daily.utils.easyui.EasyUIComboboxResult;
import com.zhide.daily.utils.server.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户相关controller
 *
 * @author wuchenxi
 * @date 2018/4/30
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ServerResponse<String> login(HttpSession session, String username, String password) {
        return userService.login(session, username, password);
    }

    @GetMapping("/getUsers")
    public List<EasyUIComboboxResult> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/logout")
    public ServerResponse login(HttpSession session) {
        session.invalidate();
        return ServerResponse.createBySuccess();
    }
}
