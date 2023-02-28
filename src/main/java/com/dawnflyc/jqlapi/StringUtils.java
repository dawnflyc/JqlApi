package com.dawnflyc.jqlapi;

public class StringUtils {
    /**
     * 占位符格式化
     *
     * @param str    带占位符的字符串
     * @param params 参数
     * @return 格式化后的字符串
     */
    public static String format(String str, Object... params) {

        StringBuilder stringBuilder = new StringBuilder();
        int index = 0;
        for (char c : str.toCharArray()) {
            if ('?' == c) {
                if (params.length > index) {
                    stringBuilder.append(params[index]);
                    index++;
                } else {
                    throw new RuntimeException("占位符数量和参数数量不符");
                }
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }
}
