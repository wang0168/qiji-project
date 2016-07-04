package tts.project.qiji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.adapter.HomeSortAdapter;
import tts.project.qiji.bean.HomeSortBean;
import tts.project.qiji.utils.ImageLoader;

/**
 * 呼叫服务
 */
public class CallServiceActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded mlist;
    private View header;
    private HomeSortBean data;
    private ImageView iconImg;
    private TextView service_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_service);
        setTitle(new BarBean().setMsg("呼叫服务"));
        data = (HomeSortBean) getIntent().getSerializableExtra("data");
        findAllView();
        adapter();

    }

    private void bindData() {
        service_name.setText(data.getName());
        ImageLoader.load(this, data.getImg(), iconImg);
    }

    private void adapter() {

        mlist.setLayoutManager(new GridLayoutManager(this, 3));
        if (data == null) {
            CustomUtils.showTipShort(this, "数据异常 CallServiceAct  1");
            return;
        }
        bindData();
        HomeSortAdapter callServiceAdapter = new HomeSortAdapter(this, data.getSort_types());
        mlist.setAdapter(callServiceAdapter);
        callServiceAdapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                if ("1".equals(data.getSort_types().get(position).getXiadan())) {
//                    CustomUtils.showTipShort(CallServiceActivity.this,"已经是最后一级分类啦");
                    startActivityForResult(new Intent(CallServiceActivity.this, OrderActivity.class).
                            putExtra("price", data.getPrice()).putExtra("service_id", data.getFuwu_id()), 10001);
                    return;
                }
                startActivityForResult(new Intent(CallServiceActivity.this, CallServiceActivity.class).putExtra("data", data.getSort_types().get(position)), 10001);
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
    }

    private void findAllView() {
        header = LayoutInflater.from(this).inflate(R.layout.layout_call_service_header, null, false);
        iconImg = (ImageView) header.findViewById(R.id.iconImg);
        service_name = (TextView) header.findViewById(R.id.service_name);
        mlist = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.mlist);
        mlist.addHeader(header);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 10001:
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    }
}
