package com.wq.halfbeanapp.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.MsgListAdapter;
import com.wq.halfbeanapp.adapter.MyItemClickListener;
import com.wq.halfbeanapp.bean.MsgModel;
import com.wq.halfbeanapp.bean.SocketModel;
import com.wq.halfbeanapp.bean.UserMessageList;
import com.wq.halfbeanapp.constants.UrlConstants;
import com.wq.halfbeanapp.net.response.DataListResponseCallback;
import com.wq.halfbeanapp.net.response.JsonTools;
import com.wq.halfbeanapp.net.response.RoNetWorkUtil;
import com.wq.halfbeanapp.util.user.UserInfoUtil;
import com.wq.halfbeanapp.view.AboutMyActivity;
import com.wq.halfbeanapp.view.ChatMsgActivity;
import com.wq.halfbeanapp.view.CommentMyActivity;
import com.wq.halfbeanapp.view.PraiseMyActivity;
import com.wq.halfbeanapp.view.presenter.ChatSocketPresenter;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by vivianWQ on 2017/12/7
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class MessageFragment extends BaseFragment {
    private ChatSocketPresenter chatSocketPresenter;
    private Button btnSend;
    private MsgListAdapter msgListAdapter;
    private RecyclerView rvMessage;
    private TextView tvAboutMe, tvComment, tvPraise;

    @Override
    public void initEventData() {
        chatSocketPresenter = new ChatSocketPresenter(mContext);
        chatSocketPresenter.connectService();
        msgListAdapter = new MsgListAdapter(mContext);
        rvMessage.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvMessage.setAdapter(msgListAdapter);


    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_halfbean;
    }

    @Override
    public void initView() {
        btnSend = (Button) mContentView.findViewById(R.id.btnSend);
        rvMessage = (RecyclerView) mContentView.findViewById(R.id.rvMessage);
        tvAboutMe = (TextView) mContentView.findViewById(R.id.tvAboutMe);
        tvComment = (TextView) mContentView.findViewById(R.id.tvComment);
        tvPraise = (TextView) mContentView.findViewById(R.id.tvPraise);

    }

    @Override
    public void bindEvent() {
        msgListAdapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                getBaseActivity().launcher(mContext, ChatMsgActivity.class);

            }
        });
        tvAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().launcher(mContext, AboutMyActivity.class);
            }
        });

        tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getBaseActivity().launcher(mContext, CommentMyActivity.class);
            }
        });

        tvPraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().launcher(mContext, PraiseMyActivity.class);
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgModel msgModel = new MsgModel();
                msgModel.setMsgContent("尝试发送一条消息2018900");
                msgModel.setMsgFromId(UserInfoUtil.getUserInfo(mContext).getUserId());
                msgModel.setMsgTime(new Timestamp(System.currentTimeMillis()));
                msgModel.setMsgToId(2);
                msgModel.setMsgType(1);//文字消息
                SocketModel socketModel = new SocketModel();
                socketModel.setData(msgModel);
                socketModel.setSocketType("send_message");
                chatSocketPresenter.startConnect(JsonTools.getJsonString(socketModel));
            }
        });

    }

    @Override
    public void loadData() {

        RoNetWorkUtil
                .getInstance()
                .get(UrlConstants.GET_MSG_LIST)
                .params("")
                .execute(new DataListResponseCallback<UserMessageList>() {
                    @Override
                    public void onResponseSuccess(List<UserMessageList> response) {
                        if (response != null && response.size() > 0)
                            msgListAdapter.addData(response);

                    }

                    @Override
                    public void onResponseFail(String errorString) {

                    }
                });

    }
}
