package com.zhide.daily.controller;

import com.zhide.daily.service.DailyService;
import com.zhide.daily.utils.easyui.EasyUIDataGridResult;
import com.zhide.daily.utils.server.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日报相关操作
 *
 * @author wuchenxi
 * @date 2018/4/30
 */
@RestController
@RequestMapping("/daily")
public class DailyController {

    @Autowired
    private DailyService dailyService;

    /**
     * 新增或修改日志
     *
     * @param session 当前登录人员信息
     * @param param   需要修改或保存的数据
     * @param flag    保存草稿或者保存的标志：0.存为草稿；1.保存
     * @param id      通过日志预览修改草稿状态日志的id
     * @return
     */
    @PostMapping("/add-daily")
    public ServerResponse<String> saveDaily(HttpSession session, String param, Integer flag, Long id) {
        try {
            return dailyService.saveDaily(session, param, flag, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 草稿状态下内容不做修改直接保存执行此处代码
     *
     * @param session 当前登录人员信息
     * @param id      通过日志预览修改草稿状态日志的id
     * @return
     */
    @PostMapping("/update-daily")
    public ServerResponse<String> updateDaily(HttpSession session, Long id) {
        try {
            return dailyService.updateDaily(session, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/getDailies")
    public EasyUIDataGridResult getDailies(HttpSession session,
                                           @RequestParam(name = "userId", required = false) String userId1,
                                           @RequestParam(name = "workDate", required = false) String workDate1,
                                           @RequestParam(name = "page", defaultValue = "1") Integer page,
                                           @RequestParam(name = "rows", defaultValue = "10") Integer rows) {
        try {
            Long userId = "0".equals(userId1) || StringUtils.isEmpty(userId1) ? null : Long.parseLong(userId1);
            Date workDate = StringUtils.isEmpty(workDate1) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(workDate1);
            return dailyService.getDailies(userId, workDate, session, page, rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除指定日报信息
     *
     * @param session 当前登录用户信息
     * @param id      日报id
     * @return
     */
    @PostMapping("/deleteDaily")
    public ServerResponse<String> deleteDaily(HttpSession session, Long id) {
        return dailyService.deleteDailyById(session, id);
    }

}
