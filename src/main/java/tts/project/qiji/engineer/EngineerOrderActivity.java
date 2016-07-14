package tts.project.qiji.engineer;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import tts.moudle.api.widget.TabManager;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.engineer.fragment.OrdersFragment;
import tts.project.qiji.engineer.fragment.TakeOrderFragment;
import tts.project.qiji.service.LocationService;
import tts.project.qiji.utils.Util;


/**
 * 工程师首页
 * Created by sjb on 2016/1/27.
 */
public class EngineerOrderActivity extends BaseActivity implements View.OnClickListener {
    public static TabHost mTabHost;
    private TabManager mTabManager;
    private ImageView engineer_personal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.engineer_order_activity);
        Intent serviceIntent = new Intent(this, LocationService.class);
        startService(serviceIntent);
//        ActivityManager activityManager = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(100);
        engineer_personal = (ImageView) findViewById(R.id.engineer_personal);
        engineer_personal.setOnClickListener(this);
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabManager = new TabManager(this, mTabHost, R.id.containertabcontent);
        mTabManager.addTab(mTabHost.newTabSpec("tab0").setIndicator("tab0"), OrdersFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("tab1").setIndicator("tab1"), TakeOrderFragment.class, null);
    }

    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.takeBtn:
                mTabHost.setCurrentTabByTag("tab0");
                break;
            case R.id.sendBtn:
                mTabHost.setCurrentTabByTag("tab1");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.engineer_personal:
                startActivity(new Intent(this, EngPersonalActivity.class));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Util.exit(this);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
