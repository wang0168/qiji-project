package tts.project.qiji.engineer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.moudle.AccountMoudle;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.AppManager;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.login.LoginActivity;


/**
 * Created by sjb on 2016/1/27.
 */
public class EngPersonalActivity extends BaseActivity {


    @Bind(R.id.left_title)
    TextView leftTitle;
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.iconImg)
    Button iconImg;
    @Bind(R.id.subTitle)
    TextView subTitle;
    @Bind(R.id.RLBtn)
    RelativeLayout RLBtn;
    @Bind(R.id.titleTxt)
    TextView titleTxt;
    @Bind(R.id.rightTxt)
    TextView rightTxt;
    @Bind(R.id.menuList)
    LinearLayout menuList;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.engineer_img)
    ImageView engineerImg;
    @Bind(R.id.engineer_name)
    TextView engineerName;
    @Bind(R.id.engineer_evaluate)
    RatingBar engineerEvaluate;
    @Bind(R.id.qqq)
    LinearLayout qqq;
    @Bind(R.id.engineer_phone)
    TextView engineerPhone;
    @Bind(R.id.title_lay)
    RelativeLayout titleLay;
    @Bind(R.id.personal_lay)
    RelativeLayout personalLay;
    @Bind(R.id.server_lay)
    RelativeLayout serverLay;
    @Bind(R.id.evaluate_lay)
    RelativeLayout evaluateLay;
    @Bind(R.id.engineer_notice)
    RelativeLayout engineerNotice;
    @Bind(R.id.evaluate_setting)
    RelativeLayout evaluateSetting;
    @Bind(R.id.exit_btn)
    TextView exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.engineer_personal_center);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("个人中心").setIsRemoveBack(true));
        MenuItemBean bean = new MenuItemBean();
        bean.setTitle("我的订单");
        bean.setTextColor(Color.parseColor("#f9bf54"));
        addMenu(bean);
        initData();
    }

    private void initData() {
        engineerEvaluate.setRating(Float.parseFloat(AccountMoudle.getInstance().getUserInfo().getXing()));
        Glide.with(this).load(AccountMoudle.getInstance().getUserInfo().getPhoto()).into(engineerImg);
        engineerName.setText(TextUtils.isEmpty(AccountMoudle.getInstance().getUserInfo().getNickname()) ? "" : AccountMoudle.getInstance().getUserInfo().getNickname());
        engineerPhone.setText(TextUtils.isEmpty(AccountMoudle.getInstance().getUserInfo().getMobile()) ? "" : AccountMoudle.getInstance().getUserInfo().getMobile());
    }

    @OnClick({R.id.exit_btn, R.id.engineer_img, R.id.personal_lay, R.id.server_lay, R.id.evaluate_lay, R.id.engineer_notice, R.id.evaluate_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.engineer_img:
                startActivity(new Intent(this, EngPersonalDataActivity.class));
                break;
            case R.id.personal_lay:
                startActivity(new Intent(this, EngPersonalDataActivity.class));
                break;
            case R.id.server_lay:
                break;
            case R.id.evaluate_lay:
                startActivity(new Intent(this, MyEvaluateActivity.class));
                break;
            case R.id.engineer_notice:
                break;
            case R.id.evaluate_setting:
                break;
            case R.id.exit_btn:
                new AlertDialog(this).builder().setTitle("提示")
                        .setMsg("是否确认退出")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppManager.getAppManager().finishAllActivity();
                                startActivity(new Intent(EngPersonalActivity.this, LoginActivity.class).putExtra("login", "1"));
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
                break;
        }
    }

    @Override
    protected void doMenu(MenuItemBean menuItemBean) {
        super.doMenu(menuItemBean);
        if (!"1".equals(AccountMoudle.getInstance().getUserInfo().getIs_shenhe())) {
            CustomUtils.showTipShort(this, "审核还未通过，无法接取订单");
            return;
        }
        startActivity(new Intent(this, EngineerOrderActivity.class));
    }
}
