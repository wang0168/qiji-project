package tts.project.qiji.login;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.utils.LoginInfoSave;


public class FixPasswordActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.old_password)
    EditText oldPassword;
    @Bind(R.id.new_password)
    EditText newPassword;
    @Bind(R.id.confirmPassword)
    EditText confirmPassword;
    @Bind(R.id.action)
    Button action;
    private String title;
    private BarBean barBean;
    private String codeStr;
    private String mobileStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_password);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra("title");
        barBean = new BarBean().setMsg(title);
        barBean.setSubTitle("返回");
        codeStr = getIntent().getStringExtra("code");
        mobileStr = getIntent().getStringExtra("mobile");
        action.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action:
                if (TextUtils.isEmpty(oldPassword.getText().toString())) {
                    CustomUtils.showTipShort(this, "请输入原始密码");
                    return;
                }
                if (TextUtils.isEmpty(newPassword.getText().toString())) {
                    CustomUtils.showTipShort(this, "请输入新密码");
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword.getText().toString())) {
                    CustomUtils.showTipShort(this, "请再次输入新密码");
                    return;
                }
//                if (!password.getText().toString().matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$")) {
//                    CustomUtils.showTipShort(this, "密码不满足要求");
//                    return;
//                }
                if (!confirmPassword.getText().toString().equals(newPassword.getText().toString())) {
                    CustomUtils.showTipShort(this, "两次密码输入不一致，请重新输入后再次尝试");
                    return;
                } else {
                    startRequestData(submitData);
                }
                break;
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case submitData:
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("oldpwd", oldPassword.getText().toString());
                params.put("newpwd", newPassword.getText().toString());
                params.put("type", "2");
                getDataWithPost(submitData, Host.hostUrl + "api.php?m=Api&c=User&a=editpasswords", params);
                break;
            case getData:
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=User&a=getuser", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case submitData:
                startRequestData(getData);
                break;
            case getData:
                LoginInfoSave.loginOk(this, response);
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

}
