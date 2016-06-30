package tts.project.qiji.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;


/**
 * 注册时选择角色
 */
public class RegisterIdentityActivity extends BaseActivity implements OnClickListener {

    @Bind(R.id.nextBtn)
    Button nextBtn;
    @Bind(R.id.user)
    CheckBox user;
    @Bind(R.id.engineer)
    CheckBox engineer;

    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_identity);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("注册"));
        initData();
    }


    private void initData() {
        user.setOnClickListener(this);
        engineer.setOnClickListener(this);
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
                startActivityForResult(intent, 1001);
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user:
                if (user.isChecked()) {
                    type = "1";
                    engineer.setChecked(false);
                } else {
                    type = "";
                }
                break;
            case R.id.engineer:
                if (engineer.isChecked()) {
                    type = "2";
                    user.setChecked(false);
                } else {
                    type = "";
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
