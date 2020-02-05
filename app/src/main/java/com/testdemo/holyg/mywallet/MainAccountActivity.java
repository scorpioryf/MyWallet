package com.testdemo.holyg.mywallet;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


    public static final int EDIT = 1;
    public static final int ADD = 2;
    private int editPosition;

    private TextView textViewUserName;

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

        if(!list.isEmpty()) {
            recyclerView.smoothScrollToPosition(recAdapter.getItemCount()-1);
        }
        else {
            recyclerView.smoothScrollToPosition(0);
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferencesService = new PreferencesService(this);
        preferencesService.init();




        FloatingActionButton fab = findViewById(R.id.fab);
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main_account);
        textViewUserName = headerLayout.findViewById(R.id.textViewAccountName);
        SharedPreferences sharedPreferencesUserName = getSharedPreferences("UserName",MODE_PRIVATE);
        if(sharedPreferencesUserName!=null){
            Log.d("Sp", "onCreate: Shared preference get.");
            String userName = sharedPreferencesUserName.getString("Name",null);
            if(userName!=null) {
                Log.e("TAG", "onCreate: "+ userName);
                textViewUserName.setText(userName);
            }
        }


        initView();


        initData();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
            final EditText inputServer = new EditText(this);
            inputServer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Set your Name").setView(inputServer)
                    .setNegativeButton("Cancel", null);
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String _sign = inputServer.getText().toString();
                    if(!_sign.isEmpty())
                    {
                        textViewUserName = findViewById(R.id.textViewAccountName);
                        textViewUserName.setText(_sign);
                        SharedPreferences sharedPreferencesUserName = getSharedPreferences("UserName",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferencesUserName.edit();
                        editor.putString("Name",textViewUserName.getText().toString());
                        editor.apply();
                    }
                    else
                    {
                        Toast.makeText(MainAccountActivity.this,"Empty message",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@Nullable MenuItem item) {
        // Handle navigation view item clicks here.
        assert item != null;
        int id = item.getItemId();

        if (id == R.id.nav_overview) {
            recAdapter.editList(list);
            if(!list.isEmpty()) {
                recyclerView.smoothScrollToPosition(recAdapter.getItemCount()-1);
            }
            else {
                recyclerView.smoothScrollToPosition(0);
            }
        } else if (id == R.id.nav_income) {
            ArrayList<Sheet> incomeList = new ArrayList<>();
            for(int i=0;i<list.size();i++){
                if(list.get(i).getType()==RecAdapter.INCOME){
                    incomeList.add(list.get(i));
                }
            }
            recAdapter.editList(incomeList);
            if(!incomeList.isEmpty()) {
                recyclerView.smoothScrollToPosition(recAdapter.getItemCount()-1);
            }
            else {
                recyclerView.smoothScrollToPosition(0);
            }
        } else if (id == R.id.nav_expenditure) {
            ArrayList<Sheet> expendList = new ArrayList<>();
            for(int i=0;i<list.size();i++){
                if(list.get(i).getType()==RecAdapter.EXPEND){
                    expendList.add(list.get(i));
                }
            }
            recAdapter.editList(expendList);
            if(!expendList.isEmpty()) {
                recyclerView.smoothScrollToPosition(recAdapter.getItemCount()-1);
            }
            else {
                recyclerView.smoothScrollToPosition(0);
            }
        } else if (id == R.id.nav_webView) {
            openWebPage("https://github.com/scorpioryf/MyWallet");
        }
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
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
