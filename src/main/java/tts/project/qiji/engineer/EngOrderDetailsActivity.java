package tts.project.qiji.engineer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.bean.OrderBean;


/**
 * 订单详情
 */
public class EngOrderDetailsActivity extends BaseActivity {

    @Bind(R.id.state)
    TextView state;
    @Bind(R.id.order_num)
    TextView orderNum;
    @Bind(R.id.order_time)
    TextView orderTime;
    @Bind(R.id.layout_type)
    LinearLayout layoutType;
    @Bind(R.id.order_state)
    TextView orderState;
//    @Bind(R.id.list_view)
//    RecyclerView listView;
    @Bind(R.id.serivce_time)
    TextView serivceTime;
    @Bind(R.id.serivce_address)
    TextView serivceAddress;
    @Bind(R.id.server_cost)
    TextView serverCost;
    @Bind(R.id.order_cancel)
    TextView orderCancel;
    @Bind(R.id.serivce_fu)
    TextView serivceFu;
    @Bind(R.id.serivce_gps)
    TextView serivceGps;
    @Bind(R.id.serivce_com)
    TextView serivceCom;
    @Bind(R.id.serivce_evaluate)
    TextView serivceEvaluate;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.upload_time)
    TextView uploadTime;
    @Bind(R.id.service_context)
    LinearLayout serviceContext;
//    @Bind(R.id.layout_assess)
//    LinearLayout layoutAssess;
    @Bind(R.id.order_server)
    TextView orderServer;
    @Bind(R.id.orders)
    TextView orders;
    @Bind(R.id.navigation)
    TextView navigation;
    @Bind(R.id.server_shang)
    TextView serverShang;
    @Bind(R.id.server_com)
    TextView serverCom;
    private OrderBean orderBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_order_eng);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("订单详情"));
        initData();
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
                params = new HashMap<>();
//                params.put("member_id", AccountMoudle.getInstance().getUserInfo().getMember_id());
//                params.put("token", AccountMoudle.getInstance().getUserInfo().getMem_token());
//                params.put("order_id", getIntent().getStringExtra("order_id"));
//                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Order&a=orderdetails", params);
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
//                    init();
                }
                break;
            case submitData:
                startRequestData(getData);
                CustomUtils.showTipShort(this, "接单成功");
                break;
        }
    }

//    private void init() {
//        switch (orderBean.getEngineer_state()) {
//            case "1":
//                state.setText("用户已付款");
//                order_server.setVisibility(View.VISIBLE);
//                break;
//            case "2":
//                state.setText("等待上门服务");
//                server_shang.setVisibility(View.VISIBLE);
//                order_server.setVisibility(View.VISIBLE);
//                navigation.setVisibility(View.VISIBLE);
//                break;
//            case "3":
//                state.setText("等待服务确认");
//                server_shang.setVisibility(View.VISIBLE);
//                order_server.setVisibility(View.VISIBLE);
//                server_com.setVisibility(View.VISIBLE);
//                break;
//            case "4":
//                state.setText("服务已完成");
//                break;
//            case "5":
//                state.setText("服务已关闭");
//                break;
//        }
//        order_num.setText(orderBean.getOrdernumber());
//        order_time.setText(TimeUtils.getTimeFromMinmis("yyyy-MM-dd hh:mm", orderBean.getCreat_time()));
//        name.setText(orderBean.getName());
////        order_phone.setText(orderBean.getMobile());
//        address.setText(orderBean.getAddress());
//        company.setText(orderBean.getCompany());
//        order_stime.setText(orderBean.getFuwu_time());
//        ServerNeedAdapter adapter = new ServerNeedAdapter(this, orderBean.getFuwu());
//        list_view.setAdapter(adapter);
//        order_detail.setText(orderBean.getDis());
//        order_cost.setText("￥" + orderBean.getPrice());
//    }


    @OnClick({R.id.orders, R.id.order_server, R.id.navigation, R.id.server_shang, R.id.server_com})
    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.orders:
                startRequestData(getData);
                break;
            case R.id.order_server:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
//                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + orderBean.getMobile())));
                break;
            case R.id.navigation:
                break;
            case R.id.server_shang:
                break;
            case R.id.server_com:
                break;
        }

    }
}
