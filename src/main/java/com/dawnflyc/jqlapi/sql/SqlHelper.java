package com.dawnflyc.jqlapi.sql;

import org.reflections.Reflections;

import java.util.Set;

/**
 * 获取handle类
 */
public class SqlHelper {

    private static ISqlHandle dbHandle;

    /**
     * 如果为空便扫描实现类
     */
    public static ISqlHandle getDbHandle() {
        if (dbHandle == null) {
            Set<Class<? extends ISqlHandle>> impls = new Reflections().getSubTypesOf(ISqlHandle.class);
            if (impls.size() == 0) {
                throw new RuntimeException("找不到Jql实现");
            }
            if (impls.size() > 1) {
                throw new RuntimeException("Jql实现多于一个");
            }
            dbHandle = (ISqlHandle) impls.toArray()[0];
        }
        return dbHandle;
    }

    /**
     * 如果不想扫描便手动设置
     */
    public static void setDbHandle(ISqlHandle dbHandle) {
        SqlHelper.dbHandle = dbHandle;
    }
}
