package com.xuj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.xuj.lib.ToastUtil;
import com.xuj.lib.db.DataAnalyzer;
import com.xuj.lib.db.Sqlite;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    String tableName = "kventity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToastUtil.showShort(this, "");


        DataAnalyzer<KvEntity> da = new DataAnalyzer<>(this, Sqlite.DB_COMMON);


        KvEntity kvEntity = new KvEntity("key", "value");
        boolean insert = da.insert(Sqlite.DB_COMMON, tableName, kvEntity);

        Log.e("TAG", "onCreate: " + insert);

        List<KvEntity> kventity = da.query(tableName, " * ", "", null, null, KvEntity.class);
        for (KvEntity entity : kventity) {
            Log.e("TAG", "onCreate: " + entity + toString());
        }

        //执行sql语句
        da.getDataHelper().exeSql("detele from " + tableName);

    }
}