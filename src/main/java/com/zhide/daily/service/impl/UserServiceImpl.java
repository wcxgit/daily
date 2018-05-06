package com.zhide.daily.service.impl;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import com.zhide.daily.dao.UserDao;
import com.zhide.daily.pojo.User;
import com.zhide.daily.service.UserService;
import com.zhide.daily.utils.easyui.EasyUIComboboxResult;
import com.zhide.daily.utils.server.ServerResponse;
import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuchenxi
 * @date 2018/4/30
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public ServerResponse<String> login(HttpSession session, String username, String password) {

        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            User user = userDao.getUserByNameAndPassword(username, password);
            if (user != null) {
                session.setAttribute("user", user);
                return ServerResponse.createBySuccessMessage("登录成功!");
            }
        }
        return ServerResponse.createByErrorMessage("用户名或密码不正确！");
    }

    @Override
    public List<EasyUIComboboxResult> getUsers() {
        List<User> users = userDao.findAll(new Sort(Sort.Direction.ASC,"id"));
        List<EasyUIComboboxResult> comboboxResults = new ArrayList<>();
        EasyUIComboboxResult comboboxResult;

        comboboxResult = new EasyUIComboboxResult();
        comboboxResult.setSelected(true);
        comboboxResult.setId(0L);
        comboboxResult.setText("全部");
        comboboxResults.add(comboboxResult);
        if (users.size() > 0) {
            for (User user : users) {
                comboboxResult = new EasyUIComboboxResult();
                if (user.getRole() != 1) {
                    comboboxResult.setId(user.getId());
                    comboboxResult.setText(user.getName());
                    comboboxResults.add(comboboxResult);
                }
            }
        }
        return comboboxResults;
    }
}
