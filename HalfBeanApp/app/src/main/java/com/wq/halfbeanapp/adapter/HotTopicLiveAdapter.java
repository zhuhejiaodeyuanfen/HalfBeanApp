package com.wq.halfbeanapp.adapter;

import android.content.Context;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.LiveBoardModel;

/**
 * Created by vivianWQ on 2018/1/17
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class HotTopicLiveAdapter extends BaseRecyclerViewAdapter<LiveBoardModel> {
    public HotTopicLiveAdapter(Context context) {
        super(context);
    }




    @Override
    protected void bindData(BaseRecyclerViewAdapter.BaseViewHolder holder, int position) {
        LiveBoardModel item = getItem(position);
        TextView tvTitle = (TextView) holder.getView(R.id.tvTitle, false);
        TextView tvContent = (TextView) holder.getView(R.id.tvContent, false);
        holder.getView(R.id.baseItem,true);
        tvTitle.setText(item.getLiveBoardTitle());
        tvContent.setText(item.getLiveBoardContent());

    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_item_hotlive_list;
    }
}
