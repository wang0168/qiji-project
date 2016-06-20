package tts.project.qiji.activity;

import android.os.Bundle;

import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.adapter.ServiceTypeAdapter;

public class ServiceTypeActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_type);
        setTitle(new BarBean().setMsg("服务类型"));
        addMenu(new MenuItemBean().setTitle("确定"));
        findAllView();
        adapter();
    }

    private void adapter() {
        ServiceTypeAdapter serviceTypeAdapter = new ServiceTypeAdapter(this, null);
        list.setAdapter(serviceTypeAdapter);
    }

    private void findAllView() {
        list = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.mlist);
    }
}
