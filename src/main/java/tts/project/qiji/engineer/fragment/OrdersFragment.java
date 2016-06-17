package tts.project.qiji.engineer.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tts.moudle.api.adapter.FragmentViewPagerAdapter;
import tts.project.qiji.BaseFragment;
import tts.project.qiji.R;
import tts.project.qiji.fragment.OrderListFragment;

/**
 * 代付款
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends BaseFragment {


    private TabLayout tablayout;
    private ViewPager orderFragment;
    private List<Fragment> fragmentList;
    private List<String> list_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_order, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (rootView != null) {
            findAllView();
        }
    }

    private void findAllView() {
        list_title = new ArrayList<>();
        fragmentList = new ArrayList<>();
        list_title.add("企业订单");
        list_title.add("普通订单");
        fragmentList.add(OrderListFragment.newInstance(null, null));
        fragmentList.add(OrderListFragment.newInstance(null, null));
        tablayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        orderFragment = (ViewPager) rootView.findViewById(R.id.orderFragment);
        orderFragment.setAdapter(new FragmentViewPagerAdapter(getChildFragmentManager(), fragmentList,
                list_title));
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.setupWithViewPager(orderFragment);
    }

}
