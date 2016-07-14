package tts.project.qiji.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.bean.ImgBean;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.R;
import tts.project.qiji.bean.OrderBean;


/**
 *
 */
public class EngineerTakeOrderAdapter extends TTSBaseAdapterRecyclerView<OrderBean> {
    private Context mContext;
    private List<OrderBean> mData;
    public OnClickActionListener listener;

    public void setListener(OnClickActionListener listener) {
        this.listener = listener;
    }

    public EngineerTakeOrderAdapter(Context context, List<OrderBean> data) {
        super(context, data);
        mContext = context;
        mData = data;
    }


    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_engineer_take_orders, null, false));
    }


    @Override
    public void onBindViewHolder(final TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
//        LinearLayoutManager layout = new LinearLayoutManager(mContext);
//        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
//        viewHolder.pic_list.setLayoutManager(layout);

        if (!TextUtils.isEmpty(mData.get(position).getDis())) {
            viewHolder.service_desc.setText(mData.get(position).getDis());
        } else {
            viewHolder.service_desc.setText("");
        }
        if (!TextUtils.isEmpty(mData.get(position).getFuwu_time())) {
            viewHolder.serivce_time.setText(mData.get(position).getFuwu_time());
        } else {
            viewHolder.serivce_time.setText("紧急服务");
        }
        if (!TextUtils.isEmpty(mData.get(position).getAddress()) && !TextUtils.isEmpty(mData.get(position).getProvince())) {
            String addressStr = mData.get(position).getProvince() + mData.get(position).getCity() + mData.get(position).getArea()
                    + mData.get(position).getAddress();
            viewHolder.serivce_address.setText(addressStr);
        } else {
            viewHolder.serivce_address.setText("");
        }
        if (!TextUtils.isEmpty(mData.get(position).getAmount())) {
            viewHolder.server_cost.setText(mData.get(position).getAmount());
        } else {
            viewHolder.server_cost.setText("");
        }
        if (!TextUtils.isEmpty(mData.get(position).getFuwu_time())) {
            viewHolder.serivce_time.setText(mData.get(position).getFuwu_time());
        }else {
            viewHolder.serivce_time.setText("");
        }

        if (!TextUtils.isEmpty(mData.get(position).getOne_fuwu())) {
            viewHolder.type_one.setVisibility(View.VISIBLE);
            viewHolder.type_one.setText(mData.get(position).getOne_fuwu());
        } else {
            viewHolder.type_one.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mData.get(position).getTwo_fuwu())) {
            viewHolder.type_two.setVisibility(View.VISIBLE);
            viewHolder.type_two.setText(mData.get(position).getTwo_fuwu());
        } else {
            viewHolder.type_two.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mData.get(position).getThree_fuwu())) {
            viewHolder.type_three.setVisibility(View.VISIBLE);
            viewHolder.type_three.setText(mData.get(position).getThree_fuwu());
        } else {
            viewHolder.type_three.setVisibility(View.GONE);
        }


        String statusStr = "";
        if (!TextUtils.isEmpty(mData.get(position).getStatus())) {
            switch (mData.get(position).getService_type()) {
                case "2":
                    statusStr = "等待上门服务";
                    //显示的按钮
                    viewHolder.do_work.setVisibility(View.VISIBLE);
                    //隐藏的按钮
                    viewHolder.call_customer_service.setVisibility(View.GONE);
                    viewHolder.read_review.setVisibility(View.GONE);
                    viewHolder.upload_service.setVisibility(View.GONE);
                    break;
                case "3":
                    statusStr = "等待确认上传";
                    //显示的按钮
                    viewHolder.upload_service.setVisibility(View.VISIBLE);
                    //隐藏的按钮
                    viewHolder.call_customer_service.setVisibility(View.GONE);
                    viewHolder.do_work.setVisibility(View.GONE);
                    viewHolder.read_review.setVisibility(View.GONE);
                    break;
                case "4":
                    statusStr = "等待用户确认";
                    //显示的按钮
                    viewHolder.call_customer_service.setVisibility(View.VISIBLE);

                    //隐藏的按钮
                    viewHolder.do_work.setVisibility(View.GONE);
                    viewHolder.read_review.setVisibility(View.GONE);
                    viewHolder.upload_service.setVisibility(View.GONE);
                    break;
                case "5":
                    statusStr = "已完成";
                    //显示的按钮
                    viewHolder.read_review.setVisibility(View.VISIBLE);
                    //隐藏的按钮
                    viewHolder.do_work.setVisibility(View.GONE);
                    viewHolder.call_customer_service.setVisibility(View.GONE);
                    viewHolder.upload_service.setVisibility(View.GONE);
                    break;
                case "-1":
                    statusStr = "已拒单";
                    //显示的按钮

                    //隐藏的按钮
                    viewHolder.upload_service.setVisibility(View.GONE);
                    viewHolder.read_review.setVisibility(View.GONE);
                    viewHolder.do_work.setVisibility(View.GONE);
                    viewHolder.call_customer_service.setVisibility(View.GONE);
                    break;
            }
            viewHolder.order_state_inner.setText(statusStr);
        }
        viewHolder.pic_list.setVisibility(View.VISIBLE);
        LinearLayoutManager pic_layout = new LinearLayoutManager(mContext);
        pic_layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewHolder.pic_list.setLayoutManager(pic_layout);
        //判断是否有图片，填充图片recycleView
        if (TextUtils.isEmpty(mData.get(position).getImg())) {
            viewHolder.pic_list.setVisibility(View.GONE);
        } else {
            //拼装图片数据
            String[] urls = mData.get(position).getImg().split(",");
            List<ImgBean> picUrl = new ArrayList();
            for (int i = 0; i < urls.length; i++) {
                ImgBean imgBean = new ImgBean();
                imgBean.setPath(Host.hostUrl + urls[i]);
                picUrl.add(imgBean);
            }
            viewHolder.pic_list.setAdapter(new LargePicShowAdapter(mContext, picUrl));
        }


        viewHolder.do_work.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.doWork(position);
                }
            }
        });
        viewHolder.call_customer_service.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.callCustomerService(position);
                }
            }
        });
        viewHolder.read_review.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.readReview(position);
                }
            }
        });
        viewHolder.upload_service.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.uploadService(position);
                }
            }
        });
    }


    /**
     * 订单列表Item 的ViewHolder
     */
    class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView order_state_inner;//订单状态
        private RecyclerView pic_list;//图片列表
        private TextView service_desc;//服务要求（描述）
        private TextView type_one;//服务类型（一级）
        private TextView type_two;//服务类型（二级）
        private TextView type_three;//服务类型（三级）
        private TextView serivce_time;//下单时间
        private TextView serivce_address;//服务地址
        private TextView server_cost;//服务价格
        private TextView do_work;//上门服务
        private TextView call_customer_service;//呼叫客服
        private TextView read_review;//查看评价
        private TextView upload_service;//查看评价
        private LinearLayout layout_type;//服务类型

        public ViewHolder(View itemView) {
            super(itemView);
            pic_list = (RecyclerView) itemView.findViewById(R.id.pic_list);
            order_state_inner = (TextView) itemView.findViewById(R.id.order_state_inner);
            service_desc = (TextView) itemView.findViewById(R.id.service_desc);
            type_one = (TextView) itemView.findViewById(R.id.type_one);
            type_two = (TextView) itemView.findViewById(R.id.type_two);
            type_three = (TextView) itemView.findViewById(R.id.type_three);
            serivce_time = (TextView) itemView.findViewById(R.id.serivce_time);
            serivce_address = (TextView) itemView.findViewById(R.id.serivce_address);
            server_cost = (TextView) itemView.findViewById(R.id.server_cost);
            do_work = (TextView) itemView.findViewById(R.id.do_work);
            call_customer_service = (TextView) itemView.findViewById(R.id.call_customer_service);
            read_review = (TextView) itemView.findViewById(R.id.read_review);
            upload_service = (TextView) itemView.findViewById(R.id.upload_service);
            layout_type = (LinearLayout) itemView.findViewById(R.id.layout_type);
        }
    }


    public interface OnClickActionListener {
        void doWork(int pos);

        void callCustomerService(int pos);

        void readReview(int pos);

        void uploadService(int pos);


    }
}
