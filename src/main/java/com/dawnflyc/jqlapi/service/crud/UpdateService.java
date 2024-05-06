package com.dawnflyc.jqlapi.service.crud;


import com.dawnflyc.jqlapi.ParamHandle;
import com.dawnflyc.jqlapi.StringUtils;
import com.dawnflyc.jqlapi.service.WhereSql;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UpdateService extends WhereSql<UpdateService, Integer> {


    private final Map<String, Object> data = new HashMap<>();

    public UpdateService(String table, ParamHandle paramHandle) {
        super(table, paramHandle);
    }

    public UpdateService(String table) {
        super(table);
    }

    public UpdateService() {
    }

    public UpdateService set(String field, Object value) {
        if (!paramHandle.test(field, value, "update")) {
            data.put(field, value);
        }
        return this;
    }

    public UpdateService set(Map<String, Object> data) {
        this.data.putAll(data);
        return this;
    }

    /**
     * 通过实体类修改
     * @param entity 实体类对象
     * @param ignoreFields 忽略字段-类字段
     */
    public UpdateService setByEntity(Object entity,String ... ignoreFields) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        List<String> collect = Arrays.stream(declaredFields).map(Field::getName).collect(Collectors.toList());
        for (Field declaredField : declaredFields) {
            if(!collect.contains(declaredField.getName())){
                try {
                    set(StringUtils.toUnderScoreCase(declaredField.getName()),declaredField.get(entity));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return this;
    }

    @Override
    public String getSql() {
        StringBuilder sql = new StringBuilder();
//        data.entrySet().removeIf(entry -> paramHandle.test(entry.getKey(), entry.getValue(), "update"));
        sql.append("UPDATE ").append(table);
        if (!data.isEmpty()) {
            sql.append(" SET ");
        }
        data.forEach((key, value) -> {
            sql.append(key).append(" = ").append(allocPreParam(value)).append(",");
        });
        if (!data.isEmpty()) {
            sql.deleteCharAt(sql.length() - 1);
        }
        sql.append(" ");
        sql.append(buildWhere());
        return sql.toString();
    }

    @Override
    protected Integer query(String sql, Map<String,Object> params) {
        return sqlHandle.update(sql, params);
    }
}
