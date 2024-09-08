package com.dawnflyc.jqlapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//配置管理器
public class ConfigManage {

    private static final Logger logger = LoggerFactory.getLogger(ConfigManage.class);
    private static final Config config = new DefaultConfig();

    public static Config getConfig() {
        return config;
    }
}
