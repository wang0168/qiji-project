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

import de.greenrobot.event.EventBus;
import tts.moudle.api.adapter.FragmentViewPagerAdapter;
import tts.project.qiji.BaseFragment;
import tts.project.qiji.R;
import tts.project.qiji.bean.EventBusBean;
import tts.project.qiji.common.MyAccountMoudle;


/**
 * 待接订单
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends BaseFragment {


    private TabLayout tablayout;
    private ViewPager orderFragment;
    private List<Fragment> fragmentList;
    private List<String> list_title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_orders, inflater, container, savedInstanceState);
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
        fragmentList.add(OrderListFragment.newInstance("企业订单", "1"));
        fragmentList.add(OrderListFragment.newInstance("普通订单", "2"));
        tablayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        orderFragment = (ViewPager) rootView.findViewById(R.id.viewpager);
        orderFragment.setAdapter(new FragmentViewPagerAdapter(getChildFragmentManager(), fragmentList,
                list_title));
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.setupWithViewPager(orderFragment);
        if ("1".equals(MyAccountMoudle.getInstance().getUserInfo().getState())) {
            orderFragment.setCurrentItem(1);
        }
    }

    public void onEventMainThread(EventBusBean eventBusBean) {
        if ("1".equals(eventBusBean.getEngineerOrderPage())) {
            orderFragment.setCurrentItem(0);
        } else if ("2".equals(eventBusBean.getEngineerOrderPage())) {
            orderFragment.setCurrentItem(1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
