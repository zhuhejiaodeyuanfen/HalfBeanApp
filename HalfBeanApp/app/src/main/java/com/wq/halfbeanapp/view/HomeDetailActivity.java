package com.wq.halfbeanapp.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.CommentListAdapter;
import com.wq.halfbeanapp.bean.CommentBean;
import com.wq.halfbeanapp.bean.HomeBoardDetailModel;
import com.wq.halfbeanapp.constants.UrlConstants;
import com.wq.halfbeanapp.net.response.DataListResponseCallback;
import com.wq.halfbeanapp.net.response.RoNetWorkUtil;

import java.util.List;

public class HomeDetailActivity extends BaseActivity {

    private TextView tvContent, tvTitle, tvWriter, tvTimeDate;
    private RecyclerView rvComment;
    private HomeBoardDetailModel homeBoardDetailModel;
    private ImageView ivIcon;

    private CommentListAdapter commentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);
    }

    @Override
    public void initView() {
        tvContent = (TextView) findViewById(R.id.tvContent);
        rvComment = (RecyclerView) findViewById(R.id.rvComment);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvWriter = (TextView) findViewById(R.id.tvWriter);
        tvTimeDate = (TextView) findViewById(R.id.tvTimeDate);

    }

    @Override
    public void initEventData() {
        homeBoardDetailModel = (HomeBoardDetailModel) getIntent().getSerializableExtra("item");
        commentListAdapter = new CommentListAdapter(HomeDetailActivity.this);
        rvComment.setLayoutManager(new LinearLayoutManager(HomeDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        rvComment.setAdapter(commentListAdapter);

    }

    @Override
    public void bindEvent() {

    }

    @Override
    public void loadData() {
        if (homeBoardDetailModel != null) {
            tvContent.setText(homeBoardDetailModel.getPostContent());
            tvTitle.setText(homeBoardDetailModel.getPostTitle());
            tvWriter.setText(homeBoardDetailModel.getPostAdmin());
            tvTimeDate.setText(homeBoardDetailModel.getPostCreateTime().toString());
        }

        RoNetWorkUtil
                .getInstance()
                .get(UrlConstants.GET_COMMENT_LIST)
                .conParams("postId=1")
                .execute1(new DataListResponseCallback<CommentBean>() {
                    @Override
                    public void onResponseSuccess(List<CommentBean> response) {
                        if (response != null && response.size() > 0)
                            commentListAdapter.addData(response);

                    }

                    @Override
                    public void onResponseFail(String errorString) {

                    }
                });

    }
}
