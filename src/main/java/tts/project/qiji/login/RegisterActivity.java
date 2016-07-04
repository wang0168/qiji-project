package tts.project.qiji.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
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


/**
 * 注册
 */
public class RegisterActivity extends BaseActivity {
    @Bind(R.id.ETMobile)
    EditText ETMobile;
    @Bind(R.id.verification)
    EditText verification;
    @Bind(R.id.ETPassword)
    EditText ETPassword;
    @Bind(R.id.register_obtain)
    Button register_obtain;
    @Bind(R.id.loginBtn)
    Button loginBtn;

    @Bind(R.id.nickname)
    EditText nickname;
    @Bind(R.id.sign_type)
    TextView signType;
    private String type = "";
    private String state = "";
    private int count = 60;
    private final int verification_ok = 101;
    private final int register_ok = 102;
    //    private String nicknameStr = "";
    private OptionsPickerView pvOptions;
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
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("注册"));
        initData();
        ETMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ETMobile.getText().length() == 11) {
                    register_obtain.setClickable(true);
                    register_obtain.setBackgroundResource(R.drawable.shape_button_corner5_coloraccent);
                } else {
                    register_obtain.setBackgroundResource(R.drawable.shape_button_corner5_color_gray);
                }
            }
        });
        signType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvOptions = new OptionsPickerView(RegisterActivity.this);
                pvOptions.setTitle("选择类型");
                final ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add("企业");
                arrayList.add("个人");
                pvOptions.setPicker(arrayList);
                pvOptions.setCyclic(false);
                pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3) {
                        signType.setText(arrayList.get(options1));
                        if ("企业".equals(signType)) {
                            state = "2";
                        } else {
                            state = "1";
                        }
                        startRequestData(submitData);
//                            baseInfoItemBeans.get(position).setContext(arrayList.get(options1));
//                            edit_picker.setText(arrayList.get(options1));
                    }
                });
                pvOptions.show();
            }
        });
    }


    private void initData() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("type"))) {
            type = getIntent().getStringExtra("type");
        } else {
            CustomUtils.showTipShort(this, "type==null");
        }
    }

    @OnClick({R.id.loginBtn, R.id.register_obtain})
    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
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
                if (TextUtils.isEmpty(state)) {
                    CustomUtils.showTipShort(this, "请选择账户类型");
                    return;
                }
//                if (!TextUtils.isEmpty(nickname.getText().toString().trim())) {
//                    nicknameStr = nickname.getText().toString().trim();
//
//                }
                if ("1".equals(state)) {
                    startRequestData(register_ok);
                } else if ("2".equals(state)) {
                    Intent intent = new Intent(this, EnterPriseActivity.class);
                    intent.putExtra("phone", ETMobile.getText().toString().trim());
                    intent.putExtra("yzm", verification.getText().toString().trim());
                    intent.putExtra("password", ETPassword.getText().toString().trim());
                    intent.putExtra("type", type);
//                    intent.putExtra("nickname", nicknameStr);
                    intent.putExtra("state", "2");
                    startActivityForResult(intent, 1002);
                }

                break;
            case R.id.register_obtain:
                if (!TextUtils.isMobileNO(ETMobile.getText().toString().trim())) {
                    CustomUtils.showTipShort(this, "请输入正确的手机号码");
                    return;
                }
                timer.start();
                startRequestData(verification_ok);
                break;

        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case register_ok:
                params = new ArrayMap<>();
                params.put("phone", ETMobile.getText().toString().trim());
                params.put("yzm", verification.getText().toString().trim());
                params.put("password", ETPassword.getText().toString().trim());
                params.put("type", type);
//                params.put("nickname", nicknameStr);
                params.put("state", "1");
                getDataWithPost(register_ok, Host.hostUrl + "api.php?m=Api&c=Login&a=register", params);
                break;
            case verification_ok:
                params = new ArrayMap<>();
                params.put("mobile", ETMobile.getText().toString().trim());
                params.put("type", "1");
                getDataWithPost(verification_ok, Host.hostUrl + "api.php?m=Api&c=Login&a=sendSMS", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case register_ok:
                CustomUtils.showTipShort(this, response);
                Intent intent = new Intent();
                intent.putExtra("password", ETPassword.getText().toString().trim());
                intent.putExtra("phone", ETMobile.getText().toString().trim());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case verification_ok:
                CustomUtils.showTipLong(this, "验证码发送成功==" + response);
                break;
        }

    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        Logger.json(response);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1002:
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("password", ETPassword.getText().toString().trim());
                    intent.putExtra("phone", ETMobile.getText().toString().trim());
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
            }
        }
    }
}
