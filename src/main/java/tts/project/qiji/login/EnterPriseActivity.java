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

public class EnterPriseActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.true_name)
    EditText trueName;
    @Bind(R.id.belong_enterprise)
    EditText belongEnterprise;
    @Bind(R.id.loginBtn)
    Button loginBtn;
    private String phone;
    private String yzm;
    private String password;
    private String type;
    private String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_prise);
        setTitle(new BarBean().setMsg("注册"));
        ButterKnife.bind(this);
        initIntent();
        loginBtn.setOnClickListener(this);
    }

    private void initIntent() {
        phone = getIntent().getStringExtra("phone");
        yzm = getIntent().getStringExtra("yzm");
        password = getIntent().getStringExtra("password");
        type = getIntent().getStringExtra("type");
        state = getIntent().getStringExtra("state");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                if (TextUtils.isEmpty(trueName.getText().toString().trim())) {
                    CustomUtils.showTipShort(this, "请填写真实姓名");
                    return;
                }
                if (TextUtils.isEmpty(belongEnterprise.getText().toString().trim())) {
                    CustomUtils.showTipShort(this, "请填写所属企业");
                    return;
                }
                startRequestData(submitData);
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
                params.put("phone", phone);
                params.put("yzm", yzm);
                params.put("password", password);
                params.put("type", type);
                params.put("state", state);
                params.put("name", trueName.getText().toString().trim());
                params.put("company", belongEnterprise.getText().toString().trim());
                getDataWithPost(submitData, Host.hostUrl + "api.php?m=Api&c=Login&a=register", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case submitData:
                CustomUtils.showTipShort(this, response);
                setResult(RESULT_OK);
                finish();
                break;

        }
    }
}
