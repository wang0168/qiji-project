package tts.project.qiji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.CircleImageView;
import tts.project.qiji.R;
import tts.project.qiji.bean.EvaluateBean;
import tts.project.qiji.utils.ImageLoader;

/**
 *
 */
public class EvaluateAdapter extends TTSBaseAdapterRecyclerView<EvaluateBean> {
    private Context mContext;
    private List<EvaluateBean> mData;


    public EvaluateAdapter(Context context, List<EvaluateBean> data) {
        super(context, data);
        mContext = context;
        mData = data;
    }


    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.evealuate_item, null, false));
    }


    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        if (!TextUtils.isEmpty(mData.get(position).getName())) {
            viewHolder.name.setText(mData.get(position).getName());
        }
        if (!TextUtils.isEmpty(mData.get(position).getContent())) {
            viewHolder.evaluate_content.setText(mData.get(position).getContent());
        }
        if (!TextUtils.isEmpty(mData.get(position).getXing())) {
            viewHolder.ratingbar.setRating(Float.parseFloat(mData.get(position).getXing()));
        }
        if (!TextUtils.isEmpty(mData.get(position).getImg())) {
            ImageLoader.load(mContext, mData.get(position).getImg(), viewHolder.face_img);
        }
    }

    /**
     * 订单列表Item 的ViewHolder
     */
    class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private CircleImageView face_img;
        private TextView name;
        private TextView evaluate_content;
        private RatingBar ratingbar;

        public ViewHolder(View itemView) {
            super(itemView);
            face_img = (CircleImageView) itemView.findViewById(R.id.face_img);
            name = (TextView) itemView.findViewById(R.id.name);
            evaluate_content = (TextView) itemView.findViewById(R.id.evaluate_content);
            ratingbar = (RatingBar) itemView.findViewById(R.id.ratingbar);
        }
    }


}
