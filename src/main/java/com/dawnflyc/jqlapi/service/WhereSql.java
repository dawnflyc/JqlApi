package com.dawnflyc.jqlapi.service;


import com.dawnflyc.jqlapi.ParamHandle;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * where
 *
 * @param <Children> 子类
 */
public abstract class WhereSql<Children extends WhereSql<Children, R>, R> extends AbstractSql<Children, R> {

    /**
     * where对象
     */
    private final Where where = new Where(this.paramHandle, this.preParamManage);


    public WhereSql(String table, ParamHandle paramHandle) {
        super(table, paramHandle);
    }

    public WhereSql(String table) {
        super(table);
    }

    public WhereSql() {
    }

    /**
     * 构建sql
     *
     * @return
     */
    protected String buildWhere() {
        return where.build();
    }

    /**
     * 添加条件
     *
     * @param field  字段
     * @param symbol 操作符
     * @param value  值
     * @return this
     */
    public Children where(String field, String symbol, Object value) {
        where.where(field, symbol, value);
        return (Children) this;
    }

    /**
     * 添加条件
     *
     * @param field 字段
     * @param value 值
     * @return this
     */
    public Children where(String field, Object value) {
        where.where(field, value);
        return (Children) this;
    }


    /**
     * 批量添加并条件
     *
     * @param where 条件
     * @param tied  是否括号括住
     * @return
     */
    public Children whereAnd(Map<String, Object> where, Boolean tied) {
        this.where.whereAnd(where, tied);
        return (Children) this;
    }

    public Children whereAnd(Map<String, Object> where) {
        return whereAnd(where, false);
    }

    /**
     * 同键多值语句
     * @param key 键
     * @param symbol 符号
     * @param values 值
     * @param tied 是否加括号
     */
    public Children whereAnd(String key, String symbol, Collection<?> values, Boolean tied){
        where.whereAnd(key,symbol,values,tied);
        return (Children)this;
    }
    public Children whereAnd(String key,String symbol, Collection<?> values){
        whereAnd(key,"=",values,true);
        return (Children)this;
    }
    public Children whereAnd(String key, Collection<?> values,Boolean tied){
        whereAnd(key,"=",values,tied);
        return (Children)this;
    }
    public Children whereAnd(String key, Collection<?> values){
        whereAnd(key,values,true);
        return (Children)this;
    }

    /**
     * 批量添加或条件
     *
     * @param where 条件
     * @param tied  是否括号括住
     * @return
     */
    public Children whereOr(Map<String, Object> where, Boolean tied) {
        this.where.whereOr(where, tied);
        return (Children) this;
    }

    public Children whereOr(Map<String, Object> where) {
        return whereOr(where, false);
    }

    /**
     * 同键多值语句
     * @param key 键
     * @param symbol 符号
     * @param values 值
     * @param tied 是否加括号
     */
    public Children whereOr(String key, String symbol, Collection<?> values, Boolean tied){
        where.whereOr(key,symbol,values,tied);
        return (Children)this;
    }
    public Children whereOr(String key, String symbol, Collection<?> values){
        where.whereOr(key,symbol,values,false);
        return (Children)this;
    }
    public Children whereOr(String key, Collection<?> values, Boolean tied){
        whereOr(key,"=",values,tied);
        return (Children)this;
    }
    public Children whereOr(String key, Collection<?> values){
        whereOr(key,"=",values,false);
        return (Children)this;
    }
    /**
     * 直接写sql
     *
     * @param sql sql语句
     * @return this
     */
    public Children where(String sql,Object ... params) {
        this.where.where(sql,params);
        return (Children) this;
    }

    public Children where(Consumer<Where> consumer) {
        where.where(consumer);
        return (Children) this;
    }

    /**
     * 添加in条件
     *
     * @param field
     * @param value
     * @return
     */
    public Children whereIn(String field, Object[] value) {
        where.whereIn(field, value);
        return (Children) this;
    }

    /**
     * 添加like条件
     *
     * @param field
     * @param value
     * @return
     */
    public Children whereLike(String field, String value) {
        where.whereLike(field, value);
        return (Children) this;
    }

    /**
     * 或
     *
     * @return this
     */
    public Children or() {
        where.or();
        return (Children) this;
    }

    /**
     * 与
     *
     * @return this
     */
    public Children and() {
        where.and();
        return (Children) this;
    }

}
