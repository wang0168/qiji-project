package tts.project.qiji.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import tts.moudle.api.bean.BarBean;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;

/**
 * 确认服务
 */
public class ServiceConfirmActivity extends BaseActivity {

    @Bind(R.id.rating)
    RatingBar rating;
    @Bind(R.id.edit_assess)
    EditText editAssess;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.action)
    TextView action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_confirm);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("确认服务"));
    }
}
