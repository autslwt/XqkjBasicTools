package com.xqkj.commons.utils;


import java.io.InputStream;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utils 通用工具类
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: Utils.java 2017年4月20日 10:39:53 Exp $
 */
public abstract class Utils {

    static final Logger log = LoggerFactory.getLogger(Utils.class);

    private static final Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    private static final Pattern IPV6_STD_PATTERN = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    /**
     * 将IP地址(220.189.213.3)转变成Long
     *
     * @param ip
     * @return
     */
    public static long ip2Long(String ip) {
        long ret = 0;
        if (ip == null) {
            return ret;
        }
        String[] segs = ip.split("\\.");

        for (int i = 0; i < segs.length; i++) {
            long seg = Long.parseLong(segs[i]);
            ret += (seg << ((3 - i) * 8));
        }

        return ret;
    }

    /**
     * 将数据库中表示IP的Long型，转变成标准形式（220.189.213.3）
     *
     * @param ipLong
     * @return
     */
    public static String long2Ip(long ipLong) {
        StringBuffer ip = new StringBuffer(String.valueOf(ipLong >> 24) + ".");

        ip.append(String.valueOf((ipLong & 16711680) >> 16) + ".");
        ip.append(String.valueOf((ipLong & 65280) >> 8) + ".");
        ip.append(String.valueOf(ipLong & 255));

        return ip.toString();
    }

    public static boolean isIp(final String input) {
        if (null == input || "".equals(input)) {
            return false;
        }
        String ip = input.trim();

        return (isIPv4Address(ip) || isIPv6Address(ip));
    }

    public static boolean isIPv4Address(final String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6Address(final String input) {
        return isIPv6StdAddress(input) || isIPv6HexCompressedAddress(input);
    }

    public static boolean isIPv6StdAddress(final String input) {
        return IPV6_STD_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6HexCompressedAddress(final String input) {
        return IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
    }



    /**
     * 取文件hash值
     */
    public static String getHash(InputStream stream) throws Exception {

        byte[] b = createChecksum(stream);
        StringBuilder sbf = new StringBuilder();
        try {
            for (int i = 0; i < b.length; i++) {
                sbf.append(Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring(1));
            }
            return sbf.toString();
        } finally {
            sbf.setLength(0);
        }
    }

    /**
     * 判断是否图片格式
     */
    public static boolean isImage(byte[] bytes) {
        String type = getTypeByStream(bytes);
        String[] allowedTypes = new String[] {
                "jpg", "png", "gif", "bmp"
        };
        for (String val : allowedTypes) {
            if(Objects.equals(val, type)) {
                return true;
            }
        }

        return false;
    }

    /**
     * byte数组转换成16进制字符串
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 根据文件流读取图片文件真实类型
     */
    public static String getTypeByStream(byte[] fileTypeByte) {

        String type = bytesToHexString(fileTypeByte).toUpperCase();
        if (type.contains("FFD8FF")) {
            return "jpg";
        } else if (type.contains("89504E47")) {
            return "png";
        } else if (type.contains("47494638")) {
            return "gif";
        } else if (type.contains("49492A00")) {
            return "tif";
        } else if (type.contains("424D")) {
            return "bmp";
        } else {
            return null;
        }
    }

    public static byte[] createChecksum(InputStream fis) throws Exception {

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead = 0;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();

        return complete.digest();
    }

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+任意数
     * 17+除9的任意数
     * 145 147 166 198 199
     */
    public static boolean isChinaPhoneLegal(String str) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(14[5,7])|166|198|199)\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }



    /**
     * 以toString()方法为标准判定是否有重复的元素
     *
     * @param objects 数组
     * @return
     */
    public static Object duplicate(Object ... objects) {
        Set<String> set = new HashSet<String>();
        for (Object o : objects) {
            if(null == o) {
                continue;
            }
            boolean suc = set.add(o.toString());
            if(!suc) {
                return o;
            }
        }

        return null;
    }

    public static boolean isNumStr(String numStr) {
        return canMattchRegx(numStr,"\\d{0,}");
    }

    public static boolean canMattchRegx(String str,String regEx) {
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();

        return rs;
    }

    public static  <T> T getAndCastMapValue(Map<String,Object> argsMap, String key){
        return (T)argsMap.get(key);
    }


}
