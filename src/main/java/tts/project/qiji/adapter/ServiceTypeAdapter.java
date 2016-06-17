package tts.project.qiji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.project.qiji.R;
import tts.project.qiji.bean.ServiceTypeBean;

/**
 * Created by shanghang on 2016/6/17.
 */
public class ServiceTypeAdapter extends TTSBaseAdapterRecyclerView<ServiceTypeBean> {
    private Context mContext;
    private List<ServiceTypeBean> mData;

    public ServiceTypeAdapter(Context context, List<ServiceTypeBean> data) {
        super(context, data);
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_service_type_class_one, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
