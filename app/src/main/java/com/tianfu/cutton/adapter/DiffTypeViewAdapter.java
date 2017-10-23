package com.tianfu.cutton.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.model.MyfavoritesBean;
import com.tianfu.cutton.utils.StringJudgeUtils;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

import static java.lang.Double.parseDouble;

/**
 * adapter
 */
public class DiffTypeViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<MyfavoritesBean.ValueBean.RowsBean> mData;


    //监听checkbox选中状态
    private OncheckedListener oncheckedListener;

    public void setOncheckedListener(OncheckedListener oncheckedListener) {
        this.oncheckedListener = oncheckedListener;
    }

    public interface OncheckedListener {
        void isChecked(boolean hasChecked);
    }


    //设置两个常量
    private static final int TYPE_IMG_ONE = 1;      //第一个布局
    private static final int TYPE_IMG_TWO = 2;   //第二个布局


    public DiffTypeViewAdapter(Context context, List<MyfavoritesBean.ValueBean.RowsBean> data) {
        this.context = context;
        mData = data;
    }

    /**
     * 根据不同的position，设置不同的ViewType
     * position表示当前是第几个Item，通过position拿到当前的Item对象，然后判断这个item对象需要那种视图
     */
    @Override
    public int getItemViewType(int position) {
        if ("1".equals(mData.get(position).isProduct)) {
            return TYPE_IMG_ONE;
        } else if ("0".equals(mData.get(position).isProduct)) {
            return TYPE_IMG_TWO;
        } else {
            return 0;
        }
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder(当RecyclerView需要一个ViewHolder时会回调该方法，如果有可复用的View不会回调)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_IMG_ONE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mycollection_item, parent, false);
            TypeOneViewHolder threeViewHolder = new TypeOneViewHolder(view);
            return threeViewHolder;
        } else if (viewType == TYPE_IMG_TWO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mycollection_item2, parent, false);
            TypeTwoViewHolder recyclerViewHolder = new TypeTwoViewHolder(view);
            return recyclerViewHolder;
        }
        return null;
    }

    //填充onCreateViewHolder方法返回的holder中的控件(当一个View需要出现在屏幕上时，该方法会被回调，我们需要再该方法中根据数据来更改视图)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeOneViewHolder) {
            setOneType((TypeOneViewHolder) holder, position);
        } else if (holder instanceof TypeTwoViewHolder) {
            setTwoType((TypeTwoViewHolder) holder, position);
        }
    }

    //获取数据的数量(告诉RecyclerView有多少个视图需要显示)
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //设置第一种布局
    private void setOneType(final TypeOneViewHolder holder, final int position) {
        // TODO: 2017/7/25 填充第一种布局数据
        final MyfavoritesBean.ValueBean.RowsBean itemModel = mData.get(position);
        holder.tvTitle.setText(itemModel.code + "(" + itemModel.bagCount + "/" + itemModel.batchCount + ")");
        if (itemModel.batchType.equals("1")) {
            holder.bt_store_key.setVisibility(View.GONE);
        } else {
            if (itemModel.property != null & !"".equals(itemModel.property)) {
                holder.bt_store_key.setText(itemModel.property);
                holder.bt_store_key.setVisibility(View.VISIBLE);
            } else {
                holder.bt_store_key.setVisibility(View.GONE);
            }
        }

        if (itemModel.price != 0) {
            holder.mMoney.setText(itemModel.price + "");
            holder.tv_store_commany.setVisibility(View.VISIBLE);
        } else {
            holder.tv_store_commany.setVisibility(View.GONE);
            holder.mMoney.setText("价格面议");
        }
        if (itemModel.lengthAverage != null && !"".equals(itemModel.lengthAverage)) {
            holder.tv_store_length.setText(itemModel.lengthAverage);
            if (parseDouble(itemModel.lengthAverage) > 28) {
                holder.tv_store_length.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.red_translucent));
            } else {
                holder.tv_store_length.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            }
        } else {
            holder.tv_store_length.setText("--");
        }
        if (itemModel.breakLoadAverage != null && !"".equals(itemModel.breakLoadAverage)) {
            holder.tv_store_strong.setText(itemModel.breakLoadAverage);
            if (parseDouble(itemModel.breakLoadAverage) > 28) {
                holder.tv_store_strong.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.red_translucent));
            } else {
                holder.tv_store_strong.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            }
        } else {
            holder.tv_store_strong.setText("--");
        }
        if (itemModel.micronAverage != null && !"".equals(itemModel.micronAverage)) {
            holder.tv_store_horse.setText(itemModel.micronAverage);
        } else {
            holder.tv_store_horse.setText("--");
        }
        if (itemModel.storage != null && !"".equals(itemModel.storage)) {
            holder.tv_store_fatory.setText(itemModel.storage);
        } else {
            holder.tv_store_fatory.setText("--");
        }
        if (itemModel.type != null && !"".equals(itemModel.type)) {
            holder.tv_store_type.setText(itemModel.type);
        } else {
            holder.tv_store_type.setText("--");
        }
   /*     if (itemModel.distance != null && !"".equals(itemModel.distance)) {
            holder.tv_store_range.setText(itemModel.distance + "km");
            if (Double.parseDouble(itemModel.distance) == 0) {
                holder.tv_store_range.setText(itemModel.distance + "--");
            }
        } else {
            holder.tv_store_range.setText("--");
        }*/
        if (TextUtils.isEmpty(itemModel.isRule)) {
            holder.tv_store_range.setText("--");
        }else{
            if (itemModel.isRule.equals("0")) {
                holder.tv_store_range.setText("--");
            }else{
                StringJudgeUtils.judgeStringSts(itemModel.sts,holder.tv_store_range);
            }
        }
        if (itemModel.releaseDate != null && !"".equals(itemModel.releaseDate)) {
            holder.tv_store_date.setText(itemModel.releaseDate);
        } else {
            holder.tv_store_date.setText("--");
        }
        if (TextUtils.isEmpty(itemModel.isCheap)) {
            holder.ivIsCheap.setVisibility(View.GONE);
        } else {
            if (itemModel.isCheap.equals("0")) {
                holder.ivIsCheap.setVisibility(View.GONE);
            } else {
                holder.ivIsCheap.setVisibility(View.VISIBLE);
            }
        }
        if (isShowSelecter) {
            holder.aLL.setVisibility(View.VISIBLE);
        } else {
            holder.aLL.setVisibility(View.GONE);
        }
        holder.cbCheck.setChecked(itemModel.isSelect);
        holder.cbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemModel.isSelect = holder.cbCheck.isChecked();
                mOnItemListener.checkBoxClick(position);
                boolean flag = false;
                for (MyfavoritesBean.ValueBean.RowsBean bean : mData) {
                    if (bean.isSelect) {
                        flag = true;
                        break;
                    }
                }
                oncheckedListener.isChecked(flag);
            }
        });
        holder.ItemLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemListener.onItemClick(view, position);
            }
        });
        holder.ItemLL.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnItemListener.onItemLongClick(view, position, isShowSelecter);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.onItemClick(v, position);
            }
        });

    }

    //设置第二种布局
    private void setTwoType(final TypeTwoViewHolder holder, final int position) {
        // TODO: 2017/7/25 填充第二种布局数据
        final MyfavoritesBean.ValueBean.RowsBean itemModel = mData.get(position);
        holder.tvTitle.setText(itemModel.code + "(" + itemModel.bagCount + "/" + itemModel.batchCount + ")");
        if (itemModel.batchType.equals("1")) {
            holder.bt_key.setVisibility(View.GONE);
        } else {
            if (itemModel.property != null && !"".equals(itemModel.property)) {
                holder.bt_key.setText(itemModel.property);
                holder.bt_key.setVisibility(View.VISIBLE);
            } else {
                holder.bt_key.setVisibility(View.GONE);
            }
        }

        if (itemModel.type != null && !"".equals(itemModel.type)) {
            holder.tv_quality_type.setText(itemModel.type);
            holder.tv_quality_type.setVisibility(View.VISIBLE);
        } else {
            holder.tv_quality_type.setVisibility(View.GONE);
        }
        if (itemModel.lengthAverage != null && !"".equals(itemModel.lengthAverage)) {
            holder.tv_quality_length.setText(itemModel.lengthAverage);
            if (parseDouble(itemModel.lengthAverage) > 28) {
                holder.tv_quality_length.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.red_translucent));
            } else {
                holder.tv_quality_length.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            }
        } else {
            holder.tv_quality_length.setText("--");
        }
        if (itemModel.breakLoadAverage != null && !"".equals(itemModel.breakLoadAverage)) {
            holder.tv_quality_break.setText(itemModel.breakLoadAverage);
            if (parseDouble(itemModel.breakLoadAverage) > 28) {
                holder.tv_quality_break.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.red_translucent));
            } else {
                holder.tv_quality_break.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            }
        } else {
            holder.tv_quality_break.setText("--");
        }
        if (itemModel.micronAverage != null && !"".equals(itemModel.micronAverage)) {
            holder.tv_quality_horse.setText(itemModel.micronAverage);
        } else {
            holder.tv_quality_horse.setText("--");
        }
        if (itemModel.checkStorage != null && !"".equals(itemModel.checkStorage)) {
            holder.tv_quality_storage.setText(itemModel.checkStorage);
        } else {
            holder.tv_quality_storage.setText("--");
        }
        if (itemModel.factory != null && !"".equals(itemModel.factory)) {
            holder.tv_quality_factory.setText(itemModel.factory);
        } else {
            holder.tv_quality_factory.setText("--");
        }
        if (isShowSelecter) {
            holder.aLL.setVisibility(View.VISIBLE);
        } else {
            holder.aLL.setVisibility(View.GONE);
        }
        holder.cbCheck.setChecked(itemModel.isSelect);
        holder.cbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemModel.isSelect = holder.cbCheck.isChecked();
                mOnItemListener.checkBoxClick(position);
            }
        });
        holder.ItemLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemListener.onItemClick(view, position);
            }
        });
        holder.ItemLL.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnItemListener.onItemLongClick(view, position, isShowSelecter);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.onItemClick(v, position);
            }
        });
    }

    private OnItemListener mOnItemListener;

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public void removeData(MyfavoritesBean.ValueBean.RowsBean itemModel) {
        mData.remove(itemModel);
    }


    private boolean isShowSelecter;

    public void showSelect(boolean showSelect) {
        isShowSelecter = showSelect;
        notifyDataSetChanged();
    }

    public MyfavoritesBean.ValueBean.RowsBean getItem(int pos) {
        return mData.get(pos);
    }

    //第一种布局
    public class TypeOneViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public AutoLinearLayout ItemLL;
        public AutoLinearLayout aLL;
        public CheckBox cbCheck;
        public TextView mMoney;
        public TextView tv_store_commany;
        public TextView tv_store_length;
        public TextView tv_store_strong;
        public TextView tv_store_horse;
        public TextView tv_store_fatory;
        public TextView tv_store_type;
        public TextView tv_store_range;
        public TextView tv_store_date;
        public Button bt_store_key;
        public ImageView ivIsCheap;

        public TypeOneViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_content);
            ItemLL = (AutoLinearLayout) itemView.findViewById(R.id.ll_collection_item);
            cbCheck = (CheckBox) itemView.findViewById(R.id.cb_check);
            aLL = (AutoLinearLayout) itemView.findViewById(R.id.ll_checkbox);
            mMoney = (TextView) itemView.findViewById(R.id.tv_store_money);
            bt_store_key = (Button) itemView.findViewById(R.id.bt_store_key);
            tv_store_commany = (TextView) itemView.findViewById(R.id.tv_store_commany);
            tv_store_length = (TextView) itemView.findViewById(R.id.tv_store_length);
            tv_store_strong = (TextView) itemView.findViewById(R.id.tv_store_strong);
            tv_store_horse = (TextView) itemView.findViewById(R.id.tv_store_horse);
            tv_store_fatory = (TextView) itemView.findViewById(R.id.tv_store_fatory);
            tv_store_type = (TextView) itemView.findViewById(R.id.tv_store_type);
            tv_store_range = (TextView) itemView.findViewById(R.id.tv_store_range);
            tv_store_date = (TextView) itemView.findViewById(R.id.tv_store_date);
            ivIsCheap = (ImageView) itemView.findViewById(R.id.ivIsCheap);
        }
    }

    //第二种布局
    public class TypeTwoViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public AutoLinearLayout ItemLL;
        public AutoLinearLayout aLL;
        public CheckBox cbCheck;
        public Button bt_key;
        public TextView tv_quality_type;
        public TextView tv_quality_length;
        public TextView tv_quality_break;
        public TextView tv_quality_horse;
        public TextView tv_quality_storage;
        public TextView tv_quality_factory;


        public TypeTwoViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_bitch);
            ItemLL = (AutoLinearLayout) itemView.findViewById(R.id.ll_collection_item2);
            cbCheck = (CheckBox) itemView.findViewById(R.id.cb_check);
            aLL = (AutoLinearLayout) itemView.findViewById(R.id.ll_checkbox);
            bt_key = (Button) itemView.findViewById(R.id.bt_key);
            tv_quality_type = (TextView) itemView.findViewById(R.id.tv_quality_type);
            tv_quality_length = (TextView) itemView.findViewById(R.id.tv_quality_length);
            tv_quality_break = (TextView) itemView.findViewById(R.id.tv_quality_break);
            tv_quality_horse = (TextView) itemView.findViewById(R.id.tv_quality_horse);
            tv_quality_storage = (TextView) itemView.findViewById(R.id.tv_quality_storage);
            tv_quality_factory = (TextView) itemView.findViewById(R.id.tv_quality_factory);

        }
    }
}
