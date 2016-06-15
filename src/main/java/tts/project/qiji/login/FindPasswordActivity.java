package tts.project.qiji.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;


/**
 * 一键预约
 */
public class FindPasswordActivity extends BaseActivity {
    @Bind(R.id.ETMobile)
    EditText ETMobile;
    @Bind(R.id.verification)
    EditText verification;
    @Bind(R.id.ETPassword)
    EditText ETPassword;
    @Bind(R.id.register_obtain)
    Button register_obtain;
    @Bind(R.id.trueBtn)
    Button trueBtn;
    private int count = 60;
    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            count--;
            register_obtain.setClickable(false);
            register_obtain.setText(count + "秒后重新发送");
        }

        @Override
        public void onFinish() {
            count = 60;
            register_obtain.setClickable(true);
            register_obtain.setText("获取验证码");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("找回密码"));
    }

    @OnClick({R.id.trueBtn, R.id.register_obtain})
    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.trueBtn:
                if (!TextUtils.isMobileNO(ETMobile.getText().toString().trim())) {
                    CustomUtils.showTipShort(this, "请输入正确的手机号码");
                    return;
                }
                if (TextUtils.isEmpty(verification.getText().toString().trim())) {
                    CustomUtils.showTipShort(this, "请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(ETPassword.getText().toString().trim())) {
                    CustomUtils.showTipShort(this, "请输入密码");
                    return;
                }
//                startRequestData(Constant.findpsd_ok);
                break;
            case R.id.register_obtain:
                if (!TextUtils.isMobileNO(ETMobile.getText().toString().trim())) {
                    CustomUtils.showTipShort(this, "请输入正确的手机号码");
                    return;
                }
                timer.start();
//                startRequestData(Constant.verification_ok);
                break;
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
//            case Constant.findpsd_ok:
//                params = new HashMap<String, String>();
//                params.put("phone", ETMobile.getText().toString().trim());
//                params.put("yzm", verification.getText().toString().trim());
//                params.put("password", ETPassword.getText().toString().trim());
//                getDataWithPost(Constant.findpsd_ok, Host.hostUrl + "api.php?m=Api&c=Login&a=forgetpwd", params);
//                break;
//            case Constant.verification_ok:
//                getDataWithGet(Constant.verification_ok, Host.hostUrl + "api.php/Login/sendSMS" + "?mobile=" + ETMobile.getText().toString().trim() + "&type=2");
//                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
//            case Constant.findpsd_ok:
//                CustomUtils.showTipShort(this, response);
//                finish();
//                break;
//            case Constant.verification_ok:
//                CustomUtils.showTipShort(this, "验证码发送成功");
//                break;
        }
    }

}
