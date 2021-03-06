/**
 * Copyright (c) 2014 CoderKiss
 * <p/>
 * CoderKiss[AT]gmail.com
 */

package com.choosecity.choosecity.util;

import android.text.TextUtils;
import android.util.Log;

public class LogUtil {

    public final static int LOG_VERBOSE = Log.VERBOSE;
    public final static int LOG_DEBUG = Log.DEBUG;
    public final static int LOG_INFO = Log.INFO;
    public final static int LOG_WARN = Log.WARN;
    public final static int LOG_ERROR = Log.ERROR;
    public final static int LOG_ASSERT = Log.ASSERT;
    public final static int LOG_NONE = Log.ASSERT + 1;
    public final static int LOG_DEFAULT = LOG_VERBOSE;
    private final static String TAG = "LogUtil";
    private static final String LOG_FORMAT = "%1$s\n%2$s";
    private final static int LOG_MAX_MEM = 16384; // 16k
    private final static int LOG_MAX_LEN = 2048; // 2k
    private final static long LOG_FILE_LEN = 4194304; // 4MB
    private static int mLogPriority = LOG_DEFAULT;
    private static boolean mFileLog = false;
    private static StringBuilder mStringBuilder = new StringBuilder();

//    private static boolean isFlag = MConfiguration.isDebug();

    public static int getLogPriority() {
        return mLogPriority;
    }

    public static void setLogPriority(int priority) {
        mLogPriority = priority;
    }

    public static void setFileLog(boolean fileLog) {
        mFileLog = fileLog;
    }

    public static void v(String tag, String msg) {
        log(Log.VERBOSE, null, tag, msg);
    }

    public static void d(String tag, String msg) {
        log(LOG_DEBUG, null, tag, msg);
    }

    public static void i(String tag, String msg) {
        log(LOG_INFO, null, tag, msg);
    }

    public static void w(String tag, String msg) {
        log(LOG_WARN, null, tag, msg);
    }

    public static void e(String tag, String msg) {
        log(LOG_ERROR, null, tag, msg);
    }

    public static void e(String msg) {
        log(LOG_ERROR, null, TAG, msg);
    }

    public static void e(Throwable ex) {
        log(LOG_ERROR, ex, null, null);
    }

    public static void e(String tag, Throwable ex) {
        log(LOG_ERROR, ex, tag, null);
    }

    public static void e(String tag, String msg, Throwable ex) {
        log(LOG_ERROR, ex, tag, msg);
    }


    public static void d(String msg) {
        log(LOG_DEBUG, null, TAG, msg);
    }

    public static void d(String tag, String msg, Throwable tr) {
        log(LOG_ERROR, tr, tag, msg);
    }

    private static void log(int priority, Throwable ex, String tag, String message) {

//        if (!isFlag) {
//            return;
//        }
        if (tag == null) {
            tag = TAG;
        }
        if (priority < mLogPriority) {
            return;
        }

        if (TextUtils.isEmpty(message)) {
            return;
        }

        if (ex != null) {
            message = message == null ? ex.getMessage() : message;
            String logBody = Log.getStackTraceString(ex);
            message = String.format(LOG_FORMAT, message, logBody);
        }

        String text = message;
        int partLen = 0;
        int length = text.length();
        while (text != null && length > 0) {
            partLen = length > LOG_MAX_LEN ? LOG_MAX_LEN : length;
            String partLog = text.substring(0, partLen);
            Log.println(priority, tag, partLog);
            text = text.substring(partLen);
            length = text.length();
        }

        if (mFileLog) {
            synchronized (mStringBuilder) {
                mStringBuilder.append(message);
                int builderLen = mStringBuilder.length();
                if (builderLen > LOG_MAX_MEM) {
//                    writeLog();
                }
            }
        }
    }




}
