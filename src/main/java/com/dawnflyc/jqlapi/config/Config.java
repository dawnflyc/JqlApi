package com.dawnflyc.jqlapi.config;

/**
 * 全局配置
 * 因为没有接入spring，所以要读取配置，要么配置文件要么就是这样(虽然现在都用spring)
 */
public class Config {
    //是否打印运行时间
    private Boolean printRuntime;
    //是否打印查询
    private Boolean printQuery;

    public boolean  isPrintRuntime() {
        return printRuntime;
    }

    public void setPrintRuntime(boolean printRuntime) {
        this.printRuntime = printRuntime;
    }

    public boolean isPrintQuery() {
        return printQuery;
    }

    public void setPrintQuery(boolean printQuery) {
        this.printQuery = printQuery;
    }
}
