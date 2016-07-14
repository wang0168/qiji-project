package tts.project.qiji.engineer;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.RatingBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.JsonUtils;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.adapter.EvaluateAdapter;
import tts.project.qiji.bean.EvaluateBean;
import tts.project.qiji.common.MyAccountMoudle;


public class MyEvaluateActivity extends BaseActivity {
    @Bind(R.id.evealuate_recy)
    RecyclerViewAutoRefreshUpgraded list_view;
    private RatingBar ratingBar;
    List<EvaluateBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myevealuate);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("我的评价"));
        findAllView();
        adapter();
        startRequestData(getData);
    }

    private void adapter() {
        mData = new ArrayList<>();
        EvaluateAdapter adapter = new EvaluateAdapter(this, mData);
        list_view.setAdapter(adapter);
    }

    private void findAllView() {
        list_view.setLayoutManager(new LinearLayoutManager(this));
        ratingBar = (RatingBar) findViewById(R.id.ratingbar);
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
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Engineer&a=getmypingjia", params);
                break;
        }

    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                List<EvaluateBean> temp = new Gson().fromJson(JsonUtils.getJsonFromJson(response, "pingjia"), new TypeToken<List<EvaluateBean>>() {
                }.getType());
                mData.clear();
                mData.addAll(temp);
                if (!TextUtils.isEmpty(JsonUtils.getJsonFromJson(response, "allxing"))) {
                    ratingBar.setRating(Float.parseFloat(JsonUtils.getJsonFromJson(response, "allxing")));
                }
                list_view.notifyDataSetChanged();
                Logger.json(response);
                break;
        }
    }

}
