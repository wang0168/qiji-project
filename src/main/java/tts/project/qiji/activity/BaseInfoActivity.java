package tts.project.qiji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.ImgBean;
import tts.moudle.api.moudle.AccountMoudle;
import tts.moudle.api.moudle.SharedPreferencesMoudle;
import tts.moudle.api.utils.ImageFactory;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.MainActivity;
import tts.project.qiji.R;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.utils.LoginInfoSave;


public class BaseInfoActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerViewAutoRefreshUpgraded list;
    private TextView exit_btn;
    private final int face = 20001;
    //    private TimePickerView pvTime;
//    private OptionsPickerView pvOptions;
    private String sexStr = "";
    private String birthStr = "";
    private String facePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_info);

        findAllView();
        setTitle(new BarBean().setMsg("基本信息"));
        startRequestData(getData);

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

//        UserInfoBean userInfoBean = MyAccountMoudle.getInstance().getUserInfo();
//        View headerView = LayoutInflater.from(this).inflate(R.layout.header_base_info, null, false);
//        headerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivityForResult(new Intent(BaseInfoActivity.this, CustomPictureSelectorView.class).putExtra("maxCount", 1), 1000);
//            }
//        });
//        CircleImageView face = (CircleImageView) headerView.findViewById(R.id.face);
//        ImageLoader.load(this, userInfoBean.getHead_path(), face, R.mipmap.touxiang_2x);
//        list.addHeader(headerView);
//
//        final List<MeItemBean> meItemBeens = new ArrayList<>();
//        meItemBeens.add(new MeItemBean("昵称", userInfoBean.getNick_name(), "", true, true));
//        meItemBeens.add(new MeItemBean("手机号", userInfoBean.getMobile(), "", true, true));
//        meItemBeens.add(new MeItemBean("地址", "1".equals(userInfoBean.getSex()) ? "男" : "女", "", true, true));
//        meItemBeens.add(new MeItemBean("公司", userInfoBean.getAge(), "", true, true));
//        meItemBeens.add(new MeItemBean("工作单位", userInfoBean.getJob_unit(), "", true, true));
//        meItemBeens.add(new MeItemBean("职位", userInfoBean.getPosition(), "", true, true));
//        meItemBeens.add(new MeItemBean("爱好", userInfoBean.getHobby(), "", true, true));
//        list.setLayoutManager(new LinearLayoutManager(this));
//        MeItemAdapter baseInfoItemAdapter = new MeItemAdapter(this, meItemBeens);
//        baseInfoItemAdapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
//            @Override
//            public void onClick(View itemView, final int position) {
//                if (position == 2) {
//
//                    return;
//                }
//                if (position == 3) {
//
//                    return;
//                }
////                startActivityForResult(new Intent(BaseInfoActivity.this, EditActivity.class).putExtra("title",
////                        meItemBeens.get(position).getName()).putExtra("oldStr", "" + meItemBeens.get(position).getContext()), 2000);
//            }
//
//            @Override
//            public void onLongClick(View itemView, int position) {
//
//            }
//        });
//        list.setAdapter(baseInfoItemAdapter);

    }

    private void findAllView() {
        list = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.mlist);
        list.setIsHead(true);
        exit_btn = (TextView) findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(this);
    }

    public String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    private ImgBean bean;

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getUser_id() + "");
                params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                getDataWithPost(getData, Host.hostUrl + "memberInterface.api?getMemberDetail", params);
                break;
            case face:
                params = new ArrayMap<>();
                List<PostFormBuilder.FileInput> files = new ArrayList<>();
                Logger.wtf(facePath);
                files.add(new PostFormBuilder.FileInput("head_path", "head_path.jpg", new File(facePath)));
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getUser_id() + "");
                params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                uploadFile(face, Host.hostUrl + "memberInterface.api?updateMemberDetail", params, files);
                break;
            case submitData:
                params = new ArrayMap<>();
                List<PostFormBuilder.FileInput> filess = new ArrayList<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getUser_id() + "");
                params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                if (!TextUtils.isEmpty(sexStr)) {
                    if ("男".equals(sexStr)) {
                        params.put("sex", "1");
                    } else {
                        params.put("sex", "0");
                    }
                } else if (!TextUtils.isEmpty(birthStr)) {
                    Logger.wtf(birthStr);
                    params.put("age", birthStr);
                }
                uploadFile(submitData, Host.hostUrl + "memberInterface.api?updateMemberDetail", params, filess, true);
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
                    break;
                case 2000:
                    startRequestData(getData);
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
                    Logger.wtf(SharedPreferencesMoudle.getInstance().getJson(this
                            .getApplicationContext(), "user_login"));

                }
                break;
        }
    }
}
