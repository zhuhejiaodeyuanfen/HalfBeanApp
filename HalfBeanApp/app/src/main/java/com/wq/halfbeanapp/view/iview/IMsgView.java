package com.wq.halfbeanapp.view.iview;

import com.wq.halfbeanapp.bean.MsgDetailModel;

import java.util.List;

/**
 * Created by vivianWQ on 2018/1/30
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public interface IMsgView {

    void getMsgList(List<MsgDetailModel> msgModels);
}
