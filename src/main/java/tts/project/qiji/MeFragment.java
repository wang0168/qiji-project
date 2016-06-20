package tts.project.qiji;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.activity.AddressManagerActivity;
import tts.project.qiji.activity.FeedBackActivity;
import tts.project.qiji.activity.MyCollectionActivity;
import tts.project.qiji.activity.PayActivity;
import tts.project.qiji.adapter.MeItemAdapter;
import tts.project.qiji.bean.MeItemBean;
import tts.project.qiji.engineer.EngineerOrderActivity;

/**
 * Created by shanghang on 2016/6/7.
 */
public class MeFragment extends BaseFragment {
    private View header;
    private RecyclerViewAutoRefreshUpgraded mList;

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
        data.add(new MeItemBean(R.mipmap.scxh, "我的收藏", true, false, true));
        data.add(new MeItemBean(0, "地址管理", true, true, false));
        data.add(new MeItemBean(0, "意见反馈", true, true, false));
        data.add(new MeItemBean(0, "关于我们", true, true, false));
        data.add(new MeItemBean(0, "推荐给朋友", true, false, true));
        data.add(new MeItemBean(0, "清理缓存", true, true, false));
        data.add(new MeItemBean(0, "服务协议条款", true, false, false));
        MeItemAdapter adapter = new MeItemAdapter(getActivity(), data);
        mList.setAdapter(adapter);
        adapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                if ("地址管理".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(getActivity(), AddressManagerActivity.class));
                } else if ("意见反馈".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(getActivity(), FeedBackActivity.class));
                } else if ("服务协议条款".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(getActivity(), EngineerOrderActivity.class));
                } else if ("清理缓存".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(getActivity(), PayActivity.class));
                } else if ("我的收藏".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(getActivity(), MyCollectionActivity.class));
                }
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
    }

    private void findAllView() {

        header = LayoutInflater.from(getActivity()).inflate(R.layout.layout_header_personal, null, false);
        mList = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.mlist);
        mList.addHeader(header);
    }
}
