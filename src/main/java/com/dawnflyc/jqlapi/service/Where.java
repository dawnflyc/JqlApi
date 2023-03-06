package com.dawnflyc.jqlapi.service;

import com.dawnflyc.jqlapi.ParamHandle;
import com.dawnflyc.jqlapi.StringUtils;
import com.dawnflyc.jqlapi.sql.IPreParamManage;
import com.dawnflyc.jqlapi.sql.SqlHelper;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

/**
 * where处理器
 */
public class Where {

    /**
     * 空参处理器
     */
    private final ParamHandle paramHandle;
    /**
     * 预编译参数管理器
     */
    private final IPreParamManage preParamManage;
    /**
     * root 语句
     */
    private WhereStatement root = new WhereStatement("root", "where");
    /**
     * 当前语句
     */
    private WhereStatement current = root;

    public Where() {
        this.paramHandle = ParamHandle.ignoreNull;
        this.preParamManage = SqlHelper.getPreParamManageFactory().create();
    }

    public Where(ParamHandle paramHandle) {
        this.paramHandle = paramHandle;
        this.preParamManage = SqlHelper.getPreParamManageFactory().create();
    }

    public Where(WhereStatement root) {
        this.root = root;
        this.current = root;
        this.paramHandle = ParamHandle.ignoreNull;
        this.preParamManage = SqlHelper.getPreParamManageFactory().create();
    }

    public Where(IPreParamManage preParamManage) {
        this.preParamManage = preParamManage;
        this.paramHandle = ParamHandle.ignoreNull;
    }

    public Where(ParamHandle paramHandle, IPreParamManage preParamManage) {
        this.paramHandle = paramHandle;
        this.preParamManage = preParamManage;
    }

    public Where(WhereStatement root, ParamHandle paramHandle, IPreParamManage preParamManage) {
        this.root = root;
        this.current = root;
        this.paramHandle = paramHandle;
        this.preParamManage = preParamManage;
    }


