package com.wq.halfbeanapp.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.DynamicListAdapter;
import com.wq.halfbeanapp.bean.DynamicListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivianWQ on 2018/2/6
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class DynamicFragment extends BaseFragment {
    private DynamicListAdapter dynamicListAdapter;
    private RecyclerView rvDynamic;
    private List<DynamicListBean> dynamicListBeans;

    @Override
    public void initEventData() {
        dynamicListAdapter = new DynamicListAdapter(mContext);
        dynamicListBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            dynamicListBeans.add(new DynamicListBean());
        dynamicListAdapter.addData(dynamicListBeans,true);
        rvDynamic.setAdapter(dynamicListAdapter);


    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    public void initView() {
        initTitleNoLeft("动态");
        rvDynamic = (RecyclerView) mContentView.findViewById(R.id.rvDynamic);
        rvDynamic.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));


    }

    @Override
    public void bindEvent() {

    }

    @Override
    public void loadData() {

    }
}
