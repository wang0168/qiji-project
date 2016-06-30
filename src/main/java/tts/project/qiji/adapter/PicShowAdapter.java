package tts.project.qiji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.bean.ImgBean;
import tts.project.qiji.R;
import tts.project.qiji.bean.CollectionBean;
import tts.project.qiji.utils.ImageLoader;


/**
 * Created by lenove on 2016/5/16.
 */
public class PicShowAdapter extends TTSBaseAdapterRecyclerView<ImgBean> {
    private Context mContext;
    private List<ImgBean> mData;

    public PicShowAdapter(Context context, List<ImgBean> data) {
        super(context, data);
        mData = data;
        mContext = context;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_pic_show, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        ImageLoader.load(mContext, mData.get(position).getPath(), viewHolder.img);
    }


    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
