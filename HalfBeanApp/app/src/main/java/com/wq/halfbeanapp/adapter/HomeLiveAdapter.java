package com.wq.halfbeanapp.adapter;

import android.content.Context;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.HomeBoardDetailModel;

/**
 * Created by vivianWQ on 2018/1/19
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class HomeLiveAdapter extends BaseRecyclerViewAdapter<HomeBoardDetailModel> {
    public HomeLiveAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_item_homelive_list;
    }
}
