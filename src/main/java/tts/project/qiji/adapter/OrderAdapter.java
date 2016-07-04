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

import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.R;
import tts.project.qiji.bean.OrderBean;


/**
 *
 */
public class OrderAdapter extends TTSBaseAdapterRecyclerView<OrderBean> {
    private Context mContext;
    private List<OrderBean> mData;
    public OnClickActionListener listener;

    public void setListener(OnClickActionListener listener) {
        this.listener = listener;
    }

    public OrderAdapter(Context context, List<OrderBean> data) {
        super(context, data);
        mContext = context;
        mData = data;
    }


    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_orders, null, false));
    }


    @Override
    public void onBindViewHolder(final TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        LinearLayoutManager layout = new LinearLayoutManager(mContext);
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewHolder.pic_list.setLayoutManager(layout);

        if (!TextUtils.isEmpty(mData.get(position).getDis())) {
            viewHolder.service_desc.setText(mData.get(position).getDis());
        }
        if (!TextUtils.isEmpty(mData.get(position).getDate())) {
            viewHolder.serivce_time.setText(mData.get(position).getDate());
        }
        if (!TextUtils.isEmpty(mData.get(position).getAddress()) && !TextUtils.isEmpty(mData.get(position).getProvince())) {
            String addressStr = mData.get(position).getProvince() + mData.get(position).getCity() + mData.get(position).getArea()
                    + mData.get(position).getAddress();
            viewHolder.serivce_address.setText(addressStr);
        }
        if (!TextUtils.isEmpty(mData.get(position).getAmount())) {
            viewHolder.server_cost.setText(mData.get(position).getAmount());
        }
        if (!TextUtils.isEmpty(mData.get(position).getFuwu_time())) {
            viewHolder.serivce_time.setText(mData.get(position).getFuwu_time());
        }
        String statusStr = "";
        if (!TextUtils.isEmpty(mData.get(position).getStatus())) {
            switch (mData.get(position).getStatus()) {
                case "1":
                    statusStr = "待指派工程师";
                    viewHolder.order_cancel.setVisibility(View.VISIBLE);
                    viewHolder.hurry_order.setVisibility(View.VISIBLE);
                    viewHolder.modify_order.setVisibility(View.VISIBLE);
                    break;
                case "2":
                    statusStr = "等待上门服务";
                    viewHolder.contact_engineer.setVisibility(View.VISIBLE);
                    viewHolder.serivce_gps.setVisibility(View.VISIBLE);
                    break;
                case "3":
                    statusStr = "待确认服务";
                    viewHolder.serivce_confirm.setVisibility(View.VISIBLE);
                    break;
                case "4":
                    statusStr = "订单已完成";
                    break;
            }
            viewHolder.order_state_inner.setText(statusStr);
        }
        viewHolder.order_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.cancelOrder(position);
                }
            }
        });
        viewHolder.hurry_order.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.hurryOrder(position);
                }
            }
        });
        viewHolder.modify_order.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.modifyOrder(position);
                }
            }
        });
        viewHolder.contact_engineer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.contactEngineer(position);
                }
            }
        });
        viewHolder.serivce_gps.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.serviceGPS(position);
                }
            }
        });
        viewHolder.serivce_confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.serviceConfirm(position);
                }
            }
        });

    }


//    @Override
//    public int getItemCount() {
//        return 20;
//    }


    /**
     * 订单列表Item 的ViewHolder
     */
    class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView order_state_inner;//订单状态
        private RecyclerView pic_list;//图片列表
        private TextView service_desc;//服务要求（描述）
        private TextView serivce_time;//下单时间
        private TextView serivce_address;//服务地址
        private TextView server_cost;//服务价格
        private TextView order_cancel;//取消订单
        private TextView serivce_gps;//查看位置
        private TextView serivce_confirm;//确认订单
        private TextView contact_engineer;//联系师傅
        private TextView modify_order;//修改订单
        private TextView hurry_order;//催单
        private LinearLayout layout_type;//服务类型

        public ViewHolder(View itemView) {
            super(itemView);
            pic_list = (RecyclerView) itemView.findViewById(R.id.pic_list);
            order_state_inner = (TextView) itemView.findViewById(R.id.order_state_inner);
            serivce_time = (TextView) itemView.findViewById(R.id.serivce_time);
            serivce_address = (TextView) itemView.findViewById(R.id.serivce_address);
            server_cost = (TextView) itemView.findViewById(R.id.server_cost);
            order_cancel = (TextView) itemView.findViewById(R.id.order_cancel);
            serivce_gps = (TextView) itemView.findViewById(R.id.serivce_gps);
            serivce_confirm = (TextView) itemView.findViewById(R.id.serivce_confirm);
            contact_engineer = (TextView) itemView.findViewById(R.id.contact_engineer);
            modify_order = (TextView) itemView.findViewById(R.id.modify_order);
            hurry_order = (TextView) itemView.findViewById(R.id.hurry_order);
            layout_type = (LinearLayout) itemView.findViewById(R.id.layout_type);
        }
    }


    public interface OnClickActionListener {
        void cancelOrder(int pos);

        void hurryOrder(int pos);

        void modifyOrder(int pos);

        void serviceConfirm(int pos);

        void contactEngineer(int pos);

        void serviceGPS(int pos);
    }
}
