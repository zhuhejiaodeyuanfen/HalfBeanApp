package com.wq.halfbeanapp.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.HomeBoardDetailModel;
import com.wq.halfbeanapp.util.AppDateUtil;
import com.wq.halfbeanapp.util.sdk.glide.GlideImageLoader;

import java.text.DecimalFormat;

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
    private Context context;

    public HomeLiveAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        HomeBoardDetailModel item = getItem(position);
        TextView tvTitle = (TextView) holder.getView(R.id.tvTitle, false);
        tvTitle.setText(item.getPostTitle());
        TextView tvWriter = (TextView) holder.getView(R.id.tvWriter, false);
        tvWriter.setText(item.getPostAdmin());

        TextView tvCount = (TextView) holder.getView(R.id.tvCount, false);
        if (item.getPostCommentCount() < 999)
            tvCount.setText(item.getPostCommentCount() + "");
        else {
            float num = (float) item.getPostCommentCount() / 1000;
            DecimalFormat df = new DecimalFormat("0.0");//格式化小数
            String s = df.format(num);//返回的是String类型
            tvCount.setText(s + "k");
        }
        holder.getView(R.id.baseItem, true);
        TextView tvUpdateTime = (TextView) holder.getView(R.id.tvUpdateTime, false);
        tvUpdateTime.setText(AppDateUtil.getHomeBoard(item.getSysCurrentTime().getTime(), item.getPostUpdateTime().getTime()) + "更新");
        ImageView ivIcon = (ImageView) holder.getView(R.id.ivIcon, false);
        GlideImageLoader.display(context, ivIcon, item.getPostAdminIcon(), R.mipmap.ic_launcher);


    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_item_homelive_list;
    }
}
