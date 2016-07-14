package tts.project.qiji.engineer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.activity.CustomPictureSelectorView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.ImgBean;
import tts.moudle.api.cityapi.CityMoudle;
import tts.moudle.api.cityapi.ProvinceSelectPopupwindow;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.ImageFactory;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.user.EditActivity;
import tts.project.qiji.user.ServiceTypeActivity;
import tts.project.qiji.bean.UserInfoBean;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.utils.ImageLoader;
import tts.project.qiji.utils.LoginInfoSave;


public class EngineerAuthenticateActivity extends BaseActivity implements View.OnClickListener {

    private TextView tijiao;
    private TextView tv_address;
    private TextView tv_company;
    private TextView tv_truename;
    private TextView tv_id_number;
    private ImageView head_img;
    private ImageView id_zheng;
    private ImageView id_fan;
    private ImageView idcard_with_hand;
    private ImageView certificate;
    private RelativeLayout layout_service_types;
    private RelativeLayout layout_address;
    private RelativeLayout layout_company_info;
    private RelativeLayout layout_truename;
    private RelativeLayout layout_id_number;

    String imgBean1;//个人头像
    String imgBean2;//身份证正面
    String imgBean3;//身份证反面
    String imgBean4;//手持身份证
    String imgBean5;//职业证照
    private String provinceStr;
    private String cityStr;
    private String countryStr;
    private String fuwu_id_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer_authenticate);
        setTitle(new BarBean().setMsg("我的认证"));
        findAllView();
        startRequestData(getData);
    }

    private void findAllView() {
        tijiao = (TextView) findViewById(R.id.tijiao);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_company = (TextView) findViewById(R.id.tv_company);
        tv_truename = (TextView) findViewById(R.id.tv_truename);
        tv_id_number = (TextView) findViewById(R.id.tv_id_number);
        tijiao.setOnClickListener(this);

        layout_service_types = (RelativeLayout) findViewById(R.id.layout_service_types);
        layout_address = (RelativeLayout) findViewById(R.id.layout_address);
        layout_company_info = (RelativeLayout) findViewById(R.id.layout_company_info);
        layout_truename = (RelativeLayout) findViewById(R.id.layout_truename);
        layout_id_number = (RelativeLayout) findViewById(R.id.layout_id_number);

        layout_service_types.setOnClickListener(this);
        layout_address.setOnClickListener(this);
        layout_company_info.setOnClickListener(this);
        layout_truename.setOnClickListener(this);
        layout_id_number.setOnClickListener(this);

        head_img = (ImageView) findViewById(R.id.head_img);
        id_zheng = (ImageView) findViewById(R.id.id_zheng);
        id_fan = (ImageView) findViewById(R.id.id_fan);
        idcard_with_hand = (ImageView) findViewById(R.id.idcard_with_hand);
        certificate = (ImageView) findViewById(R.id.certificate);
        head_img.setOnClickListener(this);
        id_zheng.setOnClickListener(this);
        id_fan.setOnClickListener(this);
        idcard_with_hand.setOnClickListener(this);
        certificate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_address:
                CityMoudle.getInstance().showProvincePopupwindow(EngineerAuthenticateActivity.this, tv_address, 2, new ProvinceSelectPopupwindow.OnClickListener() {
                    @Override
                    public void doClick(String province, String city, String area) {
                        provinceStr = province;
                        cityStr = city;
                        countryStr = area;
                        tv_address.setText(provinceStr + cityStr + countryStr);
                    }
                });
                break;
            case R.id.layout_truename:
                startActivityForResult(new Intent(EngineerAuthenticateActivity.this, EditActivity.class)
                        .putExtra("oldStr", tv_truename.getText().toString().trim()).putExtra("title", "真实姓名"), 103);
                break;
            case R.id.layout_service_types:
                startActivityForResult(new Intent(EngineerAuthenticateActivity.this, ServiceTypeActivity.class), 104);
                break;
            case R.id.layout_company_info:
                startActivityForResult(new Intent(EngineerAuthenticateActivity.this, EditActivity.class)
                        .putExtra("oldStr", tv_company.getText().toString().trim()).putExtra("title", "所属公司"), 101);
                break;
            case R.id.layout_id_number:
                startActivityForResult(new Intent(EngineerAuthenticateActivity.this, EditActivity.class)
                        .putExtra("oldStr", tv_id_number.getText().toString().trim()).putExtra("title", "身份证号码"), 102);
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
            case R.id.idcard_with_hand:
                startActivityForResult(new Intent(this, CustomPictureSelectorView.class).putExtra("maxCount", 1), 4000);
                break;
            case R.id.certificate:
                startActivityForResult(new Intent(this, CustomPictureSelectorView.class).putExtra("maxCount", 1), 5000);
                break;
            case R.id.tijiao:

                if (TextUtils.isEmpty(tv_address.getText().toString())) {
                    CustomUtils.showTipShort(this, "请选择省市区");
                    return;
                }

                if (TextUtils.isEmpty(fuwu_id_str)) {
                    CustomUtils.showTipShort(this, "请选择服务类型");
                    return;
                }
                if (TextUtils.isEmpty(tv_company.getText().toString().trim())) {
                    CustomUtils.showTipShort(this, "请填写公司名称");
                    return;
                }
                if (TextUtils.isEmpty(tv_truename.getText().toString().trim())) {
                    CustomUtils.showTipShort(this, "请填写您的姓名");
                    return;
                }
                if (TextUtils.isEmpty(tv_id_number.getText().toString().trim())) {
                    CustomUtils.showTipShort(this, "请填写您的身份证号码");
                    return;
                }

                if (TextUtils.isEmpty(imgBean1)) {
                    CustomUtils.showTipShort(this, "请上传个人头像");
                    return;
                }

                if (TextUtils.isEmpty(imgBean2)) {
                    CustomUtils.showTipShort(this, "请上传身份证正面");
                    return;
                }

                if (TextUtils.isEmpty(imgBean3)) {
                    CustomUtils.showTipShort(this, "请上传身份证反面");
                }

                if (TextUtils.isEmpty(imgBean4)) {
                    CustomUtils.showTipShort(this, "请上传职业技能照");
                    return;
                }
                if (TextUtils.isEmpty(imgBean5)) {
                    CustomUtils.showTipShort(this, "请上传职业技能照");
                    return;
                }
                startRequestData(submitData);
                break;

        }
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
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=User&a=getuser", params);
                break;
            case submitData:
                List<PostFormBuilder.FileInput> files = new ArrayList<>();
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("fuwu", fuwu_id_str);
                params.put("truename", tv_truename.getText().toString().trim());
                params.put("idcard", tv_id_number.getText().toString().trim());
                params.put("province", provinceStr);
                params.put("city", cityStr);
                params.put("area", countryStr);
                params.put("is_company", tv_company.getText().toString().trim());
                files.add(new PostFormBuilder.FileInput("touxiang", "touxiang.jpg", new File(imgBean1)));
                files.add(new PostFormBuilder.FileInput("idcardimg1", "idcardimg1.jpg", new File(imgBean2)));
                files.add(new PostFormBuilder.FileInput("idcardimg2", "idcardimg2.jpg", new File(imgBean3)));
                files.add(new PostFormBuilder.FileInput("handidcardphoto", "handidcardphoto.jpg", new File(imgBean4)));
                files.add(new PostFormBuilder.FileInput("zhiyephoto", "zhiyephoto.jpg", new File(imgBean5)));
                uploadFile(submitData, Host.hostUrl + "api.php?m=Api&c=Engineer&a=subdatum", params, files);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                LoginInfoSave.loginOk(this, response);
                UserInfoBean userInfoBean = new Gson().fromJson(response, UserInfoBean.class);
                if (!"-1".equals(userInfoBean.getShenhe())) {
                    bindData(userInfoBean);
                }
                break;
            case submitData:
                CustomUtils.showTipLong(this, response);
