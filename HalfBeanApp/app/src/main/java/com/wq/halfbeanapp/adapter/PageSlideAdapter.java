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
public class PageSlideAdapter extends BaseRecyclerViewAdapter<String> {
    public PageSlideAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        TextView tvSlide = (TextView) holder.getView(R.id.tvSlide, true);
        tvSlide.setText(getItem(position));

    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_item_slide;
    }
}
