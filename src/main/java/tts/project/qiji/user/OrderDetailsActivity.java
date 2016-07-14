package tts.project.qiji.user;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

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
import tts.project.qiji.bean.CustomServiceBean;
import tts.project.qiji.bean.EngineerBean;
import tts.project.qiji.bean.EventBusBean;
import tts.project.qiji.bean.OrderBean;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.utils.ImageLoader;
import tts.project.qiji.utils.Util;


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
    @Bind(R.id.eng_rating)
    RatingBar engRating;
    @Bind(R.id.layout_engineer)
    RelativeLayout layoutEngineer;
    @Bind(R.id.layout_engineer_action)
    LinearLayout layoutEngineerAction;
    @Bind(R.id.layout_engineer_outer)
    RelativeLayout layoutEngineerOuter;
    @Bind(R.id.call_customer_service)
    TextView callCustomerService;
    //    @Bind(R.id.orders)
//    TextView orders;
//    @Bind(R.id.navigation)
//    TextView navigation;
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
    @Bind(R.id.upload_time)
    TextView uploadTime;
    @Bind(R.id.context_pic_list)
    RecyclerView contextPicList;
    @Bind(R.id.tv_service_context)
    TextView tvServiceContext;
    @Bind(R.id.layout_service_context)
    LinearLayout layoutServiceContext;

    @Bind(R.id.order_state_inner)
    TextView orderStateInner;
    @Bind(R.id.type_one)
    TextView typeOne;
    @Bind(R.id.type_two)
    TextView typeTwo;
    @Bind(R.id.type_three)
    TextView typeThree;
    @Bind(R.id.call_engineer)
    TextView callEngineer;
    @Bind(R.id.preview_location)
    TextView previewLocation;
    private OrderBean orderBean;
    private String order_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_order);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("订单详情"));

        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        picList.setLayoutManager(layout);

        order_id = getIntent().getStringExtra("order_id");
        if (TextUtils.isEmpty(order_id)) {
            CustomUtils.showTipShort(this, "数据异常");
            finish();

        }
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
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Order&a=orderdetails", params);
                break;

            case cancel:
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("order_id", orderBean.getOrder_id());
                getDataWithPost(cancel, Host.hostUrl + "/api.php?m=Api&c=Order&a=cancel_order", params);
                break;
            case hurryOrder:
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("order_id", orderBean.getOrder_id());
                getDataWithPost(hurryOrder, Host.hostUrl + "api.php?m=Api&c=Order&a=reminder", params);
                break;
        }
    }

    List<CustomServiceBean> listCall;

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

            case cancel:
                EventBus.getDefault().post(new EventBusBean().setRefresh(true));
                finish();
                break;
            case hurryOrder:
                Util.createSimpleAlert(this, "催单成功", "您已成功催单！");
                break;
        }
    }

    private void bindData(OrderBean orderBean) {

        String orderStatusStr = "";
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
                    showEngineerInfo(orderBean.getEngineer());
                    if (!TextUtils.isEmpty(orderBean.getEngineer().getImg() + "")) {
                        ImageLoader.load(this, orderBean.getEngineer().getImg() + "", engFace);
                    }
                    break;
                case "3":
                    orderStatusStr = "待确认服务";
                    callCustomerService.setVisibility(View.VISIBLE);
                    confirmServiceOuter.setVisibility(View.VISIBLE);
                    layoutServiceContext.setVisibility(View.VISIBLE);
                    layoutEngineerOuter.setVisibility(View.VISIBLE);
                    showEngineerInfo(orderBean.getEngineer());
                    showServiceContext(orderBean);
                    break;
                case "4":
                    orderStatusStr = "订单已完成";
                    callCustomerService.setVisibility(View.VISIBLE);
                    layoutServiceContext.setVisibility(View.VISIBLE);
                    showEngineerInfo(orderBean.getEngineer());
                    layoutEngineerOuter.setVisibility(View.VISIBLE);
                    showServiceContext(orderBean);
                    break;
            }
        }
        orderState.setText(orderStatusStr);
        orderStateInner.setText(orderStatusStr);

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

        if (!TextUtils.isEmpty(orderBean.getAmount())) {
            serverCost.setText(orderBean.getAmount());
        }
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
        } else {
            serivceTime.setText("急需服务");
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
            tvPhone.setText(orderBean.getPhone());
        }
    }

    @OnClick({R.id.call_customer_service, R.id.cancel_order_outer, R.id.confirm_service_outer,
            R.id.modify_order_outer, R.id.hurry_order_outer, R.id.preview_location, R.id.call_engineer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_customer_service:
                Util.createSimpleDialog(this, "呼叫客服", "确认呼叫客服电话15179165382？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        applyPermission(1, Manifest.permission.CALL_PHONE);
                    }
                });
                break;

            case R.id.cancel_order_outer:
                Util.createSimpleDialog(this, "取消订单", "您确定要取消订单吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestData(cancel);
                    }
                });
                break;
            case R.id.confirm_service_outer:
                if ("2".equals(MyAccountMoudle.getInstance().getUserInfo().getState()) && "1".equals(orderBean.getType())) {
                    startActivityForResult(new Intent(this, ServiceConfirmActivity.class).putExtra("order_id", order_id), 10001);
                } else {
                    startActivity(new Intent(this, PayActivity.class).putExtra("order_number", orderBean.getOrder_number())
                            .putExtra("money", orderBean.getAmount()).putExtra("order_id", orderBean.getOrder_id()));
//                startActivityForResult(new Intent(this, ServiceConfirmActivity.class).putExtra("order_id", orderBean.getOrder_id()), 10001);
                }
                break;
            case R.id.modify_order_outer:
                startActivity(new Intent(this, OrderActivity.class));
                break;
            case R.id.hurry_order_outer:
                startRequestData(hurryOrder);
                break;
            case R.id.call_engineer:
                Util.createSimpleDialog(this, "呼叫工程师", "确认呼叫工程师电话？" + orderBean.getEngineer().getPhone(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        applyPermission(2, Manifest.permission.CALL_PHONE);
                    }
                });
                break;
            case R.id.preview_location:
                startActivity(new Intent(this, MapActivity.class).putExtra("from", "2")
                        .putExtra("log", orderBean.getEngineer().getLog()).putExtra("lag", orderBean.getEngineer().getLag()));
                break;
        }
    }

    private void showServiceContext(OrderBean orderBean) {
        if (orderBean.getService_true_time() != null) {
            uploadTime.setText(orderBean.getService_true_time().toString());
        }
        if (!TextUtils.isEmpty(orderBean.getEngineer_dis())) {
            tvServiceContext.setText(orderBean.getEngineer_dis());
        }
        if (!TextUtils.isEmpty(orderBean.getEngineer_img())) {
            List<ImgBean> imgBeens = new ArrayList<>();
            for (String s : orderBean.getEngineer_img().split(",")) {
                ImgBean imgbean = new ImgBean();
                imgbean.setPath(Host.hostUrl + s);
                imgBeens.add(imgbean);
            }
            LinearLayoutManager layout1 = new LinearLayoutManager(this);
            layout1.setOrientation(LinearLayoutManager.HORIZONTAL);
            contextPicList.setLayoutManager(layout1);
            contextPicList.setAdapter(new LargePicShowAdapter(this, imgBeens));
        }
    }

    private void showEngineerInfo(EngineerBean engineerBean) {
        if (engineerBean.getImg() != null) {
            ImageLoader.load(this, Host.hostUrl + engineerBean.getImg().toString(), engFace);
        }
        if (!TextUtils.isEmpty(engineerBean.getUsername())) {
            engName.setText(engineerBean.getUsername());
        }
        engRating.setRating(engineerBean.getXing());

    }


    @Override
    protected void applyPermissionsFailed() {
        super.applyPermissionsFailed();
        CustomUtils.showTipShort(this, "无拨打电话权限,请授予权限后再试");
    }

    @Override
    protected void startApplyPermissions(int index) {
        super.startApplyPermissions(index);
        String phoneStr = "";
        switch (index) {
            case 1:
                if (!TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getService_phone())) {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + MyAccountMoudle.
                            getInstance().getUserInfo().getService_phone())));
                }
                break;
            case 2:
                if (orderBean.getEngineer().getPhone() != null) {
                    phoneStr = orderBean.getEngineer().getPhone().toString().trim();
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneStr)));
                } else {
                    CustomUtils.showTipShort(this, "数据错误");
                }
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
    protected void onDestroy() {
        super.onDestroy();
    }
}
