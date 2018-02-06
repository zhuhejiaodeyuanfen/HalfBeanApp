package com.wq.halfbeanapp.view.topic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.MyItemClickListener;
import com.wq.halfbeanapp.adapter.PhotoVpAdapter;
import com.wq.halfbeanapp.bean.LiveBoardModel;
import com.wq.halfbeanapp.bean.LivePhotoDetailModel;
import com.wq.halfbeanapp.constants.UrlConstants;
import com.wq.halfbeanapp.net.response.DataListResponseCallback;
import com.wq.halfbeanapp.net.response.RoNetWorkUtil;
import com.wq.halfbeanapp.util.AppDateUtil;
import com.wq.halfbeanapp.util.sdk.glide.GlideImageLoader;
import com.wq.halfbeanapp.view.BaseActivity;
import com.wq.halfbeanapp.view.UserDetailActivity;
import com.wq.halfbeanapp.widget.recyclerviewpagerlib.RecyclerViewPager;

import java.util.HashMap;
import java.util.List;

public class TopicCardActivity extends BaseActivity {
    private RecyclerViewPager rvPager;
    private PhotoVpAdapter photoVpAdapter;
    private TextView btnCommit;
    private TextView tvMainTitle, tvSubTitle, tvTime;
    private LiveBoardModel liveBoardModel;
    private ImageView ivIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_card);

    }

    @Override
    public void initView() {
        initTitle("A Live");
        btnCommit = (TextView) findViewById(R.id.btnCommit);
        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        tvMainTitle = (TextView) findViewById(R.id.tvMainTitle);
        tvSubTitle = (TextView) findViewById(R.id.tvSubTitle);
        tvTime = (TextView) findViewById(R.id.tvTime);
        photoVpAdapter = new PhotoVpAdapter(TopicCardActivity.this);
        rvPager = (RecyclerViewPager) findViewById(R.id.rvPager);
        rvPager.setAdapter(photoVpAdapter);
        final LinearLayoutManager layout = new LinearLayoutManager(TopicCardActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvPager.setLayoutManager(layout);
        rvPager.setHasFixedSize(true);
        rvPager.setLongClickable(true);


        rvPager.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
//                updateState(scrollState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                int childCount = recyclerView.getChildCount();
                int width = recyclerView.getChildAt(0).getWidth();
                int padding = (recyclerView.getWidth() - width) / 2;

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
                    float rate = 0;

                    if (v.getLeft() <= padding) {
                        if (v.getLeft() >= padding - v.getWidth()) {
                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
                        } else {
                            rate = 1;
                        }
                        v.setScaleY(1 - rate * 0.1f);
                        v.setScaleX(1 - rate * 0.1f);

                    } else {
                        //往右 从 padding 到 recyclerView.getWidth()-padding 的过程中，由大到小
                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                        }
                        v.setScaleY(0.9f + rate * 0.1f);
                        v.setScaleX(0.9f + rate * 0.1f);
                    }
                }
            }
        });
        photoVpAdapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LivePhotoDetailModel item = photoVpAdapter.getItem(position);
                switch (view.getId()) {
                    case R.id.ivIcon:
                        //点击头像
                        Bundle args = new Bundle();
                        args.putInt("uid", item.getPostAdminId());
                        launcher(TopicCardActivity.this, UserDetailActivity.class, args);
                        break;
                    case R.id.tvPraiseCount:
                        showToast("用户点击了赞");
                        break;
                    case R.id.tvCommentCount:
                        break;
                }
            }
        });


        rvPager.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (rvPager.getChildCount() < 3) {
                    if (rvPager.getChildAt(1) != null) {
                        if (rvPager.getCurrentPosition() == 0) {
                            View v1 = rvPager.getChildAt(1);
                            v1.setScaleY(0.9f);
                            v1.setScaleX(0.9f);
                        } else {
                            View v1 = rvPager.getChildAt(0);
                            v1.setScaleY(0.9f);
                            v1.setScaleX(0.9f);
                        }
                    }
                } else {
                    if (rvPager.getChildAt(0) != null) {
                        View v0 = rvPager.getChildAt(0);
                        v0.setScaleY(0.9f);
                        v0.setScaleX(0.9f);
                    }
                    if (rvPager.getChildAt(2) != null) {
                        View v2 = rvPager.getChildAt(2);
                        v2.setScaleY(0.9f);
                        v2.setScaleX(0.9f);
                    }
                }

            }
        });
    }

    @Override
    public void initEventData() {
        liveBoardModel = (LiveBoardModel) getIntent().getSerializableExtra("data");
        if (liveBoardModel != null) {
            tvMainTitle.setText(liveBoardModel.getLiveBoardTitle());
            tvSubTitle.setText(liveBoardModel.getLiveBoardContent());
            tvTime.setText(AppDateUtil.getStringByFormat(liveBoardModel.getCreateTime().getTime(), AppDateUtil.dateFormatYMD));
            GlideImageLoader.display(TopicCardActivity.this, ivIcon, liveBoardModel.getLiveIcon());
        }

    }

    @Override
    public void bindEvent() {
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("data", liveBoardModel);
                launcher(TopicCardActivity.this, AddTopicActivity.class, args);

            }
        });

    }

    @Override
    public void loadData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("id", liveBoardModel.getLiveBoardModelId() + "");
        RoNetWorkUtil
                .getInstance()
                .get(UrlConstants.QUERY_HOT_List)
                .params(map)
                .execute(new DataListResponseCallback<LivePhotoDetailModel>() {
                    @Override
                    public void onResponseSuccess(List<LivePhotoDetailModel> response) {
                        if (response != null && response.size() > 0) {
                            photoVpAdapter.addData(response);
                        }

                    }

                    @Override
                    public void onResponseFail(String errorString) {

                    }
                });

    }
}
