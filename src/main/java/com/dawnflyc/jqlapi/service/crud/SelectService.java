package com.dawnflyc.jqlapi.service.crud;


import com.dawnflyc.jqlapi.ParamHandle;
import com.dawnflyc.jqlapi.StringUtils;
import com.dawnflyc.jqlapi.service.WhereSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询服务
 */
public class SelectService extends WhereSql<SelectService, List<Map<String, Object>>> {

    /**
     * 字段
     */
    private final List<String> fields = new ArrayList<>();
    /**
     * 存储排序
     */
    private final List<String> order = new ArrayList<>();
    /**
     * 存储分组
     */
    private final List<String> group = new ArrayList<>();
    /**
     * 联查
     */
    private String join = "";
    /**
     * limit
     */
    private String limit = "";

    public SelectService(String table, ParamHandle paramHandle) {
        super(table, paramHandle);
    }

    public SelectService(String table) {
        super(table);
    }

    public SelectService() {
    }

    @Override
    public String getSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        if (fields.size() > 0) {
            sql.append(String.join(",", fields));
        } else {
            sql.append("*");
        }
        sql.append(" FROM ").append(table).append(join == null ? "" : join);
        sql.append(buildWhere());
        if (group.size() > 0) {
            sql.append(" GROUP BY ").append(String.join(",", group));
        }
        if (order.size() > 0) {
            sql.append(" ORDER BY ").append(String.join(",", order));
        }
        sql.append(limit);
        return sql.toString();
    }

    @Override
    protected List<Map<String, Object>> query() {
        return sqlHandle.select(getSql(), getStringParam());
    }

    /**
     * 查询一个
     */
    public Map<String, Object> executeGetOne() {
        List<Map<String, Object>> execute = limit(1).execute();
        if (execute.size() > 0) {
            return execute.get(0);
        }
        return null;
    }

    /**
     * 查询记录数
     */
    public Integer executeGetCount() {
        if (fields.size() > 0) {
            fields.clear();
        }
        return Integer.parseInt(field("count(1) as count").executeGetOne().get("count").toString());
    }

    /**
     * 查询值
     */
    public Integer executeGetOneValue(String field) {
        return Integer.parseInt(executeGetOne().get(field).toString());
    }

    /**
     * 设置查询字段
     */
    public SelectService field(String field) {
        this.fields.add(field);
        return this;
    }

    /**
     * 排序
     *
     * @param field 字段
     * @param mode  正反排序
     */
    public SelectService order(String field, String mode) {
        this.order.add(field + " " + mode);
        return this;
    }

    /**
     * 分组
     */
    public SelectService group(String field) {
        this.group.add(field);
        return this;
    }

    /**
     * limit语句
     *
     * @param offset 从哪行开始
     * @param size   查几个
     */
    public SelectService limit(Integer offset, Integer size) {
        limit = StringUtils.format(" limit ? , ?", offset, size);
        return this;
    }

    /**
     * limit语句
     *
     * @param size 查几个
     */
    public SelectService limit(Integer size) {
        limit = StringUtils.format(" limit ? ", size);
        return this;
    }

    /**
     * 联查
     *
     * @param mode  左右内
     * @param table 联查表
     * @param on    on
     */
    public SelectService join(String mode, String table, String on) {
        this.join += " " + mode + " JOIN " + table + " ON " + on;

        return this;
    }


}
