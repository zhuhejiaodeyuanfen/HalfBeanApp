package com.wq.halfbeanapp.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.view.fragment.HalfBeanFragment;
import com.wq.halfbeanapp.view.fragment.HomeFragment;
import com.wq.halfbeanapp.view.fragment.HotTopicFragment;
import com.wq.halfbeanapp.view.fragment.MineFragment;

public class HomeActivity extends BaseActivity {

    private FragmentManager fm;
    private FragmentTransaction transaction;
    private HomeFragment homeFragment;
    private HalfBeanFragment halfBeanFragment;
    private HotTopicFragment hotTopicFragment;
    private MineFragment mineFragment;
    private FragmentManager fragmentManager;
    private int index=-1;
    private TextView tvHome,tvBean,tvTopic,tvMine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void initView() {
        tvHome= (TextView) findViewById(R.id.tvHome);
        tvBean= (TextView) findViewById(R.id.tvBean);
        tvTopic= (TextView) findViewById(R.id.tvTopic);
        tvMine= (TextView) findViewById(R.id.tvMine);

    }

    @Override
    public void initEventData() {
        fragmentManager = getSupportFragmentManager();


    }

    @Override
    public void bindEvent() {
        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTabSelection(0);
            }
        });
        tvBean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTabSelection(1);
            }
        });
        tvTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTabSelection(2);
            }
        });
        tvMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTabSelection(3);
            }
        });

    }

    @Override
    public void loadData() {
        setTabSelection(0);


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
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.id_content, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;

            case 1:
                if (halfBeanFragment == null) {
                    halfBeanFragment = new HalfBeanFragment();
                    transaction.add(R.id.id_content, halfBeanFragment);
                } else {
                    transaction.show(halfBeanFragment);
                }
                break;


            case 2:
                if (hotTopicFragment == null) {
                    hotTopicFragment = new HotTopicFragment();
                    transaction.add(R.id.id_content, hotTopicFragment);
                } else {
                    transaction.show(hotTopicFragment);
                }

                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.id_content, mineFragment);
                } else {
                    transaction.show(mineFragment);
                }
                break;

        }
        transaction.commitAllowingStateLoss();

    }

    /**
     * 将所有Fragment都置为隐藏状态
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (halfBeanFragment != null) {
            transaction.hide(halfBeanFragment);
        }
        if (hotTopicFragment != null) {
            transaction.hide(hotTopicFragment);
        }
        if (mineFragment != null)
            transaction.hide(mineFragment);
    }
}
