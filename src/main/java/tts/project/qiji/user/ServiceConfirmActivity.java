package tts.project.qiji.user;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.bean.EventBusBean;
import tts.project.qiji.common.MyAccountMoudle;

/**
 * 确认服务
 */
public class ServiceConfirmActivity extends BaseActivity implements OnClickListener {

    @Bind(R.id.rating)
    RatingBar rating;
    @Bind(R.id.edit_assess)
    EditText editAssess;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.action)
    TextView action;
    private String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_confirm);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("确认服务"));
        order_id = getIntent().getStringExtra("order_id");
        action.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action:
                startRequestData(submitData);
                break;
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case submitData:
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("order_id", order_id);
                params.put("xing", rating.getRating() + "");
                params.put("content", editAssess.getText().toString().trim());
                getDataWithPost(submitData, Host.hostUrl + "api.php?m=Api&c=Order&a=affirmorder", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        CustomUtils.showTipShort(this, response);
//        startActivity(new Intent(this,));
        EventBus.getDefault().post(new EventBusBean().setRefresh(true).setUserOrderPage("4"));
        setResult(RESULT_OK);
        finish();
    }
}
