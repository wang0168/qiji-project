package tts.project.hoop.engineer;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.hoop.BaseActivity;
import tts.project.hoop.R;
import tts.project.hoop.adapter.EvaluateAdapter;
import tts.project.hoop.adapter.VideoMeetingAdapter;

public class MyEvaluateActivity extends BaseActivity {
    @Bind(R.id.evealuate_recy)
    RecyclerViewAutoRefreshUpgraded list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myevealuate);
        ButterKnife.bind(this);
        barBean.setMsg("我的评价");
        setTitle(barBean);
        initData();
    }


    private void initData() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("1111");
        list.add("2222");
        list.add("3333");
        list.add("3333");
        EvaluateAdapter adapter = new EvaluateAdapter(this, list);
        list_view.setAdapter(adapter);

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
