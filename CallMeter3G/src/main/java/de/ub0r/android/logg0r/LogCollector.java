package de.ub0r.android.logg0r;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.NonNull;

/**
 * Helper class to collect and send device Logs by intent.
 *
 * @author flx
 */
@SuppressWarnings("UnusedDeclaration")
public class LogCollector {

    private static final String TAG = "LogCollector";

    private static final String SENDLOG_PACKAGE_NAME = "org.l6n.sendlog";

    private LogCollector() {
        // hide constructor
    }

    /**
     * Collect logs and send them by Intent.
     *
     * @param context             context
     * @param recipient           recipient, most likely a mail address
     * @param sendlogInstallTitle title to show in dialog asking to install the sendlog app
     * @param sendlogInstallText  text to show in dialog asking to install the sendlog app
     * @param sendlogRunTitle     title to show in dialog asking to run sendlog
     * @param sendlogRunText      text to show in dialog asking to run sendlog
     */
    @SuppressWarnings("UnusedDeclaration")
    public static void collectAndSendLogs(final Context context, final String recipient,
            final String sendlogInstallTitle, final String sendlogInstallText,
            final String sendlogRunTitle, final String sendlogRunText) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            if (!sendLogsPlain(context, recipient, true)) {
                sendLogsWithSendlog(context, recipient, sendlogInstallTitle, sendlogInstallText,
                        sendlogRunTitle, sendlogRunText);
            }
        } else {
            sendLogsPlain(context, recipient, false);
        }
    }

    /**
     * Collect and send logs with sendlog app.
     *
     * @param context             context
     * @param recipient           recipient, most likely a mail address
     * @param sendlogInstallTitle title to show in dialog asking to install the sendlog app
     * @param sendlogInstallText  text to show in dialog asking to install the sendlog app
     * @param sendlogRunTitle     title to show in dialog asking to run sendlog
     * @param sendlogRunText      text to show in dialog asking to run sendlog
     */
    private static void sendLogsWithSendlog(final Context context, final String recipient,
            final String sendlogInstallTitle, final String sendlogInstallText,
            final String sendlogRunTitle, final String sendlogRunText) {
        PackageManager pm = context.getPackageManager();
        Intent intent = pm == null ? null : pm.getLaunchIntentForPackage(SENDLOG_PACKAGE_NAME);
        String title, message;
        if (intent == null) {
            intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + SENDLOG_PACKAGE_NAME));
            title = sendlogInstallTitle;
            message = sendlogInstallText;
        } else {
            intent.putExtra(Intent.EXTRA_SUBJECT, "SendLog: " + context.getPackageName());
            if (!TextUtils.isEmpty(recipient)) {
                intent.setType("0||" + recipient);
            }
            title = sendlogRunTitle;
            message = sendlogRunText;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        final Intent i = intent;
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setIcon(android.R.drawable.ic_dialog_info);
        b.setTitle(title);
        b.setMessage(message);
        b.setPositiveButton(android.R.string.ok, (dialogInterface, which) -> context.startActivity(i));
        b.setNegativeButton(android.R.string.cancel, null);
        b.show();
    }

    /**
     * Send logs by reading them directly from device
     *
     * @param context   context
     * @param recipient recipient, most likely a mail address
     * @param silent    do not show any message to user
     * @return true, if intent was fired to send logs
     */
    private static boolean sendLogsPlain(@NonNull final Context context, final String recipient,
                                         final boolean silent) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String pkgName = context.getPackageName();

        StringBuilder sb = new StringBuilder();
        sb.append("Manufacturer: ").append(Build.MANUFACTURER).append("\n");
        sb.append("Model: ").append(Build.MODEL).append("\n");
        sb.append("Device: ").append(Build.DEVICE).append("\n");
        sb.append("Product: ").append(Build.PRODUCT).append("\n");
        sb.append("Android Release: ").append(Build.VERSION.RELEASE).append("\n");
        sb.append("SDK: ").append(Build.VERSION.SDK_INT).append("\n");
        sb.append("\n");
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(pkgName, 0);
            sb.append("App Version: ").append(info.versionName).append("\n");
            sb.append("App Version Code: ").append(info.versionCode).append("\n");
        } catch (NullPointerException e) {
            Log.w(TAG, "PackageManager not found: ", pkgName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, "package not found: ", pkgName);
        }
        sb.append("\n");
        try {
            Process p = Runtime.getRuntime().exec("logcat -d -v time");

            @SuppressLint("SimpleDateFormat")
            File f = new File(context.getExternalCacheDir(), pkgName + "-" + new SimpleDateFormat("y-MM-d_HH-mm-ss")
                    .format(Calendar.getInstance().getTime()) + "-device-logs.log");
            if (f.exists()) {
                //noinspection ResultOfMethodCallIgnored
                f.delete();
            }

            FileOutputStream os = new FileOutputStream(f);
            InputStream is = p.getInputStream();

            byte[] buf = new byte[1024];
            int c;
            while ((c = is.read(buf, 0, buf.length)) > 0) {
                os.write(buf, 0, c);
            }
            is.close();
            os.close();
            p.destroy();
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
        } catch (IOException e) {
            String s = "IOException while reading logs";
            Log.e(TAG, s, e);
            if (!silent) {
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }
            sb.append(s);
            sb.append("\n\n");
            return false;
        }
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "SendLog: " + pkgName);
        if (!TextUtils.isEmpty(recipient)) {
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient, ""});
        }
        intent.putExtra(Intent.EXTRA_TEXT, sb.toString());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(Intent.createChooser(intent, "SendLog"));
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "No app found for this action", e);
            if (!silent) {
                Toast.makeText(context, "No app found for this action", Toast.LENGTH_LONG).show();
            }
            return false;
        }
        return true;
    }
}
