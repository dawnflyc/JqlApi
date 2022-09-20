package com.dawnflyc.jqlapi.service.crud;


import com.dawnflyc.jqlapi.ParamHandle;
import com.dawnflyc.jqlapi.service.WhereSql;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public String getSql() {
        StringBuilder sql = new StringBuilder();
        data.entrySet().removeIf(entry -> paramHandle.test(entry.getKey(), entry.getValue(), "update"));
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
    protected Integer query() {
        return sqlHandle.update(getSql(), getStringParam());
    }
}
