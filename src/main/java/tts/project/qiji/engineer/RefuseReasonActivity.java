package tts.project.qiji.engineer;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
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
 * 拒单原因
 */
public class RefuseReasonActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.edit_refuse_reason)
    EditText editRefuseReason;
    @Bind(R.id.action)
    TextView action;
    private final int refuse_order = 14;
    private String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refuse_reason);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("拒单原因"));
        order_id = getIntent().getStringExtra("order_id");
        action.setOnClickListener(this);
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case refuse_order:
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("order_id", order_id);
                getDataWithPost(refuse_order, Host.hostUrl + "/api.php?m=Api&c=Engineer&a=judan", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case refuse_order:
                EventBus.getDefault().post(new EventBusBean().setRefresh(true));
                CustomUtils.showTipShort(this, "拒单成功");
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action:
                startRequestData(refuse_order);
                break;
        }
    }
}
