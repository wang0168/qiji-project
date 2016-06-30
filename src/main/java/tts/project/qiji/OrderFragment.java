package tts.project.qiji;

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
import tts.moudle.api.bean.BarBean;
import tts.project.qiji.fragment.OrderListFragment;

/**
 * Created by shanghang on 2016/6/7.
 */
public class OrderFragment extends BaseFragment {
    private TabLayout tablayout;
    private ViewPager viewpager;
    private List<String> titlelist;
    private List<Fragment> fragments;
    private FragmentViewPagerAdapter adapter;

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
        fragments.add(OrderListFragment.newInstance("待指派", null));
        fragments.add(OrderListFragment.newInstance("待服务", null));
        fragments.add(OrderListFragment.newInstance("daiqueren ", null));
        fragments.add(OrderListFragment.newInstance("已完成", null));
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
}
