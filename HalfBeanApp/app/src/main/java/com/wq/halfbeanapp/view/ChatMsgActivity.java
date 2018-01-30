package com.wq.halfbeanapp.view;

import android.os.Bundle;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.MsgDetailModel;
import com.wq.halfbeanapp.presenter.MessagePresenter;
import com.wq.halfbeanapp.util.AppLogUtil;
import com.wq.halfbeanapp.view.iview.IMsgView;

import java.util.List;

public class ChatMsgActivity extends BaseActivity implements IMsgView {
    private MessagePresenter<ChatMsgActivity> messagePresenter;
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
        messagePresenter = new MessagePresenter<>(this, this);
        id = getIntent().getIntExtra("id", 0);


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
        AppLogUtil.i("收到数据");

    }
}
