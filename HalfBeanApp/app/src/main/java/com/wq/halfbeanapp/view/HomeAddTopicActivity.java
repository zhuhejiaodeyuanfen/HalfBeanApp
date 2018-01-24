package com.wq.halfbeanapp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wq.halfbeanapp.R;

public class HomeAddTopicActivity extends BaseActivity {
    private TextView tvAddPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_add_topic);
    }

    @Override
    public void initView() {

        tvAddPic = (TextView) findViewById(R.id.tvAddPic);
    }

    @Override
    public void initEventData() {

    }

    @Override
    public void bindEvent() {
        //从相册选择
        tvAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher(HomeAddTopicActivity.this, PhotoSelectActivity.class);

            }
        });

    }

    @Override
    public void loadData() {

    }
}
