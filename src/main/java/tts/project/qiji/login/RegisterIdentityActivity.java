package tts.project.qiji.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;


/**
 * 注册角色
 */
public class RegisterIdentityActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @Bind(R.id.nextBtn)
    Button nextBtn;
    @Bind(R.id.user)
    CheckBox user;
    @Bind(R.id.engineer)
    CheckBox engineer;

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_identity);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("注册"));
        initData();
    }


    private void initData() {
        user.setOnCheckedChangeListener(this);
        engineer.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.nextBtn})
    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.nextBtn:
                if (TextUtils.isEmpty(type)) {
                    CustomUtils.showTipShort(this, "请选择要注册的身份");
                    return;
                }
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.user:
                if (isChecked) {
                    engineer.setChecked(false);
                    type = "1";
                } else {
                    engineer.setChecked(true);
                    type = "2";
                }
                break;
            case R.id.engineer:
                if (isChecked) {
                    user.setChecked(false);
                    type = "2";
                } else {
                    user.setChecked(true);
                    type = "1";
                }
                break;
        }
    }
}
