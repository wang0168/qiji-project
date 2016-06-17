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
import tts.project.qiji.R;
import tts.project.qiji.bean.CollectionBean;


/**
 * Created by lenove on 2016/5/16.
 */
public class CollectionAdapter extends TTSBaseAdapterRecyclerView<CollectionBean> {
    private Context mContext;
    private List<CollectionBean> mData;

    public CollectionAdapter(Context context, List<CollectionBean> data) {
        super(context, data);
        mData = data;
        mContext = context;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CollectionViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_collection, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }
    //    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        final CollectionViewHolder collectionViewHolder = (CollectionViewHolder) RecyclerViewDragHolder.getHolder(holder);
//        collectionViewHolder.price.setText("￥" + mData.get(position).getGoodsBean().getGoods_price());
//        collectionViewHolder.title.setText(mData.get(position).getGoodsBean().getGoods_name());
//        ImageLoader.load(mContext, mData.get(position).getGoodsBean().getGoods_img(), collectionViewHolder.img);
//        collectionViewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    collectionViewHolder.close();
//                    listener.deleteAddress(position);
//                }
//            }
//        });
//
//        collectionViewHolder.topView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onClick(position);
//                }
//            }
//        });
//    }


    @Override
    public int getItemCount() {
        return 5;
    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View mybg = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_item, null);
//        View view = LayoutInflater.from(mContext).inflate(R.layout.item_collection, null, false);
//        return new CollectionViewHolder(mContext, mybg, view).getDragViewHolder();
//    }

    public class CollectionViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private ImageView img;
        private TextView title;
        private TextView price;
        public Button deleteBtn;

        public CollectionViewHolder(View itemView) {
            super(itemView);
//            img = (ImageView) itemView.findViewById(R.id.img);
//            title = (TextView) itemView.findViewById(R.id.title);
//            price = (TextView) itemView.findViewById(R.id.price);
//            deleteBtn = (Button) itemView.findViewById(R.id.deleteBtn);
        }

//        public CollectionViewHolder(Context context, View bgView, View topView) {
//            super(context, bgView, topView);
//        }


//        @Override
//        public void initView(View itemView) {
//            img = (ImageView) itemView.findViewById(R.id.img);
//            title = (TextView) itemView.findViewById(R.id.title);
//            price = (TextView) itemView.findViewById(R.id.price);
//            deleteBtn = (Button) itemView.findViewById(R.id.deleteBtn);
//        }
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(int position);

        void deleteAddress(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
