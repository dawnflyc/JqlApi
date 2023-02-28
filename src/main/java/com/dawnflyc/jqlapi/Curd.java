package com.dawnflyc.jqlapi;


import com.dawnflyc.jqlapi.service.crud.DeleteService;
import com.dawnflyc.jqlapi.service.crud.InsertService;
import com.dawnflyc.jqlapi.service.crud.SelectService;
import com.dawnflyc.jqlapi.service.crud.UpdateService;

/**
 * 链式查询入口
 */
public class Curd {

    //构造查询
    public static InsertService insert(String table, ParamHandle paramHandle) {
        return new InsertService(table, paramHandle);
    }

    public static UpdateService update(String table, ParamHandle paramHandle) {
        return new UpdateService(table, paramHandle);
    }

    public static SelectService select(String table, ParamHandle paramHandle) {
        return new SelectService(table, paramHandle);
    }

    public static DeleteService delete(String table, ParamHandle paramHandle) {
        return new DeleteService(table, paramHandle);
    }

    //重写
    public static InsertService insert(String table) {
        return insert(table, ParamHandle.ignoreEmpty);
    }

    public static UpdateService update(String table) {
        return update(table, ParamHandle.ignoreEmpty);
    }

    public static SelectService select(String table) {
        return select(table, ParamHandle.ignoreEmpty);
    }

    public static DeleteService delete(String table) {
        return delete(table, ParamHandle.ignoreEmpty);
    }

}
