package com.wq.halfbeanapp.adapter;

import android.content.Context;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.HomeBoardDetailModel;
import com.wq.halfbeanapp.util.AppDateUtil;

/**
 * Created by vivianWQ on 2018/1/19
 * Mail: wangqi_vivian@sina.com
 * desc: 时间显示原则
 * 小于60秒  xx秒前更新
 * 小于1小时  大于60秒 xx分钟前更新
 * 大于1小时 小于1天  xx小时前更新
 * 大于1天 小于7天  xx天前更新
 * 当前年份内更新   xx月xx日更新
 * 非当前年份更新 xx年xx月xx日更新
 * Version: 1.0
 * <p>
 * 首页 话题 adapter
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
        holder.getView(R.id.baseItem, true);
        TextView tvUpdateTime = (TextView) holder.getView(R.id.tvUpdateTime, false);
        tvUpdateTime.setText(AppDateUtil.getHomeBoard(item.getSysCurrentTime().getTime(), item.getPostUpdateTime().getTime()) + "更新");


    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_item_homelive_list;
    }
}
