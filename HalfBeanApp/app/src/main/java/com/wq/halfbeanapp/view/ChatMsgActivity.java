package com.wq.halfbeanapp.view;

import android.os.Bundle;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.MsgModel;
import com.wq.halfbeanapp.constants.UrlConstants;
import com.wq.halfbeanapp.util.network.HttpListCallBack;
import com.wq.halfbeanapp.util.network.HttpUtil;

import java.util.HashMap;
import java.util.List;

public class ChatMsgActivity extends BaseActivity {
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_msg);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEventData() {
        id = getIntent().getIntExtra("id", 0);


    }

    @Override
    public void bindEvent() {

    }

    @Override
    public void loadData() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("msgCountId", id + "");
        hashMap.put("page", 0 + "");
        HttpUtil.getInstance().requestList(UrlConstants.GET_MSG_FROM_LIST, hashMap, new HttpListCallBack<MsgModel>() {
            @Override
            public void onSuccess(List<MsgModel> data) {


            }

            @Override
            public void onFail(String msg) {

            }
        });

    }
}
