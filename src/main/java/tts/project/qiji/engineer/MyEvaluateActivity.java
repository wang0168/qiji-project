package tts.project.qiji.engineer;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;


public class MyEvaluateActivity extends BaseActivity {
    @Bind(R.id.evealuate_recy)
    RecyclerViewAutoRefreshUpgraded list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myevealuate);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("我的评价"));
        initData();
    }


    private void initData() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("1111");
        list.add("2222");
        list.add("3333");
        list.add("3333");
//        EvaluateAdapter adapter = new EvaluateAdapter(this, list);
//        list_view.setAdapter(adapter);

    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        CustomUtils.showTipShort(this, response);
        finish();
    }

}
