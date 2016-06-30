package tts.project.qiji.engineer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.activity.MyCollectionActivity;
import tts.project.qiji.adapter.MeItemAdapter;
import tts.project.qiji.bean.MeItemBean;


/**
 * Created by sjb on 2016/1/27.
 */
public class EngPersonalActivity extends BaseActivity {


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
        adapter();
    }

    private void adapter() {
        mList.setLayoutManager(new LinearLayoutManager(this));
//        mList.addItemDecoration(new RecyclerViewGridItemDecoration(getActivity()));
        final List<MeItemBean> data = new ArrayList<>();
        data.add(new MeItemBean(R.mipmap.hj_icon, "一键呼叫", true, true, false));
        data.add(new MeItemBean(R.mipmap.zx_icon, "在线咨询", true, true, false));
        data.add(new MeItemBean(R.mipmap.rz_icon, "我的认证", true, false, true));
        data.add(new MeItemBean(R.mipmap.sc_icon, "我的收藏", true, true, false));
        data.add(new MeItemBean(R.mipmap.pl_icon, "我的评论", true, false, true));
        data.add(new MeItemBean(R.mipmap.sz_icon, "设置", true, false, false));
        MeItemAdapter adapter = new MeItemAdapter(this, data);
        adapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                if ("我的认证".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(EngPersonalActivity.this, EngPersonalDataActivity.class));
                } else if ("我的收藏".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(EngPersonalActivity.this, MyCollectionActivity.class));
                } else if ("我的评论".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(EngPersonalActivity.this, MyEvaluateActivity.class));
                }
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
        mList.setAdapter(adapter);
    }

    private void findAllView() {
        header = LayoutInflater.from(this).inflate(R.layout.layout_header_personal, null, false);
        mList = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.mlist);
        mList.addHeader(header);
    }

    @Override
    protected void doMenu(MenuItemBean menuItemBean) {
        super.doMenu(menuItemBean);
//        if (!"1".equals(AccountMoudle.getInstance().getUserInfo().getIs_shenhe())) {
//            CustomUtils.showTipShort(this, "审核还未通过，无法接取订单");
//            return;
//        }
        startActivity(new Intent(this, EngineerOrderActivity.class));
    }
}
