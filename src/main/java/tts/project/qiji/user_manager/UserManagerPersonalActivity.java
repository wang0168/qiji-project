package tts.project.qiji.user_manager;

import android.os.Bundle;

import tts.moudle.api.bean.BarBean;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;

public class UserManagerPersonalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager_personal);
        setTitle(new BarBean().setMsg("个人中心(用户管理员)"));
    }
}
