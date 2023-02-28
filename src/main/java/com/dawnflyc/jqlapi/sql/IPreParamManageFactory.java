package com.dawnflyc.jqlapi.sql;

/**
 * 预编译管理器简单工厂
 */
public interface IPreParamManageFactory {
    /**
     * 创建预编译管理器
     */
    IPreParamManage create();
}
