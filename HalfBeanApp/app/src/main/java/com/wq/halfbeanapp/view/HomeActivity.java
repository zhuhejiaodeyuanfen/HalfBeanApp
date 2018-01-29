package com.wq.halfbeanapp.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.view.fragment.MessageFragment;
import com.wq.halfbeanapp.view.home.HomeFragment;
import com.wq.halfbeanapp.view.fragment.HomeLiveFragment;
import com.wq.halfbeanapp.view.fragment.MineFragment;

public class HomeActivity extends BaseActivity {

    private FragmentManager fm;
    private FragmentTransaction transaction;
    private HomeFragment homeFragment;
    private MessageFragment messageFragment;
    private HomeLiveFragment homeLiveFragment;
    private MineFragment mineFragment;
    private FragmentManager fragmentManager;
    private int index = -1;
    private TextView tvHome, tvBean, tvTopic, tvMine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void initView() {
        tvHome = (TextView) findViewById(R.id.tvHome);
        tvBean = (TextView) findViewById(R.id.tvBean);
        tvTopic = (TextView) findViewById(R.id.tvTopic);
        tvMine = (TextView) findViewById(R.id.tvMine);
//        tvAddTopic = (TextView) findViewById(R.id.tvAddTopic);

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
//        tvAddTopic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                launcher(HomeActivity.this, HomeAddTopicActivity.class);
//            }
//        });

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
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    transaction.add(R.id.id_content, messageFragment);
                } else {
                    transaction.show(messageFragment);
                }
                break;


            case 2:
                if (homeLiveFragment == null) {
                    homeLiveFragment = new HomeLiveFragment();
                    transaction.add(R.id.id_content, homeLiveFragment);
                } else {
                    transaction.show(homeLiveFragment);
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
        if (messageFragment != null) {
            transaction.hide(messageFragment);
        }
        if (homeLiveFragment != null) {
            transaction.hide(homeLiveFragment);
        }
        if (mineFragment != null)
            transaction.hide(mineFragment);
    }
}
