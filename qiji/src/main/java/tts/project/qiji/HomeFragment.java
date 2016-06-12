package tts.project.qiji;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tts.moudle.api.bean.BarBean;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.moudle.api.widget.RecyclerViewGridItemDecoration;
import tts.project.qiji.adapter.HomeSortAdapter;
import tts.project.qiji.bean.HomeSortBean;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View header;
    private View footer;
    private RecyclerViewAutoRefreshUpgraded mList;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //inflater.inflate(R.layout.fragment_home, container, false)
        return setContentView(R.layout.fragment_home, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(new BarBean().setMsg("用户版").setSubTitle("上海"));
        findAllView();
        adapter();
    }

    private void adapter() {
        mList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mList.addItemDecoration(new RecyclerViewGridItemDecoration(getActivity()));
        List<HomeSortBean> data = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            data.add(new HomeSortBean("网络设备" + i, null, R.mipmap.wlsb));
        }
        HomeSortAdapter homeSortAdapter = new HomeSortAdapter(getActivity(), data);
        mList.setAdapter(homeSortAdapter);
    }

    private void findAllView() {

        header = LayoutInflater.from(getActivity()).inflate(R.layout.banner_slider_item, null, false);
        footer = LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_footer, null, false);
        mList = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.mlist);
        mList.addFooter(footer);
        mList.addHeader(header);
    }
}
