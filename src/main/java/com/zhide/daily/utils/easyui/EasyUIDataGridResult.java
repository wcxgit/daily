package com.zhide.daily.utils.easyui;

import java.util.ArrayList;
import java.util.List;

/**
 * easyUI datagrid
 * @author wuchenxi
 * @date 2018/4/23 15:23
 */
public class EasyUIDataGridResult<T> {

    /**
     * 数据总数
     */
    private Long total;
    /**
     * 数据内容
     */
    private List<T> rows = new ArrayList<>();

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
