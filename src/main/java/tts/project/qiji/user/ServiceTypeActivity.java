package tts.project.qiji.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.adapter.ServiceTypeAdapter;
import tts.project.qiji.bean.HomeSortBean;
import tts.project.qiji.utils.Util;

public class ServiceTypeActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded list;
    private List<HomeSortBean> data = new ArrayList<>();
    private String fuwu_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_type);
        setTitle(new BarBean().setMsg("服务类型"));
        addMenu(new MenuItemBean().setTitle("确定"));
        findAllView();
        startRequestData(getData);
    }

    private void adapter() {
        ServiceTypeAdapter serviceTypeAdapter = new ServiceTypeAdapter(this, data);
        list.setAdapter(serviceTypeAdapter);
    }

    @Override
    protected void doMenu(MenuItemBean menuItemBean) {
        super.doMenu(menuItemBean);
        List<HomeSortBean> temp = data;
        for (HomeSortBean h : data) {
            for (HomeSortBean hsb : h.getSort_types()) {
                if (hsb.isChecked()) {
                    fuwu_id = fuwu_id + "," + hsb.getFuwu_id();
                }

            }

        }
        setResult(RESULT_OK, new Intent().putExtra("fuwu_id", fuwu_id));
        finish();
    }

    @Override
    public void doIcon() {
        alert();
    }

    private void alert() {
        Util.createSimpleDialog(this, "提示", "数据尚未保存，确定要离开本页？(点击右上角保存)", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(RESULT_OK, new Intent().putExtra("fuwu_id", ""));
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            alert();
            return false;
        }
        return super.onKeyDown(keyCode, event);

    }

    private void findAllView() {
        list = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.mlist);
        list.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Order&a=getfuwulist", params);
                break;

        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                data.clear();
                List<HomeSortBean> temp = new Gson().fromJson(response, new TypeToken<List<HomeSortBean>>() {
                }.getType());
                for (HomeSortBean h : temp) {
                    if (!"其他服务".equals(h.getName())) {
                        data.add(h);
                    }
                }
                adapter();
                break;

        }
    }

}
