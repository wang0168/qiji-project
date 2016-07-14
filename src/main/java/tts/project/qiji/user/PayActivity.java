package tts.project.qiji.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.pingplusplus.android.Pingpp;
import com.pingplusplus.android.PingppLog;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import tts.moudle.api.Host;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.bean.EventBusBean;
import tts.project.qiji.common.MyAccountMoudle;

/**
 * 支付界面
 */
public class PayActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private String order_number = "";
    private String order_id = "";
    private final int order_ok = 1011;
    private String paytype;
//    private int from;//1,购物车,2其他单个订单的

    private CheckBox mWeixin, mZhifubao, fapiao_header;
    private LinearLayout pay_weixin_lay, pay_zhifubao_lay;
    private Button action_pay;
    private EditText edit_fapiao_header;
    private TextView tv_total_money;
    private TextView tv_pay_money;
    private RelativeLayout layout_coupon;
    private String headerStr;
    private String money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
//        from = getIntent().getIntExtra("from", 0);
        setTitle("请选择支付方式");
        getData();
        findAllView();
        bindData();
        PingppLog.DEBUG = true;
    }

    private void bindData() {
        tv_total_money.setText(money + "");
        tv_pay_money.setText(money + "");
    }

    private void getData() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("order_number"))) {
            order_number = getIntent().getStringExtra("order_number");
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("money"))) {
            money = getIntent().getStringExtra("money");
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("order_id"))) {
            order_id = getIntent().getStringExtra("order_id");
        }

    }

    private void findAllView() {
        pay_weixin_lay = (LinearLayout) findViewById(R.id.pay_weixin_lay);
        pay_zhifubao_lay = (LinearLayout) findViewById(R.id.pay_zhifubao_lay);
        pay_weixin_lay.setOnClickListener(this);
        pay_zhifubao_lay.setOnClickListener(this);
        mWeixin = (CheckBox) findViewById(R.id.pay_weixin);
        mZhifubao = (CheckBox) findViewById(R.id.pay_zhifubao);
        fapiao_header = (CheckBox) findViewById(R.id.fapiao_header);
        action_pay = (Button) findViewById(R.id.action_pay);
        edit_fapiao_header = (EditText) findViewById(R.id.edit_fapiao_header);
        tv_total_money = (TextView) findViewById(R.id.tv_total_money);
        tv_pay_money = (TextView) findViewById(R.id.tv_pay_money);
        action_pay.setOnClickListener(this);
        fapiao_header.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fapiao_header:
                if (fapiao_header.isChecked()) {
                    headerStr = edit_fapiao_header.getText().toString().trim();
                }
                break;
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
            case R.id.action_pay:
                showTipMsg("请求支付中。。。");
                startRequestData(order_ok);
                break;
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case order_ok:
                params = new HashMap<>();
                params.put("order_number", order_number);
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                if (paytype == null) {
                    CustomUtils.showTipShort(this, "请选择支付方式");
                    return;
                }
                params.put("type", paytype);
                if (headerStr != null) {
                    params.put("invoice_header", headerStr);
                }
//                params.put("coupon_id", "");
                getDataWithPost(order_ok, Host.hostUrl + "api.php?m=Api&c=Pingxx&a=ping", params);
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
                mProgressDialog.dismiss();
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
            if ("success".equals(result)) {
                startActivityForResult(new Intent(this, ServiceConfirmActivity.class).putExtra("order_id", order_id), 10001);
//                EventBus.getDefault().post(new EventBusBean().setRefresh(true).setUserOrderPage("4"));
                setResult(RESULT_OK);
                finish();
            } else if ("fail".equals(result)) {
                CustomUtils.showTipLong(this, "付款失败，请重试");
                EventBus.getDefault().post(new EventBusBean().setRefresh(true));
//                if (from == 1) {
//                    startActivity(new Intent(this, OrderListActivity.class).putExtra("title", "待付款").putExtra("order_state", "wait_pay"));
//                } else if (from == 2) {
//                    startActivity(new Intent(this, OrderDetailActivity.class).putExtra("order_id", order_number));
//                } else {
//                    CustomUtils.showTipShort(this, "数据错误");
//                }
                setResult(RESULT_OK);
                finish();
            } else if ("cancel".equals(result)) {
                CustomUtils.showTipLong(this, "付款已取消");
                EventBus.getDefault().post(new EventBusBean().setRefresh(true));
//                if (from == 1) {
//                    startActivity(new Intent(this, OrderListActivity.class).putExtra("title", "待付款").putExtra("order_state", "wait_pay"));
//                } else if (from == 2) {
//                    startActivity(new Intent(this, OrderDetailActivity.class).putExtra("order_id", order_number));
//
//                } else {
//                    CustomUtils.showTipShort(this, "数据错误");
//                }
//                setResult(RESULT_OK);
            }


        }
    }
}
