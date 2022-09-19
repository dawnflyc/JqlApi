package com.dawnflyc.jqlapi;


import com.dawnflyc.jqlapi.sql.SqlHelper;

import java.util.List;
import java.util.Map;

/**
 * sql直查入口
 */
public class Sql {

    //直接sql
    public static Integer insert(String sql, Map<String, Object> params) {
        return SqlHelper.getDbHandle().insert(sql, params);
    }

    public static Integer update(String sql, Map<String, Object> params) {
        return SqlHelper.getDbHandle().update(sql, params);
    }

    public static List<Map<String, Object>> select(String sql, Map<String, Object> params) {
        return SqlHelper.getDbHandle().select(sql, params);
    }

    public static Integer delete(String sql, Map<String, Object> params) {
        return SqlHelper.getDbHandle().delete(sql, params);
    }


    // ?当占位符
    public static Integer insert(String sql, Object... params) {
        PreParamManage preParamManage = new PreParamManage();
        sql = StringUtils.format(sql, params);
        Integer result = SqlHelper.getDbHandle().insert(sql, preParamManage.getStringParam());
        preParamManage.closePreParam();
        return result;
    }

    public static Integer update(String sql, Object... params) {
        PreParamManage preParamManage = new PreParamManage();
        sql = StringUtils.format(sql, params);
        Integer result = SqlHelper.getDbHandle().update(sql, preParamManage.getStringParam());
        preParamManage.closePreParam();
        return result;
    }

    public static List<Map<String, Object>> select(String sql, Object... params) {
        PreParamManage preParamManage = new PreParamManage();
        sql = StringUtils.format(sql, params);
        List<Map<String, Object>> result = SqlHelper.getDbHandle().select(sql, preParamManage.getStringParam());
        preParamManage.closePreParam();
        return result;
    }

    public static Integer delete(String sql, Object... params) {
        PreParamManage preParamManage = new PreParamManage();
        sql = StringUtils.format(sql, params);
        Integer result = SqlHelper.getDbHandle().delete(sql, preParamManage.getStringParam());
        preParamManage.closePreParam();
        return result;
    }


}
