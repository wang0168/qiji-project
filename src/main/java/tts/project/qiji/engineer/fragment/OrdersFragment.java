package tts.project.qiji.engineer.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unionpay.tsmservice.data.Constant;

import java.util.ArrayList;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.moudle.AccountMoudle;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseFragment;
import tts.project.qiji.R;
import tts.project.qiji.bean.OrderBean;

/**
 * 代付款
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends BaseFragment {


    private RecyclerViewAutoRefreshUpgraded list;
    SwipeRefreshLayout swipeRefreshLayout;
    private OrderAdapter orderAdapter;
    private int currentPage;
    private ImageView noData;
    private Map<String, String> map;
    private ArrayList<OrderBean> data;
    private ArrayList<OrderBean> datas;
    private OrderEnginerrAdapter adapter;
    private int mPosition;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null && adapter == null) {
            startRequestData(Constant.init_ok);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_order_list, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (rootView != null) {
            findAllView();
//        }
    }

    private void findAllView() {
        startRequestData(Constant.init_ok);
        list = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.recycleview_order_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);
//        list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        noData = (ImageView) rootView.findViewById(R.id.no_data);
        list.setHeadVisible(true);
        list.setOnRefreshListener(new RecyclerViewAutoRefreshUpgraded.OnRefreshListener() {
            @Override
            public void onLoadMore() {
                startRequestData(Constant.updtae_ok);
            }

            @Override
            public void onRefreshData() {
                startRequestData(Constant.init_ok);
            }
        });
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> param;
        switch (index) {
            case Constant.init_ok:
                currentPage = 1;
                param = new ArrayMap<>();
                param.put("member_id", AccountMoudle.getInstance().getUserInfo().getMember_id());
                param.put("token", AccountMoudle.getInstance().getUserInfo().getMem_token());
                param.put("state", "1");
                param.put("page", currentPage + "");
                getDataWithPost(Constant.init_ok, Host.hostUrl + "api.php?m=Api&c=Engineer&a=orders", param);
                break;
            case Constant.updtae_ok:
                currentPage++;
                param = new ArrayMap<>();
                param.put("member_id", AccountMoudle.getInstance().getUserInfo().getMember_id());
                param.put("token", AccountMoudle.getInstance().getUserInfo().getMem_token());
                param.put("state", "1");
                param.put("page", currentPage + "");
                getDataWithPost(Constant.updtae_ok, Host.hostUrl + "api.php?m=Api&c=Engineer&a=orders", param);
                break;
            case Constant.submit_ok:
                param = new ArrayMap<>();
                param.put("member_id", AccountMoudle.getInstance().getUserInfo().getMember_id());
                param.put("token", AccountMoudle.getInstance().getUserInfo().getMem_token());
                param.put("order_id", datas.get(mPosition).getOrder_id());
                getDataWithPost(Constant.delete_ok, Host.hostUrl + "api.php?m=Api&c=Engineer&a=ordertrue", param);
                break;

        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case Constant.init_ok:
                initData(response);
                break;
            case Constant.updtae_ok:
                if (datas == null || orderAdapter == null) {
                    initData(response);
                    return;
                }
                data = new Gson().fromJson(response, new TypeToken<ArrayList<OrderBean>>() {
                }.getType());
                datas.addAll(data);
                list.notifyDataSetChanged();
                break;
            case Constant.submit_ok:
                startRequestData(Constant.init_ok);
                CustomUtils.showTipShort(getActivity(), "接单成功");
                break;
        }
        list.setOnRefreshFinished(true);
    }

    private void initData(String response) {
        datas = new Gson().fromJson(response, new TypeToken<ArrayList<OrderBean>>() {
        }.getType());

        adapter = new OrderEnginerrAdapter(getActivity(), datas);
        list.setAdapter(adapter);
        adapter.setOnItemClickListener(new OrderEnginerrAdapter.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                startActivity(new Intent(getActivity(), EngOrderDetailsActivity.class).putExtra("order_id", datas.get(position).getOrder_id()));
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }

            @Override
            public void Orders(int position) {
                startRequestData(Constant.submit_ok);
            }

            @Override
            public void Navigation(int position) {

            }

            @Override
            public void SerivceCcom(int position) {

            }

            @Override
            public void SerivceShang(int position) {

            }
        });
    }


    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        switch (index) {
            case Constant.init_ok:
                if (datas != null) {
                    datas.clear();
                    list.notifyDataSetChanged();
                }
//                list.setVisibility(View.VISIBLE);
//                noData.setVisibility(View.VISIBLE);
                break;
        }
        if (list != null) {
            list.setOnRefreshFinished(true);
        }
    }


}
