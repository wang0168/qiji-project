package tts.project.hoop.engineer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tts.moudle.api.Host;
import tts.moudle.api.activity.CustomPictureSelectorView;
import tts.moudle.api.bean.ImgBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.hoop.BaseActivity;
import tts.project.hoop.R;
import tts.project.hoop.adapter.PopuJinengAdapter;
import tts.project.hoop.bean.ServerPriceBean;
import tts.project.hoop.bean.UserInfo;
import tts.project.hoop.utils.AccountMoudle;
import tts.project.hoop.utils.Constant;
import tts.project.hoop.widget.ProvinceSelectPopupwindow;

public class EngPersonalDataActivity extends BaseActivity {


    @Bind(R.id.eng_mobli)
    EditText engMobli;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.address_lay)
    LinearLayout addressLay;
    @Bind(R.id.eng_address)
    EditText engAddress;
    @Bind(R.id.eng_jineng_lay)
    LinearLayout eng_jineng_lay;
    @Bind(R.id.eng_jineng)
    TextView engJineng;
    @Bind(R.id.eng_company)
    EditText engCompany;
    @Bind(R.id.eng_name)
    EditText engName;
    @Bind(R.id.eng_num)
    EditText engNum;
    @Bind(R.id.head_img)
    ImageView headImg;
    @Bind(R.id.id_zheng)
    ImageView idZheng;
    @Bind(R.id.id_fan)
    ImageView idFan;
    @Bind(R.id.certificate)
    ImageView certificate;
    @Bind(R.id.tijiao)
    TextView tijiao;
    @Bind(R.id.renzheng_ing)
    TextView renzhengIng;
    @Bind(R.id.renzheng_over)
    TextView renzhengOver;
    @Bind(R.id.sex_group)
    RadioGroup sex_group;
    @Bind(R.id.nan_btn)
    RadioButton nan_btn;
    @Bind(R.id.nv_btn)
    RadioButton nv_btn;

    List<ImgBean> imgBeans;
    String imgBean1;
    String imgBean2;
    String imgBean3;
    String imgBean4;
    ProvinceSelectPopupwindow popupwindow;
    PopupWindow popup;
    private String mProvince;//省
    private String mCity;//市
    private String mArea;//区
    private List<ServerPriceBean> priceBeans;//获取服务信息
    private String sex;
    List<String> jineng = new ArrayList<>();
    private boolean isok = false;
    protected ProgressDialog mProgressDialogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);
        barBean.setMsg("我的认证");
        setTitle(barBean);
        initData();
    }


    private void initData() {
        mProgressDialogs = new ProgressDialog(this);
        // mProgressDialog.setCancelable(false);//除了自己以外的所有地方 包含返回键
        mProgressDialogs.setCanceledOnTouchOutside(false);// 点击自己以外的地方 不允许放弃 //
        // 不包含返回键
        mProgressDialogs.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
                // Toast.makeText(BaseActivity.this, "放弃加载..",
                // Toast.LENGTH_SHORT).show();
                OkHttpUtils.getInstance().cancelTag(this);
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        sex_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == nan_btn.getId()) {
                    sex = "2";
                } else {
                    sex = "1";
                }
            }
        });
        startRequestData(Constant.init_ok);
        startRequestData(Constant.delete_ok);
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        List<PostFormBuilder.FileInput> files;
        switch (index) {
            case Constant.delete_ok:
                params = new HashMap<>();
                params.put("member_id", AccountMoudle.getInstance().getUserInfo().getMember_id());
                params.put("token", AccountMoudle.getInstance().getUserInfo().getMem_token());
                getDataWithPost(Constant.delete_ok, Host.hostUrl + "api.php?m=Api&c=Member&a=getmember", params);
                break;
            case Constant.init_ok:
                getDataWithGet(Constant.init_ok, Host.hostUrl + "api.php?m=Api&c=Order&a=getfuwu");
                break;
            case Constant.updtae_ok:
                for (int j = 0; j < priceBeans.size(); j++) {
                    if (priceBeans.get(j).isSelect()) {
                        jineng.add(priceBeans.get(j).getFuwu_id());
                    }
                }
                params = new HashMap<>();
                params.put("member_id", AccountMoudle.getInstance().getUserInfo().getMember_id());
                params.put("token", AccountMoudle.getInstance().getUserInfo().getMem_token());
                params.put("mobile", engMobli.getText().toString().trim());
                params.put("province", mProvince);
                params.put("city", mCity);
                params.put("area", mArea);
                params.put("address", engAddress.getText().toString().trim());
                params.put("fwtype", new Gson().toJson(jineng));
                params.put("is_conpany", engCompany.getText().toString().trim());
                params.put("name", engName.getText().toString().trim());
                params.put("idcard", engNum.getText().toString().trim());
                getDataWithPost(Constant.updtae_ok, Host.hostUrl + "api.php?m=Api&c=Engineer&a=subdatum ", params);
                break;
            case Constant.photo_ok:
                mProgressDialogs.setMessage("头像上传中");
                mProgressDialogs.show();
                files = new ArrayList<>();
                files.add(new PostFormBuilder.FileInput("ss", "img.jpg", new File(imgBean1)));
                params = new HashMap<>();
                params.put("member_id", AccountMoudle.getInstance().getUserInfo().getMember_id());
                params.put("token", AccountMoudle.getInstance().getUserInfo().getMem_token());
                uploadFile(Constant.photo_ok, Host.hostUrl + "api.php?m=Api&c=Engineer&a=photoupload", params, files);
                break;
            case Constant.findpsd_ok:
                mProgressDialogs.setMessage("身份证正面上传中");
                mProgressDialogs.show();
                files = new ArrayList<>();
                files.add(new PostFormBuilder.FileInput("ss", "img.jpg", new File(imgBean2)));
                params = new HashMap<>();
                params.put("member_id", AccountMoudle.getInstance().getUserInfo().getMember_id());
                params.put("token", AccountMoudle.getInstance().getUserInfo().getMem_token());
                uploadFile(Constant.findpsd_ok, Host.hostUrl + "api.php?m=Api&c=Engineer&a=idcardupload1", params, files);
                break;
            case Constant.login_ok:
                mProgressDialogs.setMessage("身份证反面上传中");
                mProgressDialogs.show();
                files = new ArrayList<>();
                files.add(new PostFormBuilder.FileInput("ss", "img.jpg", new File(imgBean3)));
                params = new HashMap<>();
                params.put("member_id", AccountMoudle.getInstance().getUserInfo().getMember_id());
                params.put("token", AccountMoudle.getInstance().getUserInfo().getMem_token());
                uploadFile(Constant.login_ok, Host.hostUrl + "api.php?m=Api&c=Engineer&a=idcardupload2", params, files);
                break;
            case Constant.logo_ok:
                mProgressDialogs.setMessage("职业证照上传中");
                mProgressDialogs.show();
                files = new ArrayList<>();
                files.add(new PostFormBuilder.FileInput("ss", "img.jpg", new File(imgBean4)));
                params = new HashMap<>();
                params.put("member_id", AccountMoudle.getInstance().getUserInfo().getMember_id());
                params.put("token", AccountMoudle.getInstance().getUserInfo().getMem_token());
                uploadFile(Constant.logo_ok, Host.hostUrl + "api.php?m=Api&c=Engineer&a=zhizhaoupload", params, files);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case Constant.init_ok:
                priceBeans = new Gson().fromJson(response, new TypeToken<List<ServerPriceBean>>() {
                }.getType());
                break;
            case Constant.updtae_ok:
                CustomUtils.showTipShort(this, response);
                break;
            case Constant.delete_ok:
                UserInfo info = new Gson().fromJson(response, UserInfo.class);
                AccountMoudle.getInstance().setUserInfo(info);
                Glide.with(this).load(AccountMoudle.getInstance().getUserInfo().getPhoto()).placeholder(R.mipmap.shangchuan).diskCacheStrategy(DiskCacheStrategy.ALL).into(headImg);
                Glide.with(this).load(AccountMoudle.getInstance().getUserInfo().getIdcardphoto1()).placeholder(R.mipmap.shangchuan).diskCacheStrategy(DiskCacheStrategy.ALL).into(idZheng);
                Glide.with(this).load(AccountMoudle.getInstance().getUserInfo().getIdcardphoto2()).placeholder(R.mipmap.shangchuan).diskCacheStrategy(DiskCacheStrategy.ALL).into(idFan);
                Glide.with(this).load(AccountMoudle.getInstance().getUserInfo().getZhizhaophoto()).placeholder(R.mipmap.shangchuan).diskCacheStrategy(DiskCacheStrategy.ALL).into(certificate);
                imgBean1 = (TextUtils.isEmpty(AccountMoudle.getInstance().getUserInfo().getPhoto())) ? null : AccountMoudle.getInstance().getUserInfo().getPhoto();
                imgBean2 = (TextUtils.isEmpty(AccountMoudle.getInstance().getUserInfo().getIdcardphoto1())) ? null : AccountMoudle.getInstance().getUserInfo().getIdcardphoto1();
                imgBean3 = (TextUtils.isEmpty(AccountMoudle.getInstance().getUserInfo().getIdcardphoto2())) ? null : AccountMoudle.getInstance().getUserInfo().getIdcardphoto2();
                imgBean4 = (TextUtils.isEmpty(AccountMoudle.getInstance().getUserInfo().getZhizhaophoto())) ? null : AccountMoudle.getInstance().getUserInfo().getZhizhaophoto();
                engMobli.setText(TextUtils.isEmpty(AccountMoudle.getInstance().getUserInfo().getMobile()) ? "" : AccountMoudle.getInstance().getUserInfo().getMobile());
                engName.setText(TextUtils.isEmpty(AccountMoudle.getInstance().getUserInfo().getNickname()) ? "" : AccountMoudle.getInstance().getUserInfo().getNickname());
                if (!TextUtils.isEmpty(AccountMoudle.getInstance().getUserInfo().getProvince()) && !TextUtils.isEmpty(AccountMoudle.getInstance().getUserInfo().getCity()) && !TextUtils.isEmpty(AccountMoudle.getInstance().getUserInfo().getArea())) {
                    address.setText(AccountMoudle.getInstance().getUserInfo().getProvince() + AccountMoudle.getInstance().getUserInfo().getCity() + AccountMoudle.getInstance().getUserInfo().getArea());
                }
                engAddress.setText(TextUtils.isEmpty(AccountMoudle.getInstance().getUserInfo().getAddress()) ? "" : AccountMoudle.getInstance().getUserInfo().getAddress());
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < AccountMoudle.getInstance().getUserInfo().getFwtype().size(); i++) {
                    sb.append(AccountMoudle.getInstance().getUserInfo().getFwtype().get(i) + " ");
                }
                engJineng.setText(sb.toString());
                mProvince = AccountMoudle.getInstance().getUserInfo().getProvince();
                mCity = AccountMoudle.getInstance().getUserInfo().getCity();
                mArea = AccountMoudle.getInstance().getUserInfo().getArea();

                engCompany.setText(AccountMoudle.getInstance().getUserInfo().getIs_conpany());
                engNum.setText(AccountMoudle.getInstance().getUserInfo().getIdcard());
                if ("2".equals(AccountMoudle.getInstance().getUserInfo().getIs_shenhe())) {
                    CustomUtils.showTipShort(this, "认证失败，请重新提交申请数据");
                    tijiao.setVisibility(View.VISIBLE);
                    renzhengIng.setVisibility(View.GONE);
                    renzhengOver.setVisibility(View.GONE);
                } else if ("1".equals(AccountMoudle.getInstance().getUserInfo().getIs_shenhe())) {
                    tijiao.setVisibility(View.GONE);
                    renzhengIng.setVisibility(View.GONE);
                    renzhengOver.setVisibility(View.VISIBLE);
                } else if ("3".equals(AccountMoudle.getInstance().getUserInfo().getIs_shenhe())) {
                    tijiao.setVisibility(View.GONE);
                    renzhengIng.setVisibility(View.VISIBLE);
                    renzhengOver.setVisibility(View.GONE);
                } else {
                    tijiao.setVisibility(View.VISIBLE);
                    renzhengIng.setVisibility(View.GONE);
                    renzhengOver.setVisibility(View.GONE);
                }
                break;
            case Constant.photo_ok:
                mProgressDialogs.dismiss();
                CustomUtils.showTipShort(this, response);
                break;
            case Constant.findpsd_ok:
                mProgressDialogs.dismiss();
                startRequestData(Constant.login_ok);
                CustomUtils.showTipShort(this, response);
                break;
            case Constant.login_ok:
                mProgressDialogs.dismiss();
                startRequestData(Constant.logo_ok);
                CustomUtils.showTipShort(this, response);
                break;
            case Constant.logo_ok:
                mProgressDialogs.dismiss();
                CustomUtils.showTipShort(this, response);
                break;
        }
    }

    @OnClick({R.id.eng_jineng_lay, R.id.address_lay, R.id.head_img, R.id.id_zheng, R.id.id_fan, R.id.certificate, R.id.tijiao})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.address_lay:
                popupwindow = new ProvinceSelectPopupwindow(this, View.inflate(this, R.layout.city_select_popupwindow, null), 2);
                popupwindow.showAtLocation(addressLay, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);
                popupwindow.setOnClickListener(new ProvinceSelectPopupwindow.OnClickListener() {
                    @Override
                    public void doClick(String province, String city, String area) {
                        mProvince = province;
                        mCity = city;
                        mArea = area;
                        address.setText(province + city + area);
                    }
                });
                break;
            case R.id.head_img:
                startActivityForResult(new Intent(this, CustomPictureSelectorView.class).putExtra("maxCount", 1), 1000);
                break;
            case R.id.id_zheng:
                startActivityForResult(new Intent(this, CustomPictureSelectorView.class).putExtra("maxCount", 1), 2000);
                break;
            case R.id.id_fan:
                startActivityForResult(new Intent(this, CustomPictureSelectorView.class).putExtra("maxCount", 1), 3000);
                break;
            case R.id.certificate:
                startActivityForResult(new Intent(this, CustomPictureSelectorView.class).putExtra("maxCount", 1), 4000);
                break;
            case R.id.tijiao:
                if (!TextUtils.isMobileNO(engMobli.getText().toString().trim())) {
                    CustomUtils.showTipShort(this, "请填写正确的手机号码");
                    return;
                }
                if (mProvince == null) {
                    CustomUtils.showTipShort(this, "请选择省市区");
                    return;
                }
                if (TextUtils.isEmpty(engAddress.getText().toString().trim())) {
                    CustomUtils.showTipShort(this, "请填写详细地址");
                    return;
                }
                if (TextUtils.isEmpty(engJineng.getText().toString().trim())) {
                    CustomUtils.showTipShort(this, "请选择服务技能");
                    return;
                }
                if (TextUtils.isEmpty(engCompany.getText().toString().trim())) {
                    CustomUtils.showTipShort(this, "请填写公司名称");
                    return;
                }
                if (TextUtils.isEmpty(engName.getText().toString().trim())) {
                    CustomUtils.showTipShort(this, "请填写您的姓名");
                    return;
                }
