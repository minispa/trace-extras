package com.github.minispa;

import org.slf4j.MDC;

import java.util.UUID;

public final class MDCTraceHelper {

    public static final String TraceMark = "_traceMark_";
    private static final String ClearTraceMark = "_ClearTraceMark_";

    public static String newTraceMark() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String newTraceMark(String traceMark) {
        return isBlank(traceMark) ? newTraceMark() : traceMark;
    }

    public static String getNewIfAbsent() {
        return newTraceMark(MDC.get(TraceMark));
    }

    private MDCTraceHelper() {
    }

    public static String setNew() {
        String traceMark = newTraceMark();
        MDC.put(ClearTraceMark, String.valueOf(true));
        MDC.put(TraceMark, traceMark);
        return traceMark;
    }

    public static String setNewIfAbsent(String traceMark) {
        boolean isBlank = isBlank(traceMark);
        if (isBlank) {
            traceMark = newTraceMark();
        }
        MDC.put(ClearTraceMark, String.valueOf(isBlank));
        MDC.put(TraceMark, traceMark);
        return traceMark;
    }

    public static void clear() {
        MDC.remove(ClearTraceMark);
        MDC.remove(TraceMark);
    }

    public static void clearMark() {
        if (Boolean.valueOf(MDC.get(ClearTraceMark))) {
            MDC.clear();
        } else {
            MDC.remove(ClearTraceMark);
        }
    }

//    // ~ =====================================================================
//    public static String newOrSetIfAbsent(String traceMark) {
//        boolean isBlank = traceMark == null || traceMark.trim().length() == 0;
//        if(isBlank) {
//            traceMark = newTraceMark();
//        }
//        MDC.put(ClearTraceMark, String.valueOf(isBlank));
//        MDC.put(TraceMark, traceMark);
//        return traceMark;
//    }
//
//    public static void clearIfPresent() {
//        if(ClearTraceMark.equals(MDC.get(ClearTraceMark))) {
//            MDC.remove(TraceMark);
//            MDC.remove(ClearTraceMark);
//        }
//    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

}
