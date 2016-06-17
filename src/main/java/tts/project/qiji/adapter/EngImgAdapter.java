package tts.project.qiji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sjb on 2016/1/20.
 */
public class EngImgAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<String> data;

    public EngImgAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 组件集合，对应list.xml中的控件
     *
     * @author Administrator
     */
    public final class Zujian {
        public ImageView img;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Zujian zujian = null;
        if (convertView == null) {
            zujian = new Zujian();
            // 获得组件，实例化组件
//            convertView = layoutInflater.inflate(R.layout.image_bean, null);
//            zujian.img = (ImageView) convertView.findViewById(R.id.img);
//            convertView.setTag(zujian);
        } else {
            zujian = (Zujian) convertView.getTag();
        }
        Glide.with(context).load(data.get(position)).diskCacheStrategy(DiskCacheStrategy.ALL).into(zujian.img);

        return convertView;
    }
}
