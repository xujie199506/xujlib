package com.xuj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.xuj.lib.ToastUtil;
import com.xuj.lib.db.DataAnalyzer;
import com.xuj.lib.db.Sqlite;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToastUtil.showShort(this,"");


        DataAnalyzer<KvEntity> da = new DataAnalyzer<>(this, Sqlite.DB_COMMON);


        KvEntity kvEntity = new KvEntity("1", "22212");
        boolean insert = da.insert(Sqlite.DB_COMMON, "kventity", kvEntity);

        Log.e("TAG", "onCreate: "+insert );

        List<KvEntity> kventity = da.query("kventity", " * ", "", null, null, KvEntity.class);
        for (KvEntity entity : kventity) {
            Log.e("TAG", "onCreate: "+ entity+toString());
        }


    }
}