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
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.BaseFragment;
import tts.project.qiji.R;
import tts.project.qiji.bean.EventBusBean;


public class TakeOrderFragment extends BaseFragment {

    private TabLayout tablayout;
    private ViewPager orderFragment;
    private List<Fragment> fragmentList;
    private List<String> list_title;
    private boolean isAdd = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return setContentView(R.layout.activity_engineer, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isAdd) {
            findAllView();
        }
    }

    private void findAllView() {
        isAdd = false;
        list_title = new ArrayList<>();
        fragmentList = new ArrayList<>();
        list_title.add("待上门");
        list_title.add("待服务");
        list_title.add("待确认");
        list_title.add("已完成");
        list_title.add("已拒单");

        fragmentList.add(TakeOrderListFragment.newInstance("待上门", "2"));
        fragmentList.add(TakeOrderListFragment.newInstance("待服务", "3"));
        fragmentList.add(TakeOrderListFragment.newInstance("待确认", "4"));
        fragmentList.add(TakeOrderListFragment.newInstance("已完成", "5"));
        fragmentList.add(TakeOrderListFragment.newInstance("已拒单", "-1"));
        tablayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        orderFragment = (ViewPager) rootView.findViewById(R.id.orderFragment);


        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getChildFragmentManager(), fragmentList, list_title);
        orderFragment.setAdapter(adapter);

        tablayout.setupWithViewPager(orderFragment);
        tablayout.setTabsFromPagerAdapter(adapter);
    }

    public void onEventMainThread(EventBusBean eventBusBean) {
        if (!TextUtils.isEmpty(eventBusBean.getEngineerOrderPage())) {
            switch (eventBusBean.getEngineerOrderPage()) {
                case "1":
                    orderFragment.setCurrentItem(0);
                    break;
                case "2":
                    orderFragment.setCurrentItem(1);
                    break;
                case "3":
                    orderFragment.setCurrentItem(2);
                    break;
                case "4":
                    orderFragment.setCurrentItem(3);
                    break;
                case "5":
                    orderFragment.setCurrentItem(4);
                    break;
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
