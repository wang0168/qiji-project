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
import android.widget.RatingBar;
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
public class EngineerTakeOrderDetailsActivity extends BaseActivity {


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
    @Bind(R.id.upload_service)
    TextView uploadService;
    @Bind(R.id.layout_actions)
    LinearLayout layoutActions;
    @Bind(R.id.do_work)
    TextView doWork;
    @Bind(R.id.upload_time)
    TextView uploadTime;
    @Bind(R.id.context_pic_list)
    RecyclerView contextPicList;
    @Bind(R.id.tv_service_context)
    TextView tvServiceContext;
    @Bind(R.id.layout_service_context)
    LinearLayout layoutServiceContext;
    @Bind(R.id.assess_grade)
    RatingBar assessGrade;
    @Bind(R.id.assess_context)
    TextView assessContext;
    @Bind(R.id.layout_assess)
    LinearLayout layoutAssess;
    @Bind(R.id.tv_refuse_reason)
    TextView tvRefuseReason;
    @Bind(R.id.layout_refuse_order)
    LinearLayout layoutRefuseOrder;

    private OrderBean orderBean;
    private String order_id;
    private final int dowork = 13;
    private final int take_order = 15;
    private String service_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_take_order_details);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("订单详情"));
        if (!TextUtils.isEmpty(getIntent().getStringExtra("order_id"))) {
            order_id = getIntent().getStringExtra("order_id");
            service_type = getIntent().getStringExtra("service_type");
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
            case dowork:
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("order_id", order_id);
                getDataWithPost(dowork, Host.hostUrl + "api.php?m=Api&c=Engineer&a=ordertruefw", params);
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
            case dowork:
                EventBus.getDefault().post(new EventBusBean().setRefresh(true));
                CustomUtils.showTipShort(this, response);
                finish();
                break;

        }
    }

    private void bindData(OrderBean orderBean) {
        if (!TextUtils.isEmpty(orderBean.getStatus())) {
            switch (orderBean.getStatus()) {
                case "2":
                    tvStateOuter.setText("等待服务");
                    break;
                case "3":
                    tvStateOuter.setText("等待上传服务");
                    break;
                case "4":
                    tvStateOuter.setText("等待确认服务");
                case "5":
                    tvStateOuter.setText("已完成");
                    break;
                case "-1":
                    tvStateOuter.setText("已拒单");
                    break;
            }
        }

        if (!TextUtils.isEmpty(orderBean.getOrder_number())) {
            orderNum.setText(orderBean.getOrder_number());
        }
        if (!TextUtils.isEmpty(orderBean.getDate())) {
            createTime.setText(orderBean.getDate());
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

        switch (service_type) {
            case "2":
                doWork.setVisibility(View.VISIBLE);
                break;
            case "3":
                uploadService.setVisibility(View.VISIBLE);
                break;
            case "4":
                layoutServiceContext.setVisibility(View.VISIBLE);
                showServiceContext(orderBean);
//                layoutActions.setVisibility(View.GONE);
                break;
            case "5":
                layoutServiceContext.setVisibility(View.VISIBLE);
                layoutAssess.setVisibility(View.VISIBLE);
                showServiceContext(orderBean);
                layoutActions.setVisibility(View.GONE);
                break;
            case "-1":
                layoutServiceContext.setVisibility(View.VISIBLE);
                layoutRefuseOrder.setVisibility(View.VISIBLE);
                layoutActions.setVisibility(View.GONE);
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


    @OnClick({R.id.communicate_online, R.id.do_call, R.id.communicate_online_outer, R.id.call_customer_service, R.id.do_work, R.id.upload_service})
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
            case R.id.do_work:
                Util.createSimpleDialog(this, "提示", "确认要前往上门服务？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestData(dowork);
                    }
                });
                break;
            case R.id.upload_service:
                startActivityForResult(new Intent(this, EngComServerActivity.class).putExtra("order_id", order_id), 10001);
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
