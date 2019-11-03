package com.testdemo.holyg.mywallet;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PreferencesService {
    String TAG = "PreferencesService :";
    private Context context;
    SharedPreferences preferences;


    public void init(){
        preferences = context.getSharedPreferences("Account",Context.MODE_PRIVATE);
    }

    public PreferencesService(Context context){
        this.context = context;
    }

    public void save(ArrayList<Sheet> list){
        Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        Log.d(TAG,"Json saved.");
        editor.putString("account",json);
        editor.commit();
    }

    public ArrayList<Sheet> getList(){
        ArrayList<Sheet> reList = new ArrayList<>();
        String json = preferences.getString("account",null);
        if(json != null){
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Sheet>>(){}.getType();
            reList = gson.fromJson(json,type);
        }
        return reList;
    }
}
