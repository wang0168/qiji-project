package tts.project.qiji.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseFragment;
import tts.project.qiji.R;
import tts.project.qiji.adapter.OrderAdapter;
import tts.project.qiji.engineer.EngOrderDetailsActivity;

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
    private String mParam1;
    private String mParam2;
    private RecyclerViewAutoRefreshUpgraded list;

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
        Logger.d("=====onCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView != null) {
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
        if (rootView != null) {
            findAllView();
            adapter();
        }
    }

    private void adapter() {
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        OrderAdapter orderAdapter = new OrderAdapter(getActivity(), null);

        list.setAdapter(orderAdapter);
        orderAdapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                Logger.d("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                startActivity(new Intent(getActivity(), EngOrderDetailsActivity.class));
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }

            @Override
            public void OrderCancel(int position) {

            }

            @Override
            public void OrderPayment(int position) {

            }

            @Override
            public void OrderCom(int position) {

            }

            @Override
            public void OrderEvaluate(int position) {

            }

            @Override
            public void lookPosition(int position) {

            }
        });
    }

    private void findAllView() {
        list = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.mlist);

    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        switch (index) {
            case getData:
                break;
            case loadMore:
                break;
        }
    }
}
