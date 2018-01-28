package com.wq.halfbeanapp.adapter;

import android.content.Context;
import android.widget.TextView;

import com.wq.halfbeanapp.R;

/**
 * Created by vivianWQ on 2018/1/27
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class PageGridAdapter extends BaseRecyclerViewAdapter<Integer> {
    public PageGridAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        Integer item = getItem(position);
        TextView tvCount= (TextView) holder.getView(R.id.tvCount,true);
        tvCount.setText(item+"");

    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_item_grid_item;
    }
}
