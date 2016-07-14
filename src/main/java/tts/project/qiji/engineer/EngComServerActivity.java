package tts.project.qiji.engineer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import tts.moudle.api.Host;
import tts.moudle.api.activity.CustomPictureSelectorView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.ImgBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.ImageFactory;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.GridViewInScrollView;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.adapter.EngImgAdapter;
import tts.project.qiji.bean.EventBusBean;
import tts.project.qiji.common.MyAccountMoudle;

//import tts.moudle.api.utils.ImageFactory;


/**
 * Created by sjb on 2016/1/27.
 */
public class EngComServerActivity extends BaseActivity {


    @Bind(R.id.server_sketch)
    EditText serverSketch;
    @Bind(R.id.gv_photo)
    GridViewInScrollView gvPhoto;
    @Bind(R.id.add_photo)
    ImageView addPhoto;
    @Bind(R.id.submit)
    Button submit;

    EngImgAdapter adapter;

    private List<ImgBean> imgBeans;
    private String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engcom_server);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("确认服务"));
        order_id = getIntent().getStringExtra("order_id");

    }


    @OnClick({R.id.add_photo, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_photo:
                startActivityForResult(new Intent(this, CustomPictureSelectorView.class).putExtra("maxCount", 5), 1000);
                break;
            case R.id.submit:
                if (TextUtils.isEmpty(serverSketch.getText().toString().trim())) {
                    CustomUtils.showTipShort(this, "请简要叙述服务内容");
                    return;
                }
                if (imgBeans == null) {
                    CustomUtils.showTipShort(this, "请上传相关服务图片");
                    return;
                }
                startRequestData(submitData);
                break;
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        showTipMsg("上传中。。。");
        Map<String, String> params = new ArrayMap<>();
        List<PostFormBuilder.FileInput> files = new ArrayList<>();
        params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
        params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
        params.put("order_id", order_id);
        params.put("jianshu", serverSketch.getText().toString().trim());
        if (imgBeans != null) {
            for (int i = 0; i < imgBeans.size(); i++) {
                ImageFactory imageFactory = new ImageFactory();
                File file = new File(this.getExternalCacheDir(), new Date().getTime() + "");
                String facePath = file.getPath();
                try {
                    imageFactory.storeImage(imageFactory.ratio(imgBeans.get(i).getPath(), 400f, 400f), facePath);
                    files.add(new PostFormBuilder.FileInput("img" + i, "img" + i + ".jpg", new File(facePath)));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        uploadFile(submitData, Host.hostUrl + "api.php?m=Api&c=Engineer&a=fwtrue", params, files);
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        CustomUtils.showTipShort(this, "服务确认成功");
        EventBus.getDefault().post(new EventBusBean().setRefresh(true).setEngineerOrderPage("3"));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1000:
                if (resultCode == RESULT_OK) {
                    imgBeans = (List<ImgBean>) data.getSerializableExtra("imgBeans");
                    adapter = new EngImgAdapter(this, imgBeans);
                    gvPhoto.setAdapter(adapter);
                }
                break;
        }
    }
}
