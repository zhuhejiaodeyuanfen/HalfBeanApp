package com.wq.halfbeanapp.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.MsgDetailListAdapter;
import com.wq.halfbeanapp.bean.MsgDetailModel;
import com.wq.halfbeanapp.bean.MsgModel;
import com.wq.halfbeanapp.bean.UserMessageList;
import com.wq.halfbeanapp.presenter.MessagePresenter;
import com.wq.halfbeanapp.util.user.UserInfoUtil;
import com.wq.halfbeanapp.view.iview.IMsgView;

import java.sql.Timestamp;
import java.util.List;

public class ChatMsgActivity extends BaseActivity implements IMsgView {
    private MessagePresenter<ChatMsgActivity> messagePresenter;
    private RecyclerView rvMsg;
    private MsgDetailListAdapter msgDetailListAdapter;
    private TextView btnSend;
    private EditText etContent;
    private UserMessageList userMessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_msg);
    }

    @Override
    public void initView() {
        rvMsg = (RecyclerView) findViewById(R.id.rvMsg);
        btnSend = (TextView) findViewById(R.id.btnSend);
        etContent = (EditText) findViewById(R.id.etContent);
        initTitle("消息");

    }

    @Override
    public void initEventData() {
        messagePresenter = new MessagePresenter<>(this, this);
        userMessageList = (UserMessageList) getIntent().getSerializableExtra("data");
        msgDetailListAdapter = new MsgDetailListAdapter(ChatMsgActivity.this);
        rvMsg.setLayoutManager(new LinearLayoutManager(ChatMsgActivity.this, LinearLayoutManager.VERTICAL, false));
        rvMsg.setAdapter(msgDetailListAdapter);
    }

    @Override
    public void bindEvent() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgModel msgModel = new MsgModel();
                msgModel.setMsgType(1);//文字消息
                msgModel.setMsgContent(etContent.getText().toString());
                msgModel.setMsgFromId(UserInfoUtil.getUserInfo(ChatMsgActivity.this).getUserId());
                msgModel.setMsgTime(new Timestamp(System.currentTimeMillis()));
                msgModel.setMsgToId(userMessageList.getMsgUserId());
                boolean b = messagePresenter.sendMsg(msgModel);
                if (b) {
                    MsgDetailModel msgDetailModel=new MsgDetailModel();
                    msgDetailModel.setMsgTime(msgModel.getMsgTime());
                    msgDetailModel.setMsgContent(etContent.getText().toString());
//                    msgDetailModel.setMsgType();
                    showToast("发送成功");
                }
                else
                    showToast("发送失败");

            }
        });

    }

    @Override
    public void loadData() {
        messagePresenter.getUserMsgList(userMessageList.getMsgListId(), 0);


    }

    @Override
    public void getMsgList(List<MsgDetailModel> msgModels) {
        msgDetailListAdapter.addData(msgModels, true);

    }
}
