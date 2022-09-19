package com.dawnflyc.jqlapi;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 预编译参数生成器
 */
public class PreParamGenerator {
    /**
     * 生成器
     */
    private static final AtomicLong paramNameSeq = new AtomicLong(0);
    /**
     * 池
     */
    private static final Set<PreParam> pool = new HashSet<>();

    public static PreParam alloc() {
        PreParam param = null;
        synchronized (pool) {
            for (PreParam preParam : pool) {
                if (preParam.isClose()) {
                    preParam.open();
                    param = preParam;
                    break;
                }
            }
            if (param == null) {
                param = new PreParam(paramNameSeq.getAndIncrement());
                pool.add(param);
            }
        }
        return param;
    }


    /**
     * 预编译参数
     */
    public static class PreParam {
        /**
         * 编号
         */
        private final Long id;
        /**
         * 是否关闭
         */
        private Boolean closed;

        /**
         * 创建时间
         */
        private Long time;

        private PreParam(Long id) {
            this.id = id;
            this.closed = false;
            this.time = System.currentTimeMillis();
        }

        private void open() {
            this.closed = false;
            this.time = System.currentTimeMillis();
        }

        private boolean isClose() {
            return this.closed || (System.currentTimeMillis() - this.time > 30 * 1000); //超过30秒关闭
        }

        @Override
        public String toString() {
            return "preParam" + id;
        }

        public void close() {
            this.closed = true;
        }
    }

}
