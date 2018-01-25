package com.wq.halfbeanapp.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.LockAdapter;

public class HomeAddTopicActivity extends BaseActivity {
    private TextView tvAddPic;
    private RecyclerView rvAddTopic;
    private LockAdapter lockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_add_topic);
    }

    @Override
    public void initView() {

        tvAddPic = (TextView) findViewById(R.id.tvAddPic);
        rvAddTopic = (RecyclerView) findViewById(R.id.rvAddTopic);
        rvAddTopic.setLayoutManager(new LinearLayoutManager(HomeAddTopicActivity.this, LinearLayoutManager.VERTICAL,false));

    }

    @Override
    public void initEventData() {
        lockAdapter = new LockAdapter(HomeAddTopicActivity.this);
        rvAddTopic.setAdapter(lockAdapter);


    }

    @Override
    public void bindEvent() {
        //从相册选择
        tvAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   launcher(HomeAddTopicActivity.this, PhotoSelectActivity.class);
//                TypeBean typeBean=new TypeBean();
//                typeBean.setType(1);
//                typeBean.setContent("");
//                lockAdapter.addData(typeBean);


            }
        });

    }


    @Override
    public void loadData() {

    }
}
