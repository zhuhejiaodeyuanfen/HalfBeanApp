package com.wq.halfbeanapp.view.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.view.fragment.BaseFragment;
import com.wq.halfbeanapp.view.fragment.HomeLiveFragment;
import com.wq.halfbeanapp.view.fragment.HomeTopicFragment;
import com.wq.halfbeanapp.widget.titlebar.CaterpillarIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivianWQ on 2017/12/7
 * Mail: wangqi_vivian@sina.com
 * desc:首页fragment
 * Version: 1.0
 */
public class HomeFragment extends BaseFragment {
    private HomeTopicFragment homeTopicFragment;
    private HomeLiveFragment halfBeanFragment;

    private CaterpillarIndicator upBar;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;

    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {

        upBar = (CaterpillarIndicator) mContentView.findViewById(R.id.upBar);


        viewPager = (ViewPager) mContentView.findViewById(R.id.viewPager);

        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeTopicFragment());
        fragmentList.add(new HomeLiveFragment());
        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                titleBar.setTextColorSelected(getResources().getColor(R.color.colorPrimary));
            }
        });
        List<CaterpillarIndicator.TitleInfo> titles = new ArrayList<>();
        titles.add(new CaterpillarIndicator.TitleInfo("话题"));
        titles.add(new CaterpillarIndicator.TitleInfo("Live"));
        upBar.init(1, titles, viewPager);

    }

    @Override
    public void initEventData() {


    }

    @Override
    public void bindEvent() {


    }

    @Override
    public void loadData() {


    }

    private class BaseFragmentAdapter extends FragmentStatePagerAdapter {

        public BaseFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList != null ? fragmentList.size() : 0;
        }
    }


}
