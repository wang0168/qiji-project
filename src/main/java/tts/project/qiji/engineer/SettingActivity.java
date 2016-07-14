package tts.project.qiji.engineer;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.activity.AboutActivity;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.moudle.AccountMoudle;
import tts.moudle.api.moudle.SharedPreferencesMoudle;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.user.FeedBackActivity;
import tts.project.qiji.user.MainActivity;
import tts.project.qiji.R;
import tts.project.qiji.adapter.MeItemAdapter;
import tts.project.qiji.bean.MeItemBean;
import tts.project.qiji.common.MyAccountMoudle;


/**
 * 师傅端设置页面
 * Created by wql on 2016/6/17.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {


    private TextView exit_btn;
    private RecyclerViewAutoRefreshUpgraded mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_setting);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("设置"));
        findAllView();
        adapter();
    }

    private void adapter() {
        mList.setLayoutManager(new LinearLayoutManager(this));
//        mList.addItemDecoration(new RecyclerViewGridItemDecoration(getActivity()));
        final List<MeItemBean> data = new ArrayList<>();
        data.add(new MeItemBean(0, "意见反馈", "", true, true, false));
        data.add(new MeItemBean(0, "关于我们", "", true, true, false));
        data.add(new MeItemBean(0, "推荐给朋友", "", true, false, true));
        data.add(new MeItemBean(0, "清理缓存", "", true, true, false));
        data.add(new MeItemBean(0, "服务协议条款", "", true, false, false));
        MeItemAdapter adapter = new MeItemAdapter(this, data);
        adapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                if ("意见反馈".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(SettingActivity.this, FeedBackActivity.class).putExtra("from", "2"));
                } else if ("关于我们".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(SettingActivity.this, AboutActivity.class).putExtra("url",
                            Host.hostUrl + "api.php?m=Api&c=Index&a=about_us"));
                } else if ("推荐给朋友".equals(data.get(position).getItem_name())) {
                    showShare();
//                    startActivity(new Intent(SettingActivity.this, MyEvaluateActivity.class));
                } else if ("清理缓存".equals(data.get(position).getItem_name())) {
//                    startActivity(new Intent(SettingActivity.this, MyEvaluateActivity.class));
                } else if ("服务协议条款".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(SettingActivity.this, AboutActivity.class).putExtra("url",
                            Host.hostUrl + "api.php?m=Api&c=Index&a=clause").putExtra("title", "服务条款协议"));
                }
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
        mList.setAdapter(adapter);
    }

    private void findAllView() {
        mList = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.mlist);
        exit_btn = (TextView) findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(this);
    }

    @Override
    protected void doMenu(MenuItemBean menuItemBean) {
        super.doMenu(menuItemBean);
//        if (!"1".equals(AccountMoudle.getInstance().getUserInfo().getIs_shenhe())) {
//            CustomUtils.showTipShort(this, "审核还未通过，无法接取订单");
//            return;
//        }
        startActivity(new Intent(this, EngineerOrderActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_btn:
                SharedPreferencesMoudle.getInstance().setJson(this
                        .getApplicationContext
                                (), "user_login", "");
                MyAccountMoudle.getInstance().setUserInfo(null);
                AccountMoudle.getInstance().setUserBean(null);
                if (TextUtils.isEmpty(SharedPreferencesMoudle.getInstance().getJson(this
                        .getApplicationContext(), "user_login"))) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("init", "1");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
//                    Logger.wtf(SharedPreferencesMoudle.getInstance().getJson(this
//                            .getApplicationContext(), "user_login"));

                }
                break;
        }
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("分享");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        //        Bitmap enableLogo = BitmapFactory.decodeResource(context.getResources(), R.drawable.ssdk_oks_logo_qq);
        final String label = "复制链接";
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager)SettingActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(label);
            }
        };
        oks.setCustomerLogo(null, label, listener);
        // 启动分享GUI
        oks.show(this);
    }
}
