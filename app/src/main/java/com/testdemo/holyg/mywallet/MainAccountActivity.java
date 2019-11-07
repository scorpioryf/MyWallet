package com.testdemo.holyg.mywallet;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewManager;

import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import cn.we.swipe.helper.WeSwipe;
import cn.we.swipe.helper.WeSwipeHelper;

import com.testdemo.holyg.mywallet.ListListDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainAccountActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,RecAdapter.DeletedItemListener,RecAdapter.EditItemListener,ListListDialogFragment.Listener {

    RecyclerView recyclerView;
    private RecAdapter recAdapter;
    private ArrayList<Sheet> list = new ArrayList<>();


    public PreferencesService preferencesService;

    public int count = 0;
    public static final int EDIT = 1;
    public static final int ADD = 2;
    private int editPosition;

    public static void start(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recAdapter = new RecAdapter(this);
        recAdapter.setDelectedItemListener(this);
        recAdapter.setEditItemListener(this);
        recyclerView.setAdapter(recAdapter);

        //设置WeSwipe。
        WeSwipe.attach(recyclerView).setType(WeSwipeHelper.SWIPE_ITEM_TYPE_FLOWING);
    }

    private void initData() {
        list = preferencesService.getList();
        recAdapter.setList(list);
        recyclerView.smoothScrollToPosition(0);
        if(!list.isEmpty()) {
            recyclerView.smoothScrollToPosition(recAdapter.getItemCount()-1);
        }
//        for (int i = 0; i < 30; i++) {
//            list.add(sheet);
//        }
//        recAdapter.setList(list);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferencesService = new PreferencesService(this);
        preferencesService.init();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                list.add(sheet);
//                preferencesService.save(list);
//                recAdapter.setList(list);
//                recyclerView.smoothScrollToPosition(recAdapter.getItemCount()-1);
//            }
//        });
        fab.setOnClickListener(onClickListener);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        initView();
        initData();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_overview) {
            // Handle the camera action
        } else if (id == R.id.nav_income) {

        } else if (id == R.id.nav_expenditure) {

        } else if (id == R.id.nav_search) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void deleted(int position) {
        recAdapter.removeDataByPosition(position);
        list.remove(position);
        preferencesService.save(list);
    }

    @Override
    public void editItem(int position) {
        Sheet editSheet = list.get(position);
        editPosition = position;
        ListListDialogFragment.newInstance(editSheet,EDIT).show(getSupportFragmentManager(),"EditDialog");
    }



    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            count++;

            String string = "";
            Calendar calendar = Calendar.getInstance();
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);
            int mHour = calendar.get(Calendar.HOUR_OF_DAY);
            int mMinute = calendar.get(Calendar.MINUTE);
            Sheet currentSheet = new Sheet(RecAdapter.UNDEFINED,0,mYear,mMonth,mDay,mHour,mMinute,RecAdapter.UNDEFINED,string);
            ListListDialogFragment.newInstance(currentSheet,ADD).show(getSupportFragmentManager(),"AddDialog");
//            list.add(currentSheet);
//            preferencesService.save(list);
//            recAdapter.setList(list);
//            recyclerView.smoothScrollToPosition(recAdapter.getItemCount()-1);
            //recyclerView.smoothScrollToPosition(0);
        }
    };

    @Override
    public void onListClicked(boolean status,Sheet sheet,int mode) {
        if(status){
            if(mode==ADD) {
                list.add(sheet);
                preferencesService.save(list);
                recAdapter.setList(list);
                recyclerView.smoothScrollToPosition(recAdapter.getItemCount() - 1);
            }
            else if(mode==EDIT){
                Log.d("EDIT", "onListClicked: recall!");
                list.set(editPosition,sheet);
                preferencesService.save(list);
                recAdapter.editList(list);
            }
        }
    }


}
