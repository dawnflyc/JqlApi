package com.dawnflyc.jqlapi.config;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

//配置管理器
public class ConfigManage {

    private static final Logger logger = LoggerFactory.getLogger(ConfigManage.class);
    private static Config config;

    public static Config getConfig() {
        if(config == null){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Config.class);
            Config currentConfig =new Config();
            Config defaultConfig = new DefaultConfig();
            InvocationHandler callback = (proxy, method, args) -> {
                if(method.getName().startsWith("set")){
                    String replace = method.getName().replace("set", "");
                    String c = Character.toString(replace.charAt(0));
                    replace = c.toLowerCase(Locale.ROOT) + replace.replace(c,"");
                    Field field = Config.class.getDeclaredField(replace);
                    field.setAccessible(true);
                    Object value = field.get(currentConfig);
                    if(value==null){
                        method.invoke(currentConfig,args);
                    }else {
                        logger.error("config只能修改一次");
                    }
                }
                if (method.getName().startsWith("get")){
                    Object invoke = method.invoke(currentConfig);
                    if (invoke==null){
                        return method.invoke(defaultConfig);
                    }else {
                        return invoke;
                    }
                }
                return null;
            };
            enhancer.setCallback(callback);
            config = (Config) enhancer.create();
        }
        return config;
    }
}
