package tts.project.qiji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tts.moudle.api.widget.HorizontalListView;
import tts.project.qiji.R;
import tts.project.qiji.bean.OrderBean;


/**
 * Created by chen on 2016/3/1.
 * <p/>
 * 订单列表适配器（可添加头部）
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<OrderBean> mData;
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
        mContext = context;
        mData = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_orders, null, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(holder.itemView, position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.onLongClick(holder.itemView, position);
                }
                return true;
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
        holder.order_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OrderCancel(position);
            }
        });
        holder.serivce_fu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OrderPayment(position);
            }
        });
        holder.serivce_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OrderCom(position);
            }
        });
        holder.serivce_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OrderEvaluate(position);
            }
        });
        holder.serivce_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.lookPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    /**
     * 获取除头部外真正的下标
     *
     * @param holder
     * @return real position
     */
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return position - 1;

    }

    /**
     * 订单列表Item 的ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder {
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
