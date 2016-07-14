package tts.project.qiji.engineer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.user.MyCollectionActivity;
import tts.project.qiji.adapter.MeItemAdapter;
import tts.project.qiji.bean.MeItemBean;
import tts.project.qiji.bean.UserInfoBean;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.utils.ImageLoader;
import tts.project.qiji.utils.LoginInfoSave;


/**
 * Created by sjb on 2016/1/27.
 */
public class EngPersonalActivity extends BaseActivity {

    private TextView name;
    private TextView phone;
    private ImageView face_img;
    private RatingBar evaluate;
    private View header;
    private RecyclerViewAutoRefreshUpgraded mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.engineer_personal_center);
        setTitle(new BarBean().setMsg("个人中心").setIsRemoveBack(true));
        MenuItemBean bean = new MenuItemBean();
        bean.setTitle("我的订单");
        bean.setTextColor(getResources().getColor(R.color.colorAccent));
        addMenu(bean);
        findAllView();
        bindData();
        adapter();
    }

    private void bindData() {
        UserInfoBean userInfoBean = MyAccountMoudle.getInstance().getUserInfo();
        name.setText(userInfoBean.getUsername() + "");
        phone.setText(userInfoBean.getPhone() + "");
        ImageLoader.load(this, userInfoBean.getImg(), face_img);
//        evaluate.setRating(Float.parseFloat(userInfoBean.get));
    }

    private void adapter() {
        mList.setLayoutManager(new LinearLayoutManager(this));
//        mList.addItemDecoration(new RecyclerViewGridItemDecoration(getActivity()));
        final List<MeItemBean> data = new ArrayList<>();
        data.add(new MeItemBean(R.mipmap.hj_icon, "一键呼叫", "", true, true, false));
        data.add(new MeItemBean(R.mipmap.zx_icon, "在线咨询", "", true, true, false));
        data.add(new MeItemBean(R.mipmap.rz_icon, "我的认证", "", true, false, true));
        data.add(new MeItemBean(R.mipmap.sc_icon, "我的收藏", "", true, true, false));
        data.add(new MeItemBean(R.mipmap.pl_icon, "我的评论", "", true, false, true));
        data.add(new MeItemBean(R.mipmap.sz_icon, "设置", "", true, false, false));
        MeItemAdapter adapter = new MeItemAdapter(this, data);
        adapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                if ("我的认证".equals(data.get(position).getItem_name())) {
                    startActivityForResult(new Intent(EngPersonalActivity.this, EngineerAuthenticateActivity.class), 10001);
                } else if ("我的收藏".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(EngPersonalActivity.this, MyCollectionActivity.class));
                } else if ("我的评论".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(EngPersonalActivity.this, MyEvaluateActivity.class));
                } else if ("设置".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(EngPersonalActivity.this, SettingActivity.class));
                }
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
        mList.setAdapter(adapter);
        mList.setOnRefreshListener(new RecyclerViewAutoRefreshUpgraded.OnRefreshListener() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onRefreshData() {
                startRequestData(getData);
            }
        });
    }

    private void findAllView() {
        header = LayoutInflater.from(this).inflate(R.layout.layout_header_personal, null, false);
        mList = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.mlist);
        mList.addHeader(header);
        name = (TextView) header.findViewById(R.id.name);
        phone = (TextView) header.findViewById(R.id.phone);
        face_img = (ImageView) header.findViewById(R.id.face_img);
        evaluate = (RatingBar) header.findViewById(R.id.evaluate);
        mList.setIsHead(true);
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
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                LoginInfoSave.loginOk(this, response);
                break;
        }
        mList.setOnRefreshFinished(true);
    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        mList.setOnRefreshFinished(true);
    }

    @Override
    protected void doMenu(MenuItemBean menuItemBean) {
        super.doMenu(menuItemBean);
        if (!"1".equals(MyAccountMoudle.getInstance().getUserInfo().getShenhe())) {
            CustomUtils.showTipShort(this, "审核还未通过，无法接取订单");
            return;
        }
        startActivity(new Intent(this, EngineerOrderActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 10001:
//                    startRequestData(getData);
                    break;
            }
        }
    }

}
