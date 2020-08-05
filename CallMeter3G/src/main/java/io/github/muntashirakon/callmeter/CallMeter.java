/*
 * Copyright (C) 2009-2013 Felix Bechstein
 * 
 * This file is part of Call Meter 3G.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.muntashirakon.callmeter;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import de.ub0r.android.lib.Utils;
import de.ub0r.android.logg0r.Log;

/**
 * @author flx
 */
public final class CallMeter extends Application {
    private static Application instance;


    private static final String TAG = "CallMeter";

    /**
     * Minimum date.
     */
    public static final long MIN_DATE = 10000000000L;

    /**
     * Milliseconds per seconds.
     */
    public static final long MILLIS = 1000L;

    /**
     * 80.
     */
    public static final int EIGHTY = 80;

    /**
     * 100.
     */
    public static final int HUNDRED = 100;

    /**
     * Seconds of a minute.
     */
    public static final int SECONDS_MINUTE = 60;

    /**
     * Seconds of a hour.
     */
    public static final int SECONDS_HOUR = 60 * SECONDS_MINUTE;

    /**
     * Seconds of a day.
     */
    public static final int SECONDS_DAY = 24 * SECONDS_HOUR;

    /**
     * 10.
     */
    public static final int TEN = 10;

    /**
     * Bytes: kB.
     */
    public static final long BYTE_KB = 1024L;

    /**
     * Bytes: MB.
     */
    public static final long BYTE_MB = BYTE_KB * BYTE_KB;

    /**
     * Bytes: GB.
     */
    public static final long BYTE_GB = BYTE_MB * BYTE_KB;

    /**
     * Bytes: TB.
     */
    public static final long BYTE_TB = BYTE_GB * BYTE_KB;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.setLocale(this);
    }

    public static Application getInstance() {
        return instance;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void setActivitySubtitle(final Activity a, final String t) {
        Objects.requireNonNull(a.getActionBar()).setSubtitle(t);
    }

    public static boolean hasPermission(final Context context, final String permission) {
        return
                Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                        || ContextCompat.checkSelfPermission(context, permission)
                        == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasPermissions(final Context context, @NonNull final String... permissions) {
        for (String p : permissions) {
            if (!hasPermission(context, p)) {
                return false;
            }
        }
        return true;
    }

    public static boolean requestPermission(final Activity activity, final String permission,
            final int requestCode, final int message,
            final DialogInterface.OnClickListener onCancelListener) {
        Log.i(TAG, "requesting permission: " + permission);
        if (!hasPermission(activity, permission)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                new AlertDialog.Builder(activity)
                        .setTitle(R.string.permissions_)
                        .setMessage(message)
                        .setCancelable(false)
                        .setNegativeButton(android.R.string.cancel, onCancelListener)
                        .setPositiveButton(android.R.string.ok, (dialogInterface, i) ->
                                ActivityCompat.requestPermissions(activity,
                                        new String[]{permission}, requestCode))
                        .show();
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            }
            return false;
        } else {
            return true;
        }
    }
}
