package tts.project.qiji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.project.qiji.R;
import tts.project.qiji.bean.HomeSortBean;

/**
 * Created by shanghang on 2016/6/7.
 */
public class CallServiceAdapter extends TTSBaseAdapterRecyclerView<HomeSortBean> {
    private Context mContext;
    private List<HomeSortBean> mData;

    public CallServiceAdapter(Context context, List<HomeSortBean> data) {
        super(context, data);
        mContext = context;
        mData = data;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_call_service, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.iconImg.setBackgroundResource(mData.get(position).getImgId());
        viewHolder.service_name.setText(mData.get(position).getName());
        viewHolder.service_price.setText(mData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private ImageView iconImg;
        private TextView service_name;
        private TextView service_price;

        public ViewHolder(View itemView) {
            super(itemView);
            iconImg = (ImageView) itemView.findViewById(R.id.iconImg);
            service_name = (TextView) itemView.findViewById(R.id.service_name);
            service_price = (TextView) itemView.findViewById(R.id.service_price);
        }
    }
}
