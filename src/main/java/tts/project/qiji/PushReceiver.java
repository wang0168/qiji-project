package tts.project.qiji;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import tts.moudle.api.jpush.JpushReceiver;
import tts.moudle.api.utils.JsonUtils;
import tts.project.qiji.user.MainActivity;

/**
 * Created by lenove on 2016/4/25.
 */
public class PushReceiver extends JpushReceiver {
    /**
     * 处理自定义消息
     *
     * @param context
     * @param intent
     */
    @Override
    public void doCustom(Context context, Intent intent) {
        super.doCustom(context, intent);
//        Logger.wtf();

    }

    /**
     * 处理点击通知事件
     *
     * @param context
     * @param intent
     */
    @Override
    public void doNotification(Context context, Intent intent) {
        super.doNotification(context, intent);
        Bundle bundle = intent.getExtras();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Logger.i("This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Logger.json(json.toString() + "====xxxxxxx");

                    if ("1".equals(JsonUtils.getJsonFromJson(json.toString(), "global"))) {
                        if ("1".equals(JsonUtils.getJsonFromJson(json.toString(), "user_type"))) {
                            Intent noteIntent = null;
                            noteIntent = new Intent(context, MainActivity.class);
                            noteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(noteIntent);
                        } else {
                            Intent noteIntent = null;
//                            noteIntent = new Intent(context, EngineerOrderActivity.class);
                            noteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(noteIntent);
                        }

//                        return;
                    } else {
                        String message_code = JsonUtils.getJsonFromJson(json.toString(), "message_code");
                        Intent intentNote = null;
                        switch (message_code) {
                            case "1":
//                                intentNote = new Intent(context, EngineerOrderActivity.class);
                                break;
                            case "2":
//                                intentNote = new Intent(context, MyOrderActivity.class);
                                intentNote.putExtra("init", "2");
                                break;
                            case "3":
//                                intentNote = new Intent(context, MyOrderActivity.class);
                                intentNote.putExtra("init", "2");
                                break;
                            case "4":
//                                intentNote = new Intent(context, MyOrderActivity.class);
                                intentNote.putExtra("init", "3");
                                break;
                            case "5":
//                                intentNote = new Intent(context, EngineerOrderActivity.class);
                                intentNote.putExtra("init", "2");
                                break;
                        }
                        intentNote.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intentNote);
                    }
//                    Iterator<String> it = json.keys();
//                    while (it.hasNext()) {
//                        String myKey = it.next().toString();
//                        if (myKey.contains("")){
//
//                        }
//
//                    }
                } catch (JSONException e) {
                    Logger.e("Get message extra JSON error!");
                }

            }
        }


//        Intent noteIntent = new Intent(context, MainActivity.class);
//        noteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(noteIntent);
    }
}
