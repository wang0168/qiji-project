package tts.project.qiji.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lenove on 2016/5/6.
 */
public class Util {
    public static void createSimpleAlert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确认", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public static void createSimpleDialog(Context context, String title, String message, OnClickListener positive, OnClickListener negative) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确认", positive);
        builder.setNegativeButton("取消", negative);
        builder.show();
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分");
        return format.format(date);
    }

}
