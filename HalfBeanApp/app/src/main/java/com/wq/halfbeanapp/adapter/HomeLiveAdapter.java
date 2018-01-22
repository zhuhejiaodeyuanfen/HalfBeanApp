package com.wq.halfbeanapp.adapter;

import android.content.Context;
import android.widget.TextView;

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
        HomeBoardDetailModel item = getItem(position);
        TextView tvTitle = (TextView) holder.getView(R.id.tvTitle, false);
        tvTitle.setText(item.getPostTitle());
        TextView tvWriter = (TextView) holder.getView(R.id.tvWriter, false);
        tvWriter.setText(item.getPostAdmin());
        TextView tvCount = (TextView) holder.getView(R.id.tvCount, false);
        tvCount.setText(item.getPostCommentCount() + "");


    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_item_homelive_list;
    }
}
