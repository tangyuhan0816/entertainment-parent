package com.entertainment.common.utils;

import java.util.List;
import java.util.Map;

public class Preconditions {

    public Preconditions() {
    }

    public static <T> boolean isBlank(T t) {
        boolean result = false;
        if (t == null) {
            return true;
        } else {
            if (t instanceof List) {
                if (((List)t).size() == 0) {
                    return true;
                }
            } else if (t instanceof Map) {
                if (((Map)t).size() == 0) {
                    return true;
                }
            } else if (t instanceof Object[]) {
                if (((Object[])((Object[])t)).length == 0) {
                    return true;
                }
            } else if (t instanceof String) {
                int strLen = ((String)t).length();
                if (strLen == 0) {
                    return true;
                }

                for(int i = 0; i < strLen; ++i) {
                    if (!Character.isWhitespace(((String)t).charAt(i))) {
                        return false;
                    }
                }

                return true;
            }

            return result;
        }
    }

    public static <T> boolean isNotBlank(T t) {
        return !isBlank(t);
    }

}
