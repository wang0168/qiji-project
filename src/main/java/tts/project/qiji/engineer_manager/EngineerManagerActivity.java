package tts.project.qiji.engineer_manager;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.adapter.UserItemAdapter;

public class EngineerManagerActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded list;
    private View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_manager);
        setTitle(new BarBean().setMsg("工程师管理").setIsRemoveBack(true));
        MenuItemBean menuItemBean = new MenuItemBean();
        menuItemBean.setIcon(R.mipmap.grzx_eng);
        addMenu(menuItemBean);
        findAllView();
        adapter();
    }

    private void adapter() {
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new UserItemAdapter(this, null));
    }

    private void findAllView() {
        header = LayoutInflater.from(this).inflate(R.layout.layout_header_manager, null, false);
        list = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.mlist);
        list.addHeader(header);
    }
}
