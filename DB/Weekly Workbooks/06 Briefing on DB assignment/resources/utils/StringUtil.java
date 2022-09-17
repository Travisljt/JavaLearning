package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static boolean containsIgnoreCase(String baseStr, String searchStr) {
        if (baseStr == null || searchStr == null) {
            return false;
        }
        return Pattern.compile(Pattern.quote(searchStr), Pattern.CASE_INSENSITIVE).matcher(baseStr).find();
    }

    public static String getMatchedContent(String regex, String content) {
        if (regex == null || content == null) {
            return null;
        }
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(2).trim();
        }
        return null;
    }

    public static Matcher getMatcher(String regex, String content) {
        if (regex == null || content == null) {
            return null;
        }
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher;
        }
        return null;
    }

    public static String rmRedundancy (String s) {
        return s.replaceAll("\\s+", " ").trim();
    }

    public static boolean isEmpty(Object v) {
        if (v == null) {
            return true;
        }
        return "".equals(v);
    }

    public static String extractField(String str) {
        if (!isEmpty(str)) {
            return str.replace("(", "").replace(")", "").replaceAll("\\'", "").replaceAll("\\s", "");
        }
        return null;
    }

    public static boolean isWithDoubleQuotation(String str) {
        String regex = "^'.+'$";
        return Pattern.matches(regex, str);
    }

    public static String trimQuotation(String str) {
        String regex = "^'|'$";
        return str.replaceAll(regex, "");
    }
}
