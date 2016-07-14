package tts.project.qiji.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.bean.UserInfoBean;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.engineer.EngPersonalActivity;
import tts.project.qiji.engineer.EngineerOrderActivity;
import tts.project.qiji.engineer_manager.EngineerManagerActivity;
import tts.project.qiji.user.MainActivity;
import tts.project.qiji.user_manager.UserManagerActivity;
import tts.project.qiji.utils.LoginInfoSave;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity {

    private final int login_ok = 1000;
    @Bind(R.id.loginBtn)
    Button loginBtn;
    @Bind(R.id.login_register_user)
    TextView login_register_user;
    @Bind(R.id.tv_forget)
    TextView tv_forget;
    @Bind(R.id.ETMobile)
    EditText ETMobile;
    @Bind(R.id.ETPassword)
    EditText ETPassword;
    public SharedPreferences sp;
    public SharedPreferences.Editor editor;
    private String phone;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("登录"));

        sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putBoolean("IsLogin", false);
        editor.putString("info", "");
        editor.commit();
//        AccountMoudle.getInstance().setUserInfo(new UserInfo());
    }


    private void initData() {
        if (!TextUtils.isMobileNO(ETMobile.getText().toString().trim())) {
            CustomUtils.showTipShort(this, "请检查手机格式是否正确");
            return;
        }
        if (TextUtils.isEmpty(ETPassword.getText().toString().trim())) {
            CustomUtils.showTipShort(this, "密码不能为空");
            return;
        }
        startRequestData(login_ok);
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;

        switch (index) {
            case login_ok:
                params = new ArrayMap<>();
                params.put("phone", ETMobile.getText().toString().trim());
                params.put("password", ETPassword.getText().toString().trim());
                getDataWithPost(login_ok, Host.hostUrl + "/api.php?m=Api&c=Login&a=login", params);
                break;
            case submitData:
                params = new ArrayMap<>();
                params.put("phone", phone);
                params.put("password", password);
                getDataWithPost(login_ok, Host.hostUrl + "api.php?m=Api&c=Login&a=login", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case login_ok:
                LoginInfoSave.loginOk(this, response);
//                editor.putBoolean("IsLogin", true);
//                editor.putString("info", response);
//                editor.commit();
                UserInfoBean userInfo = new Gson().fromJson(response, UserInfoBean.class);
                userInfo.setLogin(true);
                MyAccountMoudle.getInstance().setUserInfo(userInfo);
                if ("1".equals(userInfo.getType())) {//用户端
                    startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                } else if ("2".equals(MyAccountMoudle.getInstance().getUserInfo().getType())) {//工程师端
                    Intent intent;
                    if ("1".equals(MyAccountMoudle.getInstance().getUserInfo().getShenhe())) {
                        intent = new Intent(this, EngineerOrderActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else {
                        intent = new Intent(this, EngPersonalActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    }
//                    AppManager.getAppManager().finishActivity(MainActivity.class);
//                    ActivityManager activityManager = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
//                    List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(100);
                    startActivity(intent);

                    finish();
                } else if ("3".equals(MyAccountMoudle.getInstance().getUserInfo().getType())) {//用户管理
                    Intent intent = new Intent(this, UserManagerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
//                    finish();
                } else if ("4".equals(MyAccountMoudle.getInstance().getUserInfo().getType())) {//工程师管理
                    Intent intent = new Intent(this, EngineerManagerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                break;
        }

    }


    @OnClick({R.id.login_register_user, R.id.tv_forget, R.id.loginBtn})
    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                initData();
                break;
            case R.id.login_register_user:
                startActivityForResult(new Intent(this, RegisterIdentityActivity.class), 1002);
                break;
            case R.id.tv_forget:
                startActivityForResult(new Intent(this, FindPasswordActivity.class), 1003);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1002:
//                    password = data.getStringExtra("password");
//                    phone = data.getStringExtra("phone");
//                    if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(phone)) {
//                        startRequestData(submitData);
//                    }
//                    break;
                case 1003:
                    password = data.getStringExtra("password");
                    phone = data.getStringExtra("phone");
                    if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(phone)) {
                        startRequestData(submitData);
                    }
                    break;
            }
        }
    }

}
