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
import tts.project.qiji.engineer.fragment.orders.EngOrderDoneFragment;
import tts.project.qiji.engineer.fragment.orders.EngOrderOffFragment;
import tts.project.qiji.engineer.fragment.orders.EngWaitConfirmFragment;
import tts.project.qiji.engineer.fragment.orders.EngWaitServiceFragment;


public class OrderEngFragment extends BaseFragment {

    private TabLayout tablayout;
    private ViewPager orderFragment;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private List<String> list_title;
    private EngOrderDoneFragment orderDoneFragment;
    private EngWaitConfirmFragment waitConfirmFragment;
    private EngWaitServiceFragment waitServiceFragment;
    private EngOrderOffFragment orderOffFragment;
    private boolean isAdd = true;

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
        list_title = new ArrayList<String>();
        list_title.add("待上门");
        list_title.add("待服务");
        list_title.add("待确认");
        list_title.add("已完成");

        waitServiceFragment = new EngWaitServiceFragment();
        waitConfirmFragment = new EngWaitConfirmFragment();
        orderDoneFragment = new EngOrderDoneFragment();
        orderOffFragment = new EngOrderOffFragment();
        fragmentList.add(waitServiceFragment);
        fragmentList.add(waitConfirmFragment);
        fragmentList.add(orderDoneFragment);
        fragmentList.add(orderOffFragment);
        tablayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        orderFragment = (ViewPager) rootView.findViewById(R.id.orderFragment);


        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getChildFragmentManager(), fragmentList, list_title);
        orderFragment.setAdapter(adapter);

        tablayout.addTab(tablayout.newTab().setText(list_title.get(0)), true);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(1)), false);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(2)), false);
        tablayout.addTab(tablayout.newTab().setText(list_title.get(3)), false);

        tablayout.setupWithViewPager(orderFragment);
        tablayout.setTabsFromPagerAdapter(adapter);
    }


}
