package com.example.androidnavigationexlistview.Helper;

import android.support.v4.app.FragmentManager;

import com.example.androidnavigationexlistview.Interface.NavigationManager;
import com.example.androidnavigationexlistview.MainActivity;

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

    }
}
