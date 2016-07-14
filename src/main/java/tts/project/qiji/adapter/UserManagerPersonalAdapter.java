package tts.project.qiji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.utils.TextUtils;
import tts.project.qiji.R;
import tts.project.qiji.bean.UserManagerDataBean;

/**
 * Created by lenove on 2016/5/11.
 */
public class UserManagerPersonalAdapter extends TTSBaseAdapterRecyclerView<UserManagerDataBean.SignBean> {
    private Context mContext;
    private List<UserManagerDataBean.SignBean> mData;

    public UserManagerPersonalAdapter(Context context, List<UserManagerDataBean.SignBean> data) {
        super(context, data);
        mData = data;
        mContext = context;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_manager_personal, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder ViewHolder = (ViewHolder) holder;
        if (!TextUtils.isEmpty(mData.get(position).getGys_name())) {
            ViewHolder.signed_company.setText(mData.get(position).getGys_name());
        } else {
            ViewHolder.signed_company.setText("");
        }
        if (!TextUtils.isEmpty(mData.get(position).getFuwu_name())) {
            ViewHolder.service_company.setText(mData.get(position).getFuwu_name());
        } else {
            ViewHolder.service_company.setText("");
        }
        if (!TextUtils.isEmpty(mData.get(position).getStart_time()) && !TextUtils.isEmpty(mData.get(position).getEnd_time())) {
            ViewHolder.term.setText(mData.get(position).getStart_time() + "~" + mData.get(position).getEnd_time());
        } else {
            ViewHolder.term.setText("");
        }
    }

    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView signed_company;
        private TextView service_company;
        private TextView term;

        public ViewHolder(View itemView) {
            super(itemView);
            signed_company = (TextView) itemView.findViewById(R.id.signed_company);
            service_company = (TextView) itemView.findViewById(R.id.service_company);
            term = (TextView) itemView.findViewById(R.id.term);
        }
    }

}
