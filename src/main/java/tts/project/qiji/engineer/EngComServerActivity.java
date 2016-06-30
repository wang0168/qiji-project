package tts.project.qiji.engineer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tts.moudle.api.Host;
import tts.moudle.api.activity.CustomPictureSelectorView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.ImgBean;
import tts.moudle.api.utils.CustomUtils;
//import tts.moudle.api.utils.ImageFactory;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.GridViewInScrollView;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.adapter.EngImgAdapter;


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
    private ArrayList<String> imgs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engcom_server);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("确认服务"));
        adapter = new EngImgAdapter(this, imgs);
        gvPhoto.setAdapter(adapter);
    }


    @OnClick({R.id.add_photo, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_photo:
                startActivityForResult(new Intent(this, CustomPictureSelectorView.class), 1000);
                break;
            case R.id.submit:
                if (TextUtils.isEmpty(serverSketch.getText().toString().trim())) {
                    CustomUtils.showTipShort(this, "请简要叙述服务内容");
                    return;
                }
                startRequestData(getData);
                break;
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        showTipMsg("上传中。。。");
        Map<String, String> params = new HashMap<>();
        List<PostFormBuilder.FileInput> files = new ArrayList<>();
//        params.put("member_id", AccountMoudle.getInstance().getUserBean().getMember_id());
//        params.put("token", AccountMoudle.getInstance().getUserBean().getMem_token());
        params.put("order_id", getIntent().getStringExtra("order_id"));
        params.put("jianshu", serverSketch.getText().toString().trim());
        for (int i = 0; i < imgs.size(); i++) {
            files.add(new PostFormBuilder.FileInput("ss[" + i + "]", "img.jpg", new File(imgs.get(i))));
        }
        uploadFile(getData, Host.hostUrl + "api.php?m=Api&c=Engineer&a=fwtrue", params, files);
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        CustomUtils.showTipShort(this, "服务确认成功");
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
//                    ImageFactory imageFactory = new ImageFactory();
//                    for (int i = 0; i < imgBeans.size(); i++) {
//                        File file = new File(this.getExternalCacheDir(), new Date().getTime() + "");
//                        String facePath = file.getPath();
//                        try {
//                            imageFactory.storeImage(imageFactory.ratio(imgBeans.get(i).getPath(), 400f, 400f), facePath);
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        imgs.add(facePath);
//                    }
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
