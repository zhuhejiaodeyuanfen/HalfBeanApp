package com.wq.halfbeanapp.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.CommentBean;
import com.wq.halfbeanapp.util.AppDateUtil;
import com.wq.halfbeanapp.util.sdk.glide.GlideImageLoader;

/**
 * Created by vivianWQ on 2018/1/24
 * Mail: wangqi_vivian@sina.com
 * desc: 评论列表adapter
 * <p>
 * desc: 时间显示原则
 * 小于1分钟   刚刚
 * 小于1小时--小于1小时   xx分钟前
 * 小于24小时---大于1小时  xx小时前
 * 大于1天---3天  x天前
 * 大于3天   当年  xx月x日
 * 大于3天 非当年 xx年xx月x日
 * Version: 1.0
 */
public class CommentListAdapter extends BaseRecyclerViewAdapter<CommentBean> {
    private Context context;

    public CommentListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        CommentBean item = getItem(position);
        TextView tvWriter = (TextView) holder.getView(R.id.tvWriter, false);
        tvWriter.setText(item.getUserName());
        ImageView ivIcon = (ImageView) holder.getView(R.id.ivIcon, false);
        GlideImageLoader.display(context, ivIcon, item.getUserIcon());
        TextView tvContent = (TextView) holder.getView(R.id.tvContent, false);
        tvContent.setText(item.getUserComment());
        TextView tvTimeDate = (TextView) holder.getView(R.id.tvTimeDate, false);
//        tvTimeDate.setText(item.getCommentTime() + "");
        tvTimeDate.setText(AppDateUtil.getCompareShowString(item.getSysCurrentTime().getTime(),item.getCommentTime().getTime()));

    }



    @Override
    public int getLayoutId() {
        return R.layout.layout_item_comment_list;
    }
}
