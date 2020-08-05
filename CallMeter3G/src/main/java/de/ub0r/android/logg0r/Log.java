/*
 * Copyright (C) 2006 The Android Open Source Project
 * Copyright (C) 2013 - 2014 Felix Bechstein
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.ub0r.android.logg0r;

import android.os.Build;

import java.util.IllegalFormatException;

/**
 * Mirror android.util.Log for easy removal with proguard.
 *
 * @author flx
 */
@SuppressWarnings("UnusedDeclaration")
public class Log {

    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final int VERBOSE = android.util.Log.VERBOSE;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int DEBUG = android.util.Log.DEBUG;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int INFO = android.util.Log.INFO;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int WARN = android.util.Log.WARN;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int ERROR = android.util.Log.ERROR;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = android.util.Log.ASSERT;

    private Log() {
        // no instances
    }

    /**
     * Send a {@link #VERBOSE} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies the class or
     *            activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int v(final String tag, final String msg) {
        return log(VERBOSE, tag, msg, (Throwable) null);
    }

    /**
     * Send a {@link #VERBOSE} log message.
     *
     * @param tag  Used to identify the source of a log message.  It usually identifies the class or
     *             activity where the log call occurs.
     * @param msg  The message you would like logged.
     * @param args Arguments for msg's String formatting. Last object may be a throwable.
     */
    public static int v(final String tag, final String msg, final Object... args) {
        return log(VERBOSE, tag, msg, args);
    }

    /**
     * Send a {@link #DEBUG} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies the class or
     *            activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int d(final String tag, final String msg) {
        return log(DEBUG, tag, msg, (Throwable) null);
    }

    /**
     * Send a {@link #DEBUG} log message.
     *
     * @param tag  Used to identify the source of a log message.  It usually identifies the class or
     *             activity where the log call occurs.
     * @param msg  The message you would like logged.
     * @param args Arguments for msg's String formatting. Last object may be a throwable.
     */
    public static int d(final String tag, final String msg, final Object... args) {
        return log(DEBUG, tag, msg, args);
    }

    /**
     * Send an {@link #INFO} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies the class or
     *            activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int i(final String tag, final String msg) {
        return log(INFO, tag, msg, (Throwable) null);
    }

    /**
     * Send an {@link #INFO} log message.
     *
     * @param tag  Used to identify the source of a log message.  It usually identifies the class or
     *             activity where the log call occurs.
     * @param msg  The message you would like logged.
     * @param args Arguments for msg's String formatting. Last object may be a throwable.
     */
    public static int i(final String tag, final String msg, final Object... args) {
        return log(INFO, tag, msg, args);
    }

    /**
     * Send a {@link #WARN} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies the class or
     *            activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int w(final String tag, final String msg) {
        return log(WARN, tag, msg, (Throwable) null);
    }

    /**
     * Send a {@link #WARN} log message.
     *
     * @param tag  Used to identify the source of a log message.  It usually identifies the class or
     *             activity where the log call occurs.
     * @param msg  The message you would like logged.
     * @param args Arguments for msg's String formatting. Last object may be a throwable.
     */
    public static int w(final String tag, final String msg, final Object... args) {
        return log(WARN, tag, msg, args);
    }

    /**
     * Send an {@link #ERROR} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies the class or
     *            activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static int e(final String tag, final String msg) {
        return log(ERROR, tag, msg, (Throwable) null);
    }

    /**
     * Send an {@link #ERROR} log message.
     *
     * @param tag  Used to identify the source of a log message.  It usually identifies the class or
     *             activity where the log call occurs.
     * @param msg  The message you would like logged.
     * @param args Arguments for msg's String formatting. Last object may be a throwable.
     */
    public static int e(final String tag, String msg, final Object... args) {
        return log(ERROR, tag, msg, args);
    }

    /**
     * What a Terrible Failure: Report a condition that should never happen. The error will always
     * be logged at level ASSERT with the call stack. Depending on system configuration, a report
     * may be added to the {@link android.os.DropBoxManager} and/or the process may be terminated
     * immediately with an error dialog.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     */
    public static int wtf(final String tag, final String msg) {
        return log(ASSERT, tag, msg, (Throwable) null);
    }

    /**
     * What a Terrible Failure: Report a condition that should never happen. The error will always
     * be logged at level ASSERT with the call stack. Depending on system configuration, a report
     * may be added to the {@link android.os.DropBoxManager} and/or the process may be terminated
     * immediately with an error dialog.
     *
     * @param tag  Used to identify the source of a log message.
     * @param msg  The message you would like logged.
     * @param args Arguments for msg's String formatting. Last object may be a throwable.
     */
    public static int wtf(final String tag, final String msg, final Object... args) {
        return log(ASSERT, tag, msg, args);
    }

