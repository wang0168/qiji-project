package tts.project.qiji;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import tts.project.qiji.bean.UserInfoBean;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.engineer.EngPersonalActivity;
import tts.project.qiji.engineer.EngineerOrderActivity;
import tts.project.qiji.engineer_manager.EngineerManagerActivity;
import tts.project.qiji.user.MainActivity;
import tts.project.qiji.user_manager.UserManagerActivity;


/**
 * 启动页
 * Created by chen on 2016/3/15.
 */
public class SplashActivity extends BaseActivity implements Animation.AnimationListener {
    private Animation alphaAnimation;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        imageView = (ImageView) findViewById(R.id.welcome);
        init();
    }

    private void init() {
        alphaAnimation = AnimationUtils.loadAnimation(this,
                R.anim.welcome_alpha);
        alphaAnimation.setFillEnabled(true);
        alphaAnimation.setFillAfter(true);
        imageView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(this);

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        UserInfoBean userInfoBean = MyAccountMoudle.getInstance().getUserInfo();
        SharedPreferences sp = this.getSharedPreferences("isFirst", Context.MODE_PRIVATE);
//        if (sp.getBoolean("IsFirst0", false)) {
        if ("1".equals(userInfoBean.getType())) {//用户端
            startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        } else if ("2".equals(MyAccountMoudle.getInstance().getUserInfo().getType())) {//工程师端
            Intent intent;
            if ("1".equals(MyAccountMoudle.getInstance().getUserInfo().getShenhe())) {
                intent = new Intent(this, EngineerOrderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else {
                intent = new Intent(this, EngPersonalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
            startActivity(intent);
            finish();
        } else if ("3".equals(MyAccountMoudle.getInstance().getUserInfo().getType())) {//用户管理
            Intent intent = new Intent(this, UserManagerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if ("4".equals(MyAccountMoudle.getInstance().getUserInfo().getType())) {//工程师管理
            Intent intent = new Intent(this, EngineerManagerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }


//        startActivity(new Intent(this, MainActivity.class));
//        } else {
//            startActivity(new Intent(this, GuidePageActivity.class));

//        }
//        finish();
    }


    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
