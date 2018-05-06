package com.zhide.daily.service.impl;

import com.zhide.daily.DailyVo;
import com.zhide.daily.dao.DailyDao;
import com.zhide.daily.pojo.Daily;
import com.zhide.daily.pojo.User;
import com.zhide.daily.service.DailyService;
import com.zhide.daily.utils.easyui.EasyUIDataGridResult;
import com.zhide.daily.utils.server.ServerResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wuchenxi
 * @date 2018/4/30
 */
@Service
public class DailyServiceImpl implements DailyService {

    @Autowired
    private DailyDao dailyDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponse<String> saveDaily(HttpSession session, String param, Integer flag, Long id) throws JSONException, ParseException {

        if (id != null) {
            // 如果有id说明是从日报预览页面修改，先删除当前id的日报，再新增
            dailyDao.deleteById(id);
        }

        JSONArray jsonArray = new JSONArray(param);
        List<Daily> dailies = new ArrayList<>();
        Daily daily;
        JSONObject jsonObject;

        User user = (User) session.getAttribute("user");

        for (int i = 0; i < jsonArray.length(); i++) {
            daily = new Daily();
            jsonObject = jsonArray.getJSONObject(i);
            daily.setComplete(jsonObject.getInt("complete"));
            daily.setContent(jsonObject.getString("content"));
            daily.setCreateTime(new Date());
            daily.setMatter(jsonObject.getString("matter"));
            daily.setRemark(jsonObject.getString("remark"));
            daily.setUser(user);
            daily.setWorkDate(new SimpleDateFormat("dd/MM/yyyy").parse(jsonObject.get("workDate").toString()));
            if (flag == 1) {
                daily.setStatus(1);
            } else {
                daily.setStatus(0);
            }
            dailies.add(daily);
        }

        try {
            dailyDao.saveAll(dailies);
            return flag == 1 ? ServerResponse.createBySuccessMessage("日报保存成功！") : ServerResponse.createBySuccessMessage("日报存为草稿成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag == 1 ? ServerResponse.createByErrorMessage("日报保存失败！") : ServerResponse.createBySuccessMessage("日报存为草稿成功！");
    }

    @Override
    public EasyUIDataGridResult getDailies(Long userId, Date workDate, HttpSession session, Integer page, Integer rows) {

        User user = (User) session.getAttribute("user");
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(page - 1, rows, sort);

        // 根据条件查询
        Page<Daily> pageInfo = dailyDao.findAll(new Specification<Daily>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Daily> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();

                if (user != null) {
                    if (user.getRole() == 1) {
                        // 管理员，可查看所有人除草稿状态的日报
                        predicates.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));
                    } else {
                        // 普通人员，只能查看自己的日报
                        predicates.add(criteriaBuilder.equal(root.get("user").as(User.class), user));
                    }

                    if (userId != null) {
                        predicates.add(criteriaBuilder.equal(root.get("user").get("id").as(Long.class), userId));
                    }

                    if (workDate != null) {
                        predicates.add(criteriaBuilder.equal(root.get("workDate").as(Date.class), workDate));
                    }
                }
                Predicate[] p = new Predicate[predicates.size()];
                return criteriaBuilder.and(predicates.toArray(p));
            }
        }, pageable);

        List<Daily> dailies = pageInfo.getContent();
        List<DailyVo> dailyVos = this.getDailyVo(dailies);

        EasyUIDataGridResult<DailyVo> easyUIDataGridResult = new EasyUIDataGridResult<>();
        easyUIDataGridResult.setTotal(pageInfo.getTotalElements());
        easyUIDataGridResult.setRows(dailyVos);
        return easyUIDataGridResult;
    }

    @Override
    @javax.transaction.Transactional
    public ServerResponse<String> updateDaily(HttpSession session, Long id) {
        try {

            User user = (User) session.getAttribute("user");
            dailyDao.updateDailyById(user, id);
            return ServerResponse.createBySuccessMessage("日报保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.createByErrorMessage("日报保存失败！");
    }

    @Override
    public ServerResponse<String> deleteDailyById(HttpSession session, Long id) {
        try {
            dailyDao.deleteById(id);
            return ServerResponse.createBySuccessMessage("日报删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.createBySuccessMessage("日报删除失败！");
    }

    /**
     * 转换页面显示内容
     *
     * @param dailies
     */
    private List<DailyVo> getDailyVo(List<Daily> dailies) {
        List<DailyVo> dailyVos = new ArrayList<>();
        if (dailies.size() > 0) {
            DailyVo dailyVo;
            for (Daily daily : dailies) {
                dailyVo = new DailyVo();
                dailyVo.setId(daily.getId());
                dailyVo.setComplete(daily.getComplete());
                dailyVo.setContent(daily.getContent());
                dailyVo.setCreateTime(daily.getCreateTime());
                dailyVo.setId(daily.getId());
                dailyVo.setMatter(daily.getMatter());
                dailyVo.setState(daily.getStatus() == 1 ? "保存" : "草稿");
                dailyVo.setOpUser(daily.getUser().getName());
                dailyVo.setRemark(daily.getRemark());
                dailyVo.setWorkDate(daily.getWorkDate());
                dailyVos.add(dailyVo);
            }
        }
        return dailyVos;
    }
}