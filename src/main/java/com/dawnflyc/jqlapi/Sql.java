package com.dawnflyc.jqlapi;


import com.dawnflyc.jqlapi.config.ConfigManage;
import com.dawnflyc.jqlapi.sql.IPreParamManage;
import com.dawnflyc.jqlapi.sql.ISqlHandle;
import com.dawnflyc.jqlapi.sql.SqlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * sql直查入口
 */
public class Sql {

    private static final Logger logger = LoggerFactory.getLogger(Sql.class);
    private static final ISqlHandle dbHandle = SqlHelper.getDbHandle();

    // ?当占位符
    public static Object insert(String sql, Object... params) {
        Long start = null;
        if (ConfigManage.getConfig().isPrintRuntime()) {
            start = System.currentTimeMillis();
        }
        IPreParamManage preParamManage = SqlHelper.getPreParamManageFactory().create();
        Object[] realParams = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            realParams[i] = preParamManage.allocPreParam(param);
        }
        Long buildTime = null;
        if (ConfigManage.getConfig().isPrintRuntime()) {
            buildTime =System.currentTimeMillis();
            logger.debug("Sql构建时间: {}毫秒", buildTime - start);
        }
        Object insert = dbHandle.insert(StringUtils.format(sql, realParams), preParamManage.toParams());
        if(ConfigManage.getConfig().isPrintRuntime()){
            logger.debug("Sql执行时间: {}毫秒", System.currentTimeMillis() - buildTime);
        }
        return insert;
    }

    public static Integer update(String sql, Object... params) {
        Long start = null;
        if (ConfigManage.getConfig().isPrintRuntime()) {
            start = System.currentTimeMillis();
        }
        IPreParamManage preParamManage = SqlHelper.getPreParamManageFactory().create();
        Object [] realParams = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            realParams[i] =preParamManage.allocPreParam(param);
        }
        Long buildTime = null;
        if (ConfigManage.getConfig().isPrintRuntime()) {
            buildTime =System.currentTimeMillis();
            logger.debug("Sql构建时间: {}毫秒", buildTime - start);
        }
        int update = dbHandle.update(StringUtils.format(sql, realParams), preParamManage.toParams());
        if(ConfigManage.getConfig().isPrintRuntime()){
            logger.debug("Sql执行时间: {}毫秒", System.currentTimeMillis() - buildTime);
        }
        return update;

    }

    public static List<Map<String, Object>> select(String sql, Object... params) {
        Long start = null;
        if (ConfigManage.getConfig().isPrintRuntime()) {
            start = System.currentTimeMillis();
        }
        IPreParamManage preParamManage = SqlHelper.getPreParamManageFactory().create();
        Object [] realParams = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            realParams[i] =preParamManage.allocPreParam(param);
        }
        Long buildTime = null;
        if (ConfigManage.getConfig().isPrintRuntime()) {
            buildTime =System.currentTimeMillis();
            logger.debug("Sql构建时间: {}毫秒", buildTime - start);
        }
        List<Map<String, Object>> select = dbHandle.select(StringUtils.format(sql, realParams), preParamManage.toParams());
        if(ConfigManage.getConfig().isPrintRuntime()){
            logger.debug("Sql执行时间: {}毫秒", System.currentTimeMillis() - buildTime);
        }
        return select;

    }

    public static Integer delete(String sql, Object... params) {
        Long start = null;
        if (ConfigManage.getConfig().isPrintRuntime()) {
            start = System.currentTimeMillis();
        }
        IPreParamManage preParamManage = SqlHelper.getPreParamManageFactory().create();
        Object [] realParams = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            realParams[i] =preParamManage.allocPreParam(param);
        }
        Long buildTime = null;
        if (ConfigManage.getConfig().isPrintRuntime()) {
            buildTime =System.currentTimeMillis();
            logger.debug("Sql构建时间: {}毫秒", buildTime - start);
        }
        int delete = dbHandle.delete(StringUtils.format(sql, realParams), preParamManage.toParams());
        if(ConfigManage.getConfig().isPrintRuntime()){
            logger.debug("Sql执行时间: {}毫秒", System.currentTimeMillis() - buildTime);
        }
        return delete;
    }


}
