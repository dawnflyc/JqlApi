package com.dawnflyc.jqlapi;


import com.dawnflyc.jqlapi.sql.IPreParamManage;
import com.dawnflyc.jqlapi.sql.ISqlHandle;
import com.dawnflyc.jqlapi.sql.SqlHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * sql直查入口
 */
public class Sql {


    private static final ISqlHandle dbHandle = SqlHelper.getDbHandle();



    // ?当占位符
    public static Object insert(String sql, Object... params) {
        IPreParamManage preParamManage = SqlHelper.getPreParamManageFactory().create();
        Object [] realParams = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            realParams[i] =preParamManage.allocPreParam(param);
        }
        return dbHandle.insert(StringUtils.format(sql,realParams),preParamManage.toParams());
    }

    public static Integer update(String sql, Object... params) {
        IPreParamManage preParamManage = SqlHelper.getPreParamManageFactory().create();
        Object [] realParams = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            realParams[i] =preParamManage.allocPreParam(param);
        }
        return dbHandle.update(StringUtils.format(sql,realParams),preParamManage.toParams());

    }

    public static List<Map<String, Object>> select(String sql, Object... params) {
        IPreParamManage preParamManage = SqlHelper.getPreParamManageFactory().create();
        Object [] realParams = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            realParams[i] =preParamManage.allocPreParam(param);
        }
        return dbHandle.select(StringUtils.format(sql,realParams),preParamManage.toParams());

    }

    public static Integer delete(String sql, Object... params) {
        IPreParamManage preParamManage = SqlHelper.getPreParamManageFactory().create();
        Object [] realParams = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            realParams[i] =preParamManage.allocPreParam(param);
        }
        return dbHandle.delete(StringUtils.format(sql,realParams),preParamManage.toParams());
    }


}
