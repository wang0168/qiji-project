package tts.project.qiji.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import tts.moudle.api.bean.BarBean;
import tts.moudle.api.cityapi.CityMoudle;
import tts.moudle.api.cityapi.ProvinceSelectPopupwindow;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;

/**
 * 编辑页面
 * Created by chen on 2016/3/15.
 */
public class EditActivity extends BaseActivity {
    private EditText editText;
    private EditText edit_picker;

    private String oldStr;
    private String paramKey;
    private String title;
    private String provinceStr;
    private String cityStr;
    private String countryStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        oldStr = getIntent().getStringExtra("oldStr");
        title = getIntent().getStringExtra("title");
        paramKey = getIntent().getStringExtra("paramKey");

        editText = (EditText) findViewById(R.id.edit_input);
        edit_picker = (EditText) findViewById(R.id.edit_picker);
        setTitle(new BarBean().setMsg(title));
        if (!TextUtils.isEmpty(oldStr)) {
            editText.setText(oldStr);
            editText.setSelection(oldStr.length());
        }
        if ("地址".equals(paramKey)) {
            edit_picker.setVisibility(View.VISIBLE);
            edit_picker.setHint("点击选择地区");
            edit_picker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CityMoudle.getInstance().showProvincePopupwindow(EditActivity.this, v, 2, new ProvinceSelectPopupwindow.OnClickListener() {
                        @Override
                        public void doClick(String province, String city, String area) {
                            provinceStr = province;
                            cityStr = city;
                            countryStr = area;
                            edit_picker.setText(provinceStr + cityStr + countryStr);
                        }
                    });
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void doIcon() {
        back();
    }

    private void back() {
        if ("地址".equals(paramKey)) {
            Intent intent = new Intent();
            intent.putExtra("newStr", editText.getText().toString().trim());
            intent.putExtra("province", provinceStr);
            intent.putExtra("city", cityStr);
            intent.putExtra("area", countryStr);
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_OK, new Intent().putExtra("newStr", editText.getText().
                    toString().trim()));
        }
        finish();
    }
}
