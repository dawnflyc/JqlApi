package com.dawnflyc.jqlapi.service.crud;


import com.dawnflyc.jqlapi.ParamHandle;
import com.dawnflyc.jqlapi.StringUtils;
import com.dawnflyc.jqlapi.service.WhereSql;

/**
 * 删除服务
 */
public class DeleteService extends WhereSql<DeleteService, Integer> {


    public DeleteService(String table, ParamHandle paramHandle) {
        super(table, paramHandle);
    }

    public DeleteService(String table) {
        super(table);
    }

    public DeleteService() {
    }

    @Override
    public String getSql() {
        return StringUtils.format("DELETE FROM ? ?", table, buildWhere());
    }

    @Override
    protected Integer query() {
        return sqlHandle.delete(getSql(), getStringParam());
    }
}
