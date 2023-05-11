package com.dawnflyc.jqlapi.config;

/**
 * 全局配置
 * 因为没有接入spring，所以要读取配置，要么配置文件要么就是这样(虽然现在都用spring)
 */
public class Config {
    //是否打印运行时间
    private Boolean printRuntime;
    //打印sql
    private Boolean printSql;


    public Boolean getPrintRuntime() {
        return printRuntime;
    }

    public void setPrintRuntime(Boolean printRuntime) {
        this.printRuntime = printRuntime;
    }

    public Boolean getPrintSql() {
        return printSql;
    }

    public void setPrintSql(Boolean printSql) {
        this.printSql = printSql;
    }
}
