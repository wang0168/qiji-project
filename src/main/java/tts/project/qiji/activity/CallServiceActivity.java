package tts.project.qiji.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import tts.moudle.api.bean.BarBean;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.adapter.CallServiceAdapter;

/**
 * 呼叫服务
 */
public class CallServiceActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded mlist;
    private View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_service);
        setTitle(new BarBean().setMsg("呼叫服务"));
        findAllView();
        adapter();
    }

    private void adapter() {
        mlist.setLayoutManager(new GridLayoutManager(this, 3));
        CallServiceAdapter callServiceAdapter = new CallServiceAdapter(this, null);
        mlist.setAdapter(callServiceAdapter);

    }

    private void findAllView() {
        header = LayoutInflater.from(this).inflate(R.layout.layout_call_service_header, null, false);
        mlist = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.mlist);
        mlist.addHeader(header);
    }
}
