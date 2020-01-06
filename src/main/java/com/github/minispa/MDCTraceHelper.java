package com.github.minispa;

import org.slf4j.MDC;

import java.util.UUID;

public final class MDCTraceHelper {

    public static final String TraceMark = "_traceMark_";
    private static final String ClearTraceMark = "_ClearTraceMark_";

    public static String newTraceMark() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private MDCTraceHelper() {
    }

    public static void set(String traceMark) {
        MDC.put(TraceMark, traceMark);
    }


    public static void clear() {
        MDC.clear();
    }

    public static String newIfBlank(String traceMark) {
        return traceMark == null || traceMark.trim().length() == 0 ? newTraceMark() : traceMark;
    }


    // ~ =====================================================================
    public static String newOrSetIfAbsent(String traceMark) {
        boolean isBlank = traceMark == null || traceMark.trim().length() == 0;
        if(isBlank) {
            traceMark = newTraceMark();
            MDC.put(ClearTraceMark, ClearTraceMark);
        }
        MDC.put(TraceMark, traceMark);
        return traceMark;
    }

    public static void clearIfPresent() {
        if(ClearTraceMark.equals(MDC.get(ClearTraceMark))) {
            MDC.remove(TraceMark);
            MDC.remove(ClearTraceMark);
        }
    }

}
