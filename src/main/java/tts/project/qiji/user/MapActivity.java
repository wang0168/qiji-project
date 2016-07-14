package tts.project.qiji.user;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.BaseActivity;
import tts.project.qiji.R;
import tts.project.qiji.bean.DistanceBean;
import tts.project.qiji.bean.EventBusBean;
import tts.project.qiji.common.MyAccountMoudle;

public class MapActivity extends BaseActivity {

    @Bind(R.id.tv_bottombar_home)
    TextView tvBottombarHome;
    @Bind(R.id.tv_bottombar_order)
    TextView tvBottombarOrder;
    @Bind(R.id.tv_bottombar_my)
    TextView tvBottombarMy;
    @Bind(R.id.home_bottom_bar)
    LinearLayout homeBottomBar;
    private BaiduMap mBaiduMap;
    private MapView mMapView;
    private GeoCodeResult fuwu_address;
    private String order_id;
    private String from;//1下单；2查看位置
    private String lag;
    private String log;
    private List<DistanceBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("地图").setIsRemoveBack(true));
        fuwu_address = getIntent().getParcelableExtra("fuwu_address");
        order_id = getIntent().getStringExtra("order_id");
        from = getIntent().getStringExtra("from");
        mMapView = (MapView) findViewById(R.id.map_view);
        mBaiduMap = mMapView.getMap();
        //
        if ("1".equals(from)) {
            startRequestData(getData);
            if (fuwu_address != null) {
                mBaiduMap.setMyLocationEnabled(true);
                LatLng latLng = new LatLng(fuwu_address.getLocation().latitude, fuwu_address.getLocation().longitude);
                //刷新地图
//            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(latLng, 14.0f);
//            mBaiduMap.setMapStatus(msu);


                MyLocationData locData = new MyLocationData.Builder().accuracy(5000)
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(fuwu_address.getLocation().latitude)
                        .longitude(fuwu_address.getLocation().longitude).build();
                mBaiduMap.setMyLocationData(locData);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(latLng).zoom(13.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            } else {
                CustomUtils.showTipShort(this, "未获取到相关信息");
            }
        } else {
            homeBottomBar.setVisibility(View.GONE);
            lag = getIntent().getStringExtra("lag");
            log = getIntent().getStringExtra("log");
            if (lag != null && log != null) {
                OverlayMarker(Double.parseDouble(lag), Double.parseDouble(log));
            }

            //刷新地图
            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(new LatLng(
                    Double.parseDouble(lag), Double.parseDouble(log)), 16.0f);
            mBaiduMap.setMapStatus(msu);
        }

//        //绘制标记点
//        OverlayMarker(0, 0);

    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params = new ArrayMap<>();
        params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
        params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
        if (TextUtils.isEmpty(order_id)) {
            CustomUtils.showTipShort(this, "数据错误");
            return;
        }
        params.put("order_id", order_id);
        getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Order&a=get_lat_lng", params);
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        mData = new Gson().fromJson(response, new TypeToken<List<DistanceBean>>() {
        }.getType());
        Logger.json(response);
        if (mData != null && mData.size() > 0) {
            markerEngineer(mData);
        }
    }

    private void markerEngineer(List<DistanceBean> mData) {

        for (DistanceBean d : mData) {

            OverlayMarker(Double.parseDouble(d.getLag()), Double.parseDouble(d.getLog()),R.mipmap.gcs);
        }
    }


    private void OverlayMarker(double lat, double log) {
        //定义Maker坐标点
        LatLng point = new LatLng(lat, log);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_marker);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }

    private void OverlayMarker(double lat, double log, int icon) {
        //定义Maker坐标点
        LatLng point = new LatLng(lat, log);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(icon);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }

    private void OverlayMarker(double lat, double log, String icon) {
        //定义Maker坐标点
        LatLng point = new LatLng(lat, log);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromPath(icon);
//                .fromView(icon);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @OnClick({R.id.tv_bottombar_home, R.id.tv_bottombar_order, R.id.tv_bottombar_my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bottombar_home:

                break;
            case R.id.tv_bottombar_order:
                EventBus.getDefault().post(new EventBusBean().setHomePage("1"));
                break;
            case R.id.tv_bottombar_my:
                EventBus.getDefault().post(new EventBusBean().setHomePage("2"));
                break;
        }
        finish();
    }
}
