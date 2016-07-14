package tts.project.qiji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.R;
import tts.project.qiji.bean.ManagerUserBean;
import tts.project.qiji.utils.ImageLoader;

/**
 * Created by lenove on 2016/5/11.
 */
public class UserItemAdapter extends TTSBaseAdapterRecyclerView<ManagerUserBean> {
    private Context mContext;
    private List<ManagerUserBean> mData;

    public UserItemAdapter(Context context, List<ManagerUserBean> data) {
        super(context, data);
        mData = data;
        mContext = context;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_user, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        if (!TextUtils.isEmpty(mData.get(position).getUsername())) {
            viewHolder.name.setText(mData.get(position).getUsername());
        }
        if (!TextUtils.isEmpty(mData.get(position).getPhone())) {
            viewHolder.phone.setText(mData.get(position).getPhone());
        }
        ImageLoader.load(mContext, mData.get(position).getImg(), viewHolder.left_img);
    }


    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView name;
        private TextView phone;
        private ImageView left_img;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            left_img = (ImageView) itemView.findViewById(R.id.left_img);
        }
    }


}
