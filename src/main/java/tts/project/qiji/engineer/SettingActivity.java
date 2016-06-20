package tts.project.qiji.engineer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.adapter.MeItemAdapter;
import tts.project.qiji.bean.MeItemBean;


/**
 * 师傅端设置页面
 * Created by wql on 2016/6/17.
 */
public class SettingActivity extends BaseActivity {


    private View header;
    private RecyclerViewAutoRefreshUpgraded mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.engineer_personal_center);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("设置"));
        findAllView();
        adapter();
    }

    private void adapter() {
        mList.setLayoutManager(new LinearLayoutManager(this));
//        mList.addItemDecoration(new RecyclerViewGridItemDecoration(getActivity()));
        final List<MeItemBean> data = new ArrayList<>();
        data.add(new MeItemBean(R.mipmap.hj_icon, "意见反馈", true, true, false));
        data.add(new MeItemBean(R.mipmap.zx_icon, "关于我们", true, true, false));
        data.add(new MeItemBean(R.mipmap.rz_icon, "推荐给朋友", true, false, true));
        data.add(new MeItemBean(R.mipmap.sc_icon, "清理缓存", true, true, false));
        data.add(new MeItemBean(R.mipmap.pl_icon, "服务协议条款", true, false, false));
        MeItemAdapter adapter = new MeItemAdapter(this, data);
        adapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                if ("意见反馈".equals(data.get(position).getItem_name())) {
//                    startActivity(new Intent(SettingActivity.this, EngPersonalDataActivity.class));
                } else if ("关于我们".equals(data.get(position).getItem_name())) {
//                    startActivity(new Intent(SettingActivity.this, MyCollectionActivity.class));
                } else if ("推荐给朋友".equals(data.get(position).getItem_name())) {
//                    startActivity(new Intent(SettingActivity.this, MyEvaluateActivity.class));
                } else if ("清理缓存".equals(data.get(position).getItem_name())) {
//                    startActivity(new Intent(SettingActivity.this, MyEvaluateActivity.class));
                } else if ("服务协议条款".equals(data.get(position).getItem_name())) {
//                    startActivity(new Intent(SettingActivity.this, MyEvaluateActivity.class));
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
