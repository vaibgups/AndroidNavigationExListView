package com.example.androidnavigationexlistview.Helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.androidnavigationexlistview.BuildConfig;
import com.example.androidnavigationexlistview.Fragment.FragmentContent;
import com.example.androidnavigationexlistview.Interface.NavigationManager;
import com.example.androidnavigationexlistview.MainActivity;
import com.example.androidnavigationexlistview.R;

public class FragmentNavigationManager implements NavigationManager {

    private static FragmentNavigationManager mInstance;

    private FragmentManager mFragmentManager;
    private MainActivity mainActivity;

    public static FragmentNavigationManager getInstance(MainActivity mainActivity){
        if (mInstance == null)
            mInstance = new FragmentNavigationManager();
        mInstance.configure(mainActivity);
        return mInstance;
    }

    private void configure(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
        mFragmentManager = mainActivity.getSupportFragmentManager();


    }

    @Override
    public void showFragment(String title) {

        showFragment(FragmentContent.newInstance(title),false);
    }

    private void showFragment(Fragment newInstance, boolean b) {
        FragmentManager fm = mFragmentManager;
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.container, newInstance);
        ft.addToBackStack(null);
        if ( b || !BuildConfig.DEBUG){
            ft.commitAllowingStateLoss();
        }else{
            ft.commit();
        }
        fm.executePendingTransactions();
    }
}
