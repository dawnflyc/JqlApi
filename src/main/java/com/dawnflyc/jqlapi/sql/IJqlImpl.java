package com.dawnflyc.jqlapi.sql;

/**
 * jql实现
 */
public interface IJqlImpl {
    /**
     * 提供sqlhandle
     * @return
     */
    ISqlHandle getSqlHandle();

    /**
     * 提供预编译器
     * @return
     */
    IPreParamManageFactory getPreParamManageFactory();
}
