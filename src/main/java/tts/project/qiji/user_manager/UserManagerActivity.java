package tts.project.qiji.user_manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;

public class UserManagerActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded list;
    private View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);
        setTitle(new BarBean().setMsg("用户管理"));
        MenuItemBean menuItemBean = new MenuItemBean();
        menuItemBean.setIcon(R.mipmap.grzx_eng);
        findAllView();
    }

    private void findAllView() {
        header = LayoutInflater.from(this).inflate(R.layout.layout_header_manager, null, false);
        list = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.mlist);
        list.addHeader(header);
    }
}
