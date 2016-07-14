package tts.project.qiji.engineer;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.ImgBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.adapter.LargePicShowAdapter;
import tts.project.qiji.bean.EventBusBean;
import tts.project.qiji.bean.OrderBean;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.utils.Util;


/**
 * 订单详情
 */
public class EngOrderDetailsActivity extends BaseActivity {


    @Bind(R.id.tv_state_outer)
    TextView tvStateOuter;
    @Bind(R.id.order_num)
    TextView orderNum;
    @Bind(R.id.create_time)
    TextView createTime;
    @Bind(R.id.type_one)
    TextView typeOne;
    @Bind(R.id.type_two)
    TextView typeTwo;
    @Bind(R.id.type_three)
    TextView typeThree;
    @Bind(R.id.layout_type)
    LinearLayout layoutType;
    @Bind(R.id.order_state_inner)
    TextView orderStateInner;
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
    @Bind(R.id.communicate_online)
    TextView communicateOnline;
    @Bind(R.id.do_call)
    TextView doCall;
    @Bind(R.id.communicate_online_outer)
    TextView communicateOnlineOuter;
    @Bind(R.id.call_customer_service)
    TextView callCustomerService;
    @Bind(R.id.refuse_order)
    TextView refuseOrder;
    @Bind(R.id.take_order)
    TextView takeOrder;
    private OrderBean orderBean;
    private String order_id;

    private final int take_order = 15;
    private String order_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_order_details);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("订单详情"));
        if (!TextUtils.isEmpty(getIntent().getStringExtra("order_id"))) {
            order_id = getIntent().getStringExtra("order_id");
            initData();
        } else {
            CustomUtils.showTipShort(this, "数据错误");
        }
    }

    private void initData() {
        startRequestData(getData);
    }


    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("order_id", order_id);
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Engineer&a=orderdetails", params);
                break;
            case take_order:
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("order_id", order_id);
                params.put("type", order_type);
                getDataWithPost(take_order, Host.hostUrl + "api.php?m=Api&c=Engineer&a=agree_order", params);
                break;

        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                Logger.json(response);
                orderBean = new Gson().fromJson(response, OrderBean.class);
                if (orderBean != null) {
                    bindData(orderBean);
                }
                break;
            case take_order:
                EventBus.getDefault().post(new EventBusBean().setRefresh(true));
                CustomUtils.showTipShort(this, "接单成功");
                finish();
                break;

        }
    }

    private void bindData(OrderBean orderBean) {
        if ("1".equals(orderBean.getType())) {
            order_type = "1";
            tvStateOuter.setText("等待接单");
            orderStateInner.setText("等待接单");
        } else {
            refuseOrder.setVisibility(View.GONE);
            takeOrder.setText("抢单");
            order_type = "2";
            orderStateInner.setText("等待抢单");
            tvStateOuter.setText("等待抢单");
        }
        if (!TextUtils.isEmpty(orderBean.getOrder_number())) {
            orderNum.setText(orderBean.getOrder_number());
        }
        if (!TextUtils.isEmpty(orderBean.getDate())) {
            orderNum.setText(orderBean.getDate());
        }
        if (!TextUtils.isEmpty(orderBean.getOne_fuwu())) {
            typeOne.setVisibility(View.VISIBLE);
            typeOne.setText(orderBean.getOne_fuwu());
        }
        if (!TextUtils.isEmpty(orderBean.getTwo_fuwu())) {
            typeTwo.setVisibility(View.VISIBLE);
            typeTwo.setText(orderBean.getTwo_fuwu());
        }
        if (!TextUtils.isEmpty(orderBean.getThree_fuwu())) {
            typeThree.setVisibility(View.VISIBLE);
            typeThree.setText(orderBean.getThree_fuwu());
        }
        if (!TextUtils.isEmpty(orderBean.getDis())) {
            serviceDesc.setText(orderBean.getDis());
        }
        //判断是否有图片，填充图片recycleView
        LinearLayoutManager layout = new LinearLayoutManager(this);
        picList.setLayoutManager(layout);
        if (TextUtils.isEmpty(orderBean.getImg())) {
            picList.setVisibility(View.GONE);
        } else {
            //拼装图片数据
            String[] urls = orderBean.getImg().split(",");
            List<ImgBean> picUrl = new ArrayList();
            for (int i = 0; i < urls.length; i++) {
                ImgBean imgBean = new ImgBean();
                imgBean.setPath(Host.hostUrl + urls[i]);
                picUrl.add(imgBean);
            }
            picList.setAdapter(new LargePicShowAdapter(this, picUrl));
        }
        if (!TextUtils.isEmpty(orderBean.getFuwu_time())) {
            serivceTime.setText(orderBean.getFuwu_time());
        }
        String addressStr = "";
        if (!TextUtils.isEmpty(orderBean.getAddress()) && !TextUtils.isEmpty(orderBean.getProvince())) {
            addressStr = orderBean.getProvince() + orderBean.getCity() + orderBean.getArea() + orderBean.getAddress();
        }
        serivceAddress.setText(addressStr);
        if (!TextUtils.isEmpty(orderBean.getAmount())) {
            serverCost.setText(orderBean.getAmount());
        }
        if (!TextUtils.isEmpty(orderBean.getName())) {
            tvName.setText(orderBean.getName());
        }
        if (!TextUtils.isEmpty(orderBean.getPhone())) {
            tvPhone.setText(orderBean.getPhone());
        }
//        if (!TextUtils.isEmpty(orderBean.getService_type())) {
//            switch (orderBean.getService_type()) {
//                case "2":
//                    refuseOrder.setVisibility(View.GONE);
//                    break;
//                case "3":
//                    break;
//                case "4":
//                    break;
//                case "-1":
//                    break;
//            }
//        }

    }


    @OnClick({R.id.communicate_online, R.id.do_call, R.id.communicate_online_outer, R.id.call_customer_service, R.id.refuse_order, R.id.take_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.communicate_online:
                break;
            case R.id.do_call:
                Util.createSimpleDialog(this, "呼叫客户", "确认呼叫客户电话？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        applyPermission(2, Manifest.permission.CALL_PHONE);
                    }
                });
                break;
            case R.id.communicate_online_outer:
                break;
            case R.id.call_customer_service:
                Util.createSimpleDialog(this, "联系客服", "确认呼叫客户服务电话？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        applyPermission(1, Manifest.permission.CALL_PHONE);
                    }
                });
                break;
            case R.id.refuse_order:
                startActivityForResult(new Intent(this, RefuseReasonActivity.class).putExtra("order_id", order_id), 10001);

                break;
            case R.id.take_order:
                Util.createSimpleDialog(this, "提示", "确认要接单吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startRequestData(take_order);
                    }
                });

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 10001:
                    finish();
                    break;
            }
        }
    }

    @Override
    protected void applyPermissionsFailed() {
        super.applyPermissionsFailed();
        CustomUtils.showTipShort(this, "无拨打电话权限,请授予权限后再试");
    }

    @Override
    protected void startApplyPermissions(int index) {
        super.startApplyPermissions(index);
        switch (index) {
            case 1:
                if (!TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getService_phone())) {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + MyAccountMoudle.
                            getInstance().getUserInfo().getService_phone())));
                }
                break;
            case 2:
                String phoneStr = orderBean.getPhone();
                if (TextUtils.isEmpty(phoneStr)) {
                    CustomUtils.showTipShort(this, "系统异常，未获取到正确号码");
                    return;
                }
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneStr)));
                break;
        }

    }
}
