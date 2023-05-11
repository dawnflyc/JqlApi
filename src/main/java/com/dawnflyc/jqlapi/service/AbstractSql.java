package com.dawnflyc.jqlapi.service;


import com.dawnflyc.jqlapi.ParamHandle;
import com.dawnflyc.jqlapi.config.ConfigManage;
import com.dawnflyc.jqlapi.sql.IPreParamManage;
import com.dawnflyc.jqlapi.sql.ISqlHandle;
import com.dawnflyc.jqlapi.sql.SqlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * sql父类
 */
public abstract class AbstractSql<Children extends AbstractSql<Children, R>, R> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractSql.class);

    /**
     * 创建时间
     */
    private final long time = System.currentTimeMillis();
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
        long executeTime = System.currentTimeMillis();
        if(ConfigManage.getConfig().getPrintRuntime()){
            logger.debug("sql构建器构建时间: {}毫秒",executeTime - this.time);
        }
        R query = query();
        if(ConfigManage.getConfig().getPrintRuntime()){
            logger.debug("sql构建器执行时间: {}毫秒",System.currentTimeMillis() - executeTime);
        }
        preParamManage.done();
        return query;
    }


}
