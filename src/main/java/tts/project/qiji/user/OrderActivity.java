package tts.project.qiji.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import tts.moudle.api.Host;
import tts.moudle.api.activity.CustomPictureSelectorView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.ImgBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.ImageFactory;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.adapter.PicShowAdapter;
import tts.project.qiji.bean.AddressBean;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.login.LoginActivity;
import tts.project.qiji.utils.Util;

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
    RecyclerView pictureList;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.layout_no_address)
    RelativeLayout layoutNoAddress;

    private AddressBean addressBean;
    private String price;
    private String service_typeStr;
    private String service_id;
    private TimePickerView pvTime;
    private String service_date;
    private String isUrgentStr;
    private String address_id;
    private double log;
    private double lag;
    private List<ImgBean> imgBeansTemp;
    private GeoCoder mSearch;
    private GeoCodeResult getResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("呼叫服务"));
        price = getIntent().getStringExtra("price");
        service_typeStr = getIntent().getStringExtra("service_type");
        service_id = getIntent().getStringExtra("service_id");
        if (!TextUtils.isEmpty(service_typeStr)) {
            tvType.setText(service_typeStr + "");
        }
        startRequestData(getData);
        layoutAddress.setOnClickListener(this);
        choosePic.setOnClickListener(this);
        actionSubmit.setOnClickListener(this);
        layoutChooseDate.setOnClickListener(this);
        isUrgent.setOnClickListener(this);
        layoutNoAddress.setOnClickListener(this);
        if (!TextUtils.isEmpty(price)) {
            tvTotalMoney.setText("￥" + price + "元");
        }
        isUrgent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isUrgentStr = "1";
                    service_date = "";
                } else {
                    isUrgentStr = null;
                }
            }
        });

    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                if (TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getUser_id())) {
//                    startActivity(new Intent(this, LoginActivity.class));
                    layoutAddress.setVisibility(View.GONE);
                    layoutNoAddress.setVisibility(View.VISIBLE);

                    return;
                }
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Order&a=getdefaultaddress", params);
                break;
            case submitData:
                showTipMsg("下单中...");
                List<PostFormBuilder.FileInput> files = new ArrayList<>();
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("address_id", address_id);
                if (!TextUtils.isEmpty(service_date)) {
                    params.put("fuwu_time", service_date);
                }
                if (!TextUtils.isEmpty(isUrgentStr)) {
                    params.put("need_server", isUrgentStr);
                }
                params.put("dis", editDesc.getText().toString().trim());
                params.put("amount", price);
                params.put("fuwu_id", service_id);
                params.put("log", log + "");
                params.put("lag", lag + "");
                if (imgBeansTemp != null) {
                    for (int i = 0; i < imgBeansTemp.size(); i++) {
                        ImageFactory imageFactory = new ImageFactory();

                        File file = new File(this.getExternalCacheDir(), new Date().getTime() + "");
                        String facePath = file.getPath();
                        try {
                            imageFactory.storeImage(imageFactory.ratio(imgBeansTemp.get(i).getPath(), 400f, 400f), facePath);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        files.add(new PostFormBuilder.FileInput("img" + i, "img" + i + ".jpg", new File(facePath)));
                    }
                    uploadFile(submitData, Host.hostUrl + "api.php?m=Api&c=Order&a=addorder", params, files);
                } else {
                    getDataWithPost(submitData, Host.hostUrl + "api.php?m=Api&c=Order&a=addorder", params);
//                    CustomUtils.showTipShort(this, "请上传最少一张图片");
                }
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
            case submitData:
                CustomUtils.showTipShort(this, "下单成功！");
                String order_id = "";
                if (response.contains(",")) {
                    order_id = response.split(",")[1];
                }
                startActivity(new Intent(this, MapActivity.class).putExtra("fuwu_address", getResult)
                        .putExtra("from", "1").putExtra("order_id", order_id));
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        switch (index) {
            case getData:
                layoutAddress.setVisibility(View.GONE);
                layoutNoAddress.setVisibility(View.VISIBLE);
                break;
        }
        Logger.e(response);
    }

    String addressStr;

    private void bindData(AddressBean addressBean) {

        //地理编码
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(listener);

        address_id = addressBean.getAddress_id() + "";
        if (TextUtils.isEmpty(address_id)) {
            layoutAddress.setVisibility(View.GONE);
            layoutNoAddress.setVisibility(View.VISIBLE);
        }
        name.setText("联系人：" + addressBean.getName());
        addressStr = addressBean.getProvince() +
                addressBean.getCity() + addressBean.getArea() + addressBean.getAddress();
        if (!TextUtils.isEmpty(addressStr) && !TextUtils.isEmpty(addressBean.getCity())) {
            address.setText("服务地址：" + addressStr);
            mSearch.geocode(new GeoCodeOption().city(addressBean.getCity()).address(addressStr));

        }
        if (TextUtils.isEmpty(addressBean.getMobile())) {
//            return;
        } else {
            phone.setText(addressBean.getMobile());
        }

    }

    OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
        public void onGetGeoCodeResult(GeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                log = -1;
                lag = -1;
                //没有检索到结果
            } else {
                getResult = result;
                //获取地理编码结果
                log = result.getLocation().longitude;
                lag = result.getLocation().latitude;
                Logger.d("log==" + log + "   lag==" + lag);

            }
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有找到检索结果
            }
            //获取反向地理编码结果
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSearch != null) {
            mSearch.destroy();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_address:
                startActivityForResult(new Intent(this, AddressManagerActivity.class).putExtra("from", "1"), 10001);
                break;
            case R.id.layout_no_address:
                startActivityForResult(new Intent(this, AddressManagerActivity.class).putExtra("from", "1"), 10001);
                break;
            case R.id.action_submit:
                if (TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getUser_id())) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                if (address_id == null) {
                    CustomUtils.showTipShort(this, "请选择地址");
                    return;
                }
                if (isUrgentStr == null && service_date == null) {
                    CustomUtils.showTipShort(this, "请选择服务时间或者勾选急需服务");
                    return;
                }
                startRequestData(submitData);
                break;
            case R.id.choose_pic:
                startActivityForResult(new Intent(this, CustomPictureSelectorView.class).putExtra("maxCount", 5), 10002);
                break;
            case R.id.layout_choose_date:
                pvTime = new TimePickerView(this, TimePickerView.Type.ALL);
                pvTime.setTime(new Date());
                pvTime.setCyclic(false);
                pvTime.setCancelable(true);
                pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

                    @Override
                    public void onTimeSelect(Date date) {
                        isUrgent.setChecked(false);
                        isUrgentStr = null;
                        service_date = Util.getTime(date);
                        tvServiceTime.setText(service_date);
                    }
                });

                pvTime.show();
                break;
            case R.id.is_urgent:
                if (isUrgent.isChecked()) {
                    service_date = null;
                    tvServiceTime.setText("");
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
                    addressBean = (AddressBean) data.getSerializableExtra("data");
                    bindData(addressBean);
                    break;
                case 10002:
                    imgBeansTemp = (List<ImgBean>) data.getSerializableExtra("imgBeans");
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    pictureList.setLayoutManager(linearLayoutManager);
                    PicShowAdapter adapter = new PicShowAdapter(this, imgBeansTemp);
                    pictureList.setAdapter(adapter);
                    break;
            }
        }
    }
}
