package tts.project.qiji.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.activity.CustomPictureSelectorView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.ImgBean;
import tts.moudle.api.moudle.AccountMoudle;
import tts.moudle.api.moudle.SharedPreferencesMoudle;
import tts.moudle.api.utils.ImageFactory;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.CircleImageView;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.adapter.MeItemAdapter;
import tts.project.qiji.bean.MeItemBean;
import tts.project.qiji.bean.UserInfoBean;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.login.FixPasswordActivity;
import tts.project.qiji.utils.ImageLoader;
import tts.project.qiji.utils.LoginInfoSave;

//import tts.moudle.api.utils.ImageFactory;


public class BaseInfoActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerViewAutoRefreshUpgraded list;
    private TextView exit_btn;
    private final int face = 20001;
    private String nicknameStr = "";
    private String addressStr = "";
    private String facePath;
    private CircleImageView face_img;
    private String provinceStr;
    private String cityStr;
    private String countryStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_info);

        findAllView();
        setTitle(new BarBean().setMsg("基本信息"));
        startRequestData(getData);
        adapter();
    }

    @Override
    public void doIcon() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK);
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void adapter() {
        String addressStr = "";
        UserInfoBean userInfoBean = MyAccountMoudle.getInstance().getUserInfo();
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_base_info, null, false);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(BaseInfoActivity.this, CustomPictureSelectorView.class).putExtra("maxCount", 1), 1000);
            }
        });
        face_img = (CircleImageView) headerView.findViewById(R.id.face);
        ImageLoader.load(this, userInfoBean.getImg(), face_img, R.mipmap.mrt);
        list.addHeader(headerView);

        final List<MeItemBean> data = new ArrayList<>();
//        data.add(new MeItemBean(R.mipmap.scxh, "我的收藏", true, false, true));
        data.add(new MeItemBean(0, "昵称", userInfoBean.getUsername() == null ? "" : userInfoBean.getUsername().toString(), true, true, false));
        data.add(new MeItemBean(0, "联系号码", userInfoBean.getPhone(), false, true, false));
        if (userInfoBean.getProvince() != null) {
            addressStr += userInfoBean.getProvince().toString();
        }
        if (userInfoBean.getCity() != null) {
            addressStr += userInfoBean.getCity().toString();
        }
        if (userInfoBean.getArea() != null) {
            addressStr += userInfoBean.getArea().toString();
        }
        if (userInfoBean.getAddress() != null) {
            addressStr += userInfoBean.getAddress().toString();
        }
        if ("2".equals(userInfoBean.getState())) {
            data.add(new MeItemBean(0, "公司", userInfoBean.getCompanyname(), false, true, false));
        }
        data.add(new MeItemBean(0, "地址", addressStr, true, false, true));
        data.add(new MeItemBean(0, "修改密码", "", true, true, false));
        list.setLayoutManager(new LinearLayoutManager(this));
        MeItemAdapter baseInfoItemAdapter = new MeItemAdapter(this, data);
        baseInfoItemAdapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, final int position) {
                if ("昵称".equals(data.get(position).getItem_name())) {
                    startActivityForResult(new Intent(BaseInfoActivity.this, EditActivity.class).putExtra("title", "昵称"), 10001);
                }

                if ("地址".equals(data.get(position).getItem_name())) {
                    startActivityForResult(new Intent(BaseInfoActivity.this, EditActivity.class).putExtra("paramKey", "地址"), 10002);
                }

                if ("修改密码".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(BaseInfoActivity.this, FixPasswordActivity.class).putExtra("title", "修改密码"));
                }
                if (position == 3) {

                    return;
                }
//                startActivityForResult(new Intent(BaseInfoActivity.this, EditActivity.class).putExtra("title",
//                        meItemBeens.get(position).getName()).putExtra("oldStr", "" + meItemBeens.get(position).getContext()), 2000);
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
        list.setAdapter(baseInfoItemAdapter);

    }

    private void findAllView() {
        list = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.mlist);
        list.setIsHead(true);
        exit_btn = (TextView) findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(this);
    }

    private ImgBean bean;

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        List<PostFormBuilder.FileInput> filess = new ArrayList<>();
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=User&a=getuser", params);
                break;
            case face:
                params = new ArrayMap<>();
                List<PostFormBuilder.FileInput> files = new ArrayList<>();
                Logger.wtf(facePath);
                files.add(new PostFormBuilder.FileInput("key", "head_path.jpg", new File(facePath)));
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                uploadFile(face, Host.hostUrl + "api.php?m=Api&c=User&a=edituser", params, files);
                break;
            case submitData:
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("nickname", nicknameStr);
                uploadFile(submitData, Host.hostUrl + "api.php?m=Api&c=User&a=edituser", params, filess, true);
                break;
            case submit:
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("province", provinceStr);
                params.put("city", cityStr);
                params.put("area", countryStr);
                params.put("address", addressStr);
                uploadFile(submit, Host.hostUrl + "api.php?m=Api&c=User&a=edituser", params, filess, true);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                LoginInfoSave.loginOk(this, response);
                adapter();
                break;
            case face:
                startRequestData(getData);
                break;
            case submitData:
                startRequestData(getData);
                break;
            case submit:
                startRequestData(getData);
                break;

        }
//        CustomUtils.showTipShort(this, response);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1000:
                    bean = ((List<ImgBean>) data.getSerializableExtra("imgBeans")).get(0);
                    ImageFactory imageFactory = new ImageFactory();

                    File file = new File(this.getExternalCacheDir(), new Date().getTime() + "");
                    facePath = file.getPath();
                    try {
                        imageFactory.storeImage(imageFactory.ratio(bean.getPath(), 400f, 400f), facePath);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startRequestData(face);
                    showTipMsg("数据提交中...");
                    break;
                case 10001:
                    nicknameStr = data.getStringExtra("newStr");
                    startRequestData(submitData);
                    showTipMsg("数据提交中...");
                    break;
                case 10002:
                    provinceStr = data.getStringExtra("province");
                    cityStr = data.getStringExtra("city");
                    countryStr = data.getStringExtra("area");
                    addressStr = data.getStringExtra("newStr");
                    startRequestData(submit);
                    showTipMsg("数据提交中...");
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_btn:
                SharedPreferencesMoudle.getInstance().setJson(this
                        .getApplicationContext
                                (), "user_login", "");
                MyAccountMoudle.getInstance().setUserInfo(null);
                AccountMoudle.getInstance().setUserBean(null);
                if (TextUtils.isEmpty(SharedPreferencesMoudle.getInstance().getJson(this
                        .getApplicationContext(), "user_login"))) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("init", "1");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
//                    Logger.wtf(SharedPreferencesMoudle.getInstance().getJson(this
//                            .getApplicationContext(), "user_login"));

                }
                break;
        }
    }
}
