package com.dawnflyc.jqlapi.service.crud;


import com.dawnflyc.jqlapi.ParamHandle;
import com.dawnflyc.jqlapi.StringUtils;
import com.dawnflyc.jqlapi.service.WhereSql;

import java.lang.reflect.Field;
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
    protected List<Map<String, Object>> query(String sql, Map<String,Object> params) {
        return sqlHandle.select(sql, params);
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
        Map<String, Object> stringObjectMap = field("count(1) as count").executeGetOne();
        if(stringObjectMap==null){
            return 0;
        }
        return Integer.parseInt(stringObjectMap.get("count").toString());
    }

    /**
     * 查询值
     */
    public Object executeGetOneValue(String field) {
        Map<String, Object> stringObjectMap = executeGetOne();
        if(stringObjectMap == null){
            return null;
        }
        return stringObjectMap.get(field);
    }

    /**
     * 查询转换成实体类
     * @param entityClass 实体类类型
     * @return 实体类对象
     * @param <T> 实体类
     */
    public <T> T  executeGetEntity(Class<T> entityClass){
        Map<String, Object> stringObjectMap = executeGetOne();
        Field[] declaredFields = entityClass.getDeclaredFields();
       try {
           T t = entityClass.newInstance();
           for (Field declaredField : declaredFields) {
               String field = StringUtils.toUnderScoreCase(declaredField.getName());
               if(stringObjectMap.containsKey(field)){
                   declaredField.set(t,stringObjectMap.get(field));
               }
           }
           return t;
       } catch (InstantiationException | IllegalAccessException e) {
           throw new RuntimeException(e);
       }
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
