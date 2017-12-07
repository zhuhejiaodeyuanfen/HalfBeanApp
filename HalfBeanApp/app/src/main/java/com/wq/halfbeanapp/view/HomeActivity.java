package com.wq.halfbeanapp.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.view.fragment.HomeFragment;

public class HomeActivity extends BaseActivity {

    private FragmentManager fm;
    private FragmentTransaction transaction;
    private HomeFragment homeFragment;
    private
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void initView() {

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

    void selectFM(int i) {
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        switch (i) {
            case 1:
                HomeFragment homeFragment = new HomeFragment();
                transaction.hide(this);
                transaction.add(R.id.id_content, fm_first);
                transaction.addToBackStack(null);
                Log.i("TAG", "进入port");
                break;
            case 2:
                SecondFragment fm_second = new SecondFragment();
                transaction.hide(this);
                transaction.add(R.id.id_content, fm_second);
                transaction.addToBackStack(null);
                break;
            case 3:
                ThreadFragment fm_thread = new ThreadFragment();
                transaction.hide(this);
                transaction.add(R.id.id_content, fm_thread);
                transaction.addToBackStack(null);
                break;
            default:
                break;
        }
        transaction.commit();
    }
}