    /**
     * 构建sql语句
     *
     * @return
     */
    public String build() {
        StringBuilder sb = new StringBuilder();
        if (root.next == null) {
            return "";
        }
        sb.append(" ").append(root.value).append(" ");
//        WhereStatement current = root.next;
//        while (current != null) {
//            if ("where".equals(current.name) && "where".equals(current.pre.name)) {
//                sb.append(" and ");
//                sb.append(current.value);
//            } else if ("root".equals(current.pre.name) && "door".equals(current.name)) {
//                //需要优化 这块不做任何处理
//                ;
//            }else if("door".equals(current.name) && "door".equals(current.pre.name)){
//                ;
//            }else if("door".equals(current.name) && current.next == null){
//                ;
//            }
//            else {
//                sb.append(current.value);
//            }
//            if (current.next != null) {
//                sb.append(" ");
//            }
//            current = current.next;
//        }
        for (WhereStatement current = root.next; current != null; current = current.next) {
            if ("where".equals(current.name) && "where".equals(current.pre.name)) {
                sb.append(" and ");
            }
            if (
                    ("root".equals(current.pre.name) && "door".equals(current.name)) ||
                            ("door".equals(current.name) && "door".equals(current.pre.name)) ||
                            ("door".equals(current.name) && current.next == null)
            ) {
                continue;
            }
            sb.append(current.value);
            if (current.next != null) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * where语句
     */
    public Where where(String field, String symbol, Object value) {
        if (!paramHandle.test(field, value, "where")) {
            this.addWhere("where", StringUtils.format("? ? ?", field, symbol, preParamManage.allocPreParam(value)));
        }
        return this;
    }

    /**
     * where 等于语句
     *
     * @return
     */
    public Where where(String field, Object value) {
        where(field, "=", value);
        return this;
    }

    public Where WhereIs(String field){
        where(field, "is" ,"null");
        return this;
    }
    public Where WhereNotIs(String field){
        where(field, "is not" ,"null");
        return this;
    }

    /**
     * 多个where语句 and
     *
     * @param where where map
     * @param tied  是否需要用括号包住
     */
    public Where whereAnd(Map<String, Object> where, Boolean tied) {
//        if (where.size() < 1) {
//            return this;
//        }
//        StringBuilder sb = new StringBuilder();
//        if (tied) {
//            sb.append(" ( ");
//        }
//        Iterator<String> iterator = where.keySet().iterator();
//        while (iterator.hasNext()) {
//            String key = iterator.next();
////            where(key, where.get(key));
//            sb.append(" ").append(key).append(" ").append("=").append(" ").append(preParamManage.allocPreParam(where.get(key)));
//            if (iterator.hasNext()) {
//                sb.append(" and ");
//            }
//        }
//        if (tied) {
//            sb.append(" ) ");
//        }
//        this.addWhere("where", sb.toString());
        if (tied) {
            this.where(where1 -> {
                for (String key : where.keySet()) {
                    where1.where(key, where.get(key));
                }
            });
        } else {
            for (String key : where.keySet()) {
                this.where(key, where.get(key));
            }
        }
        return this;
    }

    /**
     * 同键多值语句
     *
     * @param key    键
     * @param symbol 符号
     * @param values 值
     * @param tied   是否加括号
     */
    public Where whereAnd(String key, String symbol, Collection<?> values, Boolean tied) {
        if (tied) {
            this.where(where -> {
                for (Object value : values) {
                    where.where(key, symbol, value);
                }
            });
        } else {
            for (Object value : values) {
                this.where(key, symbol, value);
            }
        }
        return this;
    }

    /**
     * 多个where语句 or
     *
     * @param where where map
     * @param tied  是否需要用括号包住
     */
    public Where whereOr(Map<String, Object> where, Boolean tied) {
//        if (where.size() < 1) {
//            return this;
//        }
//        StringBuilder sb = new StringBuilder();
//        if (tied) {
//            sb.append(" ( ");
//        }
//        Iterator<String> iterator = where.keySet().iterator();
//        while (iterator.hasNext()) {
//            String key = iterator.next();
//            sb.append(" ").append(key).append(" ").append("=").append(" ").append(preParamManage.allocPreParam(where.get(key)));
//            if (iterator.hasNext()) {
//                sb.append(" or ");
//            }
//        }
//        if (tied) {
//            sb.append(" ) ");
//        }
//        this.addWhere("where", sb.toString());
        if (tied) {
            this.where(where1 -> {
                for (String key : where.keySet()) {
                    where1.where(key, where.get(key));
                    where1.or();
                }
            });
        } else {
            for (String key : where.keySet()) {
                this.where(key, where.get(key));
                this.or();
            }
        }
        return this;
    }

    /**
     * 同键多值语句
     *
     * @param key    键
     * @param symbol 符号
     * @param values 值
     * @param tied   是否加括号
     */
    public Where whereOr(String key, String symbol, Collection<?> values, Boolean tied) {
        if (tied) {
            this.where(where -> {
                for (Object value : values) {
                    where.where(key, symbol, value);
                    where.or();
                }
            });
        } else {
            for (Object value : values) {
                this.where(key, symbol, value);
                this.or();
            }
        }
        return this;
    }

    /**
     * 直接写sql
     */
    public Where where(String sql,Object [] params) {
        Object [] realParams = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            realParams[i] =preParamManage.allocPreParam(param);
        }
        this.addWhere("where", StringUtils.format(sql, realParams));
        return this;
    }

    /**
     * 括号包住的语句
     */
    public Where where(Consumer<Where> consumer) {
        Where where = new Where(new WhereStatement("root", ""), this.paramHandle, this.preParamManage);
        consumer.accept(where);
        if (where.root.next != null) {
            this.addWhere("where", StringUtils.format("( ? )", where.build()));
        }
        return this;
    }

    /**
     * in语句
     */
    public Where whereIn(String field, Object[] value) {
        StringBuilder stringBuilder = Arrays.stream(value).filter(v -> !paramHandle.test(field, v, "where")).collect(StringBuilder::new, (sb, v) -> sb.append(",").append(preParamManage.allocPreParam(v)), StringBuilder::append).delete(0, 1);
        this.addWhere("where", StringUtils.format("? in (?)", field, stringBuilder.toString()));
        return this;
    }

    /**
     * like语句
     */
    public Where whereLike(String field, String value, Boolean pre, Boolean post) {
        if (!paramHandle.test(field, value, "where")) {
            this.addWhere("where", StringUtils.format("? ? ?", field, "like", "concat(" + (pre ? "'%'," : "") + preParamManage.allocPreParam(value) + (post ? ",'%'" : "") + ")"));
        }
        return this;
    }

    /**
     * like语句
     */
    public Where whereLike(String field, String value) {
        whereLike(field, value, true, true);
        return this;
    }

    /**
     * 或
     */
    public Where or() {
        this.addWhere("door", "or");
        return this;
    }

    /**
     * 并
     */
    public Where and() {
        this.addWhere("door", "and");
        return this;
    }

    /**
     * 添加where语句
     *
     * @param name
     * @param value
     */
    private void addWhere(String name, String value) {
        this.current.next = new WhereStatement(name, value);
        this.current.next.pre = this.current;
        this.current = current.next;
    }

    /**
     * where 语句
     */
    class WhereStatement {
        /**
         * 该语句的名字
         */
        private final String name;
        /**
         * 语句内容
         */
        private final String value;
        /**
         * 上一个
         */
        private WhereStatement pre;

        /**
         * 下一句
         */
        private WhereStatement next;


        public WhereStatement(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

}
