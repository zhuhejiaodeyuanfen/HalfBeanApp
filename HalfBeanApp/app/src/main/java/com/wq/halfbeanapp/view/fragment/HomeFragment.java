package com.wq.halfbeanapp.view.fragment;

import android.support.v7.widget.RecyclerView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.HomeLiveAdapter;

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
        rvHomeList.setAdapter(homeLiveAdapter);

    }

    @Override
    public void bindEvent() {


    }

    @Override
    public void loadData() {

    }
}
