package com.wq.halfbeanapp.view.topic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.PhotoVpAdapter;
import com.wq.halfbeanapp.bean.LivePhotoDetailModel;
import com.wq.halfbeanapp.constants.UrlConstants;
import com.wq.halfbeanapp.net.response.DataListResponseCallback;
import com.wq.halfbeanapp.net.response.RoNetWorkUtil;
import com.wq.halfbeanapp.view.BaseActivity;
import com.wq.halfbeanapp.widget.recyclerviewpagerlib.RecyclerViewPager;

import java.util.HashMap;
import java.util.List;

public class TopicCardActivity extends BaseActivity {
    private RecyclerViewPager rvPager;
    private PhotoVpAdapter photoVpAdapter;
    private Button btnCommit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_card);

    }

    @Override
    public void initView() {

        btnCommit= (Button) findViewById(R.id.btnCommit);
        photoVpAdapter = new PhotoVpAdapter(TopicCardActivity.this);
        rvPager = (RecyclerViewPager) findViewById(R.id.rvPager);
        rvPager.setAdapter(photoVpAdapter);
        LinearLayoutManager layout = new LinearLayoutManager(TopicCardActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvPager.setLayoutManager(layout);
        rvPager.setHasFixedSize(true);
        rvPager.setLongClickable(true);

    }

    @Override
    public void initEventData() {

    }

    @Override
    public void bindEvent() {
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher(TopicCardActivity.this, AddTopicActivity.class);

            }
        });

    }

    @Override
    public void loadData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("id", "1");
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