    /**
     * Checks to see whether or not a log for the specified tag is loggable at the specified level.
     *
     * The default level of any tag is set to INFO. This means that any level above and including
     * INFO will be logged. Before you make any calls to a logging method you should check to see if
     * your tag should be logged. You can change the default level by setting a system property:
     * 'setprop log.tag.&lt;YOUR_LOG_TAG> &lt;LEVEL>' Where level is either VERBOSE, DEBUG, INFO,
     * WARN, ERROR, ASSERT, or SUPPRESS. SUPPRESS will turn off all logging for your tag. You can
     * also create a local.prop file that with the following in it: 'log.tag.&lt;YOUR_LOG_TAG>=&lt;LEVEL>'
     * and place that in /data/local.prop.
     *
     * @param tag   The tag to check.
     * @param level The level to check.
     * @return Whether or not that this is allowed to be logged.
     * @throws IllegalArgumentException is thrown if the tag.length() > 23.
     */
    public static boolean isLoggable(final String tag, int level) {
        return android.util.Log.isLoggable(tag, level);
    }

    /**
     * Send a log message.
     *
     * @param level Logging level
     * @param tag   Used to identify the source of a log message.  It usually identifies the class
     *              or activity where the log call occurs.
     * @param msg   The message you would like logged.
     * @param args  Arguments for msg's String formatting. Last object may be a throwable.
     */
    private static int log(final int level, final String tag, final String msg,
            final Object[] args) {
        if (args == null || args.length == 0) {
            return log(level, tag, msg, (Throwable) null);
        }

        // grep Throwable from args
        int l = args.length;
        Throwable tr;
        Object[] fargs;
        if (args[l - 1] instanceof Throwable) {
            l -= 1;
            tr = (Throwable) args[l];
            fargs = new Object[l];
            System.arraycopy(args, 0, fargs, 0, l);
        } else {
            tr = null;
            fargs = args;
        }

        // it's only Throwable, no formatting involved
        if (l == 0) {
            return log(level, tag, msg, tr);
        }

        // formatted msg
        return log(level, tag, msg, fargs, tr);
    }

    /**
     * Send a formatted log message.
     *
     * @param level Logging level
     * @param tag   Used to identify the source of a log message.  It usually identifies the class
     *              or activity where the log call occurs.
     * @param msg   The message you would like logged.
     * @param args  Arguments for msg's String formatting.
     * @param tr    A Throwable for printing stack traces.
     */
    private static int log(final int level, final String tag, final String msg,
            final Object[] args, final Throwable tr) {
        // try String.format first
        if (msg.contains("%")) {
            try {
                return log(level, tag, String.format(msg, args), tr);
            } catch (IllegalFormatException e) {
                // failed
            }
        }

        // just concatenate Objects.toString()
        StringBuilder sb = new StringBuilder(msg);
        for (Object a : args) {
            sb.append(a);
        }
        return log(level, tag, sb.toString(), tr);
    }

    /**
     * Send a plain log message.
     *
     * @param level Logging level
     * @param tag   Used to identify the source of a log message.  It usually identifies the class
     *              or activity where the log call occurs.
     * @param msg   The message you would like logged.
     * @param tr    A Throwable for printing stack traces.
     */
    private static int log(final int level, final String tag, final String msg,
            final Throwable tr) {
        switch (level) {
            case VERBOSE:
                if (tr == null) {
                    return android.util.Log.v(tag, msg);
                } else {
                    return android.util.Log.v(tag, msg, tr);
                }
            case DEBUG:
                if (tr == null) {
                    return android.util.Log.d(tag, msg);
                } else {
                    return android.util.Log.d(tag, msg, tr);
                }
            case INFO:
                if (tr == null) {
                    return android.util.Log.i(tag, msg);
                } else {
                    return android.util.Log.i(tag, msg, tr);
                }
            case WARN:
                if (tr == null) {
                    return android.util.Log.w(tag, msg);
                } else {
                    return android.util.Log.w(tag, msg, tr);
                }
            case ERROR:
                if (tr == null) {
                    return android.util.Log.e(tag, msg);
                } else {
                    return android.util.Log.e(tag, msg, tr);
                }
            case ASSERT:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                    if (tr == null) {
                        return android.util.Log.wtf(tag, msg);
                    } else {
                        return android.util.Log.wtf(tag, msg, tr);
                    }
                } else {
                    if (tr == null) {
                        return android.util.Log.e(tag, msg);
                    } else {
                        return android.util.Log.e(tag, msg, tr);
                    }
                }
            default:
                throw new IllegalArgumentException("Invalid log level: " + level);
        }
    }

}