package tts.project.qiji.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;

public class CallServiceActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_service);
        findAllView();
        adapter();
    }

    private void adapter() {
        mlist.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private void findAllView() {
        mlist = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.mlist);

    }
}
