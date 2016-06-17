package tts.project.qiji.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.orhanobut.logger.Logger;
import com.pingplusplus.android.Pingpp;
import com.pingplusplus.android.PingppLog;

import java.util.HashMap;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;

/**
 * 支付界面
 */
public class PayActivity extends BaseActivity implements View.OnClickListener {
    private CheckBox mWeixin, mZhifubao;
    private LinearLayout pay_weixin_lay, pay_zhifubao_lay;
    private Button action_pay;
    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private String order_ids = "";
    private final int pay_ok = 1012;
    private final int init_ok = 1013;
    private final int order_ok = 1011;
    private final int aecharge_ok = 1155;
    private String paytype;
    private int from;//1,购物车,2其他单个订单的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        from = getIntent().getIntExtra("from", 0);
        setTitle("请选择支付方式");
        getData();
        findAllView();
        PingppLog.DEBUG = true;
    }

    private void getData() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("order_ids"))) {
            order_ids = getIntent().getStringExtra("order_ids");
        }

    }

    private void findAllView() {
        pay_weixin_lay = (LinearLayout) findViewById(R.id.pay_weixin_lay);
        pay_zhifubao_lay = (LinearLayout) findViewById(R.id.pay_zhifubao_lay);
        pay_weixin_lay.setOnClickListener(this);
        pay_zhifubao_lay.setOnClickListener(this);
        mWeixin = (CheckBox) findViewById(R.id.pay_weixin);
        mZhifubao = (CheckBox) findViewById(R.id.pay_zhifubao);
        action_pay = (Button) findViewById(R.id.action_pay);
        action_pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.pay_affirm:
//               mAffirm.setOnClickListener(null);
//                if (order_ids != null) {
//                    startRequestData(order_ok);
//                    mProgressDialog.show();
//                }
//              else if (aecharge != null) {
//                   startRequestData(aecharge_ok);
//              }
//                break;
            case R.id.pay_weixin_lay:
                paytype = CHANNEL_WECHAT;
                mWeixin.setChecked(true);
                mZhifubao.setChecked(false);

                break;
            case R.id.pay_zhifubao_lay:
                paytype = CHANNEL_ALIPAY;
                mWeixin.setChecked(false);
                mZhifubao.setChecked(true);

                break;
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> paramts;
        switch (index) {
            case order_ok:
                paramts = new HashMap<>();
                paramts.put("order_ids", order_ids);
//                paramts.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id() + "");
//                paramts.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                paramts.put("channel", paytype);
                getDataWithPost(order_ok, Host.hostUrl + "orderInterface.api?payOrder", paramts);
                break;

        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case order_ok:
                if (null == response) {
                    CustomUtils.showTipShort(this, "URL无法获取charge");
                    return;
                }
                Logger.wtf(response);
                Pingpp.createPayment(PayActivity.this, response);
                break;
        }
    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        CustomUtils.showTipLong(this, response);
        mProgressDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            String result = data.getExtras().getString("pay_result");
            /* 处理返回值
             * "success" - payment succeed
             * "fail"    - payment failed
             * "cancel"  - user canceld
             * "invalid" - payment plugin not installed
             *
             */
            String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
            String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
            System.out.println("======resultMsg" + result + "==errorMsg" + errorMsg + "==extraMsg" + extraMsg);
//                showMsg(result, errorMsg, extraMsg);
//            if ("success".equals(result)) {
//                startActivity(new Intent(this, OrderListActivity.class).putExtra("title", "待发货").putExtra("order_state", "wait_send"));
//                setResult(RESULT_OK);
//            } else if ("fail".equals(result)) {
//                CustomUtils.showTipLong(this, "付款失败，请重试");
//                if (from == 1) {
//                    startActivity(new Intent(this, OrderListActivity.class).putExtra("title", "待付款").putExtra("order_state", "wait_pay"));
//                } else if (from == 2) {
//                    startActivity(new Intent(this, OrderDetailActivity.class).putExtra("order_id", order_ids));
//                } else {
//                    CustomUtils.showTipShort(this, "数据错误");
//                }
//                setResult(RESULT_OK);
//            } else if ("cancel".equals(result)) {
//                if (from == 1) {
//                    startActivity(new Intent(this, OrderListActivity.class).putExtra("title", "待付款").putExtra("order_state", "wait_pay"));
//                } else if (from == 2) {
//                    startActivity(new Intent(this, OrderDetailActivity.class).putExtra("order_id", order_ids));
//
//                } else {
//                    CustomUtils.showTipShort(this, "数据错误");
//                }
//                setResult(RESULT_OK);
//            }

            finish();
        }
    }
}
