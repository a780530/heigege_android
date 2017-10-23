package com.tianfu.cutton.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.List;

/**
 * Created by xiaohei on 2017/3/28.
 * RecyclerViewAdapter基类
 */

public abstract class BaseRecyclerViewAdapter<Holder extends RecyclerHolder, T> extends RecyclerView.Adapter<Holder> {
    /** adapter 数据集 */
    public List<T> data;
    /** Context */
    protected Context context;
    /** 用于解析布局 */
    protected LayoutInflater inflater;

    public BaseRecyclerViewAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public Context getContext(){
        return this.context;
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty(){
        return getItemCount() == 0;
    }

    //兼容处理．
    public T getItem(int position){
        return data.get(position);
    }

    /**
     * 判断非空
     * @param adapter
     * @return
     */
    public static boolean checkEmpty(BaseRecyclerViewAdapter adapter){
        return adapter == null || adapter.isEmpty();
    }

    public void refresh(List<T> data) {
        this.data.clear();
        this.data = data;
        notifyDataSetChanged();
    }


    public void append(T data) {
        this.data.add(data);
        notifyItemInserted(this.data.size() - 1);
    }


    public void append(int position, T data) {
        this.data.add(position,data);
        notifyItemInserted(position);
    }


    public void append(List<T> data) {
        int oldSize = this.data.size();
        this.data.addAll(data);
        notifyItemRangeInserted(oldSize, data.size());
    }


    public T remove(T item) {
        this.data.remove(item);
        notifyItemRemoved(this.data.size() + 1);
        return item;
    }


    public T remove(int position) {
        T item = this.data.get(position);
        this.data.remove(position);
        notifyItemRemoved(position);
        return item;
    }

    public void removeAll() {
        this.data.clear();
        notifyDataSetChanged();
    }
    public OnItemClickListener mClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }
}
