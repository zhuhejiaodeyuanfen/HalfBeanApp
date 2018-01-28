package com.wq.halfbeanapp.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.HotTopicLiveAdapter;
import com.wq.halfbeanapp.adapter.MyItemClickListener;
import com.wq.halfbeanapp.bean.LiveBoardModel;
import com.wq.halfbeanapp.view.iview.IHotTopicFragment;
import com.wq.halfbeanapp.view.presenter.HotTopicPresenter;
import com.wq.halfbeanapp.view.topic.BeanAddTopicActivity;
import com.wq.halfbeanapp.view.topic.TopicCardActivity;
import com.wq.halfbeanapp.widget.SwipeRefreshView;

import java.util.List;

/**
 * Created by vivianWQ on 2017/12/7
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class HomeLiveFragment extends BaseFragment implements IHotTopicFragment {
    private RecyclerView rvHotList;
    private HotTopicLiveAdapter hotTopicLiveAdapter;
    private TextView tvAddLive;
    private SwipeRefreshView refreshView;
    private HotTopicPresenter hotTopicPresenter;


    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_beantopic;
    }

    @Override
    public void initView() {
        rvHotList = (RecyclerView) mContentView.findViewById(R.id.rvHotList);
        tvAddLive = (TextView) mContentView.findViewById(R.id.tvAddLive);
        refreshView = (SwipeRefreshView) mContentView.findViewById(R.id.refreshView);

    }

    @Override
    public void initEventData() {
        hotTopicLiveAdapter = new HotTopicLiveAdapter(mContext);
        rvHotList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvHotList.setAdapter(hotTopicLiveAdapter);
        //点击进入cardView界面

        hotTopicLiveAdapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle args = new Bundle();
                args.putSerializable("data", hotTopicLiveAdapter.getItem(position));
                getBaseActivity().launcher(mContext, TopicCardActivity.class, args);

            }
        });

    }

    @Override
    public void bindEvent() {
        tvAddLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().launcher(mContext, BeanAddTopicActivity.class);

            }
        });

        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                hotTopicPresenter.getLivePage(true);

            }
        });

    }

    @Override
    public void loadData() {


        hotTopicPresenter = new HotTopicPresenter(this);
        hotTopicPresenter.getLivePage(true);


    }

    @Override
    public void showAllHot(List<LiveBoardModel> liveBoardModels) {
        refreshView.setRefreshing(false);
        hotTopicLiveAdapter.clear();
        hotTopicLiveAdapter.addData(liveBoardModels);

    }
}
