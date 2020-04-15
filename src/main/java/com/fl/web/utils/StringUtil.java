/*
 * @(#)StringUtils.java
 *
 * Copyright 2013 vision, Inc. All rights reserved.
 */

package com.fl.web.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author Administrator
 */
public class StringUtil extends StringUtils {

    /**
     * 去除字符串中的html标签，返回指定的长度
     */
    public static String htmlTagFilter(String inputStr, int len) {
        if (inputStr == null || "".equals(inputStr.trim())) {
            return "";
        }
        String outStr = inputStr.replaceAll("\\&[a-zA-Z]{1,10};", "") // 去除类似&lt;
                .replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "") // 去除开始标签及没有结束标签的标签
                .replaceAll("</[a-zA-Z]+[1-9]?>", ""); // 去除结束标签
        if (outStr.length() > len) {
            outStr = outStr.substring(0, len);
            // outStr += "......";
        }
        return outStr;
    }

    /**
     * @param obj
     * @param value
     * @return
     * @Description:将对象转换为字符串
     * @MethodName :parseString
     * @Author :wt
     */
    public static String parseString(Object obj, String value) {
        String str = value;
        try {
            if (obj == null) {
                return str;
            } else {
                str = String.valueOf(obj);
            }
        } catch (Exception e) {
            return str;
        }
        return str;
    }

    /**
     * @param @param  value
     * @param @param  obj
     * @param @return
     * @return Long
     * @throws
     * @Description:将字符转换为Long
     * @author zzp
     * @date 2015-2-4 上午9:02:40
     */
    public static Long parseToLong(String value, Long obj) {
        if (value == null) {
            return obj;
        } else {
            if (StringUtils.isNotEmpty(value.trim())) {
                return Long.parseLong(value);
            } else {
                return obj;
            }
        }
    }

    /**
     * @param @param  value
     * @param @param  obj
     * @param @return
     * @return Double
     * @throws
     * @Description: 将字符转换为Double
     * @author zzp
     * @date 2015-2-4 上午9:03:17
     */
    public static Double parseToDouble(String value, Double obj) {
        if (value == null) {
            return obj;
        } else {
            if (StringUtils.isNotEmpty(value.trim())) {
                return Double.parseDouble(value);
            } else {
                return obj;
            }
        }
    }

    /**
     * @param @param  value
     * @param @param  obj
     * @param @return
     * @return BigDecimal
     * @throws
     * @Description: 将字符转换为BigDecimal
     * @author zzp
     * @date 2015-2-4 上午9:03:28
     */
    public static BigDecimal parseToBigDecimal(String value, BigDecimal obj) {
        if (value == null) {
            return obj;
        } else {
            // if (org.apache.commons.lang.StringUtils.isNotEmpty(value.trim()))
            // {
            if (NumberUtils.isNumber(value.trim())) {
                return new BigDecimal(value);
            } else {
                return obj;
            }
        }
    }

    /**
     * @param @param  clob
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: clobToString
     * @Description: 将数据库中clob类型转换为string类型
     */
    public static String clobToString(Clob clob) {
        if (clob == null) {
            return null;
        }
        try {
            Reader inStreamDoc = clob.getCharacterStream();

            char[] tempDoc = new char[(int) clob.length()];
            inStreamDoc.read(tempDoc);
            inStreamDoc.close();
            return new String(tempDoc);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException es) {
            es.printStackTrace();
        }
        return null;
    }

    //首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    /**
     * 替换字符中的空格、换行符、制表符
     * \n 回车(
     * )
     * \t 水平制表符(\u0009)
     * \s 空格(\u0008)
     * \r 换行(
     * )
     *
     * @param str
     * @return
     * @author zzp
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
//            System.out.println("MD5(" + sourceStr + ",32) = " + result);
//            System.out.println("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }

}