//                setResult(RESULT_OK);
                finish();
                break;
        }

    }

    private void bindData(UserInfoBean userInfoBean) {
        tv_address.setText(userInfoBean.getProvince().toString() + userInfoBean.getCity().toString() + userInfoBean.getArea().toString());
        tv_company.setText(userInfoBean.getCompanyname());
        tv_truename.setText(userInfoBean.getUsername().toString());
        ImageLoader.load(this, userInfoBean.getImg(), head_img);
        ImageLoader.load(this, userInfoBean.getIdcardphoto1(), id_zheng);
        ImageLoader.load(this, userInfoBean.getIdcardphoto2(), id_fan);
        ImageLoader.load(this, userInfoBean.getHandidcardphoto(), idcard_with_hand);
        ImageLoader.load(this, userInfoBean.getZhiyephoto(), certificate);
//        tv_id_number.setText();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101:
                tv_company.setText(data.getStringExtra("newStr"));
                break;
            case 102:
                tv_id_number.setText(data.getStringExtra("newStr"));
                break;
            case 103:
                tv_truename.setText(data.getStringExtra("newStr"));
                break;
            case 104:

                fuwu_id_str = data.getStringExtra("fuwu_id");
                break;
//            case 105:
//                tv_id_number.setText(data.getStringExtra("newStr"));
//                break;
            case 1000:
                if (resultCode == RESULT_OK) {
                    imgBean1 = ((List<ImgBean>) data.getSerializableExtra("imgBeans")).get(0).getPath();
                    ImageFactory imageFactory = new ImageFactory();

                    File file = new File(this.getExternalCacheDir(), new Date().getTime() + "");
                    String facePath = file.getPath();
                    try {
                        imageFactory.storeImage(imageFactory.ratio(imgBean1, 400f, 400f), facePath);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    imgBean1 = facePath;
                    if (imgBean1 != null) {
                        Glide.with(this).load(imgBean1).into(head_img);
                    }
                }
                break;
            case 2000:
                if (resultCode == RESULT_OK) {
                    imgBean2 = ((List<ImgBean>) data.getSerializableExtra("imgBeans")).get(0).getPath();
                    ImageFactory imageFactory = new ImageFactory();

                    File file = new File(this.getExternalCacheDir(), new Date().getTime() + "");
                    String facePath = file.getPath();
                    try {
                        imageFactory.storeImage(imageFactory.ratio(imgBean2, 400f, 400f), facePath);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    imgBean2 = facePath;
                    if (imgBean2 != null) {
                        Glide.with(this).load(imgBean2).into(id_zheng);
                    }
                }
                break;
            case 3000:
                if (resultCode == RESULT_OK) {
                    imgBean3 = ((List<ImgBean>) data.getSerializableExtra("imgBeans")).get(0).getPath();
                    ImageFactory imageFactory = new ImageFactory();

                    File file = new File(this.getExternalCacheDir(), new Date().getTime() + "");
                    String facePath = file.getPath();
                    try {
                        imageFactory.storeImage(imageFactory.ratio(imgBean3, 400f, 400f), facePath);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    imgBean3 = facePath;
                    if (imgBean3 != null) {
                        Glide.with(this).load(imgBean3).into(id_fan);
                    }
                }
                break;
            case 4000:
                if (resultCode == RESULT_OK) {
                    imgBean4 = ((List<ImgBean>) data.getSerializableExtra("imgBeans")).get(0).getPath();
                    ImageFactory imageFactory = new ImageFactory();

                    File file = new File(this.getExternalCacheDir(), new Date().getTime() + "");
                    String facePath = file.getPath();
                    try {
                        imageFactory.storeImage(imageFactory.ratio(imgBean4, 400f, 400f), facePath);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    imgBean4 = facePath;
                    if (imgBean4 != null) {
                        Glide.with(this).load(imgBean4).into(idcard_with_hand);
                    }
                }
                break;
            case 5000:
                if (resultCode == RESULT_OK) {
                    imgBean5 = ((List<ImgBean>) data.getSerializableExtra("imgBeans")).get(0).getPath();
                    ImageFactory imageFactory = new ImageFactory();

                    File file = new File(this.getExternalCacheDir(), new Date().getTime() + "");
                    String facePath = file.getPath();
                    try {
                        imageFactory.storeImage(imageFactory.ratio(imgBean5, 400f, 400f), facePath);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    imgBean5 = facePath;
                    if (imgBean5 != null) {
                        Glide.with(this).load(imgBean5).into(certificate);
                    }
                }
                break;
        }
    }
}
