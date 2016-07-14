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
public class EngineerOrderAdapter extends TTSBaseAdapterRecyclerView<OrderBean> {
    private Context mContext;
    private List<OrderBean> mData;
    public OnClickActionListener listener;

    public void setListener(OnClickActionListener listener) {
        this.listener = listener;
    }

    public EngineerOrderAdapter(Context context, List<OrderBean> data) {
        super(context, data);
        mContext = context;
        mData = data;
    }


    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_engineer_orders, null, false));
    }


    @Override
    public void onBindViewHolder(final TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;

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
        if ("1".equals(mData.get(position).getType())) {
            viewHolder.order_state_inner.setText("等待接单");
            viewHolder.take_order.setText("接单");
        } else {
            viewHolder.order_state_inner.setText("等待抢单");
            viewHolder.take_order.setText("抢单");
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


        viewHolder.call_customer_service.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.callCustomerService(position);
                }
            }
        });
        viewHolder.call_users.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.callUser(position);
                }
            }
        });
        viewHolder.take_order.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.takeOrder(position);
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
        private TextView call_customer_service;//呼叫客服
        private TextView call_users;//呼叫用户
        private TextView take_order;//接单

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
            call_customer_service = (TextView) itemView.findViewById(R.id.call_customer_service);
            call_users = (TextView) itemView.findViewById(R.id.call_users);
            take_order = (TextView) itemView.findViewById(R.id.take_order);
            layout_type = (LinearLayout) itemView.findViewById(R.id.layout_type);
        }
    }


    public interface OnClickActionListener {
        void callCustomerService(int pos);

        void callUser(int pos);

        void takeOrder(int pos);
    }
}
