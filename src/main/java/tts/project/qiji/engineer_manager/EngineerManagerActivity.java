package tts.project.qiji.engineer_manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.utils.JsonUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.adapter.UserItemAdapter;
import tts.project.qiji.bean.ManagerUserBean;
import tts.project.qiji.bean.UserInfoBean;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.utils.Util;

public class EngineerManagerActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerViewAutoRefreshUpgraded list;
    private View header;
    private TextView month;
    private ImageView img_month;
    private TextView tv_order_total_money;
    private TextView t_order_number;
    private TimePickerView timePickerView;
    private final int bill = 101;
    private String yearStr;
    private String monthStr;
    private int currentPage = 1;
    private List<ManagerUserBean> mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_manager);
        setTitle(new BarBean().setMsg("工程师管理").setIsRemoveBack(true));
        MenuItemBean menuItemBean = new MenuItemBean();
        menuItemBean.setIcon(R.mipmap.grzx_eng);
        menuItemBean.setHeight(getResources().getDimensionPixelOffset(R.dimen.x50));
        menuItemBean.setWidth(getResources().getDimensionPixelOffset(R.dimen.x50));
        menuItemBean.setType("2");
        addMenu(menuItemBean);
        findAllView();
        adapter();
        yearStr = Calendar.getInstance().get(Calendar.YEAR) + "";
        monthStr = Calendar.getInstance().get(Calendar.MONTH) + "";
        month.setText(yearStr + "年" + monthStr + "月账单");
        startRequestData(bill);
        startRequestData(getData);
    }

    @Override
    protected void doMenu(MenuItemBean menuItemBean) {
        super.doMenu(menuItemBean);
        startActivity(new Intent(this, EngineerManagerPersonalActivity.class));
    }

    private void adapter() {
        mData = new ArrayList<>();
        list.setLayoutManager(new LinearLayoutManager(this));
        UserItemAdapter adapter = new UserItemAdapter(this, mData);
        list.setAdapter(adapter);
        list.setOnRefreshListener(new RecyclerViewAutoRefreshUpgraded.OnRefreshListener() {
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
        adapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                startActivity(new Intent(EngineerManagerActivity.this, EngineerDetailActivity.class));
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
    }

    private void findAllView() {
        header = LayoutInflater.from(this).inflate(R.layout.layout_header_manager, null, false);
        list = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.mlist);
        list.addHeader(header);
        list.setHeadVisible(true);
        list.setLoadMore(true);
        tv_order_total_money = (TextView) header.findViewById(R.id.tv_order_total_money);
        t_order_number = (TextView) header.findViewById(R.id.t_order_number);
        month = (TextView) header.findViewById(R.id.month);
        img_month = (ImageView) header.findViewById(R.id.img_month);
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
                        String str = Util.getTime(date, "yyyy年MM月份");
                        month.setText(str + "账单");
                    }
                });
                timePickerView.show();
                break;
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        UserInfoBean userInfoBean = MyAccountMoudle.getInstance().getUserInfo();
        switch (index) {
            case bill:
                params = new ArrayMap<>();
                params.put("company_id", userInfoBean.getCompany_id());
                params.put("token", userInfoBean.getToken());
                params.put("year", yearStr);
                params.put("month", monthStr);
                getDataWithPost(bill, Host.hostUrl + "api.php?m=Api&c=Engineer&a=bill", params);
                break;
            case getData:
                params = new ArrayMap<>();
                params.put("company_id", userInfoBean.getCompany_id());
                params.put("token", userInfoBean.getToken());
                params.put("page", "1");
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Engineer&a=userlist", params);
                break;
            case loadMore:
                params = new ArrayMap<>();
                params.put("company_id", userInfoBean.getCompany_id());
                params.put("token", userInfoBean.getToken());
                params.put("page", currentPage + "");
                getDataWithPost(loadMore, Host.hostUrl + "api.php?m=Api&c=Engineer&a=userlist", params);
                break;

        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case bill:
                tv_order_total_money.setText("￥" + JsonUtils.getJsonFromJson(response, "sum"));
                t_order_number.setText(JsonUtils.getJsonFromJson(response, "count"));
                break;
            case getData:
                mData.clear();
                List<ManagerUserBean> temp = new Gson().fromJson(response, new TypeToken<List<ManagerUserBean>>() {
                }.getType());
                mData.addAll(temp);
                list.notifyDataSetChanged();
                break;
            case loadMore:
                List<ManagerUserBean> temp1 = new Gson().fromJson(response, new TypeToken<List<ManagerUserBean>>() {
                }.getType());
                mData.addAll(temp1);
                list.notifyDataSetChanged();
                break;
        }
        list.setOnRefreshFinished(true);
    }


    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        list.setOnRefreshFinished(true);
    }
}
