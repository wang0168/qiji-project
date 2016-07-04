package tts.project.qiji.fragment;


import android.content.Intent;
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

import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseFragment;
import tts.project.qiji.R;
import tts.project.qiji.activity.OrderDetailsActivity;
import tts.project.qiji.activity.ServiceConfirmActivity;
import tts.project.qiji.adapter.OrderAdapter;
import tts.project.qiji.bean.OrderBean;
import tts.project.qiji.common.MyAccountMoudle;

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
    private List<OrderBean> data;
    private String order_id;

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
        data = new ArrayList<>();
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        OrderAdapter orderAdapter = new OrderAdapter(getActivity(), data);
        list.setAdapter(orderAdapter);
        orderAdapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                startActivity(new Intent(getActivity(), OrderDetailsActivity.class).putExtra("order_id", data.get(position).getOrder_id()));
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
        orderAdapter.setListener(new OrderAdapter.OnClickActionListener() {

            @Override
            public void cancelOrder(int pos) {
                order_id = data.get(pos).getOrder_id();
                startRequestData(cancel);
            }

            @Override
            public void hurryOrder(int pos) {
                order_id = data.get(pos).getOrder_id();
                startRequestData(hurryOrder);
            }

            @Override
            public void modifyOrder(int pos) {
                order_id = data.get(pos).getOrder_id();
                startRequestData(modify);
            }

            @Override
            public void serviceConfirm(int pos) {
                order_id = data.get(pos).getOrder_id();
                startRequestData(confirm);
            }

            @Override
            public void contactEngineer(int pos) {

            }

            @Override
            public void serviceGPS(int pos) {

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
            case confirm:
                startActivityForResult(new Intent(getActivity(), ServiceConfirmActivity.class), 10001);
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
                data.clear();
                List<OrderBean> temp = new Gson().fromJson(response, new TypeToken<List<OrderBean>>() {
                }.getType());
                data.addAll(temp);
                list.notifyDataSetChanged();
                break;
            case loadMore:
                List<OrderBean> temp2 = new Gson().fromJson(response, new TypeToken<List<OrderBean>>() {
                }.getType());
                data.addAll(temp2);
                list.notifyDataSetChanged();
                break;
        }
        list.setOnRefreshFinished(true);
    }

    @Override
    protected void doFailed(int what, int index, String response) {
//        super.doFailed(what, index, response);
        if (isShow) {
            CustomUtils.showTipLong(getActivity(), response);
        }
        list.setOnRefreshFinished(true);
    }
}
