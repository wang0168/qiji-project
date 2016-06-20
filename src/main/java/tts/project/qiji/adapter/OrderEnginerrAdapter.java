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
public class OrderEnginerrAdapter extends RecyclerView.Adapter<OrderEnginerrAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<OrderBean> mData;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(View itemView, int position);

        void onLongClick(View itemView, int position);

        void Orders(int position);

        void Navigation(int position);

        void SerivceCcom(int position);

        void SerivceShang(int position);

//        void SerivceOver(int position);
//
//        void ServerOff(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public OrderEnginerrAdapter(Context context, ArrayList<OrderBean> data) {
        mContext = context;
        mData = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.enginerr_item_orders, null, false));
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
//        switch (mData.get(position).getEngineer_state_id()) {
//            case "1":
//                holder.orders.setVisibility(View.VISIBLE);
//                break;
//            case "2":
//                holder.navigation.setVisibility(View.VISIBLE);
//                holder.serivce_sh.setVisibility(View.VISIBLE);
//                break;
//            case "3":
//                holder.navigation.setVisibility(View.VISIBLE);
//                holder.serivce_com.setVisibility(View.VISIBLE);
//                break;
//            case "4":
//                holder.serivce_over.setVisibility(View.VISIBLE);
//                break;
//            case "5":
//                holder.serivce_off.setVisibility(View.VISIBLE);
//                break;
//        }
//        holder.serivce_time.setText(mData.get(position).getFuwu_time());
//        holder.serivce_address.setText(mData.get(position).getAddress());
//        holder.server_cost.setText("￥" + mData.get(position).getPrice());getPrice
        holder.orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.Orders(position);
            }
        });
        holder.navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.Navigation(position);
            }
        });
        holder.serivce_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.SerivceCcom(position);
            }
        });
        holder.serivce_sh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.SerivceShang(position);
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
        private TextView serivce_sh, serivce_off, serivce_time, serivce_address, server_cost, orders, navigation, serivce_com, serivce_over;

        public ViewHolder(View itemView) {
            super(itemView);
            list_view = (HorizontalListView) itemView.findViewById(R.id.list_view);
            order_state = (TextView) itemView.findViewById(R.id.order_state);
            serivce_time = (TextView) itemView.findViewById(R.id.serivce_time);
            serivce_address = (TextView) itemView.findViewById(R.id.serivce_address);
            server_cost = (TextView) itemView.findViewById(R.id.server_cost);

            orders = (TextView) itemView.findViewById(R.id.orders);
            navigation = (TextView) itemView.findViewById(R.id.navigation);
            serivce_com = (TextView) itemView.findViewById(R.id.serivce_com);
            serivce_over = (TextView) itemView.findViewById(R.id.serivce_over);
            serivce_off = (TextView) itemView.findViewById(R.id.serivce_off);
            serivce_sh = (TextView) itemView.findViewById(R.id.serivce_sh);
        }
    }


}
