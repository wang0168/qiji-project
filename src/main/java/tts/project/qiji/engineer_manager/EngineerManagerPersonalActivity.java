package tts.project.qiji.engineer_manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.moudle.AccountMoudle;
import tts.moudle.api.moudle.SharedPreferencesMoudle;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.adapter.EngineerManagerPersonalAdapter;
import tts.project.qiji.bean.UserManagerDataBean;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.user.MainActivity;
import tts.project.qiji.utils.ImageLoader;

public class EngineerManagerPersonalActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.mlist)
    RecyclerViewAutoRefreshUpgraded mlist;
    @Bind(R.id.action)
    TextView action;
    private View header;
    private ImageView face_img;
    private TextView name;
    private TextView area;
    private TextView responsible_person;//负责人
    private TextView contact_way;//联系方式
    private TextView branches;//分店
    private UserManagerDataBean userManagerDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager_personal);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("个人中心(工程师管理员)"));
        action.setOnClickListener(this);
        findAllView();
        startRequestData(getData);
    }

    private void findAllView() {
        header = LayoutInflater.from(this).inflate(R.layout.layout_header_manager_personal, null, false);
        mlist.addHeader(header);
        face_img = (ImageView) header.findViewById(R.id.face_img);
        name = (TextView) header.findViewById(R.id.name);
        area = (TextView) header.findViewById(R.id.area);
        responsible_person = (TextView) header.findViewById(R.id.responsible_person);
        contact_way = (TextView) header.findViewById(R.id.contact_way);
        branches = (TextView) header.findViewById(R.id.branches);

    }

    private void adapter(List<UserManagerDataBean.SignBean> sign) {
        mlist.setLayoutManager(new LinearLayoutManager(this));
        mlist.setAdapter(new EngineerManagerPersonalAdapter(this, sign));
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                params.put("company_id", MyAccountMoudle.getInstance().getUserInfo().getCompany_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Engineer&a=user_center", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                userManagerDataBean = new Gson().fromJson(response, UserManagerDataBean.class);
                bindData(userManagerDataBean);
                break;
        }
    }

    private void bindData(UserManagerDataBean userManagerDataBean) {
        if (!TextUtils.isEmpty(userManagerDataBean.getLogo())) {
            ImageLoader.load(this, userManagerDataBean.getLogo(), face_img);
        }
        if (!TextUtils.isEmpty(userManagerDataBean.getTitle())) {
            name.setText(userManagerDataBean.getTitle());
        }

        if (!TextUtils.isEmpty(userManagerDataBean.getAddress())) {
            area.setText(userManagerDataBean.getAddress());
        }
        if (!TextUtils.isEmpty(userManagerDataBean.getName())) {
            responsible_person.setText(userManagerDataBean.getName());
        }
        if (!TextUtils.isEmpty(userManagerDataBean.getMobile())) {
            contact_way.setText(userManagerDataBean.getMobile());
        }
        if (!TextUtils.isEmpty(userManagerDataBean.getCount())) {
            branches.setText(userManagerDataBean.getCount());
        }
        if (userManagerDataBean.getSign() != null) {
            adapter(userManagerDataBean.getSign());
        }
    }

    @Override
    public void onClick(View v) {
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
    }
}
