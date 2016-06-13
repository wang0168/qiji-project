package tts.project.qiji.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.moudle.AccountMoudle;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.AppManager;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.BaseApplication;
import tts.project.qiji.MainActivity;
import tts.project.qiji.R;
import tts.project.qiji.bean.UserInfoBean;

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
        password = getIntent().getStringExtra("password");
        phone = getIntent().getStringExtra("phone");
        if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(phone)) {
            startRequestData(submitData);
        }
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
                params = new HashMap<String, String>();
                params.put("phone", ETMobile.getText().toString().trim());
                params.put("password", ETPassword.getText().toString().trim());
                getDataWithPost(login_ok, Host.hostUrl + "api.php?m=Api&c=Login&a=login", params);
                break;
            case submitData:
                params = new HashMap<String, String>();
                params.put("phone", phone);
                params.put("password",password);
                getDataWithPost(login_ok, Host.hostUrl + "api.php?m=Api&c=Login&a=login", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case login_ok:
                editor.putBoolean("IsLogin", true);
                editor.putString("info", response);
                editor.commit();
                UserInfoBean userInfo = new Gson().fromJson(sp.getString("info", ""), UserInfoBean.class);
//                userInfo.setIsLogin(true);
//                AccountMoudle.getInstance().setUserInfo(userInfo);
//                if ("1".equals(AccountMoudle.getInstance().getUserInfo().getIs_type())) {
//                    if ("1".equals(getIntent().getStringExtra("login"))) {
//                        startActivity(new Intent(this, MainActivity.class));
//                    } else {
//                        setResult(RESULT_OK);
//                    }
//                } else {
//                    AppManager.getAppManager().finishAllActivity();
//                    startActivity(new Intent(this, tts.project.hoop.engineer.EngPersonalActivity.class));
//                    BaseApplication.sendLocation();
//                }
                finish();
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
                startActivity(new Intent(this, RegisterIdentityActivity.class));
                break;
            case R.id.tv_forget:
                startActivity(new Intent(this, FindPasswordActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
