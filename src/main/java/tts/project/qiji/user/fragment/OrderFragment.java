package tts.project.qiji.user.fragment;

import android.content.Intent;
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
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.BaseFragment;
import tts.project.qiji.R;
import tts.project.qiji.bean.EventBusBean;

/**
 * Created by shanghang on 2016/6/7.
 */
public class OrderFragment extends BaseFragment {
    private TabLayout tablayout;
    private ViewPager viewpager;
    private List<String> titlelist;
    private List<Fragment> fragments;
    private FragmentViewPagerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_order, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(new BarBean().setMsg("我的订单").setIsRemoveBack(true));
        findAllView();
        initTab();
    }

    private void initTab() {
        titlelist = new ArrayList<>();
        fragments = new ArrayList<>();
        titlelist.add("待指派");
        titlelist.add("待服务");
        titlelist.add("待确认");
        titlelist.add("已完成");
        fragments.add(OrderListFragment.newInstance("待指派", "1"));
        fragments.add(OrderListFragment.newInstance("待服务", "2"));
        fragments.add(OrderListFragment.newInstance("待确认", "3"));
        fragments.add(OrderListFragment.newInstance("已完成", "4"));
        adapter = new FragmentViewPagerAdapter(getChildFragmentManager(), fragments,
                titlelist);
        viewpager.setAdapter(adapter);
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.setupWithViewPager(viewpager);
    }

    private void findAllView() {
        tablayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onEventMainThread(EventBusBean eventBusBean) {
        if (!TextUtils.isEmpty(eventBusBean.getUserOrderPage())) {
            switch (eventBusBean.getUserOrderPage()) {
                case "1":
                    viewpager.setCurrentItem(0);
                    break;
                case "2":
                    viewpager.setCurrentItem(1);
                    break;
                case "3":
                    viewpager.setCurrentItem(2);
                    break;
                case "4":
                    viewpager.setCurrentItem(3);
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
