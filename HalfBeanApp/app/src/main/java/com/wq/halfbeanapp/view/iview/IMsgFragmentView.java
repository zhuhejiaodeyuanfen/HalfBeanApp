package com.wq.halfbeanapp.view.iview;

import com.wq.halfbeanapp.bean.UserMessageList;

import java.util.List;

/**
 * Created by vivianWQ on 2018/1/31
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public interface IMsgFragmentView {

    void showUserMsgList(List<UserMessageList> userMessageLists);
}
