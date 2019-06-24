package com.example.androidnavigationexlistview;

import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.androidnavigationexlistview.Adapter.CustomExpandableListAdapter;
import com.example.androidnavigationexlistview.Helper.FragmentNavigationManager;
import com.example.androidnavigationexlistview.Interface.NavigationManager;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private String mActivityTitle;
    private String [] items;

    private ExpandableListView expandableListView;
    private CustomExpandableListAdapter adapter;
    private List<String> lstTile;
    private Map<String,List<String>> lstChild;
    private NavigationManager navigationManager;
    private int lastExpandedGroupPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Init View
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        expandableListView = (ExpandableListView)findViewById(R.id.navList);
        navigationManager = FragmentNavigationManager.getInstance(this);


        initItems();

        View listHeaderView = getLayoutInflater().inflate(R.layout.nav_header,null,false);

        expandableListView.addHeaderView(listHeaderView);

        genData();

        addDrawerItem();
        setUpDrawer();
        
        if (savedInstanceState == null){
            selectFirstItemAsDefault();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setTitle("Vaibhav");


    }

    private void selectFirstItemAsDefault() {

        if (navigationManager != null){
            String firstItem = lstTile.get(0);
            navigationManager.showFragment(firstItem);
            getSupportActionBar().setTitle(firstItem);
        }

    }

    private void setUpDrawer() {
    actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close){
        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
//            getSupportActionBar().setTitle("Vaibhav");
            invalidateOptionsMenu();
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
//            getSupportActionBar().setTitle(mActivityTitle);
            invalidateOptionsMenu();
        }
    };

    actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
    drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private void addDrawerItem() {
        adapter = new CustomExpandableListAdapter(this,lstTile,lstChild);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
               /* if(groupPosition != lastExpandedGroupPosition){
                    expandableListView.collapseGroup(lastExpandedGroupPosition);
                }

                super.onGroupExpanded(groupPosition);
                lastExpandedGroupPosition = groupPosition;*/

                for(int i=0;i<lstTile.size();i++){
                    if(i==groupPosition){
                        //do nothing
                    }
                    else{
                            expandableListView.collapseGroup(i);
                        }
                    }


                getSupportActionBar().setTitle(lstTile.get(groupPosition).toString()); // set title for Toolbar when open
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

                getSupportActionBar().setTitle("Vaibhav");
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                Change fragment when click on item
                String selectedItem = ((List) (lstChild.get(lstTile.get(groupPosition))))
                        .get(childPosition).toString();
                getSupportActionBar().setTitle(selectedItem);

                if (items[0].equals(lstTile.get(groupPosition)))
                    navigationManager.showFragment(selectedItem);
                else{

                }
//                    throw new IllegalArgumentException("Not Support Fragment");
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void genData() {

        List<String> title = Arrays.asList("Android Programing", "Xamarin Programing", "IOS Programing", "Test");
        List<String> childItem = Arrays.asList("Beginner" , "Intermediate" , "Advanced" , "Professional");

        lstChild = new TreeMap<>();

        lstChild.put(title.get(0),childItem);
        lstChild.put(title.get(1),childItem);
        lstChild.put(title.get(2),childItem);
        lstChild.put(title.get(3),null);

        String temp = (new Gson()).toJson(lstChild);

        Log.i(TAG, "genData: "+temp);
        lstTile = new ArrayList<>(lstChild.keySet());

    }

    private void initItems() {
        items = new String[]{"Android Programing", "Xamarin Programing", "IOS Programing"};
    }



    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
