package com.dawnflyc.jqlapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预参数管理器
 */
public class PreParamManage {


    /**
     * 预编码参数
     */
    private final Map<PreParamGenerator.PreParam, Object> params = new HashMap<>();

    /**
     * 关闭所有参数
     */
    public void closePreParam() {
        List<PreParamGenerator.PreParam> destroy = new ArrayList<>();
        for (PreParamGenerator.PreParam preParam : params.keySet()) {
            preParam.close();
            destroy.add(preParam);
        }
        for (PreParamGenerator.PreParam preParam : destroy) {
            params.remove(preParam);
        }
    }

    /**
     * 分配参数
     */
    public String allocPreParam(Object value) {
        PreParamGenerator.PreParam preParam = PreParamGenerator.alloc();
        this.params.put(preParam, value);
        return "#{params." + preParam.toString() + "}";
    }

    /**
     * 获取正常参数
     */
    public Map<String, Object> getStringParam() {
        Map<String, Object> params = new HashMap<>();
        for (Map.Entry<PreParamGenerator.PreParam, Object> entry : this.params.entrySet()) {
            params.put(entry.getKey().toString(), entry.getValue());
        }
        return params;
    }


}
