package com.wq.halfbeanapp.model;

import com.wq.halfbeanapp.bean.HomeBoardDetailModel;
import com.wq.halfbeanapp.service.HomeTopicService;
import com.wq.halfbeanapp.util.retrofit.NetHttpApi;
import com.wq.halfbeanapp.util.retrofit.RxHelper;
import com.wq.halfbeanapp.util.retrofit.RxSubscribe;

import java.util.List;

/**
 * Created by vivianWQ on 2018/2/6
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class HomeTopicFragModel {


    private HomeTopicService homeTopicService;


    public HomeTopicFragModel() {
        homeTopicService = NetHttpApi.getInstance().getService(HomeTopicService.class);
    }

    public void getHomeTopicList(int page, RxSubscribe<List<HomeBoardDetailModel>> subscribe) {
        homeTopicService.getHomeTopicList(page) .compose(RxHelper.<List<HomeBoardDetailModel>>handleResult())
                .subscribe(subscribe);

    }
}
