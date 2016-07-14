package tts.project.qiji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.widget.GridViewInScrollView;
import tts.project.qiji.R;
import tts.project.qiji.bean.HomeSortBean;

/**
 * Created by shanghang on 2016/6/17.
 */
public class ServiceTypeAdapter extends TTSBaseAdapterRecyclerView<HomeSortBean> {
    private Context mContext;
    private List<HomeSortBean> mData;

    public ServiceTypeAdapter(Context context, List<HomeSortBean> data) {
        super(context, data);
        mContext = context;
        mData = data;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_service_type_class_one, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
//        if ("其他服务".equals(mData.get(position).getName())) {
//            ((ViewHolder) holder).itemView.setVisibility(View.GONE);
//        }
        viewHolder.type_class_one.setText(mData.get(position).getName());
        viewHolder.service_types.setAdapter(new ServiceTpyeTwoAdapter(mContext, mData.get(position).getSort_types()));
    }


    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView type_class_one;
        private GridViewInScrollView service_types;

        public ViewHolder(View itemView) {
            super(itemView);
            type_class_one = (TextView) itemView.findViewById(R.id.type_class_one);
            service_types = (GridViewInScrollView) itemView.findViewById(R.id.service_types);
        }
    }
}
