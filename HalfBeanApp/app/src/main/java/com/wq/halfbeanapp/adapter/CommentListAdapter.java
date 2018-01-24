package com.wq.halfbeanapp.adapter;

import android.content.Context;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.CommentBean;

/**
 * Created by vivianWQ on 2018/1/24
 * Mail: wangqi_vivian@sina.com
 * desc: 评论列表adapter
 * Version: 1.0
 */
public class CommentListAdapter extends BaseRecyclerViewAdapter<CommentBean> {
    public CommentListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_item_comment_list;
    }
}
