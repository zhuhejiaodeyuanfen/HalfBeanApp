package com.wq.halfbeanapp.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.HomeLiveAdapter;
import com.wq.halfbeanapp.adapter.MyItemClickListener;
import com.wq.halfbeanapp.bean.HomeBoardDetailModel;
import com.wq.halfbeanapp.presenter.HomeTopicFragPresenter;
import com.wq.halfbeanapp.view.HomeAddTopicActivity;
import com.wq.halfbeanapp.view.home.HomeTopicDetailActivity;
import com.wq.halfbeanapp.view.iview.IHomeTopicFragView;

import java.util.List;

/**
 * Created by vivianWQ on 2017/12/7
 * Mail: wangqi_vivian@sina.com
 * desc:首页fragment
 * Version: 1.0
 */
public class HomeTopicFragment extends BaseFragment implements IHomeTopicFragView {
    private RecyclerView rvHomeList;
    private HomeLiveAdapter homeLiveAdapter;
    private TextView tvRight;
    private TwinklingRefreshLayout swipeRefresh;
    private HomeTopicFragPresenter homeTopicFragPresenter;
    private int page = 1;


    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_home_topic;
    }

    @Override
    public void initView() {

        rvHomeList = (RecyclerView) mContentView.findViewById(R.id.rvHomeList);
        tvRight = (TextView) mContentView.findViewById(R.id.tvRight);
        swipeRefresh = (TwinklingRefreshLayout) mContentView.findViewById(R.id.swipeRefresh);

    }

    @Override
    public void initEventData() {
        homeTopicFragPresenter = new HomeTopicFragPresenter(mContext, HomeTopicFragment.this);
        homeLiveAdapter = new HomeLiveAdapter(mContext);
        rvHomeList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvHomeList.setAdapter(homeLiveAdapter);
        swipeRefresh.setEnableRefresh(true);
        swipeRefresh.setEnableLoadmore(true);
        swipeRefresh.setEnableOverScroll(false);
        LoadingView loadingView = new LoadingView(mContext);
        swipeRefresh.setBottomView(loadingView);
        SinaRefreshView sinaRefreshView = new SinaRefreshView(mContext);
        swipeRefresh.setHeaderView(sinaRefreshView);

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
                getBaseActivity().launcher(mContext, HomeTopicDetailActivity.class, args);
            }
        });


        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().launcher(mContext, HomeAddTopicActivity.class);
            }
        });
        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                loadData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page = page + 1;
                loadData();
            }
        });


    }

    @Override
    public void loadData() {
        homeTopicFragPresenter.getHomeTopicList(page);
    }


    @Override
    public void showHomeTopicList(List<HomeBoardDetailModel> list) {

        swipeRefresh.finishRefreshing();
        swipeRefresh.finishLoadmore();
        if (list != null && list.size() > 0) {
            if (page == 1) {
                homeLiveAdapter.clear();
            }

            homeLiveAdapter.addData(list);
        }

    }
}