//                try {
//                    if (!"true".equals(TextUtils.IDCardValidate(engNum.getText().toString().trim()))) {
//                        CustomUtils.showTipShort(this, TextUtils.IDCardValidate(engNum.getText().toString().trim()));
//                        return;
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
                if (imgBean1 == null) {
                    if ("2".equals(AccountMoudle.getInstance().getUserInfo().getIs_shenhe())) {
                        CustomUtils.showTipShort(this, "请重新上传个人头像");
                        return;
                    } else {
                        CustomUtils.showTipShort(this, "请上传个人头像");
                        return;
                    }
                }
                if (imgBean1 != null && !imgBean1.contains("http")) {
                    startRequestData(Constant.photo_ok);
                }
                if (imgBean2 == null) {
                    if ("2".equals(AccountMoudle.getInstance().getUserInfo().getIs_shenhe())) {
                        CustomUtils.showTipShort(this, "请重新上传身份证正面");
                        return;
                    } else {
                        CustomUtils.showTipShort(this, "请上传身份证正面");
                        return;
                    }
                }
                if (imgBean2 != null && !imgBean1.contains("http")) {
                    isok = true;
                }
                if (imgBean2.contains("http")) {
                    isok = false;
                }
                if (imgBean3 == null) {
                    if ("2".equals(AccountMoudle.getInstance().getUserInfo().getIs_shenhe())) {
                        CustomUtils.showTipShort(this, "请重新上传身份证反面");
                        return;
                    } else {
                        CustomUtils.showTipShort(this, "请上传身份证反面");
                        return;
                    }
                }
                if (imgBean3 != null && !imgBean3.contains("http")) {
                    isok = true;
                }
                if (imgBean3.contains("http")) {
                    isok = false;
                }
                if (imgBean4 == null) {
                    if ("2".equals(AccountMoudle.getInstance().getUserInfo().getIs_shenhe())) {
                        CustomUtils.showTipShort(this, "请重新上传职业技能照");
                        return;
                    } else {
                        CustomUtils.showTipShort(this, "请上传职业技能照");
                        return;
                    }
                }
                if (imgBean4 != null && !imgBean4.contains("http")) {
                    isok = true;
                }
                if (imgBean4.contains("http")) {
                    isok = false;
                }
                if (isok) {
                    startRequestData(Constant.findpsd_ok);
                }
                startRequestData(Constant.updtae_ok);

                break;
            case R.id.eng_jineng_lay:
                LayoutInflater inflater = LayoutInflater.from(this);
                View popupWindowView = inflater.inflate(R.layout.acitvity_jineng_pop, null);
                ListView listView = (ListView) popupWindowView.findViewById(R.id.dataList);
                TextView button = (TextView) popupWindowView.findViewById(R.id.queren);
                PopuJinengAdapter adapter = new PopuJinengAdapter(this, priceBeans);
                listView.setAdapter(adapter);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < priceBeans.size(); i++) {
                            if (priceBeans.get(i).isSelect()) {
                                sb.append(priceBeans.get(i).getName() + " ");
                            }
                            engJineng.setText(sb.toString());
                            popup.dismiss();
                        }
                    }
                });
                popup = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                popup.setBackgroundDrawable(new BitmapDrawable());
                popup.setFocusable(true);
                // 这句是为了防止弹出菜单获取焦点之后，点击activity的其他组件没有响应
                popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                // 设置PopupWindow的弹出和消失效果
                // popupWindow.setAnimationStyle(R.style.popupAnimation);
                popup.showAtLocation(eng_jineng_lay, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1000:
                if (resultCode == RESULT_OK) {
                    imgBean1 = ((List<ImgBean>) data.getSerializableExtra("imgBeans")).get(0).getPath();
                    if (imgBean1 != null) {
                        Glide.with(this).load(imgBean1).into(headImg);
                    }
                }
                break;
            case 2000:
                if (resultCode == RESULT_OK) {
                    imgBean2 = ((List<ImgBean>) data.getSerializableExtra("imgBeans")).get(0).getPath();
                    if (imgBean2 != null) {
                        Glide.with(this).load(imgBean2).into(idZheng);
                    }
                }
                break;
            case 3000:
                if (resultCode == RESULT_OK) {
                    imgBean3 = ((List<ImgBean>) data.getSerializableExtra("imgBeans")).get(0).getPath();
                    if (imgBean3 != null) {
                        Glide.with(this).load(imgBean3).into(idFan);
                    }
                }
                break;
            case 4000:
                if (resultCode == RESULT_OK) {
                    imgBean4 = ((List<ImgBean>) data.getSerializableExtra("imgBeans")).get(0).getPath();
                    if (imgBean4 != null) {
                        Glide.with(this).load(imgBean4).into(certificate);
                    }
                }
                break;

        }
    }
}
