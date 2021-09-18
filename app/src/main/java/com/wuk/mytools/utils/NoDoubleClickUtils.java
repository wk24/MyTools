package com.wuk.mytools.utils;

/**
 * 防止多次点击工具类
 */
public class NoDoubleClickUtils {
    private static long lastClickTime = 0;
    private final static int SPACE_TIME = 500;

    public static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick2;
        isClick2 = currentTime - lastClickTime <= SPACE_TIME;
        lastClickTime = currentTime;
        return isClick2;
    }
}
