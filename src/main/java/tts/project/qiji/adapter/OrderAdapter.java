package tts.project.qiji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.widget.HorizontalListView;
import tts.project.qiji.R;
import tts.project.qiji.bean.OrderBean;


/**
 * Created by chen on 2016/3/1.
 * <p/>
 * 订单列表适配器（可添加头部）
 */
public class OrderAdapter extends TTSBaseAdapterRecyclerView<OrderBean> {
    private Context mContext;
    private List<OrderBean> mData;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(View itemView, int position);

        void onLongClick(View itemView, int position);

        void OrderCancel(int position);

        void OrderPayment(int position);

        void OrderCom(int position);

        void OrderEvaluate(int position);

        void lookPosition(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public OrderAdapter(Context context, ArrayList<OrderBean> data) {
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
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v, position);
                }
            }
        });

//        ServerNeedAdapter adapter = new ServerNeedAdapter(mContext, mData.get(position).getFuwu());
//        holder.list_view.setAdapter(adapter);
//        switch (mData.get(position).getState()) {
//            case "1":
//                holder.order_state.setText("等待用户付款");
//                holder.order_cancel.setVisibility(View.VISIBLE);
//                holder.serivce_fu.setVisibility(View.VISIBLE);
//                break;
//            case "2":
//                holder.order_state.setText("等待工程师接单");
//                holder.order_cancel.setVisibility(View.VISIBLE);
//                break;
//            case "3":
//                holder.order_state.setText("等待上门服务");
//                holder.serivce_gps.setVisibility(View.VISIBLE);
//                break;
//            case "4":
//                holder.order_state.setText("等待确认服务");
//                holder.serivce_com.setVisibility(View.VISIBLE);
//                break;
//            case "5":
//                holder.order_state.setText("服务完成");
//                break;
//        }
//        holder.serivce_time.setText(mData.get(position).getFuwu_time());
//        holder.serivce_address.setText(mData.get(position).getAddress());
//        holder.server_cost.setText("￥" + mData.get(position).getPrice());
        viewHolder.order_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OrderCancel(position);
            }
        });
        viewHolder.serivce_fu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OrderPayment(position);
            }
        });
        viewHolder.serivce_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OrderCom(position);
            }
        });
        viewHolder.serivce_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OrderEvaluate(position);
            }
        });
        viewHolder.serivce_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.lookPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 20;
    }


    /**
     * 订单列表Item 的ViewHolder
     */
    class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView order_state;
        private HorizontalListView list_view;
        private TextView serivce_gps, serivce_time, serivce_address, server_cost, order_cancel, serivce_fu, serivce_com, serivce_evaluate;

        public ViewHolder(View itemView) {
            super(itemView);
            list_view = (HorizontalListView) itemView.findViewById(R.id.list_view);
            order_state = (TextView) itemView.findViewById(R.id.order_state);
            serivce_time = (TextView) itemView.findViewById(R.id.serivce_time);
            serivce_address = (TextView) itemView.findViewById(R.id.serivce_address);
            server_cost = (TextView) itemView.findViewById(R.id.server_cost);
            order_cancel = (TextView) itemView.findViewById(R.id.order_cancel);
            serivce_fu = (TextView) itemView.findViewById(R.id.serivce_fu);
            serivce_com = (TextView) itemView.findViewById(R.id.serivce_com);
            serivce_evaluate = (TextView) itemView.findViewById(R.id.serivce_evaluate);
            serivce_gps = (TextView) itemView.findViewById(R.id.serivce_gps);
        }
    }


}
