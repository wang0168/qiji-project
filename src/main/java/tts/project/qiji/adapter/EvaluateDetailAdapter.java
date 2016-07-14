package tts.project.qiji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tts.moudle.api.widget.CircleImageView;
import tts.project.qiji.R;
import tts.project.qiji.bean.EvaluateBean;

/**
 * Created by chen on 2016/3/1.
 * <p/>
 * 订单列表适配器（可添加头部）
 */
public class EvaluateDetailAdapter extends RecyclerView.Adapter<EvaluateDetailAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<EvaluateBean> mData;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(View itemView, int position);

        void onLongClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public EvaluateDetailAdapter(Context context, ArrayList<EvaluateBean> data) {
        mContext = context;
        mData = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.evealuate_item_detail, null, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(holder.itemView, position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.onLongClick(holder.itemView, position);
                }
                return true;
            }
        });
//        Glide.with(mContext).load(mData.get(position).getPhoto()).into(holder.video_icon);
//        holder.name.setText(mData.get(position).getName());
//        holder.time.setText(mData.get(position).getIntime());
//        holder.evaluate.setText(mData.get(position).getContent());
//        ServerNeedAdapter adapter = new ServerNeedAdapter()
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    /**
     * 获取除头部外真正的下标
     *
     * @param holder
     * @return real position
     */
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return position - 1;

    }

    /**
     * 评论列表Item 的ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView video_icon;
        private TextView name;
        private TextView time;
        private TextView evaluate;
        private ListView evealuate_list;
        public ViewHolder(View itemView) {
            super(itemView);
            video_icon = (CircleImageView) itemView.findViewById(R.id.video_icon);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            evaluate = (TextView) itemView.findViewById(R.id.evaluate);
            evealuate_list = (ListView) itemView.findViewById(R.id.evealuate_list);
        }
    }


}
