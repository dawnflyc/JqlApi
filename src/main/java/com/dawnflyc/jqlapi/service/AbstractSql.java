package com.dawnflyc.jqlapi.service;


import com.dawnflyc.jqlapi.ParamHandle;
import com.dawnflyc.jqlapi.sql.IPreParamManage;
import com.dawnflyc.jqlapi.sql.ISqlHandle;
import com.dawnflyc.jqlapi.sql.SqlHelper;

import java.util.Map;


/**
 * sql父类
 */
public abstract class AbstractSql<Children extends AbstractSql<Children, R>, R> {


    /**
     * mapper
     */
    public final ISqlHandle sqlHandle = SqlHelper.getDbHandle();
    /**
     * 参数处理器
     */
    protected final ParamHandle paramHandle;
    /**
     * 预编码参数管理器
     */
    protected final IPreParamManage preParamManage = SqlHelper.getPreParamManageFactory().create();
    /**
     * 表名
     */
    protected String table;

    public AbstractSql(String table, ParamHandle paramHandle) {
        this.table = table;
        this.paramHandle = paramHandle;
    }

    public AbstractSql(String table) {
        this.table = table;
        this.paramHandle = ParamHandle.ignoreNull;
    }

    public AbstractSql() {
        this.paramHandle = ParamHandle.ignoreNull;
    }

    /**
     * 转化为正常参数
     *
     * @return
     */
    public Map<String, Object> getStringParam() {
        return preParamManage.toParams();
    }

    /**
     * 分配预编译参数
     *
     * @param value 数据
     * @return
     */
    protected String allocPreParam(Object value) {
        return preParamManage.allocPreParam(value);
    }


    /**
     * 获取sql
     *
     * @return
     */
    public abstract String getSql();

    /**
     * 执行内部sql
     *
     * @return
     */
    protected abstract R query();

    public R execute() {
        R query = query();
        preParamManage.done();
        return query;
    }


}
