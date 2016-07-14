package tts.project.qiji.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;
import java.util.Map;

import okhttp3.Request;
import tts.moudle.api.Host;
import tts.project.qiji.common.MyAccountMoudle;

public class LocationService extends Service {
    private static final int DELAY_TIME = 5 * 60 * 1000;
    private Context mContext;
    private BDLocation mBDLocation;
    /**
     * 定位SDK的核心类
     */
    private LocationClient mLocationClient;
    /**
     * 定位结果处理器 # class MyLocationListener implements BDLocationListener{}
     */
    private MyLocationListener mLocationListener;

    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

//        super.onCreate();
        // 初始化定位服务，配置相应参数
        initLocationService();
    }

    /**
     * 初始化定位服务，配置相应参数
     */
    private void initLocationService() {
        mLocationClient = new LocationClient(this.getApplicationContext());
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);

        LocationClientOption locationOption = new LocationClientOption();
        locationOption.setOpenGps(true);
        locationOption.setCoorType("bd09ll");
        locationOption.disableCache(true);
        locationOption.setPriority(LocationClientOption.GpsFirst);
        locationOption.setScanSpan(DELAY_TIME);
//        locationOption.setProdName(this.getString(R.string.loaction_prod_name));

        mLocationClient.setLocOption(locationOption);
        mLocationClient.start();
    }

    class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location

            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
            sendLocation(location);
        }
    }

    @Override
    public void onDestroy() {

        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
            if (mLocationListener != null) {
                mLocationClient.unRegisterLocationListener(mLocationListener);
            }
        }
    }

    public void sendLocation(final BDLocation location) {

        if (mBDLocation != null && location.getLatitude() == mBDLocation.getLatitude()
                && location.getLongitude() == mBDLocation.getLongitude()) {
            Logger.d("位置信息没有变化，不上传");
            return;
        }
        Logger.d(location.getRadius() + "=123xxx");
        Map<String, String> params = new ArrayMap<>();
        params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
        params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
        params.put("log", location.getLongitude() + "");
        params.put("lag", location.getLatitude() + "");
        try {
            OkHttpUtils.post().url(Host.hostUrl + "api.php?m=Api&c=Engineer&a=getweidu").params(params).tag(this).build().execute(new StringCallback() {
                @Override
                public void onError(Request request, Exception e) {
                    e.printStackTrace();
//                                Logger.wtf("xxxxxxxxxxxxxxxxxxxxxx" + e.printStackTrace(););
                }

                @Override
                public void onResponse(String response) {
                    Logger.wtf("xxx123xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx==" + response);
                    mBDLocation = location;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
