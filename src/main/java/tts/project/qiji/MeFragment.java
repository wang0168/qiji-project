package tts.project.qiji;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.activity.AddressManagerActivity;
import tts.project.qiji.activity.BaseInfoActivity;
import tts.project.qiji.activity.FeedBackActivity;
import tts.project.qiji.activity.MyCollectionActivity;
import tts.project.qiji.activity.PayActivity;
import tts.project.qiji.adapter.MeItemAdapter;
import tts.project.qiji.bean.MeItemBean;
import tts.project.qiji.bean.UserInfoBean;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.engineer.EngineerOrderActivity;
import tts.project.qiji.engineer_manager.EngineerManagerActivity;
import tts.project.qiji.login.LoginActivity;
import tts.project.qiji.user_manager.UserManagerActivity;
import tts.project.qiji.utils.ImageLoader;
import tts.project.qiji.utils.LoginInfoSave;

/**
 * Created by shanghang on 2016/6/7.
 */
public class MeFragment extends BaseFragment {
    private View header;
    private RecyclerViewAutoRefreshUpgraded mList;
    private ImageView face_img;
    private TextView name;
    private RatingBar evaluate;
    private TextView phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //inflater.inflate(R.layout.fragment_home, container, false)
        return setContentView(R.layout.fragment_home, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(new BarBean().setMsg("个人中心").setIsRemoveBack(true));
        findAllView();
        adapter();
    }

    private void adapter() {
        mList.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mList.addItemDecoration(new RecyclerViewGridItemDecoration(getActivity()));
        final List<MeItemBean> data = new ArrayList<>();
        data.add(new MeItemBean(R.mipmap.scxh, "我的收藏", "", true, false, true));
        data.add(new MeItemBean(0, "地址管理", "", true, true, false));
        data.add(new MeItemBean(0, "意见反馈", "", true, true, false));
        data.add(new MeItemBean(0, "关于我们", "", true, true, false));
        data.add(new MeItemBean(0, "推荐给朋友", "", true, false, true));
        data.add(new MeItemBean(0, "清理缓存", "", true, true, false));
        data.add(new MeItemBean(0, "服务条款协议", "", true, false, false));
        MeItemAdapter adapter = new MeItemAdapter(getActivity(), data);
        mList.setAdapter(adapter);
        adapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                if ("地址管理".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(getActivity(), AddressManagerActivity.class));
                } else if ("意见反馈".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(getActivity(), FeedBackActivity.class));
                } else if ("关于我们".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(getActivity(), EngineerOrderActivity.class));
                } else if ("推荐给朋友".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(getActivity(), PayActivity.class));
                } else if ("我的收藏".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(getActivity(), MyCollectionActivity.class));
                } else if ("清理缓存".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(getActivity(), EngineerManagerActivity.class));
                } else if ("服务条款协议".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(getActivity(), UserManagerActivity.class));
                }
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
    }

    private void findAllView() {

        header = LayoutInflater.from(getActivity()).inflate(R.layout.layout_header_personal, null, false);
        face_img = (ImageView) header.findViewById(R.id.face_img);
        name = (TextView) header.findViewById(R.id.name);
        phone = (TextView) header.findViewById(R.id.phone);
        evaluate = (RatingBar) header.findViewById(R.id.evaluate);

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), BaseInfoActivity.class), 1001);
            }
        });
        mList = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.mlist);
        mList.addHeader(header);
        bindData();
    }

    private void bindData() {
        UserInfoBean userInfoBean = MyAccountMoudle.getInstance().getUserInfo();
        if (!TextUtils.isEmpty(userInfoBean.getUsername()+"")){
            name.setText(userInfoBean.getUsername() + "");
        }
        ImageLoader.load(getActivity(), userInfoBean.getImg().toString() + "", face_img);
        phone.setText(userInfoBean.getPhone().toString() + "");
//        evaluate.setRating(Float.parseFloat(userInfoBean.get));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1001:
                    startRequestData(getData);
                    break;
            }
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
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                boolean a = LoginInfoSave.loginOk(getActivity(), response);
                if (!a) {
                    CustomUtils.showTipShort(getActivity(), "登录异常");
                    startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    return;
                }
                bindData();
                break;
        }
    }
}
