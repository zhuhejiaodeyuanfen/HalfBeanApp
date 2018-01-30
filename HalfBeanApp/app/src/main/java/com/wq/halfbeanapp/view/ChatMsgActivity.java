package com.wq.halfbeanapp.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.MsgDetailListAdapter;
import com.wq.halfbeanapp.bean.MsgDetailModel;
import com.wq.halfbeanapp.presenter.MessagePresenter;
import com.wq.halfbeanapp.view.iview.IMsgView;

import java.util.List;

public class ChatMsgActivity extends BaseActivity implements IMsgView {
    private MessagePresenter<ChatMsgActivity> messagePresenter;
    private int id;
    private RecyclerView rvMsg;
    private MsgDetailListAdapter msgDetailListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_msg);
    }

    @Override
    public void initView() {
        rvMsg = (RecyclerView) findViewById(R.id.rvMsg);
        initTitle("消息");

    }

    @Override
    public void initEventData() {
        messagePresenter = new MessagePresenter<>(this, this);
        id = getIntent().getIntExtra("id", 0);
        msgDetailListAdapter = new MsgDetailListAdapter(ChatMsgActivity.this);
        rvMsg.setLayoutManager(new LinearLayoutManager(ChatMsgActivity.this, LinearLayoutManager.VERTICAL, false));
        rvMsg.setAdapter(msgDetailListAdapter);
    }

    @Override
    public void bindEvent() {

    }

    @Override
    public void loadData() {
        messagePresenter.getUserMsgList(id, 0);


    }

    @Override
    public void getMsgList(List<MsgDetailModel> msgModels) {
        msgDetailListAdapter.addData(msgModels);

    }
}
