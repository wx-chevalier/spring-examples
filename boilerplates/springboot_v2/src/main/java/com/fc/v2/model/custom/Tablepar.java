package com.fc.v2.model.custom;

import com.fc.v2.util.ServletUtils;
import com.fc.v2.util.StringUtils;

/**
 * boostrap table post 参数
 *
 * @author fc
 */
public class Tablepar {

    /**
     * 页码
     */
    private int page;

    /**
     * 数量
     */
    private int limit;

    /**
     * 排序字段
     */
    private String orderByColumn;

    /**
     * 排序字符 asc desc
     */
    private String isAsc;

    /**
     * 列表table里面的搜索
     */
    private String searchText;

    /**
     * 获取layui table的分页参数
     *
     * @return
     */
    public static Tablepar buildPageRequest() {
        Tablepar tablepar = new Tablepar();
        //第几页
        tablepar.setPage(ServletUtils.getParameterToInt("page", 0));
        tablepar.setLimit(ServletUtils.getParameterToInt("limit", 0));
        tablepar.setOrderByColumn(ServletUtils.getParameter("orderByColumn"));
        tablepar.setIsAsc(ServletUtils.getParameter("isAsc"));
        return tablepar;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getOrderByColumn() {
        if (StringUtils.isEmpty(orderByColumn)) {
            return "";
        }
        return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getIsAsc() {
        return isAsc;
    }

    public void setIsAsc(String isAsc) {
        this.isAsc = isAsc;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

}
