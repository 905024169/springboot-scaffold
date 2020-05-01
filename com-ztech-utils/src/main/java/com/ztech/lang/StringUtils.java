package com.ztech.lang;

import com.ztech.codec.EncodeUtils;
import com.ztech.collect.ListUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.text.CharacterIterator;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类

 * @version 2018-1-6
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";
    static char[] hex = "0123456789ABCDEF".toCharArray();

    /**
     * 转换为字节数组
     * @param str
     * @return
     */
    public static byte[] getBytes(String str){
        if (str != null){
            try {
                return str.getBytes(CHARSET_NAME);
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 转换为字节数组
     * @param bytes
     * @return
     */
    public static String toString(byte[] bytes){
        try {
            return new String(bytes, CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            return EMPTY;
        }
    }

    /**
     * 是否包含字符串
     * @param str 验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inString(String str, String... strs){
        if (str != null && strs != null){
            for (String s : strs){
                if (str.equals(trim(s))){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否包含字符串
     * @param str 验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs){
        if (str != null && strs != null){
            for (String s : strs){
                if (str.equalsIgnoreCase(trim(s))){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String stripHtml(String html) {
        if (isBlank(html)){
            return "";
        }
        //html.replaceAll("\\&[a-zA-Z]{0,9};", "").replaceAll("<[^>]*>", "");
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * 替换为手机识别的HTML，去掉样式及属性，保留回车。
     * @param html
     * @return
     */
    public static String toMobileHtml(String html){
        if (html == null){
            return "";
        }
        return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
    }

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    public static int length(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }


    /**
     * 对txt进行HTML编码，并将\n转换为&gt;br/&lt;、\t转换为&nbsp; &nbsp;
     * @param txt
     * @return
     */
    public static String toHtml(String txt){
        if (txt == null){
            return "";
        }
        return replace(replace(EncodeUtils.encodeHtml(trim(txt)), "\n", "<br/>"), "\t", "&nbsp; &nbsp; ");
    }

    /**
     * 缩略字符串（不区分中英文字符）
     * @param str 目标字符串
     * @param length 截取长度
     * @return
     */
    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : stripHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
                currentLength += String.valueOf(c).getBytes("GBK").length;
                if (currentLength <= length - 3) {
                    sb.append(c);
                } else {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    // 缩略字符串替换Html正则表达式预编译
    private static Pattern p1 = Pattern.compile("<([a-zA-Z]+)[^<>]*>");

    /**
     * 缩略字符串（适应于与HTML标签的）
     * @param param 目标字符串
     * @param length 截取长度
     * @return
     */
    public static String htmlAbbr(String param, int length) {
        if (param == null) {
            return "";
        }
        StringBuffer result = new StringBuffer();
        int n = 0;
        char temp;
        boolean isCode = false; // 是不是HTML代码
        boolean isHTML = false; // 是不是HTML特殊字符,如&nbsp;
        for (int i = 0; i < param.length(); i++) {
            temp = param.charAt(i);
            if (temp == '<') {
                isCode = true;
            } else if (temp == '&') {
                isHTML = true;
            } else if (temp == '>' && isCode) {
                n = n - 1;
                isCode = false;
            } else if (temp == ';' && isHTML) {
                isHTML = false;
            }
            try {
                if (!isCode && !isHTML) {
                    n += String.valueOf(temp).getBytes("GBK").length;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (n <= length - 3) {
                result.append(temp);
            } else {
                result.append("...");
                break;
            }
        }
        // 取出截取字符串中的HTML标记
        String tempResult = result.toString().replaceAll("(>)[^<>]*(<?)", "$1$2");
        // 去掉不需要结素标记的HTML标记
        tempResult = tempResult.replaceAll("</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|"
                + "HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|"
                + "basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|"
                + "option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>", "");
        // 去掉成对的HTML标记
        tempResult = tempResult.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>", "$2");
        // 用正则表达式取出标记
        Matcher m = p1.matcher(tempResult);
        List<String> endHTML = ListUtils.newArrayList();
        while (m.find()) {
            endHTML.add(m.group(1));
        }
        // 补全不成对的HTML标记
        for (int i = endHTML.size() - 1; i >= 0; i--) {
            result.append("</");
            result.append(endHTML.get(i));
            result.append(">");
        }
        return result.toString();
    }

    /**
     * 首字母大写
     */
    public static String cap(String str){
        return capitalize(str);
    }

    /**
     * 首字母小写
     */
    public static String uncap(String str){
        return uncapitalize(str);
    }

    /**
     * 驼峰命名法工具
     * @return
     * 		camelCase("hello_world") == "helloWorld"
     * 		capCamelCase("hello_world") == "HelloWorld"
     * 		uncamelCase("helloWorld") = "hello_world"
     */
    public static String camelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     * @return
     * 		camelCase("hello_world") == "helloWorld"
     * 		capCamelCase("hello_world") == "HelloWorld"
     * 		uncamelCase("helloWorld") = "hello_world"
     */
    public static String capCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = camelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 驼峰命名法工具
     * @return
     * 		camelCase("hello_world") == "helloWorld"
     * 		capCamelCase("hello_world") == "HelloWorld"
     * 		uncamelCase("helloWorld") = "hello_world"
     */
    public static String uncamelCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 转换为JS获取对象值，生成三目运算返回结果
     * @param objectString 对象串
     *   例如：row.user.id
     *   返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
     */
    public static String jsGetVal(String objectString){
        StringBuilder result = new StringBuilder();
        StringBuilder val = new StringBuilder();
        String[] vals = split(objectString, ".");
        for (int i=0; i<vals.length; i++){
            val.append("." + vals[i]);
            result.append("!"+(val.substring(1))+"?'':");
        }
        result.append(val.substring(1));
        return result.toString();
    }

    /**
     * 获取随机字符串
     * @param count
     * @return
     */
    public static String getRandomStr(int count) {
        char[] codeSeq = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'o', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z'

        };
        Random random = new Random();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < count; i++) {
            String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);
            s.append(r);
        }
        return s.toString();
    }

    /**
     * 获取随机数字
     * @param count
     * @return
     */
    public static String getRandomNum(int count) {
        char[] codeSeq = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        Random random = new Random();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < count; i++) {
            String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);
            s.append(r);
        }
        return s.toString();
    }


    public static boolean isNullOrEmpty(Object obj) {
        try {
            if (obj == null) {
                return true;
            }
            if (obj instanceof CharSequence) {
                return ((CharSequence) obj).length() == 0;
            }
            if (obj instanceof Collection) {
                return ((Collection<?>) obj).isEmpty();
            }
            if (obj instanceof Map) {
                return ((Map<?, ?>) obj).isEmpty();
            }
            if (obj instanceof Object[]) {
                Object[] object = (Object[]) obj;
                if (object.length == 0) {
                    return true;
                }
                boolean empty = true;
                for (int i = 0; i < object.length; i++) {
                    if (!isNullOrEmpty(object[i])) {
                        empty = false;
                        break;
                    }
                }
                return empty;
            }
            return false;
        } catch (Exception e) {
            return true;
        }

    }

    // 判断是否为数字
    public static Boolean isNumber(String str) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        try {
            Integer.valueOf(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Integer findNull(Object... objs) {
        if (isNullOrEmpty(objs)) {
            return 0;
        }
        for (int i = 0; i < objs.length; i++) {
            if (isNullOrEmpty(objs[i])) {
                return i;
            }
        }
        return -1;
    }

    public static boolean hasNull(Object... objs) {
        return findNull(objs) > -1;
    }

    public static String value(Object object) {
        if (object == null) {
            return add("\"\"");
        }
        if (object instanceof Class) {
            return string(object);
        }
        if (object instanceof Boolean) {
            return bool(((Boolean) object).booleanValue());
        }
        if (object instanceof Number) {
            return add(object);
        }
        if (object instanceof String) {
            return string(object);
        }
        if (object instanceof Character) {
            return string(object);
        }
        if (object instanceof Date) {
            return date(object);
        }
        if (object instanceof java.sql.Date) {
            return date(new Date(((java.sql.Date) object).getTime()));
        }
        if (object instanceof java.sql.Time) {
            return date(new Date(((java.sql.Time) (object)).getTime()));
        }
        if (object instanceof Date) {
            return date(new Date(((Date) (object)).getTime()));
        }
        if (object instanceof Map) {
            return map((Map<?, ?>) object);
        }
        if (object.getClass().isArray()) {
            return array(object);
        }
        if (object instanceof Iterable) {
            return array(((Iterable<?>) object).iterator());
        }

        System.err.println(object.getClass().getName() + " Is Not BaseModel");
        return "";
    }

    private static String map(Map<?, ?> map) {
        try {
            StringBuilder buf = new StringBuilder();
            buf.append(add("{"));
            Iterator<?> it = map.keySet().iterator();
            Object key = null;
            String keyC = null, value = null;
            while (it.hasNext()) {
                try {
                    key = it.next();
                    if (map.get(key) == null) {
                        continue;
                    }
                    keyC = value(key);
                    value = value(map.get(key));
                    if (StringUtils.findNull(keyC, value) > -1) {
                        continue;
                    }
                    buf.append(keyC);
                    buf.append(add(":"));
                    buf.append(value);
                    if (it.hasNext()) {
                        buf.append(add(","));
                    }
                } catch (Exception e) {
                }
            }
            buf.append(add("}"));
            return buf.toString();
        } catch (Exception e) {
        }
        return "";
    }

    private static String array(Iterator<?> it) {
        StringBuilder buf = new StringBuilder();
        try {
            buf.append(add("["));
            String value = null;
            while (it.hasNext()) {
                try {
                    value = value(it.next());
                    if (StringUtils.isNullOrEmpty(value)) {
                        continue;
                    }
                    buf.append(value);
                    if (it.hasNext()) {
                        buf.append(add(","));
                    }
                } catch (Exception e) {
                }

            }
            buf.append(add("]"));
            return buf.toString();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }

    private static String array(Object object) {
        StringBuilder buf = new StringBuilder();
        try {
            String value = null;
            buf.append(add("["));
            int length = Array.getLength(object);
            for (int i = 0; i < length; ++i) {
                try {
                    value = value(Array.get(object, i));
                    if (StringUtils.isNullOrEmpty(value)) {
                        continue;
                    }
                    buf.append(value);
                    if (i < length - 1) {
                        add(',');
                    }
                } catch (Exception e) {
                }
            }
            buf.append(add("]"));
            return buf.toString();
        } catch (Exception e) {
        }
        return "";
    }

    private static String bool(boolean b) {
        return add(b ? "true" : "false");
    }

    private static String date(Object obj) {
        StringBuilder buf = new StringBuilder();
        try {
            String value = add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) obj));
            if (StringUtils.isNullOrEmpty(value)) {
                return "";
            }
            buf.append(add('"'));
            buf.append(value);
            buf.append(add('"'));
            return buf.toString();
        } catch (Exception e) {
        }
        return "";
    }

    @SuppressWarnings("unused")
    private static String sqldate(Object obj) {
        StringBuilder buf = new StringBuilder();
        try {
            String value = add(new SimpleDateFormat("yyyy-MM-dd").format((Date) obj));
            if (StringUtils.isNullOrEmpty(value)) {
                return "";
            }
            buf.append(add('"'));
            buf.append(value);
            buf.append(add('"'));
            return buf.toString();
        } catch (Exception e) {
        }
        return "";
    }

    @SuppressWarnings("unused")
    private static String sqltime(Object obj) {
        StringBuilder buf = new StringBuilder();
        try {
            String value = add(new SimpleDateFormat("HH:mm:ss.SSS").format((Date) obj));
            if (StringUtils.isNullOrEmpty(value)) {
                return "";
            }
            buf.append(add('"'));
            buf.append(value);
            buf.append(add('"'));
            return buf.toString();
        } catch (Exception e) {
        }
        return "";
    }

    private static String string(Object obj) {
        try {
            StringBuilder buf = new StringBuilder();
            buf.append(add('"'));
            CharacterIterator it = new StringCharacterIterator(obj.toString());

            for (char c = it.first(); c != CharacterIterator.DONE; c = it.next()) {
                try {
                    if (c == '"') {
                        buf.append(add("\\\""));
                    } else if (c == '\\') {
                        buf.append(add("\\\\"));
                    } else if (c == '\b') {
                        buf.append(add("\\b"));
                    } else if (c == '\f') {
                        buf.append(add("\\f"));
                    } else if (c == '\n') {
                        buf.append(add("\\n"));
                    } else if (c == '\r') {
                        buf.append(add("\\r"));
                    } else if (c == '\t') {
                        buf.append(add("\\t"));
                    } else if (Character.isISOControl(c)) {
                        buf.append(unicode(c));
                    } else {
                        buf.append(add(c));
                    }
                } catch (Exception e) {
                }
            }
            buf.append(add('"'));
            return buf.toString();
        } catch (Exception e) {
        }
        return "";
    }

    private static String add(Object obj) {
        return obj.toString();
    }

    private static String add(char c) {
        return String.valueOf(c);
    }

    private static String unicode(char c) {
        try {
            StringBuilder buf = new StringBuilder();
            buf.append(add("\\u"));
            int n = c;
            for (int i = 0; i < 4; ++i) {
                try {
                    int digit = (n & 0xf000) >> 12;

                    buf.append(add(hex[digit]));
                    n <<= 4;
                } catch (Exception e) {
                }

            }
            return buf.toString();
        } catch (Exception e) {
        }
        return "";
    }


    /**
     * 把一个数组按照分隔符拼接成字符串
     *
     * @param objs
     * @param mosaicChr
     * @return
     */
    public static String collectionMosaic(Object[] objs, String mosaicChr) {
        if (isNullOrEmpty(objs)) {
            return null;
        }
        List<Object> objList = Arrays.asList(objs);
        return collectionMosaic(objList, mosaicChr);
    }

    /**
     * 把一个数组按照分隔符拼接成字符串
     *
     * @param intObjs
     * @param mosaicChr
     * @return
     */
    public static String collectionMosaic(int[] intObjs, String mosaicChr) {
        Object[] objs = new Object[intObjs.length];
        for (int i = 0; i < intObjs.length; i++) {
            objs[i] = String.valueOf(intObjs[i]);
        }
        return collectionMosaic(objs, mosaicChr);
    }

    /**
     * 把一个或多个字符串按照分隔符拼接成字符串
     *
     * @param mosaicChr
     * @param objs
     * @return
     */
    public static String collectionMosaic(String mosaicChr, Object... objs) {
        List<Object> objList = Arrays.asList(objs);
        return collectionMosaic(objList, mosaicChr);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static String collectionMosaic(Set<?> objs, String mosaicChr) {
        return collectionMosaic(new ArrayList(objs), mosaicChr);
    }

    /**
     * 把一个集合按照分隔符拼接成字符串
     *
     * @param objs
     * @param mosaicChr
     * @return 字符串
     */
    public static String collectionMosaic(List<?> objs, String mosaicChr) {
        if (objs == null || objs.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Object obj : objs) {
            if (isNullOrEmpty(obj)) {
                continue;
            }
            sb.append(obj);
            if (i < objs.size() - 1) {
                sb.append(mosaicChr);
            }
            i++;
        }
        return sb.toString();
    }

    /**
     * 生成指定数目字符串按分隔符分割
     *
     * @param baseStr
     * @param mosaicChr
     * @param size
     * @return
     */
    public static String getStringSByMosaicChr(String baseStr, String mosaicChr, Integer size) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < size; i++) {
            if (isNullOrEmpty(baseStr)) {
                continue;
            }
            list.add(baseStr);
        }
        return collectionMosaic(list, mosaicChr);
    }

    /**
     * 按照分隔符分割,得到字符串集合
     *
     * @param text
     *            原字符串
     * @param mosaiChr
     *            分隔符
     * @return list
     */
    public static List<String> splitByMosaic(String text, String mosaiChr) {
        if (text == null || mosaiChr == null) {
            return null;
        }
        String[] tab = text.split(mosaiChr);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < tab.length; i++) {
            if (isNullOrEmpty(tab[i])) {
                continue;
            }
            list.add(tab[i]);
        }
        return list;
    }

    /**
     * 按照分隔符分割,得到字符串集合
     *
     * @param text
     *            原字符串
     * @param mosaiChr
     *            分隔符
     * @return list
     */
    public static List<Integer> splitByMosaicInteger(String text, String mosaiChr) {
        if (text == null || mosaiChr == null) {
            return null;
        }
        String[] tab = text.split(mosaiChr);
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < tab.length; i++) {
            if (isNullOrEmpty(tab[i])) {
                continue;
            }
            try {
                list.add(Integer.valueOf(tab[i]));
            } catch (Exception e) {
            }

        }
        return list;
    }

    /**
     * 按照分隔符分割,得到字符串集合
     *
     * @param text
     *            原字符串
     * @param mosaiChr
     *            分隔符
     * @return list
     */
    public static Integer[] splitByMosaicIntegers(String text, String mosaiChr) {
        if (text == null || mosaiChr == null) {
            return null;
        }
        String[] tab = text.split(mosaiChr);
        Integer[] list = new Integer[tab.length];
        for (int i = 0; i < tab.length; i++) {
            if (isNullOrEmpty(tab[i])) {
                continue;
            }
            try {
                list[i] = Integer.valueOf(tab[i]);
            } catch (Exception e) {
            }

        }
        return list;
    }

    public static List<String> doMatcher(String context, String pat) {
        try {
            List<String> images = new ArrayList<String>();
            Integer index = 0;
            Pattern pattern = Pattern.compile(pat, Pattern.DOTALL);
            Matcher matcher = pattern.matcher(context);
            String tmp = null;
            while (matcher.find(index)) {
                tmp = matcher.group(1);
                index = matcher.end();
                if (isNullOrEmpty(tmp)) {
                    continue;
                }
                images.add(tmp);
            }
            return images;
        } catch (Exception e) {
            return null;
        }
    }

    public static String doMatcherFirst(String context, String pat) {
        List<String> strs = doMatcher(context, pat);
        if (isNullOrEmpty(strs)) {
            return null;
        }
        return strs.get(0);
    }


    public static void main(String[] args) {

    }

}
