package com.dawnflyc.jqlapi.sql;

import java.util.Map;

/**
 * 预编译管理器
 */
public interface IPreParamManage {
    /**
     * 分配预编译
     * @param value 值
     * @return 预编译值
     */
    String allocPreParam(Object value);

    /**
     * 返回查询参数
     * @return
     */
    Map<String, Object> toParams();

    /**
     * 完成
     */
    void done();
}
