package com.tianfu.cutton.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tianfu.cutton.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20.
 */

public class PurchaseOrderdetailsRecylerAdapter extends RecyclerView.Adapter<PurchaseOrderdetailsRecylerAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mDatas;
    public PurchaseOrderdetailsRecylerAdapter(Context context, List<String> lists) {
        mContext = context;
        mDatas = lists;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String s = mDatas.get(position);
        holder.btGJC.setText(s);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.bt_guanjianci)
        Button btGJC;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
