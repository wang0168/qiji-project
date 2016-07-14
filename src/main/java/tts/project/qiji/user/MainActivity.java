package tts.project.qiji.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TabHost;

import de.greenrobot.event.EventBus;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.TTSRadioButton;
import tts.moudle.api.widget.TabManager;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.bean.EventBusBean;
import tts.project.qiji.bean.UserInfoBean;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.engineer.EngPersonalActivity;
import tts.project.qiji.login.LoginActivity;
import tts.project.qiji.service.LocationService;
import tts.project.qiji.user.fragment.HomeFragment;
import tts.project.qiji.user.fragment.MeFragment;
import tts.project.qiji.user.fragment.OrderFragment;
import tts.project.qiji.utils.Util;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static TabHost mTabHost;
    private TabManager mTabManager;
    private TTSRadioButton homeBtn, newsBtn, meBtn;
    private RadioGroup RGRadio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        Intent intent = new Intent(this, LocationService.class);
        stopService(intent);
        UserInfoBean userInfoBean = MyAccountMoudle.getInstance().getUserInfo();
        if ("2".equals(userInfoBean.getType()) && "1".equals(userInfoBean.getState())) {
            startActivity(new Intent(this, EngPersonalActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
            return;
        }
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabManager = new TabManager(this, mTabHost, R.id.containertabcontent);
        mTabManager.addTab(mTabHost.newTabSpec("tab0").setIndicator("tab0"), HomeFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("tab1").setIndicator("tab1"), OrderFragment.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("tab2").setIndicator("tab2"), MeFragment.class, null);
        findAllView();
    }

    private void findAllView() {
        homeBtn = (TTSRadioButton) findViewById(R.id.rb_home_bottombar_home);
        homeBtn.setOnClickListener(this);
        newsBtn = (TTSRadioButton) findViewById(R.id.rb_home_bottombar_order);
        newsBtn.setOnClickListener(this);
        meBtn = (TTSRadioButton) findViewById(R.id.rb_home_bottombar_my);
        meBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_home_bottombar_home:
                mTabHost.setCurrentTabByTag("tab0");
                break;
            case R.id.rb_home_bottombar_order:
                if (!TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getToken())) {
                    mTabHost.setCurrentTabByTag("tab1");
                } else {
                    homeBtn.setChecked(true);
                    mTabHost.setCurrentTabByTag("tab0");
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;

            case R.id.rb_home_bottombar_my:
                if (!TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getToken())) {
                    mTabHost.setCurrentTabByTag("tab2");
                } else {
                    homeBtn.setChecked(true);
                    mTabHost.setCurrentTabByTag("tab0");
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        changePage(intent);
    }

    public void changePage(Intent intent) {
        if ("1".equals(intent.getStringExtra("init"))) {
            mTabHost.setCurrentTabByTag("tab0");
            homeBtn.setChecked(true);
        }
        if ("2".equals(intent.getStringExtra("init"))) {
            mTabHost.setCurrentTabByTag("tab1");
            newsBtn.setChecked(true);
        }

        if ("3".equals(intent.getStringExtra("init"))) {
            mTabHost.setCurrentTabByTag("tab2");
            meBtn.setChecked(true);
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

//    private long exitTime = 0;
//
//    public void exit() {
//        if ((System.currentTimeMillis() - exitTime) > 2000) {
//            Toast.makeText(this, "再按一次退出程序",
//                    Toast.LENGTH_SHORT).show();
//            exitTime = System.currentTimeMillis();
//        } else {
//            AppManager.getAppManager().AppExit(this);
//        }
//    }


    public void onEventMainThread(EventBusBean eventBusBean) {
        if ("0".equals(eventBusBean.getHomePage())) {
            mTabHost.setCurrentTabByTag("tab0");
            homeBtn.setChecked(true);
        }
        if ("1".equals(eventBusBean.getHomePage())) {
            mTabHost.setCurrentTabByTag("tab1");
            newsBtn.setChecked(true);
        }
        if ("2".equals(eventBusBean.getHomePage())) {
            mTabHost.setCurrentTabByTag("tab2");
            meBtn.setChecked(true);
        }

    }

//    @Override
//    protected void startApplyPermissions(int index) {
//        super.startApplyPermissions(index);
//        String string = "111111";
//        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "15179165382")));
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String str = "l";
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
