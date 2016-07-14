package tts.project.qiji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.List;

import tts.moudle.api.TTSBaseAdapter;
import tts.project.qiji.R;
import tts.project.qiji.bean.HomeSortBean;

/**
 * Created by shanghang on 2016/7/6.
 */
public class ServiceTpyeTwoAdapter extends TTSBaseAdapter {
    private Context mContext;
    private List<HomeSortBean> mData;


    public ServiceTpyeTwoAdapter(Context context, List<HomeSortBean> data) {
        mData = data;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_service_type, parent, false);
            viewHolder.type_class_two = (CheckBox) convertView.findViewById(R.id.type_class_two);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.type_class_two.setText(mData.get(position).getName());
        if (mData.get(position).isChecked()) {
            viewHolder.type_class_two.setChecked(true);
        } else {
            viewHolder.type_class_two.setChecked(false);
        }
        viewHolder.type_class_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.get(position).setChecked(viewHolder.type_class_two.isChecked());
            }
        });
        return convertView;
    }

    class ViewHolder {
        private CheckBox type_class_two;
    }
}
