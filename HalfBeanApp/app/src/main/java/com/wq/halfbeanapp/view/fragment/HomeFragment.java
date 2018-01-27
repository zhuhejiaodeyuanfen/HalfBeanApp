package com.wq.halfbeanapp.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.HomeLiveAdapter;
import com.wq.halfbeanapp.adapter.MyItemClickListener;
import com.wq.halfbeanapp.bean.HomeBoardDetailModel;
import com.wq.halfbeanapp.constants.UrlConstants;
import com.wq.halfbeanapp.net.response.DataListResponseCallback;
import com.wq.halfbeanapp.net.response.RoNetWorkUtil;
import com.wq.halfbeanapp.view.HomeDetailActivity;

import java.util.List;

/**
 * Created by vivianWQ on 2017/12/7
 * Mail: wangqi_vivian@sina.com
 * desc:首页fragment
 * Version: 1.0
 */
public class HomeFragment extends BaseFragment {
    private RecyclerView rvHomeList;
    private HomeLiveAdapter homeLiveAdapter;


    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {

        rvHomeList = (RecyclerView) mContentView.findViewById(R.id.rvHomeList);

    }

    @Override
    public void initEventData() {
        homeLiveAdapter = new HomeLiveAdapter(mContext);
        rvHomeList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvHomeList.setAdapter(homeLiveAdapter);

    }

    @Override
    public void bindEvent() {

        homeLiveAdapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //adapter数据点击事件
                HomeBoardDetailModel item = homeLiveAdapter.getItem(position);
                Bundle args = new Bundle();
                args.putSerializable("item", item);
                getBaseActivity().launcher(mContext, HomeDetailActivity.class, args);
            }
        });


    }

    @Override
    public void loadData() {

        RoNetWorkUtil
                .getInstance()
                .get(UrlConstants.GET_HOME_LIST)
                .params("")
                .execute(new DataListResponseCallback<HomeBoardDetailModel>() {
                    @Override
                    public void onResponseSuccess(List<HomeBoardDetailModel> response) {
                        if (response != null && response.size() > 0)
                            homeLiveAdapter.addData(response);

                    }

                    @Override
                    public void onResponseFail(String errorString) {

                    }
                });

    }
}
