package com.wq.halfbeanapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.LivePhotoDetailModel;
import com.wq.halfbeanapp.util.sdk.glide.GlideImageLoader;

/**
 * Created by vivianWQ on 2018/1/18
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class PhotoVpAdapter extends BaseRecyclerViewAdapter<LivePhotoDetailModel> {
    private Context context;

    public PhotoVpAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        LivePhotoDetailModel item = getItem(position);
        View view = holder.getView(R.id.baseItem, false);
        view.setBackgroundColor(Color.parseColor(item.getPostBackColor()));
        TextView tvContent = (TextView) holder.getView(R.id.tvContent, false);
        tvContent.setText(item.getPostContent());
        TextView tvWriter = (TextView) holder.getView(R.id.tvWriter, false);
        tvWriter.setText("--------"+item.getPostAdmin());

        ImageView ivIcon = (ImageView) holder.getView(R.id.ivIcon, true);
        GlideImageLoader.display(context, ivIcon, item.getPostAdminIcon());
        TextView tvPraiseCount = (TextView) holder.getView(R.id.tvPraiseCount, true);
        TextView tvCommentCount = (TextView) holder.getView(R.id.tvCommentCount, true);


    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_item_hotlive_page;
    }
}
