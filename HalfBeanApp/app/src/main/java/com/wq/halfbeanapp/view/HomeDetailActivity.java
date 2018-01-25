package com.wq.halfbeanapp.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.CommentListAdapter;
import com.wq.halfbeanapp.adapter.HomeDetailAdapter;
import com.wq.halfbeanapp.bean.CommentBean;
import com.wq.halfbeanapp.bean.HomeBoardDetailModel;
import com.wq.halfbeanapp.bean.TypeBean;
import com.wq.halfbeanapp.constants.UrlConstants;
import com.wq.halfbeanapp.net.response.DataListResponseCallback;
import com.wq.halfbeanapp.net.response.JsonTools;
import com.wq.halfbeanapp.net.response.RoNetWorkUtil;

import java.util.List;

public class HomeDetailActivity extends BaseActivity {

    private TextView tvContent, tvSubTitle, tvWriter, tvTimeDate;
    private RecyclerView rvComment;
    private HomeBoardDetailModel homeBoardDetailModel;
    private ImageView ivIcon;
    private RecyclerView rvContent;

    private CommentListAdapter commentListAdapter;
    private HomeDetailAdapter homeDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);
    }

    @Override
    public void initView() {
        initTitle("话题详情");
        tvContent = (TextView) findViewById(R.id.tvContent);
        rvComment = (RecyclerView) findViewById(R.id.rvComment);
        tvSubTitle = (TextView) findViewById(R.id.tvSubTitle);
        tvWriter = (TextView) findViewById(R.id.tvWriter);
        tvTimeDate = (TextView) findViewById(R.id.tvTimeDate);
        rvContent = (RecyclerView) findViewById(R.id.rvContent);

    }

    @Override
    public void initEventData() {
        homeBoardDetailModel = (HomeBoardDetailModel) getIntent().getSerializableExtra("item");
        commentListAdapter = new CommentListAdapter(HomeDetailActivity.this);
        homeDetailAdapter = new HomeDetailAdapter(HomeDetailActivity.this);
        rvContent.setLayoutManager(new LinearLayoutManager(HomeDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        rvContent.setAdapter(homeDetailAdapter);
        rvComment.setLayoutManager(new LinearLayoutManager(HomeDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        rvComment.setAdapter(commentListAdapter);

    }

    @Override
    public void bindEvent() {

    }

    @Override
    public void loadData() {
        if (homeBoardDetailModel != null) {
//            tvContent.setText(homeBoardDetailModel.getPostContent());
            tvSubTitle.setText(homeBoardDetailModel.getPostTitle());
            tvWriter.setText(homeBoardDetailModel.getPostAdmin());
            tvTimeDate.setText(homeBoardDetailModel.getPostCreateTime().toString());
            List<TypeBean> beanList = JsonTools.getBeanList(homeBoardDetailModel.getPostContent(), TypeBean.class);
            if (beanList != null && beanList.size() > 0) {
                homeDetailAdapter.addData(beanList);
            }

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
