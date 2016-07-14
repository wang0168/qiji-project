package tts.project.qiji;

import android.content.Context;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.google.gson.Gson;

import cn.jpush.android.api.JPushInterface;
import tts.moudle.api.TTSBaseApplication;
import tts.moudle.api.moudle.SharedPreferencesMoudle;
import tts.moudle.huanxinapi.HXBaseApplication;
import tts.project.qiji.bean.UserInfoBean;
import tts.project.qiji.common.MyAccountMoudle;


/**
 * Created by lenove on 2016/4/29.
 */
public class BaseApplication extends TTSBaseApplication {

    private static BaseApplication baseApplication = new BaseApplication();

    public static BaseApplication getInstance() {
        return baseApplication;
    }

    public BaseApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
//        ShareSDK.initSDK(this);
        initHost("http://qj.tstmobile.com/");
        HXBaseApplication.getInstance().initHX(this,"tts.project.qiji");
        initUser();
        SDKInitializer.initialize(getApplicationContext());
//        HXBaseApplication.getInstance().initHX(this,"tts.project.llg");
//        initQQ("wxbf654da821c08af5");
//        initWb("3112442065");
//        initWx("wxbf654da821c08af5", "");
        initMyUser(getApplicationContext());
        JPushInterface.init(this);
    }

    /**
     * 初始化用户信息
     */
    public void initMyUser(Context context) {
        Log.i("", "==========================2222222222222222222");
        String json = SharedPreferencesMoudle.getInstance().getJson(context, "user_login");
//        Log.i("", "ddd===============" + json);
        UserInfoBean userInfo = new Gson().fromJson(json, UserInfoBean.class);
        MyAccountMoudle.getInstance().setUserInfo(userInfo);
    }

}
