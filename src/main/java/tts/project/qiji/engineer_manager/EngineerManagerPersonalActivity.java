package tts.project.qiji.engineer_manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import tts.moudle.api.bean.BarBean;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;

public class EngineerManagerPersonalActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded list;
    private View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_manager_personal);
        setTitle(new BarBean().setMsg("个人中心(工程师管理员)"));
        findAllView();
    }

    private void findAllView() {
        header = LayoutInflater.from(this).inflate(R.layout.layout_header_manager, null, false);
        list = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.mlist);
        list.addHeader(header);
    }
}
