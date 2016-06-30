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
import tts.project.qiji.utils.ImageLoader;

/**
 * Created by shanghang on 2016/6/7.
 */
public class HomeSortAdapter extends TTSBaseAdapterRecyclerView<HomeSortBean> {
    private Context mContext;
    private List<HomeSortBean> mData;

    public HomeSortAdapter(Context context, List<HomeSortBean> data) {
        super(context, data);
        mContext = context;
        mData = data;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_sort, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
//        viewHolder.sort_icon.setBackgroundResource(mData.get(position).getImgId());
        viewHolder.sort_name.setText(mData.get(position).getName());
        ImageLoader.load(mContext, mData.get(position).getImg(), viewHolder.sort_icon);
    }


    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private ImageView sort_icon;
        private TextView sort_name;

        public ViewHolder(View itemView) {
            super(itemView);
            sort_icon = (ImageView) itemView.findViewById(R.id.sort_icon);
            sort_name = (TextView) itemView.findViewById(R.id.sort_name);
        }
    }
}
