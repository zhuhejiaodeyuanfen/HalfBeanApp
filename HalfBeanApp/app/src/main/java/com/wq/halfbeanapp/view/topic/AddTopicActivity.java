package com.wq.halfbeanapp.view.topic;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.LiveBoardModel;
import com.wq.halfbeanapp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddTopicActivity extends BaseActivity {

    private Button btnCommit, btnChange;
    private EditText etTopic;

    private List<String> colorList = new ArrayList<>();
    private LiveBoardModel liveBoardModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);
    }

    @Override
    public void initView() {
        etTopic = (EditText) findViewById(R.id.etTopic);
        btnCommit = (Button) findViewById(R.id.btnCommit);
        btnChange = (Button) findViewById(R.id.btnChange);

    }

    @Override
    public void initEventData() {
        colorList.add("#c63c26");
        colorList.add("#f3715c");
        colorList.add("#cd9a5b");
        colorList.add("#ad8b3d");
        colorList.add("#dea32c");
        colorList.add("#df9464");
        colorList.add("#c88400");
        colorList.add("#ffd400");
        colorList.add("#7bbfea");
        colorList.add("#008792");
        colorList.add("#78cdd1");


    }

    @Override
    public void bindEvent() {
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etTopic.setBackgroundColor(Color.parseColor(colorList.get(new Random().nextInt(colorList.size()))));


            }
        });

    }

    @Override
    public void loadData() {

    }
}
