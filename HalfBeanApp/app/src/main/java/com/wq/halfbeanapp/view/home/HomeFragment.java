package com.wq.halfbeanapp.view.home;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.view.fragment.BaseFragment;
import com.wq.halfbeanapp.view.fragment.HomeLiveFragment;
import com.wq.halfbeanapp.view.fragment.HomeTopicFragment;

/**
 * Created by vivianWQ on 2017/12/7
 * Mail: wangqi_vivian@sina.com
 * desc:首页fragment
 * Version: 1.0
 */
public class HomeFragment extends BaseFragment {
    private TextView tvRight, tvLeft;
    private int index=-1;
    private HomeTopicFragment homeTopicFragment;
    private HomeLiveFragment halfBeanFragment;

    private FragmentManager fragmentManager;

    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {

        tvRight = (TextView) mContentView.findViewById(R.id.tvRight);
        tvLeft = (TextView) mContentView.findViewById(R.id.tvLeft);

    }

    @Override
    public void initEventData() {

        fragmentManager = getChildFragmentManager();
        setTabSelection(0);
    }

    @Override
    public void bindEvent() {
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabSelection(0);
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabSelection(1);
            }
        });


    }

    @Override
    public void loadData() {


    }

    /**
     * 将所有Fragment都置为隐藏状态
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homeTopicFragment != null) {
            transaction.hide(homeTopicFragment);
        }
        if (halfBeanFragment != null) {
            transaction.hide(halfBeanFragment);
        }

    }

    public void setTabSelection(int index) {
        if (this.index == index) {
            return;
        }
        this.index = index;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                //需要实时刷新
                if (homeTopicFragment == null) {
                    homeTopicFragment = new HomeTopicFragment();
                    transaction.add(R.id.viewHome, homeTopicFragment);
                } else {
                    transaction.show(homeTopicFragment);
                }
                break;

            case 1:
                if (halfBeanFragment == null) {
                    halfBeanFragment = new HomeLiveFragment();
                    transaction.add(R.id.viewHome, halfBeanFragment);
                } else {
                    transaction.show(halfBeanFragment);
                }
                break;


        }
        transaction.commitAllowingStateLoss();

    }
}
