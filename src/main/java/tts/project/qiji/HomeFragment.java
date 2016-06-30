package tts.project.qiji;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.activity.AboutActivity;
import tts.moudle.api.adapter.BannerAdapter;
import tts.moudle.api.bean.BannerBean;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.cityapi.CityBean;
import tts.moudle.api.cityapi.ProvinceSelectActivity;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.BannerView;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.moudle.api.widget.RecyclerViewGridItemDecoration;
import tts.project.qiji.activity.CallServiceActivity;
import tts.project.qiji.adapter.HomeSortAdapter;
import tts.project.qiji.bean.HomeSortBean;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.login.LoginActivity;
import tts.project.qiji.utils.Util;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment implements OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View header;
    private View footer;
    private RecyclerViewAutoRefreshUpgraded mList;
    private List<HomeSortBean> data;
    private BarBean barBean;
    private RelativeLayout layout_call;
    private RelativeLayout layout_advisory;
    private BannerView bannerView;
    private final int get_banner = 102;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //inflater.inflate(R.layout.fragment_home, container, false)
        return setContentView(R.layout.fragment_home, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        barBean = new BarBean().setMsg("用户版").setSubTitle("上海");
        setTitle(barBean);
        if (!isLoad) {
            isLoad = true;
            findAllView();
            adapter();
        }
        startRequestData(getData);
        startRequestData(get_banner);
    }

    @Override
    public void doIcon() {
//        super.doIcon();
        startActivityForResult(new Intent(getActivity(), ProvinceSelectActivity.class), 3000);
    }

    RecyclerViewGridItemDecoration recyclerViewGridItemDecoration;

    private void adapter() {
        mList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerViewGridItemDecoration = new RecyclerViewGridItemDecoration(getActivity());
        mList.addItemDecoration(recyclerViewGridItemDecoration);
        data = new ArrayList<>();

        HomeSortAdapter homeSortAdapter = new HomeSortAdapter(getActivity(), data);
        homeSortAdapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                startActivity(new Intent(getActivity(), CallServiceActivity.class).putExtra("data", data.get(position)));
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
        mList.setAdapter(homeSortAdapter);
    }

    private void findAllView() {
        header = LayoutInflater.from(getActivity()).inflate(R.layout.banner_slider_item, null, false);
        footer = LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_footer, null, false);
        mList = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.mlist);
        mList.addFooter(footer);
        mList.addHeader(header);
        bannerView = (BannerView) header.findViewById(R.id.bannerView);
        layout_call = (RelativeLayout) footer.findViewById(R.id.layout_call);
        layout_advisory = (RelativeLayout) footer.findViewById(R.id.layout_advisory);
        layout_call.setOnClickListener(this);
        layout_advisory.setOnClickListener(this);

    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                if (TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getUser_id())) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Order&a=getfuwulist", params);
                break;
            case get_banner:
                params = new ArrayMap<>();
                getDataWithPost(get_banner, Host.hostUrl + "api.php?m=Api&c=Index&a=index", params);
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
                data.addAll(temp);
                mList.notifyDataSetChanged();
                break;
            case get_banner:
                final List<BannerBean> bannerBeans = new Gson().fromJson(response, new TypeToken<List<BannerBean>>() {
                }.getType());
                BannerAdapter bannerAdapter = new BannerAdapter(getActivity(), bannerBeans);
                bannerView.setAdapter(bannerAdapter);
                bannerAdapter.setOnItemClickListener(new BannerAdapter.OnItemClickListener() {
                    @Override
                    public void doClick(int position) {
                        if (!TextUtils.isEmpty(bannerBeans.get(position).getUrl())) {
//                            startActivity(new Intent(getActivity(), AboutActivity.class).putExtra("title",
//                                    bannerBeans.get(position).getTitle()).putExtra("url", bannerBeans.get(position).getUrl()));
                        } else {
                            CustomUtils.showTipShort(getActivity(), "暂无链接地址");
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 3000:
                    CityBean cityBean = (CityBean) data.getSerializableExtra("cityBean");
                    barBean.setSubTitle(cityBean.getName().trim());
                    setTitle(barBean);
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_call:
                Util.createSimpleDialog(getActivity(), "呼叫客户", "确认呼叫客服电话15179165382？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        applyPermission(1, Manifest.permission.CALL_PHONE);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

    @Override
    protected void applyPermissionsFailed() {
        super.applyPermissionsFailed();
        CustomUtils.showTipShort(getActivity(), "无拨打电话权限,请授予权限后再试");
    }

    @Override
    protected void startApplyPermissions(int index) {
        super.startApplyPermissions(index);
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "15179165382")));
    }
}
