package com.wq.halfbeanapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.LivePhotoDetailModel;

/**
 * Created by vivianWQ on 2018/1/18
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class PhotoVpAdapter extends BaseRecyclerViewAdapter<LivePhotoDetailModel> {
    public PhotoVpAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        LivePhotoDetailModel item = getItem(position);
        View view = holder.getView(R.id.baseItem, false);
        view.setBackgroundColor(Color.parseColor(item.getPostBackColor()));
        TextView tvContent = (TextView) holder.getView(R.id.tvContent, false);
        tvContent.setText(item.getPostContent());
        TextView tvWriter = (TextView) holder.getView(R.id.tvWriter, false);
        tvWriter.setText(item.getPostAdmin());


    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_item_hotlive_page;
    }
}
