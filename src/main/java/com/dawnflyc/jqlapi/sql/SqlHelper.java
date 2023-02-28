package com.dawnflyc.jqlapi.sql;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

/**
 * 获取handle类
 */
public class SqlHelper {


    private static IJqlImpl jqlImpl;


    private static IJqlImpl getJqlImpl(){
        if(jqlImpl==null){
            Set<Class<? extends IJqlImpl>> impls = new Reflections(new ConfigurationBuilder().forPackage("com.dawnflyc.jql.impl")).getSubTypesOf(IJqlImpl.class);
            if (impls.size() == 0) {
                throw new RuntimeException("找不到Jql实现");
            }
            if (impls.size() > 1) {
                throw new RuntimeException("Jql实现多于一个");
            }
            Class<? extends IJqlImpl> impl = impls.iterator().next();
            try {
                jqlImpl = impl.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return jqlImpl;
    }

    /**
     * 如果为空便扫描实现类
     */
    public static ISqlHandle getDbHandle() {
        return getJqlImpl().getSqlHandle();
    }

    public static IPreParamManageFactory getPreParamManageFactory() {
        return getJqlImpl().getPreParamManageFactory();
    }

    public static void setJqlImpl(IJqlImpl impl){
        jqlImpl = impl;
    }
}
