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
        } else if ("1".equals(mData.get(position).getIs_need_server())) {
            viewHolder.serivce_time.setText("急需服务");
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
        } else {
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
            switch (mData.get(position).getStatus()) {
                case "1":
                    statusStr = "待指派工程师";
                    //显示的按钮
                    viewHolder.order_cancel.setVisibility(View.VISIBLE);
                    viewHolder.hurry_order.setVisibility(View.VISIBLE);
                    viewHolder.modify_order.setVisibility(View.VISIBLE);
                    //隐藏的按钮
                    viewHolder.serivce_gps.setVisibility(View.GONE);
                    viewHolder.contact_engineer.setVisibility(View.GONE);
                    viewHolder.serivce_confirm.setVisibility(View.GONE);
                    break;
                case "2":
                    statusStr = "等待上门服务";
                    //显示的按钮
                    viewHolder.contact_engineer.setVisibility(View.VISIBLE);
                    viewHolder.serivce_gps.setVisibility(View.VISIBLE);
                    //隐藏的按钮
                    viewHolder.serivce_confirm.setVisibility(View.GONE);
                    viewHolder.order_cancel.setVisibility(View.GONE);
                    viewHolder.hurry_order.setVisibility(View.GONE);
                    viewHolder.modify_order.setVisibility(View.GONE);
                    break;
                case "3":
                    statusStr = "待确认服务";
                    //显示的按钮
                    viewHolder.serivce_confirm.setVisibility(View.VISIBLE);
                    //隐藏的按钮
                    viewHolder.contact_engineer.setVisibility(View.GONE);
                    viewHolder.serivce_gps.setVisibility(View.GONE);
                    viewHolder.order_cancel.setVisibility(View.GONE);
                    viewHolder.hurry_order.setVisibility(View.GONE);
                    viewHolder.modify_order.setVisibility(View.GONE);
                    break;
                case "4":
                    statusStr = "订单已完成";
                    //显示的按钮

                    //隐藏的按钮
                    viewHolder.serivce_confirm.setVisibility(View.GONE);
                    viewHolder.contact_engineer.setVisibility(View.GONE);
                    viewHolder.serivce_gps.setVisibility(View.GONE);
                    viewHolder.order_cancel.setVisibility(View.GONE);
                    viewHolder.hurry_order.setVisibility(View.GONE);
                    viewHolder.modify_order.setVisibility(View.GONE);
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
        private TextView type_one;//服务类型（一级）
        private TextView type_two;//服务类型（二级）
        private TextView type_three;//服务类型（三级）
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
            service_desc = (TextView) itemView.findViewById(R.id.service_desc);
            type_one = (TextView) itemView.findViewById(R.id.type_one);
            type_two = (TextView) itemView.findViewById(R.id.type_two);
            type_three = (TextView) itemView.findViewById(R.id.type_three);
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
