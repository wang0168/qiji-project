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
import tts.project.qiji.bean.MeItemBean;

/**
 * Created by shanghang on 2016/6/7.
 */
public class MeItemAdapter extends TTSBaseAdapterRecyclerView<MeItemBean> {
    private Context mContext;
    private List<MeItemBean> mData;

    public MeItemAdapter(Context context, List<MeItemBean> data) {
        super(context, data);
        mContext = context;
        mData = data;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_me, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        if (mData.get(position).getLeft_img() != 0) {
            viewHolder.left_img.setVisibility(View.VISIBLE);
            viewHolder.left_img.setBackgroundResource(mData.get(position).getLeft_img());
        } else {
            viewHolder.left_img.setVisibility(View.GONE);
        }
        viewHolder.name.setText(mData.get(position).getItem_name());
        if (TextUtils.isEmpty(mData.get(position).getContext())) {
            viewHolder.context.setText("");
        } else {
            viewHolder.context.setText(mData.get(position).getContext());
        }
        if (mData.get(position).isRight()) {
            viewHolder.right_jt.setVisibility(View.VISIBLE);
        } else {
            viewHolder.right_jt.setVisibility(View.INVISIBLE);
        }
        if (mData.get(position).isline()) {
            viewHolder.line.setVisibility(View.VISIBLE);
        } else {
            viewHolder.line.setVisibility(View.GONE);
        }
        if (mData.get(position).iswidth()) {
            viewHolder.width.setVisibility(View.VISIBLE);
        } else {
            viewHolder.width.setVisibility(View.GONE);
        }
    }


    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private ImageView left_img;
        private ImageView right_jt;
        private TextView context;
        private TextView name;
        private View line;
        private View width;

        public ViewHolder(View itemView) {
            super(itemView);
            left_img = (ImageView) itemView.findViewById(R.id.left_img);
            right_jt = (ImageView) itemView.findViewById(R.id.right_jt);
            name = (TextView) itemView.findViewById(R.id.name);
            context = (TextView) itemView.findViewById(R.id.context);
            line = itemView.findViewById(R.id.line);
            width = itemView.findViewById(R.id.width);
        }
    }
}
