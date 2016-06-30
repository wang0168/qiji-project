package tts.project.qiji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import tts.moudle.api.Host;
import tts.moudle.api.activity.CustomPictureSelectorView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.bean.AddressBean;
import tts.project.qiji.common.MyAccountMoudle;

/**
 * 下订单
 */
public class OrderActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.left_icon)
    ImageView leftIcon;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.layout_address)
    RelativeLayout layoutAddress;
    @Bind(R.id.tv_service_time)
    TextView tvServiceTime;
    @Bind(R.id.layout_choose_date)
    RelativeLayout layoutChooseDate;
    @Bind(R.id.is_urgent)
    CheckBox isUrgent;
    @Bind(R.id.edit_desc)
    EditText editDesc;
    @Bind(R.id.choose_pic)
    ImageView choosePic;
    @Bind(R.id.tv_total_money)
    TextView tvTotalMoney;
    @Bind(R.id.action_submit)
    TextView actionSubmit;
    @Bind(R.id.pictureList)
    RecyclerViewAutoRefreshUpgraded pictureList;
    private AddressBean addressBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("呼叫服务"));
        startRequestData(getData);
        layoutAddress.setOnClickListener(this);
        choosePic.setOnClickListener(this);
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
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Order&a=getdefaultaddress", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                if (!TextUtils.isEmpty(response)) {
                    addressBean = new Gson().fromJson(response, AddressBean.class);
                    bindData(addressBean);
                }
                break;
        }
    }

    private void bindData(AddressBean addressBean) {
        name.setText("联系人：" + addressBean.getName());
        address.setText("服务地址：" + addressBean.getProvince() +
                addressBean.getCity() + addressBean.getArea() + addressBean.getAddress());
        if (TextUtils.isEmpty(addressBean.getMobile())) {
            return;
        } else {
            phone.setText(addressBean.getMobile());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_address:
                startActivityForResult(new Intent(this, AddressManagerActivity.class).putExtra("from", "1"), 10001);
                break;
            case R.id.choose_pic:
                startActivityForResult(new Intent(this, CustomPictureSelectorView.class), 10002);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 10001:
                    addressBean = (AddressBean) data.getSerializableExtra("data");
                    bindData(addressBean);
                    break;
                case 10002:
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    pictureList.setLayoutManager(linearLayoutManager);
                    pictureList.setAdapter(null);
                    break;
            }
        }
    }
}
