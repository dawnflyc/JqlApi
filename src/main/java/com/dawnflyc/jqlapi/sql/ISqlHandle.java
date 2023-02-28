package com.dawnflyc.jqlapi.sql;

import java.util.List;
import java.util.Map;

/**
 * curd接口
 */
public interface ISqlHandle {

    List<Map<String, Object>> select(String sql, Map<String, Object> params);

    Object insert(String sql, Map<String, Object> params);

    int update(String sql, Map<String, Object> params);

    int delete(String sql, Map<String, Object> params);
}
