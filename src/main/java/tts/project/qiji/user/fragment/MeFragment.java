package tts.project.qiji.user.fragment;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.activity.AboutActivity;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.qiji.BaseFragment;
import tts.project.qiji.R;
import tts.project.qiji.adapter.MeItemAdapter;
import tts.project.qiji.bean.MeItemBean;
import tts.project.qiji.bean.UserInfoBean;
import tts.project.qiji.common.MyAccountMoudle;
import tts.project.qiji.engineer_manager.EngineerManagerActivity;
import tts.project.qiji.login.LoginActivity;
import tts.project.qiji.user.AddressManagerActivity;
import tts.project.qiji.user.BaseInfoActivity;
import tts.project.qiji.user.FeedBackActivity;
import tts.project.qiji.user.MyCollectionActivity;
import tts.project.qiji.utils.ImageLoader;
import tts.project.qiji.utils.LoginInfoSave;

/**
 * Created by shanghang on 2016/6/7.
 */
public class MeFragment extends BaseFragment {
    private View header;
    private RecyclerViewAutoRefreshUpgraded mList;
    private ImageView face_img;
    private TextView name;
    private RatingBar evaluate;
    private TextView phone;

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
        setTitle(new BarBean().setMsg("个人中心").setIsRemoveBack(true));
        findAllView();
        adapter();
    }

    private void adapter() {
        mList.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mList.addItemDecoration(new RecyclerViewGridItemDecoration(getActivity()));
        final List<MeItemBean> data = new ArrayList<>();
//        data.add(new MeItemBean(R.mipmap.scxh, "我的收藏", "", true, false, true));
        data.add(new MeItemBean(0, "地址管理", "", true, true, false));
        data.add(new MeItemBean(0, "意见反馈", "", true, true, false));
        data.add(new MeItemBean(0, "关于我们", "", true, true, false));
        data.add(new MeItemBean(0, "推荐给朋友", "", true, false, true));
        data.add(new MeItemBean(0, "清理缓存", "", true, true, false));
        data.add(new MeItemBean(0, "服务条款协议", "", true, false, false));
        MeItemAdapter adapter = new MeItemAdapter(getActivity(), data);
        mList.setAdapter(adapter);
        adapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                if ("地址管理".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(getActivity(), AddressManagerActivity.class));
                } else if ("意见反馈".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(getActivity(), FeedBackActivity.class).putExtra("from", "1"));
                } else if ("关于我们".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(getActivity(), AboutActivity.class).putExtra("url",
                            Host.hostUrl + "api.php?m=Api&c=Index&a=about_us"));
                } else if ("推荐给朋友".equals(data.get(position).getItem_name())) {
                    showShare();
//                    startActivity(new Intent(getActivity(), PayActivity.class));
                } else if ("我的收藏".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(getActivity(), MyCollectionActivity.class));
                } else if ("清理缓存".equals(data.get(position).getItem_name())) {
                    startActivity(new Intent(getActivity(), EngineerManagerActivity.class));
                } else if ("服务条款协议".equals(data.get(position).getItem_name())) {
                    showShare();
                    startActivity(new Intent(getActivity(), AboutActivity.class).putExtra("url",
                            Host.hostUrl + "api.php?m=Api&c=Index&a=clause").putExtra("title", "服务条款协议"));
                }
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
    }

    private void findAllView() {

        header = LayoutInflater.from(getActivity()).inflate(R.layout.layout_header_personal, null, false);
        face_img = (ImageView) header.findViewById(R.id.face_img);
        name = (TextView) header.findViewById(R.id.name);
        phone = (TextView) header.findViewById(R.id.phone);
        evaluate = (RatingBar) header.findViewById(R.id.evaluate);

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), BaseInfoActivity.class), 1001);
            }
        });
        mList = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.mlist);
        mList.addHeader(header);
        bindData();
    }

    private void bindData() {
        UserInfoBean userInfoBean = MyAccountMoudle.getInstance().getUserInfo();
        if (userInfoBean.getUsername() != null) {
            name.setText(userInfoBean.getUsername() + "");
        }
        if (!TextUtils.isEmpty(userInfoBean.getImg())) {
            ImageLoader.load(getActivity(), userInfoBean.getImg(), face_img);
        }
        phone.setText(userInfoBean.getPhone());
//        evaluate.setRating(Float.parseFloat(userInfoBean.get));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1001:
                    startRequestData(getData);
                    break;
            }
        }
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
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=User&a=getuser", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                boolean a = LoginInfoSave.loginOk(getActivity(), response);
                if (!a) {
                    CustomUtils.showTipShort(getActivity(), "登录异常");
                    startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    return;
                }
                bindData();
                break;
        }
    }

    private void showShare() {
        ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("分享");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        //        Bitmap enableLogo = BitmapFactory.decodeResource(context.getResources(), R.drawable.ssdk_oks_logo_qq);
        final String label = "复制链接";
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(label);
            }
        };
        oks.setCustomerLogo(null, label, listener);
        // 启动分享GUI
        oks.show(getActivity());
    }
}
