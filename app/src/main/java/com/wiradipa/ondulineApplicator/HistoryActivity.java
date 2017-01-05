package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryActivity extends AppCompatActivity {


    protected ListView lv;
    protected ListAdapter adapter;
    SimpleAdapter Adapter;
    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> mylist;
    String[] bulan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lv = (ListView) findViewById(R.id.lv_history);
        bulan = new String[] {"JUN", "JUN", "DES", "JUL", "JAN"};
        mylist = new ArrayList<HashMap<String,String>>();

        for (int i = 0; i < bulan.length; i++){
            map = new HashMap<String, String>();
            map.put("bulan", bulan[i]);

            mylist.add(map);
        }

        Adapter = new SimpleAdapter(this, mylist, R.layout.list_history,
                new String[] {"bulan"}, new int[] {R.id.txtBulanHistory});
        lv.setAdapter(Adapter);
    }

    public void onClickHistory(View v){
        Intent i;
        switch (v.getId()) {
            case R.id.btnNewOrder:
                Toast.makeText(this, "btnMyOrder", Toast.LENGTH_LONG).show();
                i = new Intent(this, NewOrderActivity.class);
                startActivity(i);
                break;
        }
    }
}
