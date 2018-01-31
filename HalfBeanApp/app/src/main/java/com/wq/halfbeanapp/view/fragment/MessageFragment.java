package com.wq.halfbeanapp.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.MsgListAdapter;
import com.wq.halfbeanapp.adapter.MyItemClickListener;
import com.wq.halfbeanapp.bean.UserMessageList;
import com.wq.halfbeanapp.presenter.ChatSocketPresenter;
import com.wq.halfbeanapp.presenter.MsgFragmentPresenter;
import com.wq.halfbeanapp.view.AboutMyActivity;
import com.wq.halfbeanapp.view.ChatMsgActivity;
import com.wq.halfbeanapp.view.CommentMyActivity;
import com.wq.halfbeanapp.view.PraiseMyActivity;
import com.wq.halfbeanapp.view.iview.IMsgFragmentView;

import java.util.List;

/**
 * Created by vivianWQ on 2017/12/7
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class MessageFragment extends BaseFragment implements IMsgFragmentView {
    public static ChatSocketPresenter chatSocketPresenter;
    private MsgListAdapter msgListAdapter;
    private RecyclerView rvMessage;
    private TextView tvAboutMe, tvComment, tvPraise;
    private MsgFragmentPresenter msgFragmentPresenter;

    @Override
    public void initEventData() {
        msgFragmentPresenter=new MsgFragmentPresenter(getContext(),MessageFragment.this);
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
                UserMessageList item = msgListAdapter.getItem(position);
                Bundle args = new Bundle();
                args.putSerializable("data", item);
                getBaseActivity().launcher(mContext, ChatMsgActivity.class, args);

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


    }

    @Override
    public void loadData() {
        msgFragmentPresenter.getListUser(2);

//        RoNetWorkUtil
//                .getInstance()
//                .get(UrlConstants.GET_MSG_LIST)
//                .params("")
//                .execute(new DataListResponseCallback<UserMessageList>() {
//                    @Override
//                    public void onResponseSuccess(List<UserMessageList> response) {
//                        if (response != null && response.size() > 0)
//                            msgListAdapter.addData(response);
//
//                    }
//
//                    @Override
//                    public void onResponseFail(String errorString) {
//
//                    }
//                });

    }

    @Override
    public void showUserMsgList(List<UserMessageList> userMessageLists) {
        if(userMessageLists!=null&&userMessageLists.size()>0)
        {
            msgListAdapter.addData(userMessageLists);
        }
    }
}
