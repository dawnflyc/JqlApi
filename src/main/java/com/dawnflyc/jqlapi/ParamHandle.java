package com.dawnflyc.jqlapi;

/**
 * 参数为空处理模式
 */
public interface ParamHandle {
    /**
     * 忽略Null
     */
    ParamHandle ignoreNull = (key, value, sqlmode) -> value == null;
    /**
     * 忽略Null和空字符串
     */
    ParamHandle ignoreEmpty = (key, value, sqlmode) -> value == null || value.toString().isEmpty();
    /**
     * 不忽略
     */
    ParamHandle notIgnore = (key, value, sqlmode) -> false;

    /**
     * 判断是否保留字段
     *
     * @param key     字段
     * @param value   值
     * @param sqlmode sql模式 insert update where
     */
    boolean test(String key, Object value, String sqlmode);


}


