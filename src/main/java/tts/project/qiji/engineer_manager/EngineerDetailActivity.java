package tts.project.qiji.engineer_manager;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.adapter.OrderAdapter;
import tts.project.qiji.bean.OrderBean;
import tts.project.qiji.bean.UserDetailBean;
import tts.project.qiji.bean.UserInfoBean;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.utils.ImageLoader;
import tts.project.qiji.utils.Util;

public class EngineerDetailActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerViewAutoRefreshUpgraded mlist;
    private View header;
    private List<OrderBean> mData;
    private ImageView face_img;
    private ImageView img_month;
    private TimePickerView timePickerView;
    private TextView month;
    private TextView name;
    private TextView company;
    private TextView tv_address;
    private TextView tv_phone;
    private TextView tv_order_total_money;
    private TextView t_order_number;
    private String yearStr;
    private String monthStr;
    private UserDetailBean userDetailBean;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        findAllView();
        adapter();
        yearStr = Calendar.getInstance().get(Calendar.YEAR) + "";
        monthStr = Calendar.getInstance().get(Calendar.MONTH) + "";
        month.setText(yearStr + "年" + monthStr + "月账单");
        startRequestData(getData);
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        UserInfoBean userInfoBean = MyAccountMoudle.getInstance().getUserInfo();
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                params.put("company_id", userInfoBean.getCompany_id());
                params.put("token", userInfoBean.getToken());
                params.put("user_id", yearStr);
                params.put("year", yearStr);
                params.put("month", monthStr);
                params.put("page", monthStr);
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Engineer&a=user_details", params);
                break;
        }
    }

    private void adapter() {
        mData = new ArrayList<>();
        mlist.setLayoutManager(new LinearLayoutManager(this));
        OrderAdapter adapter = new OrderAdapter(this, mData);
        mlist.setAdapter(adapter);
        mlist.setOnRefreshListener(new RecyclerViewAutoRefreshUpgraded.OnRefreshListener() {
            @Override
            public void onLoadMore() {
                currentPage++;
                startRequestData(loadMore);
            }

            @Override
            public void onRefreshData() {
                currentPage = 1;
                startRequestData(getData);
            }
        });
//        adapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
//            @Override
//            public void onClick(View itemView, int position) {
//                startActivity(new Intent(UserManagerActivity.this, UserDetailActivity.class));
//            }
//
//            @Override
//            public void onLongClick(View itemView, int position) {
//
//            }
//        });
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                userDetailBean = new Gson().fromJson(response, UserDetailBean.class);
                bindData(userDetailBean);
                break;
        }
    }

    private void bindData(UserDetailBean userDetailBean) {
        if (!TextUtils.isEmpty(userDetailBean.getImg())) {
            ImageLoader.load(this, userDetailBean.getImg(), face_img);
        }
        if (!TextUtils.isEmpty(userDetailBean.getUsername())) {
            name.setText(userDetailBean.getUsername());
        }
        if (!TextUtils.isEmpty(userDetailBean.getCompanyname2())) {
            company.setText(userDetailBean.getCompanyname2());
        }

        if (!TextUtils.isEmpty(userDetailBean.getPhone())) {
            tv_phone.setText(userDetailBean.getPhone());
        }
        String addressStr = "";
        if (userDetailBean.getProvince() != null) {
            addressStr += userDetailBean.getProvince().toString();
        }
        if (userDetailBean.getCity() != null) {
            addressStr += userDetailBean.getCity().toString();
        }
        if (userDetailBean.getArea() != null) {
            addressStr += userDetailBean.getArea().toString();
        }
        if (userDetailBean.getAddress() != null) {
            addressStr += userDetailBean.getAddress().toString();
        }
        if (!TextUtils.isEmpty(addressStr)) {
            tv_address.setText(addressStr);
        }
        month.setText(yearStr + "年" + monthStr + "月账单");
        if (!TextUtils.isEmpty(userDetailBean.getBill().getSum())) {
            tv_order_total_money.setText("￥" + userDetailBean.getBill().getSum());
        }
        if (!TextUtils.isEmpty(userDetailBean.getBill().getCount())) {
            t_order_number.setText(userDetailBean.getBill().getCount());
        }
        mData = userDetailBean.getBill().getOrder();
        mlist.notifyDataSetChanged();
    }

    private void findAllView() {
        header = LayoutInflater.from(this).inflate(R.layout.layout_header_manager_statistics, null, false);
        mlist = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.mlist);
        mlist.addHeader(header);
        mlist.setHeadVisible(true);
        mlist.setLoadMore(true);
        name = (TextView) header.findViewById(R.id.name);
        company = (TextView) header.findViewById(R.id.company);
        tv_address = (TextView) header.findViewById(R.id.tv_address);
        tv_phone = (TextView) header.findViewById(R.id.tv_phone);
        tv_order_total_money = (TextView) header.findViewById(R.id.tv_order_total_money);
        t_order_number = (TextView) header.findViewById(R.id.t_order_number);
        month = (TextView) header.findViewById(R.id.month);
        img_month = (ImageView) header.findViewById(R.id.img_month);
        face_img = (ImageView) header.findViewById(R.id.face_img);
        img_month.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_month:
                timePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH);
                timePickerView.setTitle("请选择月份");
                timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        String str = Util.getTime(date, "yyyy年MM月");
                        month.setText(str + "账单");
                        yearStr = Util.getTime(date, "yyyy");
                        monthStr = Util.getTime(date, "MM");
                        startRequestData(getData);
                    }
                });
                timePickerView.show();
                break;
        }
    }
}
