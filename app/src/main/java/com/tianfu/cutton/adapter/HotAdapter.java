package com.tianfu.cutton.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.adapter.base.OnItemClickListener;

import java.util.List;

/**
 * Created by admin on 2017/8/1.
 */

public class HotAdapter extends RecyclerView.Adapter<HotAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mDatas;
    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
    public HotAdapter(Context context, List<String> lists) {
        mContext = context;
        mDatas = lists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bt_hot_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String s = mDatas.get(position);
        holder.bt_hot.setText(s);
        if(mOnItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView,position); // 2
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView bt_hot;

        public ViewHolder(View itemView) {
            super(itemView);
            bt_hot = (TextView) itemView.findViewById(R.id.bt_hot);
        }
    }
}
