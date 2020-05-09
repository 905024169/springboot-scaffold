/*package com.ztech.lang;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.ztech.codec.EncodeUtils;
import com.ztech.collect.ListUtils;
import com.ztech.common.Assert;
import com.ztech.core.StrSpliter;
import com.ztech.framework.commons.utils.core.StringFormatter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.sql.Time;
import java.text.CharacterIterator;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringEscapeUtils;

public class StringUtil extends org.apache.commons.lang3.StringUtils {
    public static final int INDEX_NOT_FOUND = -1;
    public static final char C_SPACE = ' ';
    public static final char C_TAB = '\t';
    public static final char C_DOT = '.';
    public static final char C_SLASH = '/';
    public static final char C_BACKSLASH = '\\';
    public static final char C_CR = '\r';
    public static final char C_LF = '\n';
    public static final char C_UNDERLINE = '_';
    public static final char C_COMMA = ',';
    public static final char C_DELIM_START = '{';
    public static final char C_DELIM_END = '}';
    public static final char C_BRACKET_START = '[';
    public static final char C_BRACKET_END = ']';
    public static final char C_COLON = ':';
    public static final String SPACE = " ";
    public static final String TAB = "\t";
    public static final String DOT = ".";
    public static final String DOUBLE_DOT = "..";
    public static final String SLASH = "/";
    public static final String BACKSLASH = "\\";
    public static final String EMPTY = "";
    public static final String NULL = "null";
    public static final String CR = "\r";
    public static final String LF = "\n";
    public static final String CRLF = "\r\n";
    public static final String UNDERLINE = "_";
    public static final String DASHED = "-";
    public static final String COMMA = ",";
    public static final String DELIM_START = "{";
    public static final String DELIM_END = "}";
    public static final String BRACKET_START = "[";
    public static final String BRACKET_END = "]";
    public static final String COLON = ":";
    public static final String HTML_NBSP = "&nbsp;";
    public static final String HTML_AMP = "&amp;";
    public static final String HTML_QUOTE = "&quot;";
    public static final String HTML_APOS = "&apos;";
    public static final String HTML_LT = "&lt;";
    public static final String HTML_GT = "&gt;";
    public static final String EMPTY_JSON = "{}";
    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";
    static char[] hex = "0123456789ABCDEF".toCharArray();
    private static Pattern p1 = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
    private static final Map<String, Pattern> PATTERN_CACHE = new ConcurrentHashMap();

    public StringUtils() {
    }

    public static byte[] getBytes(String str) {
        if (str != null) {
            try {
                return str.getBytes("UTF-8");
            } catch (UnsupportedEncodingException var2) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String toString(byte[] bytes) {
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            return "";
        }
    }

    public static String toString(Object obj) {
        if (null == obj) {
            return null;
        } else if (obj instanceof long[]) {
            return Arrays.toString((long[])((long[])obj));
        } else if (obj instanceof int[]) {
            return Arrays.toString((int[])((int[])obj));
        } else if (obj instanceof short[]) {
            return Arrays.toString((short[])((short[])obj));
        } else if (obj instanceof char[]) {
            return Arrays.toString((char[])((char[])obj));
        } else if (obj instanceof byte[]) {
            return Arrays.toString((byte[])((byte[])obj));
        } else if (obj instanceof boolean[]) {
            return Arrays.toString((boolean[])((boolean[])obj));
        } else if (obj instanceof float[]) {
            return Arrays.toString((float[])((float[])obj));
        } else if (obj instanceof double[]) {
            return Arrays.toString((double[])((double[])obj));
        } else {
            if (ArrayUtils.isArray(obj)) {
                try {
                    return Arrays.deepToString((Object[])((Object[])obj));
                } catch (Exception var2) {
                    ;
                }
            }

            return obj.toString();
        }
    }

    public static boolean inString(String str, String... strs) {
        if (str != null && strs != null) {
            String[] var2 = strs;
            int var3 = strs.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String s = var2[var4];
                if (str.equals(trim(s))) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            String[] var2 = strs;
            int var3 = strs.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String s = var2[var4];
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean containsString(String searchStr, String... strs) {
        if (searchStr != null && strs != null) {
            String[] var2 = strs;
            int var3 = strs.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String s = var2[var4];
                if (s.indexOf(searchStr) >= 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean containsStringIgnoreCase(String searchStr, String... strs) {
        if (searchStr != null && strs != null) {
            String str = searchStr.toLowerCase();
            String[] var3 = strs;
            int var4 = strs.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String s = var3[var5];
                if (s.toLowerCase().indexOf(str) >= 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String stripHtml(String html) {
        if (isBlank(html)) {
            return "";
        } else {
            String regEx = "<.+?>";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(html);
            String s = m.replaceAll("");
            return s;
        }
    }

    public static String toMobileHtml(String html) {
        return html == null ? "" : html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
    }

    public static int length(String value) {
        int valueLength = 0;
        String chinese = "[Α-￥]";

        for(int i = 0; i < value.length(); ++i) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                ++valueLength;
            }
        }

        return valueLength;
    }

    public static String toHtml(String txt) {
        return txt == null ? "" : replace(replace(EncodeUtils.encodeHtml(trim(txt)), "\n", "<br/>"), "\t", "&nbsp; &nbsp; ");
    }

    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        } else {
            try {
                StringBuilder sb = new StringBuilder();
                int currentLength = 0;
                char[] var4 = stripHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray();
                int var5 = var4.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    char c = var4[var6];
                    currentLength += String.valueOf(c).getBytes("GBK").length;
                    if (currentLength > length - 3) {
                        sb.append("...");
                        break;
                    }

                    sb.append(c);
                }

                return sb.toString();
            } catch (UnsupportedEncodingException var8) {
                var8.printStackTrace();
                return "";
            }
        }
    }

    public static String htmlAbbr(String param, int length) {
        if (param == null) {
            return "";
        } else {
            StringBuffer result = new StringBuffer();
            int n = 0;
            boolean isCode = false;
            boolean isHTML = false;

            for(int i = 0; i < param.length(); ++i) {
                char temp = param.charAt(i);
                if (temp == '<') {
                    isCode = true;
                } else if (temp == '&') {
                    isHTML = true;
                } else if (temp == '>' && isCode) {
                    --n;
                    isCode = false;
                } else if (temp == ';' && isHTML) {
                    isHTML = false;
                }

                try {
                    if (!isCode && !isHTML) {
                        n += String.valueOf(temp).getBytes("GBK").length;
                    }
                } catch (UnsupportedEncodingException var11) {
                    var11.printStackTrace();
                }

                if (n > length - 3) {
                    result.append("...");
                    break;
                }

                result.append(temp);
            }

            String tempResult = result.toString().replaceAll("(>)[^<>]*(<?)", "$1$2");
            tempResult = tempResult.replaceAll("</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/

/*
?>", "");
            tempResult = tempResult.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>", "$2");
            Matcher m = p1.matcher(tempResult);
            ArrayList endHTML = ListUtils.newArrayList();

            while(m.find()) {
                endHTML.add(m.group(1));
            }

            for(int i = endHTML.size() - 1; i >= 0; --i) {
                result.append("</");
                result.append((String)endHTML.get(i));
                result.append(">");
            }

            return result.toString();
        }
    }

    public static String cap(String str) {
        return capitalize(str);
    }

    public static String uncap(String str) {
        return uncapitalize(str);
    }

    public static String camelCase(String s) {
        if (s == null) {
            return null;
        } else {
            s = s.toLowerCase();
            StringBuilder sb = new StringBuilder(s.length());
            boolean upperCase = false;

            for(int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                if (c == '_') {
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
    }

    public static String capCamelCase(String s) {
        if (s == null) {
            return null;
        } else {
            s = camelCase(s);
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        }
    }

    public static String uncamelCase(String s) {
        if (s == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean upperCase = false;

            for(int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                boolean nextUpperCase = true;
                if (i < s.length() - 1) {
                    nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
                }

                if (i > 0 && Character.isUpperCase(c)) {
                    if (!upperCase || !nextUpperCase) {
                        sb.append('_');
                    }

                    upperCase = true;
                } else {
                    upperCase = false;
                }

                sb.append(Character.toLowerCase(c));
            }

            return sb.toString();
        }
    }

    public static String jsGetVal(String objectString) {
        StringBuilder result = new StringBuilder();
        StringBuilder val = new StringBuilder();
        String[] vals = split(objectString, ".");

        for(int i = 0; i < vals.length; ++i) {
            val.append("." + vals[i]);
            result.append("!" + val.substring(1) + "?'':");
        }

        result.append(val.substring(1));
        return result.toString();
    }

    public static String getRandomStr(int count) {
        char[] codeSeq = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'o', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        Random random = new Random();
        StringBuilder s = new StringBuilder();

        for(int i = 0; i < count; ++i) {
            String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);
            s.append(r);
        }

        return s.toString();
    }

    public static String getRandomNum(int count) {
        char[] codeSeq = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        Random random = new Random();
        StringBuilder s = new StringBuilder();

        for(int i = 0; i < count; ++i) {
            String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);
            s.append(r);
        }

        return s.toString();
    }

    public static Boolean isNumber(String str) {
        if (ObjectUtils.isNullOrEmpty(str)) {
            return false;
        } else {
            try {
                Integer.valueOf(str);
                return true;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static Integer findNull(Object... objs) {
        if (ObjectUtils.isNullOrEmpty(objs)) {
            return 0;
        } else {
            for(int i = 0; i < objs.length; ++i) {
                if (ObjectUtils.isNullOrEmpty(objs[i])) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static boolean hasNull(Object... objs) {
        return findNull(objs) > -1;
    }

    public static String valueOf(Object object) {
        if (object == null) {
            return add("\"\"");
        } else if (object instanceof Class) {
            return string(object);
        } else if (object instanceof Boolean) {
            return bool((Boolean)object);
        } else if (object instanceof Number) {
            return add(object);
        } else if (object instanceof String) {
            return string(object);
        } else if (object instanceof Character) {
            return string(object);
        } else if (object instanceof Date) {
            return date(object);
        } else if (object instanceof java.sql.Date) {
            return date(new Date(((java.sql.Date)object).getTime()));
        } else if (object instanceof Time) {
            return date(new Date(((Time)((Time)object)).getTime()));
        } else if (object instanceof Date) {
            return date(new Date(((Date)((Date)object)).getTime()));
        } else if (object instanceof Map) {
            return map((Map)object);
        } else if (object.getClass().isArray()) {
            return array(object);
        } else if (object instanceof Iterable) {
            return array(((Iterable)object).iterator());
        } else {
            System.err.println(object.getClass().getName() + " Is Not BaseModel");
            return "";
        }
    }

    private static String map(Map<?, ?> map) {
        try {
            StringBuilder buf = new StringBuilder();
            buf.append(add("{"));
            Iterator<?> it = map.keySet().iterator();
            Object key = null;
            String keyC = null;
            String value = null;

            while(it.hasNext()) {
                try {
                    key = it.next();
                    if (map.get(key) != null) {
                        keyC = valueOf(key);
                        value = valueOf(map.get(key));
                        if (findNull(keyC, value) <= -1) {
                            buf.append(keyC);
                            buf.append(add(":"));
                            buf.append(value);
                            if (it.hasNext()) {
                                buf.append(add(","));
                            }
                        }
                    }
                } catch (Exception var7) {
                    ;
                }
            }

            buf.append(add("}"));
            return buf.toString();
        } catch (Exception var8) {
            return "";
        }
    }

    private static String array(Iterator<?> it) {
        StringBuilder buf = new StringBuilder();

        try {
            buf.append(add("["));
            String value = null;

            while(it.hasNext()) {
                try {
                    value = valueOf(it.next());
                    if (!ObjectUtils.isNullOrEmpty(value)) {
                        buf.append(value);
                        if (it.hasNext()) {
                            buf.append(add(","));
                        }
                    }
                } catch (Exception var4) {
                    ;
                }
            }

            buf.append(add("]"));
            return buf.toString();
        } catch (Exception var5) {
            return "";
        }
    }

    private static String array(Object object) {
        StringBuilder buf = new StringBuilder();

        try {
            String value = null;
            buf.append(add("["));
            int length = Array.getLength(object);

            for(int i = 0; i < length; ++i) {
                try {
                    value = valueOf(Array.get(object, i));
                    if (!ObjectUtils.isNullOrEmpty(value)) {
                        buf.append(value);
                        if (i < length - 1) {
                            add(',');
                        }
                    }
                } catch (Exception var6) {
                    ;
                }
            }

            buf.append(add("]"));
            return buf.toString();
        } catch (Exception var7) {
            return "";
        }
    }

    private static String bool(boolean b) {
        return add(b ? "true" : "false");
    }

    private static String date(Object obj) {
        StringBuilder buf = new StringBuilder();

        try {
            String value = add((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format((Date)obj));
            if (ObjectUtils.isNullOrEmpty(value)) {
                return "";
            } else {
                buf.append(add('"'));
                buf.append(value);
                buf.append(add('"'));
                return buf.toString();
            }
        } catch (Exception var3) {
            return "";
        }
    }

    private static String sqldate(Object obj) {
        StringBuilder buf = new StringBuilder();

        try {
            String value = add((new SimpleDateFormat("yyyy-MM-dd")).format((Date)obj));
            if (ObjectUtils.isNullOrEmpty(value)) {
                return "";
            } else {
                buf.append(add('"'));
                buf.append(value);
                buf.append(add('"'));
                return buf.toString();
            }
        } catch (Exception var3) {
            return "";
        }
    }

    private static String sqltime(Object obj) {
        StringBuilder buf = new StringBuilder();

        try {
            String value = add((new SimpleDateFormat("HH:mm:ss.SSS")).format((Date)obj));
            if (ObjectUtils.isNullOrEmpty(value)) {
                return "";
            } else {
                buf.append(add('"'));
                buf.append(value);
                buf.append(add('"'));
                return buf.toString();
            }
        } catch (Exception var3) {
            return "";
        }
    }

    private static String string(Object obj) {
        try {
            StringBuilder buf = new StringBuilder();
            buf.append(add('"'));
            CharacterIterator it = new StringCharacterIterator(obj.toString());

            for(char c = it.first(); c != '\uffff'; c = it.next()) {
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
                } catch (Exception var5) {
                    ;
                }
            }

            buf.append(add('"'));
            return buf.toString();
        } catch (Exception var6) {
            return "";
        }
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

            for(int i = 0; i < 4; ++i) {
                try {
                    int digit = (n & '\uf000') >> 12;
                    buf.append(add(hex[digit]));
                    n <<= 4;
                } catch (Exception var5) {
                    ;
                }
            }

            return buf.toString();
        } catch (Exception var6) {
            return "";
        }
    }

    public static String collectionMosaic(Object[] objs, String mosaicChr) {
        if (ObjectUtils.isNullOrEmpty(objs)) {
            return null;
        } else {
            List<Object> objList = Arrays.asList(objs);
            return collectionMosaic(objList, mosaicChr);
        }
    }

    public static String collectionMosaic(int[] intObjs, String mosaicChr) {
        Object[] objs = new Object[intObjs.length];

        for(int i = 0; i < intObjs.length; ++i) {
            objs[i] = String.valueOf(intObjs[i]);
        }

        return collectionMosaic(objs, mosaicChr);
    }

    public static String collectionMosaic(String mosaicChr, Object... objs) {
        List<Object> objList = Arrays.asList(objs);
        return collectionMosaic(objList, mosaicChr);
    }

    public static String collectionMosaic(Set<?> objs, String mosaicChr) {
        return collectionMosaic((List)(new ArrayList(objs)), (String)mosaicChr);
    }

    public static String collectionMosaic(List<?> objs, String mosaicChr) {
        if (objs != null && !objs.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            Iterator var4 = objs.iterator();

            while(var4.hasNext()) {
                Object obj = var4.next();
                if (!ObjectUtils.isNullOrEmpty(obj)) {
                    sb.append(obj);
                    if (i < objs.size() - 1) {
                        sb.append(mosaicChr);
                    }

                    ++i;
                }
            }

            return sb.toString();
        } else {
            return null;
        }
    }

    public static String getStringSByMosaicChr(String baseStr, String mosaicChr, Integer size) {
        List<String> list = new ArrayList();

        for(int i = 0; i < size; ++i) {
            if (!ObjectUtils.isNullOrEmpty(baseStr)) {
                list.add(baseStr);
            }
        }

        return collectionMosaic((List)list, (String)mosaicChr);
    }

    public static List<String> splitByMosaic(String text, String mosaiChr) {
        if (text != null && mosaiChr != null) {
            String[] tab = text.split(mosaiChr);
            List<String> list = new ArrayList();

            for(int i = 0; i < tab.length; ++i) {
                if (!ObjectUtils.isNullOrEmpty(tab[i])) {
                    list.add(tab[i]);
                }
            }

            return list;
        } else {
            return null;
        }
    }

    public static List<Integer> splitByMosaicInteger(String text, String mosaiChr) {
        if (text != null && mosaiChr != null) {
            String[] tab = text.split(mosaiChr);
            List<Integer> list = new ArrayList();

            for(int i = 0; i < tab.length; ++i) {
                if (!ObjectUtils.isNullOrEmpty(tab[i])) {
                    try {
                        list.add(Integer.valueOf(tab[i]));
                    } catch (Exception var6) {
                        ;
                    }
                }
            }

            return list;
        } else {
            return null;
        }
    }

    public static Integer[] splitByMosaicIntegers(String text, String mosaiChr) {
        if (text != null && mosaiChr != null) {
            String[] tab = text.split(mosaiChr);
            Integer[] list = new Integer[tab.length];

            for(int i = 0; i < tab.length; ++i) {
                if (!ObjectUtils.isNullOrEmpty(tab[i])) {
                    try {
                        list[i] = Integer.valueOf(tab[i]);
                    } catch (Exception var6) {
                        ;
                    }
                }
            }

            return list;
        } else {
            return null;
        }
    }

    public static List<String> doMatcher(String context, String pat) {
        try {
            List<String> images = new ArrayList();
            Integer index = 0;
            Pattern pattern = Pattern.compile(pat, 32);
            Matcher matcher = pattern.matcher(context);
            String tmp = null;

            while(matcher.find(index)) {
                tmp = matcher.group(1);
                index = matcher.end();
                if (!ObjectUtils.isNullOrEmpty(tmp)) {
                    images.add(tmp);
                }
            }

            return images;
        } catch (Exception var7) {
            return null;
        }
    }

    public static String doMatcherFirst(String context, String pat) {
        List<String> strs = doMatcher(context, pat);
        return ObjectUtils.isNullOrEmpty(strs) ? null : (String)strs.get(0);
    }

    public static String subStringCN(String str, int maxLength) {
        if (str == null) {
            return str;
        } else {
            String suffix = "...";
            int suffixLen = suffix.length();
            StringBuffer sbuffer = new StringBuffer();
            char[] chr = str.trim().toCharArray();
            int len = 0;

            int i;
            for(i = 0; i < chr.length; ++i) {
                if (chr[i] >= 161) {
                    len += 2;
                } else {
                    ++len;
                }
            }

            if (len <= maxLength) {
                return str;
            } else {
                len = 0;

                for(i = 0; i < chr.length; ++i) {
                    if (chr[i] >= 161) {
                        len += 2;
                        if (len + suffixLen > maxLength) {
                            break;
                        }

                        sbuffer.append(chr[i]);
                    } else {
                        ++len;
                        if (len + suffixLen > maxLength) {
                            break;
                        }

                        sbuffer.append(chr[i]);
                    }
                }

                sbuffer.append(suffix);
                return sbuffer.toString();
            }
        }
    }

    public static final Pattern compileRegex(String regex) {
        Pattern pattern = (Pattern)PATTERN_CACHE.get(regex);
        if (pattern == null) {
            pattern = Pattern.compile(regex);
            PATTERN_CACHE.put(regex, pattern);
        }

        return pattern;
    }

    public static String matcherFirst(String patternStr, String text) {
        Pattern pattern = compileRegex(patternStr);
        Matcher matcher = pattern.matcher(text);
        String group = null;
        if (matcher.find()) {
            group = matcher.group();
        }

        return group;
    }

    public static String concat(Object... more) {
        return concatSpiltWith("", more);
    }

    public static String concatSpiltWith(String split, Object... more) {
        StringBuilder buf = new StringBuilder();

        for(int i = 0; i < more.length; ++i) {
            if (i != 0) {
                buf.append(split);
            }

            buf.append(more[i]);
        }

        return buf.toString();
    }

    public static String[] stringSpilt(String text, Integer pageSize) {
        String[] strs = null;
        if (isNotEmpty(text)) {
            if (text.length() <= pageSize) {
                strs = new String[]{text};
                return strs;
            }

            Integer pageNum = text.length() % pageSize > 0 ? 1 + text.length() / pageSize : text.length() / pageSize;
            strs = new String[pageNum];

            for(int i = 1; i <= pageNum; ++i) {
                int pos = (i - 1) * pageSize;
                int len = pos + pageSize > text.length() ? text.length() : pos + pageSize;
                String temp = text.substring(pos, len);
                strs[i - 1] = temp;
            }
        }

        return strs;
    }

    public static String format(CharSequence template, Object... params) {
        if (null == template) {
            return null;
        } else {
            return !ArrayUtils.isEmpty(params) && !isBlank(template) ? StringFormatter.format(template.toString(), params) : template.toString();
        }
    }

    public static String indexedFormat(CharSequence pattern, Object... arguments) {
        return MessageFormat.format(pattern.toString(), arguments);
    }

    public static String format(CharSequence template, Map<?, ?> map) {
        if (null == template) {
            return null;
        } else if (null != map && !map.isEmpty()) {
            String template2 = template.toString();
            Iterator var4 = map.entrySet().iterator();

            while(var4.hasNext()) {
                Entry<?, ?> entry = (Entry)var4.next();
                String value = utf8Str(entry.getValue());
                if (null != value) {
                    template2 = replace(template2, "{" + entry.getKey() + "}", value);
                }
            }

            return template2;
        } else {
            return template.toString();
        }
    }

    public static String utf8Str(Object obj) {
        return str(obj, CharsetUtils.CHARSET_UTF_8);
    }

    public static String str(Object obj, String charsetName) {
        return str(obj, Charset.forName(charsetName));
    }

    public static String str(Object obj, Charset charset) {
        if (null == obj) {
            return null;
        } else if (obj instanceof String) {
            return (String)obj;
        } else if (obj instanceof byte[]) {
            return str((byte[])((byte[])obj), charset);
        } else if (obj instanceof Byte[]) {
            return str((Byte[])((Byte[])obj), charset);
        } else if (obj instanceof ByteBuffer) {
            return str((ByteBuffer)obj, charset);
        } else {
            return ArrayUtils.isArray(obj) ? ArrayUtils.toString(obj) : obj.toString();
        }
    }

    public static String str(byte[] bytes, String charset) {
        return str(bytes, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    public static String str(byte[] data, Charset charset) {
        if (data == null) {
            return null;
        } else {
            return null == charset ? new String(data) : new String(data, charset);
        }
    }

    public static String str(Byte[] bytes, String charset) {
        return str(bytes, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    public static String str(Byte[] data, Charset charset) {
        if (data == null) {
            return null;
        } else {
            byte[] bytes = new byte[data.length];

            for(int i = 0; i < data.length; ++i) {
                Byte dataByte = data[i];
                bytes[i] = null == dataByte ? -1 : dataByte;
            }

            return str(bytes, charset);
        }
    }

    public static String str(ByteBuffer data, String charset) {
        return data == null ? null : str(data, Charset.forName(charset));
    }

    public static String str(ByteBuffer data, Charset charset) {
        if (null == charset) {
            charset = Charset.defaultCharset();
        }

        return charset.decode(data).toString();
    }

    public static String str(CharSequence cs) {
        return null == cs ? null : cs.toString();
    }

    public static String emptyIfNull(CharSequence str) {
        return nullToEmpty(str);
    }

    public static String nullToEmpty(CharSequence str) {
        return nullToDefault(str, "");
    }

    public static String nullToDefault(CharSequence str, String defaultStr) {
        return str == null ? defaultStr : str.toString();
    }

    public static String emptyToDefault(CharSequence str, String defaultStr) {
        return isEmpty(str) ? defaultStr : str.toString();
    }

    public static String blankToDefault(CharSequence str, String defaultStr) {
        return isBlank(str) ? defaultStr : str.toString();
    }

    public static String emptyToNull(CharSequence str) {
        return isEmpty(str) ? null : str.toString();
    }

    public static boolean hasEmpty(CharSequence... strs) {
        if (ArrayUtils.isEmpty(strs)) {
            return true;
        } else {
            CharSequence[] var1 = strs;
            int var2 = strs.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                CharSequence str = var1[var3];
                if (isEmpty(str)) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean isAllEmpty(CharSequence... strs) {
        if (ArrayUtils.isEmpty(strs)) {
            return true;
        } else {
            CharSequence[] var1 = strs;
            int var2 = strs.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                CharSequence str = var1[var3];
                if (isNotEmpty(str)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isNullOrUndefined(CharSequence str) {
        return null == str ? true : isNullOrUndefinedStr(str);
    }

    public static boolean isEmptyOrUndefined(CharSequence str) {
        return isEmpty(str) ? true : isNullOrUndefinedStr(str);
    }

    public static boolean isBlankOrUndefined(CharSequence str) {
        return isBlank(str) ? true : isNullOrUndefinedStr(str);
    }

    private static boolean isNullOrUndefinedStr(CharSequence str) {
        String strString = str.toString().trim();
        return "null".equals(strString) || "undefined".equals(strString);
    }

    public static String wrap(CharSequence str, CharSequence prefixAndSuffix) {
        return wrap(str, prefixAndSuffix, prefixAndSuffix);
    }

    public static String wrap(CharSequence str, CharSequence prefix, CharSequence suffix) {
        return nullToEmpty(prefix).concat(nullToEmpty(str)).concat(nullToEmpty(suffix));
    }

    public static String[] wrapAll(CharSequence prefixAndSuffix, CharSequence... strs) {
        return wrapAll(prefixAndSuffix, prefixAndSuffix, strs);
    }

    public static String[] wrapAll(CharSequence prefix, CharSequence suffix, CharSequence... strs) {
        String[] results = new String[strs.length];

        for(int i = 0; i < strs.length; ++i) {
            results[i] = wrap(strs[i], prefix, suffix);
        }

        return results;
    }

    public static String wrapIfMissing(CharSequence str, CharSequence prefix, CharSequence suffix) {
        int len = 0;
        if (isNotEmpty(str)) {
            len += str.length();
        }

        if (isNotEmpty(prefix)) {
            len += str.length();
        }

        if (isNotEmpty(suffix)) {
            len += str.length();
        }

        StringBuilder sb = new StringBuilder(len);
        if (isNotEmpty(prefix) && !startsWith(str, prefix)) {
            sb.append(prefix);
        }

        if (isNotEmpty(str)) {
            sb.append(str);
        }

        if (isNotEmpty(suffix) && !endsWith(str, suffix)) {
            sb.append(suffix);
        }

        return sb.toString();
    }

    public static String[] wrapAllIfMissing(CharSequence prefixAndSuffix, CharSequence... strs) {
        return wrapAllIfMissing(prefixAndSuffix, prefixAndSuffix, strs);
    }

    public static String[] wrapAllIfMissing(CharSequence prefix, CharSequence suffix, CharSequence... strs) {
        String[] results = new String[strs.length];

        for(int i = 0; i < strs.length; ++i) {
            results[i] = wrapIfMissing(strs[i], prefix, suffix);
        }

        return results;
    }

    public static String unWrap(CharSequence str, String prefix, String suffix) {
        return isWrap(str, prefix, suffix) ? substring(str, prefix.length(), str.length() - suffix.length()) : str.toString();
    }

    public static String unWrap(CharSequence str, char prefix, char suffix) {
        if (isEmpty(str)) {
            return str(str);
        } else {
            return str.charAt(0) == prefix && str.charAt(str.length() - 1) == suffix ? substring(str, 1, str.length() - 1) : str.toString();
        }
    }

    public static String unWrap(CharSequence str, char prefixAndSuffix) {
        return unWrap(str, prefixAndSuffix, prefixAndSuffix);
    }

    public static boolean isWrap(CharSequence str, String prefix, String suffix) {
        if (ArrayUtils.hasNull(new CharSequence[]{str, prefix, suffix})) {
            return false;
        } else {
            String str2 = str.toString();
            return str2.startsWith(prefix) && str2.endsWith(suffix);
        }
    }

    public static boolean isWrap(CharSequence str, String wrapper) {
        return isWrap(str, wrapper, wrapper);
    }

    public static boolean isWrap(CharSequence str, char wrapper) {
        return isWrap(str, wrapper, wrapper);
    }

    public static boolean isWrap(CharSequence str, char prefixChar, char suffixChar) {
        if (null == str) {
            return false;
        } else {
            return str.charAt(0) == prefixChar && str.charAt(str.length() - 1) == suffixChar;
        }
    }

    public static String padPre(CharSequence str, int minLength, CharSequence padStr) {
        if (null == str) {
            return null;
        } else {
            int strLen = str.length();
            if (strLen == minLength) {
                return str.toString();
            } else {
                return strLen > minLength ? subPre(str, minLength) : repeatByLength(padStr, minLength - strLen).concat(str.toString());
            }
        }
    }

    public static String padPre(CharSequence str, int minLength, char padChar) {
        if (null == str) {
            return null;
        } else {
            int strLen = str.length();
            if (strLen == minLength) {
                return str.toString();
            } else {
                return strLen > minLength ? subPre(str, minLength) : repeat(padChar, minLength - strLen).concat(str.toString());
            }
        }
    }

    public static String padAfter(CharSequence str, int minLength, char padChar) {
        if (null == str) {
            return null;
        } else {
            int strLen = str.length();
            if (strLen == minLength) {
                return str.toString();
            } else {
                return strLen > minLength ? substring(str, strLen - minLength, strLen) : str.toString().concat(repeat(padChar, minLength - strLen));
            }
        }
    }

    public static String padAfter(CharSequence str, int minLength, CharSequence padStr) {
        if (null == str) {
            return null;
        } else {
            int strLen = str.length();
            if (strLen == minLength) {
                return str.toString();
            } else {
                return strLen > minLength ? subSufByLength(str, minLength) : str.toString().concat(repeatByLength(padStr, minLength - strLen));
            }
        }
    }

    public static String center(CharSequence str, int size) {
        return center(str, size, ' ');
    }

    public static String center(CharSequence str, int size, char padChar) {
        if (str != null && size > 0) {
            int strLen = str.length();
            int pads = size - strLen;
            if (pads <= 0) {
                return str.toString();
            } else {
                CharSequence str = padPre(str, strLen + pads / 2, padChar);
                str = padAfter(str, size, padChar);
                return str.toString();
            }
        } else {
            return str(str);
        }
    }

    public static String center(CharSequence str, int size, CharSequence padStr) {
        if (str != null && size > 0) {
            if (isEmpty((CharSequence)padStr)) {
                padStr = " ";
            }

            int strLen = str.length();
            int pads = size - strLen;
            if (pads <= 0) {
                return str.toString();
            } else {
                CharSequence str = padPre(str, strLen + pads / 2, (CharSequence)padStr);
                str = padAfter(str, size, (CharSequence)padStr);
                return str.toString();
            }
        } else {
            return str(str);
        }
    }

    public static String substring(CharSequence str, int fromIndex, int toIndex) {
        if (isEmpty(str)) {
            return str(str);
        } else {
            int len = str.length();
            if (fromIndex < 0) {
                fromIndex += len;
                if (fromIndex < 0) {
                    fromIndex = 0;
                }
            } else if (fromIndex > len) {
                fromIndex = len;
            }

            if (toIndex < 0) {
                toIndex += len;
                if (toIndex < 0) {
                    toIndex = len;
                }
            } else if (toIndex > len) {
                toIndex = len;
            }

            if (toIndex < fromIndex) {
                int tmp = fromIndex;
                fromIndex = toIndex;
                toIndex = tmp;
            }

            return fromIndex == toIndex ? "" : str.toString().substring(fromIndex, toIndex);
        }
    }

    public static String subPreGbk(CharSequence str, int len, CharSequence suffix) {
        if (isEmpty(str)) {
            return str(str);
        } else {
            int counterOfDoubleByte = 0;
            byte[] b = str.toString().getBytes(CharsetUtils.CHARSET_GBK);
            if (b.length <= len) {
                return str.toString();
            } else {
                for(int i = 0; i < len; ++i) {
                    if (b[i] < 0) {
                        ++counterOfDoubleByte;
                    }
                }

                if (counterOfDoubleByte % 2 != 0) {
                    ++len;
                }

                return new String(b, 0, len, CharsetUtils.CHARSET_GBK) + suffix;
            }
        }
    }

    public static String subPre(CharSequence string, int toIndex) {
        return substring(string, 0, toIndex);
    }

    public static String subSuf(CharSequence string, int fromIndex) {
        return isEmpty(string) ? null : substring(string, fromIndex, string.length());
    }

    public static String maxLength(CharSequence string, int length) {
        Assert.isTrue(length > 0);
        if (null == string) {
            return null;
        } else {
            return string.length() <= length ? string.toString() : substring(string, 0, length) + "...";
        }
    }

    public static String subBefore(CharSequence string, CharSequence separator, boolean isLastSeparator) {
        if (!isEmpty(string) && separator != null) {
            String str = string.toString();
            String sep = separator.toString();
            if (sep.isEmpty()) {
                return "";
            } else {
                int pos = isLastSeparator ? str.lastIndexOf(sep) : str.indexOf(sep);
                if (-1 == pos) {
                    return str;
                } else {
                    return 0 == pos ? "" : str.substring(0, pos);
                }
            }
        } else {
            return null == string ? null : string.toString();
        }
    }

    public static String subBefore(CharSequence string, char separator, boolean isLastSeparator) {
        if (isEmpty(string)) {
            return null == string ? null : string.toString();
        } else {
            String str = string.toString();
            int pos = isLastSeparator ? str.lastIndexOf(separator) : str.indexOf(separator);
            if (-1 == pos) {
                return str;
            } else {
                return 0 == pos ? "" : str.substring(0, pos);
            }
        }
    }

    public static String subAfter(CharSequence string, CharSequence separator, boolean isLastSeparator) {
        if (isEmpty(string)) {
            return null == string ? null : string.toString();
        } else if (separator == null) {
            return "";
        } else {
            String str = string.toString();
            String sep = separator.toString();
            int pos = isLastSeparator ? str.lastIndexOf(sep) : str.indexOf(sep);
            return -1 != pos && string.length() - 1 != pos ? str.substring(pos + separator.length()) : "";
        }
    }

    public static String subAfter(CharSequence string, char separator, boolean isLastSeparator) {
        if (isEmpty(string)) {
            return null == string ? null : string.toString();
        } else {
            String str = string.toString();
            int pos = isLastSeparator ? str.lastIndexOf(separator) : str.indexOf(separator);
            return -1 == pos ? "" : str.substring(pos + 1);
        }
    }

    public static String subBetween(CharSequence str, CharSequence before, CharSequence after) {
        if (str != null && before != null && after != null) {
            String str2 = str.toString();
            String before2 = before.toString();
            String after2 = after.toString();
            int start = str2.indexOf(before2);
            if (start != -1) {
                int end = str2.indexOf(after2, start + before2.length());
                if (end != -1) {
                    return str2.substring(start + before2.length(), end);
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static String subBetween(CharSequence str, CharSequence beforeAndAfter) {
        return subBetween(str, beforeAndAfter, beforeAndAfter);
    }

    public static String subSufByLength(CharSequence string, int length) {
        if (isEmpty(string)) {
            return null;
        } else {
            return length <= 0 ? "" : substring(string, -length, string.length());
        }
    }

    public static String subWithLength(String input, int fromIndex, int length) {
        return substring(input, fromIndex, fromIndex + length);
    }

    public static String repeatByLength(CharSequence str, int padLen) {
        if (null == str) {
            return null;
        } else if (padLen <= 0) {
            return "";
        } else {
            int strLen = str.length();
            if (strLen == padLen) {
                return str.toString();
            } else if (strLen > padLen) {
                return subPre(str, padLen);
            } else {
                char[] padding = new char[padLen];

                for(int i = 0; i < padLen; ++i) {
                    padding[i] = str.charAt(i % strLen);
                }

                return new String(padding);
            }
        }
    }

    public static String repeatAndJoin(CharSequence str, int count, CharSequence conjunction) {
        if (count <= 0) {
            return "";
        } else {
            StringBuilder builder = new StringBuilder();

            for(boolean isFirst = true; count-- > 0; builder.append(str)) {
                if (isFirst) {
                    isFirst = false;
                } else if (isNotEmpty(conjunction)) {
                    builder.append(conjunction);
                }
            }

            return builder.toString();
        }
    }

    public static String[] splitToArray(CharSequence str, char separator) {
        return splitToArray(str, separator, 0);
    }

    public static List<String> split(CharSequence str, char separator) {
        return split(str, separator, 0);
    }

    public static String[] splitToArray(CharSequence str, char separator, int limit) {
        return null == str ? new String[0] : StrSpliter.splitToArray(str.toString(), separator, limit, false, false);
    }

    public static List<String> split(CharSequence str, char separator, int limit) {
        return split(str, separator, limit, false, false);
    }

    public static List<String> splitTrim(CharSequence str, char separator) {
        return splitTrim(str, separator, -1);
    }

    public static List<String> splitTrim(CharSequence str, CharSequence separator) {
        return splitTrim(str, separator, -1);
    }

    public static List<String> splitTrim(CharSequence str, char separator, int limit) {
        return split(str, separator, limit, true, true);
    }

    public static List<String> splitTrim(CharSequence str, CharSequence separator, int limit) {
        return split(str, separator, limit, true, true);
    }

    public static List<String> split(CharSequence str, char separator, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, 0, isTrim, ignoreEmpty);
    }

    public static List<String> split(CharSequence str, char separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        return (List)(null == str ? new ArrayList(0) : StrSpliter.split(str.toString(), separator, limit, isTrim, ignoreEmpty));
    }

    public static List<String> split(CharSequence str, CharSequence separator, int limit, boolean isTrim, boolean ignoreEmpty) {
        if (null == str) {
            return new ArrayList(0);
        } else {
            String separatorStr = null == separator ? null : separator.toString();
            return StrSpliter.split(str.toString(), separatorStr, limit, isTrim, ignoreEmpty);
        }
    }

    public static String[] split(CharSequence str, CharSequence separator) {
        if (str == null) {
            return new String[0];
        } else {
            String separatorStr = null == separator ? null : separator.toString();
            return StrSpliter.splitToArray(str.toString(), separatorStr, 0, false, false);
        }
    }

    public static String[] split(CharSequence str, int len) {
        return null == str ? new String[0] : StrSpliter.splitByLength(str.toString(), len);
    }

    public static StringBuilder builder() {
        return new StringBuilder();
    }

    public static int indexOf(CharSequence str, char searchChar, int start, int end) {
        int len = str.length();
        if (start < 0 || start > len) {
            start = 0;
        }

        if (end > len || end < 0) {
            end = len;
        }

        for(int i = start; i < end; ++i) {
            if (str.charAt(i) == searchChar) {
                return i;
            }
        }

        return -1;
    }

    public static int indexOf(CharSequence str, CharSequence searchStr, int fromIndex, boolean ignoreCase) {
        if (str != null && searchStr != null) {
            if (fromIndex < 0) {
                fromIndex = 0;
            }

            int endLimit = str.length() - searchStr.length() + 1;
            if (fromIndex > endLimit) {
                return -1;
            } else if (searchStr.length() == 0) {
                return fromIndex;
            } else if (!ignoreCase) {
                return str.toString().indexOf(searchStr.toString(), fromIndex);
            } else {
                for(int i = fromIndex; i < endLimit; ++i) {
                    if (isSubEquals(str, i, searchStr, 0, searchStr.length(), true)) {
                        return i;
                    }
                }

                return -1;
            }
        } else {
            return -1;
        }
    }

    public static boolean isSubEquals(CharSequence str1, int start1, CharSequence str2, int start2, int length, boolean ignoreCase) {
        return null != str1 && null != str2 ? str1.toString().regionMatches(ignoreCase, start1, str2.toString(), start2, length) : false;
    }

    public static boolean hasBlank(CharSequence... strs) {
        if (ArrayUtils.isEmpty(strs)) {
            return true;
        } else {
            CharSequence[] var1 = strs;
            int var2 = strs.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                CharSequence str = var1[var3];
                if (isBlank(str)) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean equals(CharSequence str1, CharSequence str2, boolean ignoreCase) {
        if (null == str1) {
            return str2 == null;
        } else if (null == str2) {
            return false;
        } else {
            return ignoreCase ? str1.toString().equalsIgnoreCase(str2.toString()) : str1.equals(str2);
        }
    }

    public static String removePrefix(CharSequence str, CharSequence prefix) {
        if (!isEmpty(str) && !isEmpty(prefix)) {
            String str2 = str.toString();
            return str2.startsWith(prefix.toString()) ? subSuf(str2, prefix.length()) : str2;
        } else {
            return str(str);
        }
    }

    public static String removePrefixIgnoreCase(CharSequence str, CharSequence prefix) {
        if (!isEmpty(str) && !isEmpty(prefix)) {
            String str2 = str.toString();
            return str2.toLowerCase().startsWith(prefix.toString().toLowerCase()) ? subSuf(str2, prefix.length()) : str2;
        } else {
            return str(str);
        }
    }

    public static String removeSuffix(CharSequence str, CharSequence suffix) {
        if (!isEmpty(str) && !isEmpty(suffix)) {
            String str2 = str.toString();
            return str2.endsWith(suffix.toString()) ? subPre(str2, str2.length() - suffix.length()) : str2;
        } else {
            return str(str);
        }
    }

    public static String upperFirst(CharSequence str) {
        if (null == str) {
            return null;
        } else {
            if (str.length() > 0) {
                char firstChar = str.charAt(0);
                if (Character.isLowerCase(firstChar)) {
                    return Character.toUpperCase(firstChar) + subSuf(str, 1);
                }
            }

            return str.toString();
        }
    }

    public static String lowerFirst(CharSequence str) {
        if (null == str) {
            return null;
        } else {
            if (str.length() > 0) {
                char firstChar = str.charAt(0);
                if (Character.isUpperCase(firstChar)) {
                    return Character.toLowerCase(firstChar) + subSuf(str, 1);
                }
            }

            return str.toString();
        }
    }

    public static String removeSufAndLowerFirst(CharSequence str, CharSequence suffix) {
        return lowerFirst(removeSuffix(str, suffix));
    }

    public static String removeSuffixIgnoreCase(CharSequence str, CharSequence suffix) {
        if (!isEmpty(str) && !isEmpty(suffix)) {
            String str2 = str.toString();
            return str2.toLowerCase().endsWith(suffix.toString().toLowerCase()) ? subPre(str2, str2.length() - suffix.length()) : str2;
        } else {
            return str(str);
        }
    }

    public static byte[] utf8Bytes(CharSequence str) {
        return bytes(str, CharsetUtils.CHARSET_UTF_8);
    }

    public static byte[] bytes(CharSequence str) {
        return bytes(str, Charset.defaultCharset());
    }

    public static byte[] bytes(CharSequence str, String charset) {
        return bytes(str, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    public static byte[] bytes(CharSequence str, Charset charset) {
        if (str == null) {
            return null;
        } else {
            return null == charset ? str.toString().getBytes() : str.toString().getBytes(charset);
        }
    }

    public static boolean startsWith(CharSequence str, char c) {
        return c == str.charAt(0);
    }

    public static String removeAll(CharSequence str, CharSequence strToRemove) {
        return isEmpty(str) ? str(str) : str.toString().replace(strToRemove, "");
    }

    public static String removeAll(CharSequence str, char... chars) {
        if (null != str && !ArrayUtils.isEmpty(chars)) {
            int len = str.length();
            if (0 == len) {
                return str(str);
            } else {
                StringBuilder builder = new StringBuilder(len);

                for(int i = 0; i < len; ++i) {
                    char c = str.charAt(i);
                    if (!ArrayUtils.contains(chars, c)) {
                        builder.append(c);
                    }
                }

                return builder.toString();
            }
        } else {
            return str(str);
        }
    }

    public static String removeAllLineBreaks(CharSequence str) {
        return removeAll(str, '\r', '\n');
    }

    public static String addSuffixIfNot(CharSequence str, CharSequence suffix) {
        if (!isEmpty(str) && !isEmpty(suffix)) {
            String str2 = str.toString();
            String suffix2 = suffix.toString();
            return !str2.endsWith(suffix2) ? str2.concat(suffix2) : str2;
        } else {
            return str(str);
        }
    }

    public static String cleanBlank(CharSequence str) {
        if (str == null) {
            return null;
        } else {
            int len = str.length();
            StringBuilder sb = new StringBuilder(len);

            for(int i = 0; i < len; ++i) {
                char c = str.charAt(i);
                if (!CharUtils.isBlankChar(c)) {
                    sb.append(c);
                }
            }

            return sb.toString();
        }
    }

    public static String removePreAndLowerFirst(CharSequence str, int preLength) {
        if (str == null) {
            return null;
        } else if (str.length() > preLength) {
            char first = Character.toLowerCase(str.charAt(preLength));
            return str.length() > preLength + 1 ? first + str.toString().substring(preLength + 1) : String.valueOf(first);
        } else {
            return str.toString();
        }
    }

    public static String removePreAndLowerFirst(CharSequence str, CharSequence prefix) {
        return lowerFirst(removePrefix(str, prefix));
    }

    public static String upperFirstAndAddPre(CharSequence str, String preString) {
        return str != null && preString != null ? preString + upperFirst(str) : null;
    }

    public static String toUnderlineCase(CharSequence str) {
        return toSymbolCase(str, '_');
    }

    public static String toSymbolCase(CharSequence str, char symbol) {
        if (str == null) {
            return null;
        } else {
            int length = str.length();
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < length; ++i) {
                char c = str.charAt(i);
                Character preChar = i > 0 ? str.charAt(i - 1) : null;
                if (Character.isUpperCase(c)) {
                    Character nextChar = i < str.length() - 1 ? str.charAt(i + 1) : null;
                    if (null != preChar && Character.isUpperCase(preChar)) {
                        sb.append(c);
                    } else if (null != nextChar && Character.isUpperCase(nextChar)) {
                        if (null != preChar && symbol != preChar) {
                            sb.append(symbol);
                        }

                        sb.append(c);
                    } else {
                        if (null != preChar && symbol != preChar) {
                            sb.append(symbol);
                        }

                        sb.append(Character.toLowerCase(c));
                    }
                } else {
                    if (sb.length() > 0 && Character.isUpperCase(sb.charAt(sb.length() - 1)) && symbol != c) {
                        sb.append(symbol);
                    }

                    sb.append(c);
                }
            }

            return sb.toString();
        }
    }

    public static String toCamelCase(CharSequence name) {
        if (null == name) {
            return null;
        } else {
            String name2 = name.toString();
            if (name2.contains("_")) {
                StringBuilder sb = new StringBuilder(name2.length());
                boolean upperCase = false;

                for(int i = 0; i < name2.length(); ++i) {
                    char c = name2.charAt(i);
                    if (c == '_') {
                        upperCase = true;
                    } else if (upperCase) {
                        sb.append(Character.toUpperCase(c));
                        upperCase = false;
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                }

                return sb.toString();
            } else {
                return name2;
            }
        }
    }

    public static boolean containsAnyIgnoreCase(CharSequence str, CharSequence... testStrs) {
        return null != getContainsStrIgnoreCase(str, testStrs);
    }

    public static String getContainsStrIgnoreCase(CharSequence str, CharSequence... testStrs) {
        if (!isEmpty(str) && !ArrayUtils.isEmpty(testStrs)) {
            CharSequence[] var2 = testStrs;
            int var3 = testStrs.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                CharSequence testStr = var2[var4];
                if (containsIgnoreCase(str, testStr)) {
                    return testStr.toString();
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static int count(CharSequence content, CharSequence strForSearch) {
        if (!hasEmpty(content, strForSearch) && strForSearch.length() <= content.length()) {
            int count = 0;
            int idx = 0;
            String content2 = content.toString();

            for(String strForSearch2 = strForSearch.toString(); (idx = content2.indexOf(strForSearch2, idx)) > -1; idx += strForSearch.length()) {
                ++count;
            }

            return count;
        } else {
            return 0;
        }
    }

    public static int count(CharSequence content, char charForSearch) {
        int count = 0;
        if (isEmpty(content)) {
            return 0;
        } else {
            int contentLength = content.length();

            for(int i = 0; i < contentLength; ++i) {
                if (charForSearch == content.charAt(i)) {
                    ++count;
                }
            }

            return count;
        }
    }

    public static void main(String[] args) {
        String s = "select 1 from sys_emp_scheduling where year ='{}'";
        System.out.println(format(s, (Object[])(11)));
        System.out.println(format(s, (Object[])("';delete from")));
    }
}

*/
