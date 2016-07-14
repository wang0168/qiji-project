package tts.project.qiji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import tts.moudle.api.bean.ImgBean;
import tts.project.qiji.R;
import tts.project.qiji.utils.ImageLoader;


/**
 * Created by lenove on 2016/5/16.
 */
public class LargePicShowAdapter extends RecyclerView.Adapter<LargePicShowAdapter.ViewHolder> {
    private Context mContext;
    private List<ImgBean> mData;

    public LargePicShowAdapter(Context context, List<ImgBean> data) {

        mData = data;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_large_pic_show, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageLoader.load(mContext, mData.get(position).getPath(), holder.img);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
