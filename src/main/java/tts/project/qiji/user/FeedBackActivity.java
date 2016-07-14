package tts.project.qiji.user;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.common.MyAccountMoudle;


/**
 * 意见反馈
 */
public class FeedBackActivity extends BaseActivity implements View.OnClickListener {
    private EditText edit_feedback;
    private Button btn_submit;
    private String from;//1用户端，2工程师端

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        setTitle(new BarBean().setMsg("意见反馈").setSubTitle("返回"));
        from = getIntent().getStringExtra("from");
        edit_feedback = (EditText) findViewById(R.id.edit_feedback);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
    }


    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        if (index == submitData) {
            params = new ArrayMap<>();
            params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
            params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
            params.put("content", edit_feedback.getText().toString().trim());
            params.put("type", from);
            getDataWithPost(submitData, Host.hostUrl + "api.php?m=Api&c=Index&a=feedback", params);
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        CustomUtils.showTipShort(this, response);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (!TextUtils.isEmpty(edit_feedback.getText().toString())) {
            startRequestData(submitData);
        } else
            CustomUtils.showTipShort(this, "请填写内容");
    }
}
