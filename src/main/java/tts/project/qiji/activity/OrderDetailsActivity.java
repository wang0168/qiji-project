package tts.project.qiji.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.bean.OrderBean;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.utils.ImageLoader;


/**
 * 订单详情
 */
public class OrderDetailsActivity extends BaseActivity {


    @Bind(R.id.order_state)
    TextView orderState;
    @Bind(R.id.order_num)
    TextView orderNum;
    @Bind(R.id.order_time)
    TextView orderTime;
    @Bind(R.id.layout_type)
    LinearLayout layoutType;
    @Bind(R.id.service_desc)
    TextView serviceDesc;
    @Bind(R.id.pic_list)
    RecyclerView picList;
    @Bind(R.id.serivce_time)
    TextView serivceTime;
    @Bind(R.id.serivce_address)
    TextView serivceAddress;
    @Bind(R.id.server_cost)
    TextView serverCost;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.layout_user)
    RelativeLayout layoutUser;
    @Bind(R.id.eng_face)
    ImageView engFace;
    @Bind(R.id.eng_name)
    TextView engName;
    @Bind(R.id.eng_phone)
    RatingBar engPhone;
    @Bind(R.id.layout_engineer)
    RelativeLayout layoutEngineer;
    @Bind(R.id.layout_engineer_action)
    LinearLayout layoutEngineerAction;
    @Bind(R.id.layout_engineer_outer)
    RelativeLayout layoutEngineerOuter;
    @Bind(R.id.call_customer_service)
    TextView callCustomerService;
    @Bind(R.id.orders)
    TextView orders;
    @Bind(R.id.navigation)
    TextView navigation;
    @Bind(R.id.cancel_order_outer)
    TextView cancelOrderOuter;
    @Bind(R.id.modify_order_outer)
    TextView modifyOrderOuter;
    @Bind(R.id.hurry_order_outer)
    TextView hurryOrderOuter;
    @Bind(R.id.confirm_service_outer)
    TextView confirmServiceOuter;
    @Bind(R.id.layout_order_action)
    LinearLayout layoutOrderAction;
    private OrderBean orderBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_order);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("订单详情"));

        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        picList.setLayoutManager(layout);
        startRequestData(getData);
    }


    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                params = new HashMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("order_id", getIntent().getStringExtra("order_id"));
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Order&a=orderdetails", params);
                break;
            case submitData:
//                params = new HashMap<>();
//                params.put("member_id", AccountMoudle.getInstance().getUserInfo().getMember_id());
//                params.put("token", AccountMoudle.getInstance().getUserInfo().getMem_token());
//                params.put("order_id", getIntent().getStringExtra("order_id"));
//                getDataWithPost(submitData, Host.hostUrl + "api.php?m=Api&c=Engineer&a=ordertrue", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                orderBean = new Gson().fromJson(response, OrderBean.class);
                if (orderBean != null) {
                    bindData(orderBean);
                }
                break;
            case submitData:
                startRequestData(getData);
                CustomUtils.showTipShort(this, "接单成功");
                break;
        }
    }

    private void bindData(OrderBean orderBean) {
        String orderStatusStr = "";
        if (!TextUtils.isEmpty(orderBean.getStatus())) {
            switch (orderBean.getStatus()) {
                case "1":
                    orderStatusStr = "待指派工程师";
                    callCustomerService.setVisibility(View.VISIBLE);
                    hurryOrderOuter.setVisibility(View.VISIBLE);
                    cancelOrderOuter.setVisibility(View.VISIBLE);
                    modifyOrderOuter.setVisibility(View.VISIBLE);
                    break;
                case "2":
                    orderStatusStr = "等待上门服务";
                    callCustomerService.setVisibility(View.VISIBLE);
                    cancelOrderOuter.setVisibility(View.VISIBLE);
                    modifyOrderOuter.setVisibility(View.VISIBLE);
                    layoutEngineerOuter.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(orderBean.getEngineer().getImg() + "")) {
                        ImageLoader.load(this, orderBean.getEngineer().getImg() + "", engFace);
                    }
                    break;
                case "3":
                    orderStatusStr = "待确认服务";
                    callCustomerService.setVisibility(View.VISIBLE);
                    confirmServiceOuter.setVisibility(View.VISIBLE);
                    break;
                case "4":
                    orderStatusStr = "订单已完成";
                    callCustomerService.setVisibility(View.VISIBLE);
                    break;
            }
        }
        orderState.setText("订单状态:" + orderStatusStr);
        if (!TextUtils.isEmpty(orderBean.getOrder_number())) {
            orderNum.setText(orderBean.getOrder_number());
        }
        if (!TextUtils.isEmpty(orderBean.getDate())) {
            orderTime.setText(orderBean.getDate());
        }
        if (!TextUtils.isEmpty(orderBean.getDis())) {
            serviceDesc.setText(orderBean.getDis());
        }
        if (!TextUtils.isEmpty(orderBean.getFuwu_time())) {
            serivceTime.setText(orderBean.getFuwu_time());
        }

        String addressStr = "";
        if (!TextUtils.isEmpty(orderBean.getAddress()) && !TextUtils.isEmpty(orderBean.getProvince())) {
            addressStr = orderBean.getProvince() + orderBean.getCity() + orderBean.getArea() + orderBean.getAddress();
        }
        serivceAddress.setText(addressStr);

        if (!TextUtils.isEmpty(orderBean.getName())) {
            tvName.setText(orderBean.getName());
        }
        if (!TextUtils.isEmpty(orderBean.getPhone())) {
            tvPhone.setText(orderBean.getName());
        }
    }

    @OnClick({R.id.call_customer_service, R.id.orders, R.id.navigation, R.id.cancel_order_outer, R.id.confirm_service_outer, R.id.modify_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_customer_service:
                break;
            case R.id.orders:
                break;
            case R.id.navigation:
                break;
            case R.id.cancel_order_outer:
                break;
            case R.id.confirm_service_outer:
                break;
            case R.id.modify_order:
                break;
        }
    }


}
