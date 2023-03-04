package com.dawnflyc.jqlapi.service.crud;


import com.dawnflyc.jqlapi.ParamHandle;
import com.dawnflyc.jqlapi.StringUtils;
import com.dawnflyc.jqlapi.service.AbstractSql;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 插入服务
 */
public class InsertService extends AbstractSql<InsertService, Object> {

    private final Map<String, Object> data = new HashMap<>();

    public InsertService(String table, ParamHandle paramHandle) {
        super(table, paramHandle);
    }

    public InsertService(String table) {
        super(table);
    }

    public InsertService() {
    }

    @Override
    public String getSql() {
        StringBuilder sql = new StringBuilder("");
        sql.append("insert into ").append(table);
        sql.append("(");
        List<String> keys = new ArrayList<>(data.keySet());
        sql.append(String.join(",", data.keySet()));
        sql.append(")");
        sql.append("values");
        sql.append("(");
        for (int i = 0; i < keys.size(); i++) {
            sql.append(allocPreParam(data.get(keys.get(i))));
            if (i < keys.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(")");
        return sql.toString();
    }

    @Override
    protected Object query() {
        return sqlHandle.insert(getSql(), getStringParam());
    }

    /**
     * 添加一个
     */
    public InsertService add(String field, Object value) {
        if (!paramHandle.test(field, value, "insert")) {
            data.put(field, value);
        }
        return this;
    }

    /**
     * 添加多个
     */
    public InsertService add(Map<String, Object> map) {
        for (String key : map.keySet()) {
            add(key, map.get(key));
        }
        return this;
    }

    /**
     * 通过实体类添加
     * @param entity 实体类对象
     * @param ignoreFields 忽略字段-类字段
     */
    public InsertService add(Object entity,String ... ignoreFields) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        List<String> collect = Arrays.stream(declaredFields).map(Field::getName).collect(Collectors.toList());
        for (Field declaredField : declaredFields) {
            if(!collect.contains(declaredField.getName())){
                try {
                    add(StringUtils.toUnderScoreCase(declaredField.getName()),declaredField.get(entity));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return this;
    }


}
