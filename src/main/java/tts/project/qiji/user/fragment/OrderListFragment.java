package tts.project.qiji.user.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseFragment;
import tts.project.qiji.R;
import tts.project.qiji.adapter.OrderAdapter;
import tts.project.qiji.bean.EventBusBean;
import tts.project.qiji.bean.OrderBean;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.user.MapActivity;
import tts.project.qiji.user.OrderActivity;
import tts.project.qiji.user.OrderDetailsActivity;
import tts.project.qiji.user.PayActivity;
import tts.project.qiji.user.ServiceConfirmActivity;
import tts.project.qiji.utils.Util;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderListFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;//订单状态
    private String mParam2;//订单状态代码
    private RecyclerViewAutoRefreshUpgraded list;
    private int currentPage = 1;

    private boolean isShow;
    private boolean isFirst = false;
    private List<OrderBean> mData;
    private String order_id;
    private String engineerPhone;

    public OrderListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderListFragment newInstance(String param1, String param2) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        CustomUtils.showTipShort(fragment.getActivity(), param1);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
//        Logger.d("=====onCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isShow = isVisibleToUser;
        if (rootView != null && isVisibleToUser) {
            startRequestData(getData);
            adapter();
        }
//        Logger.d("=====setUserVisibleHint   " + isVisibleToUser);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Logger.d("=====onCreateView");
        // Inflate the layout for this fragment
        //inflater.inflate(R.layout.fragment_order_list, container, false)
        return setContentView(R.layout.fragment_order_list, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Logger.d("=====onActivityCreated");
        if (rootView != null && !isFirst) {
            isFirst = true;
            startRequestData(getData);
            findAllView();
            adapter();
        }
    }

    private void adapter() {
        mData = new ArrayList<>();
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        OrderAdapter orderAdapter = new OrderAdapter(getActivity(), mData);
        list.setAdapter(orderAdapter);
        orderAdapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                startActivityForResult(new Intent(getActivity(), OrderDetailsActivity.class).putExtra("order_id", mData.get(position).getOrder_id()), 10002);
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
        orderAdapter.setListener(new OrderAdapter.OnClickActionListener() {

            @Override
            public void cancelOrder(int pos) {
                order_id = mData.get(pos).getOrder_id();
                Util.createSimpleDialog(getActivity(), "取消订单", "您确定要取消订单吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestData(cancel);
                    }
                });

            }

            @Override
            public void hurryOrder(int pos) {
                order_id = mData.get(pos).getOrder_id();
                startRequestData(hurryOrder);
            }

            @Override
            public void modifyOrder(int pos) {
                order_id = mData.get(pos).getOrder_id();
                startActivity(new Intent(getActivity(), OrderActivity.class));
//                startRequestData(modify);
            }

            @Override
            public void serviceConfirm(int pos) {
                order_id = mData.get(pos).getOrder_id();
                if ("2".equals(MyAccountMoudle.getInstance().getUserInfo().getState()) && "1".equals(mData.get(pos).getType())) {
                    startActivityForResult(new Intent(getActivity(), ServiceConfirmActivity.class).putExtra("order_id", order_id), 10001);
                } else {
                    startActivity(new Intent(getActivity(), PayActivity.class).putExtra("order_number", mData.get(pos).getOrder_number())
                            .putExtra("money", mData.get(pos).getAmount()).putExtra("order_id", order_id));
//                startActivityForResult(new Intent(getActivity(), ServiceConfirmActivity.class).putExtra("order_id", order_id), 10001);
                }
            }

            @Override
            public void contactEngineer(int pos) {
                if (mData.get(pos).getEngineer().getPhone() != null) {
                    engineerPhone = mData.get(pos).getEngineer().getPhone().toString();

                    Util.createSimpleDialog(getActivity(), "呼叫师傅", "确认呼叫师傅电话" + engineerPhone + "?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            applyPermission(1, Manifest.permission.CALL_PHONE);
                        }
                    });
                }
            }

            @Override
            public void serviceGPS(int pos) {
//                mData.get(pos).getEngineer().ge
                startActivity(new Intent(getActivity(), MapActivity.class).putExtra("from", "2"));
            }
        });
    }

    private void findAllView() {
        list = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.mlist);
        list.setIsHead(true);
        list.setIsFooter(true);
        list.setOnRefreshListener(new RecyclerViewAutoRefreshUpgraded.OnRefreshListener() {
            @Override
            public void onLoadMore() {
                currentPage++;
                startRequestData(loadMore);
            }

            @Override
            public void onRefreshData() {
                startRequestData(getData);
            }
        });
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("state", mParam2);
                params.put("page", "1");
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Order&a=orderlist", params);
                break;
            case loadMore:
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("state", mParam2);
                params.put("page", currentPage + "");
                getDataWithPost(loadMore, Host.hostUrl + "api.php?m=Api&c=Order&a=orderlist", params);
                break;
            case cancel:
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("order_id", order_id);
                getDataWithPost(cancel, Host.hostUrl + "/api.php?m=Api&c=Order&a=cancel_order", params);
                break;
            case hurryOrder:
                params = new ArrayMap<>();
                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("order_id", order_id);
                getDataWithPost(hurryOrder, Host.hostUrl + "api.php?m=Api&c=Order&a=reminder", params);
                break;
            case modify:
//                params = new ArrayMap<>();
//                params.put("uid", MyAccountMoudle.getInstance().getUserInfo().getUser_id());
//                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
//                params.put("order_id", order_id);
//                getDataWithPost(modify, Host.hostUrl + "/api.php?m=Api&c=Order&a=cancel_order", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        Logger.json(response);
        switch (index) {
            case getData:
                mData.clear();
                List<OrderBean> temp = new Gson().fromJson(response, new TypeToken<List<OrderBean>>() {
                }.getType());
                mData.addAll(temp);
                list.notifyDataSetChanged();
                break;
            case loadMore:
                List<OrderBean> temp2 = new Gson().fromJson(response, new TypeToken<List<OrderBean>>() {
                }.getType());
                mData.addAll(temp2);
                list.notifyDataSetChanged();
                break;
            case cancel:
                startRequestData(getData);
                break;
            case hurryOrder:
                Util.createSimpleAlert(getActivity(), "催单成功", "您已成功催单！");
                break;
        }
        list.setOnRefreshFinished(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 10001:

                    break;
                case 10002:

                    startRequestData(getData);
                    break;
            }
        }
    }

    public void onEventMainThread(EventBusBean eventBusBean) {
        if (eventBusBean.isRefresh()) {
            startRequestData(getData);
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
        if (!TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getService_phone())) {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + MyAccountMoudle.
                    getInstance().getUserInfo().getService_phone())));
        }
    }

    @Override
    protected void doFailed(int what, int index, String response) {
//        super.doFailed(what, index, response);
        if (isShow) {
            CustomUtils.showTipLong(getActivity(), response);
        }
        mData.clear();
        list.setOnRefreshFinished(true);
        list.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
