package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryActivity extends AppCompatActivity {


    protected ListView lv;
    protected ListAdapter adapter;
    SimpleAdapter Adapter;
    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> mylist;
    String[] ProductName;
    String pil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayout();

    }

    public void onClickHistory(View v){
        Intent i;
        switch (v.getId()) {
            case R.id.btnAddNew:
//                Toast.makeText(this, "btnMyOrder", Toast.LENGTH_LONG).show();
                i = new Intent(this, AddNewProjectAndOrderActivity.class);
                i.putExtra("pil", pil);
                startActivity(i);
                break;
        }
    }

    //function to set layout(produk, brosur, souvenir)
    public void setLayout(){
        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");
        switch (pil) {
            case "order retailer":
                historyOrderRetail();
                break;
            case "order applicator":
                historyOrderApplicator();
                break;
            case "project applicator":
                historyProjectApplicator();
                break;
        }
    }

    public void historyOrderRetail(){
        setContentView(R.layout.activity_history_order_retailer);

        pil = "order retailer";
        lv = (ListView) findViewById(R.id.lv_history);
        ProductName = new String[] {"Onduline®", "Onduvila®", "Onduvila®", "Onduline®", "Onduline®"};
        mylist = new ArrayList<HashMap<String,String>>();

        for (int i = 0; i < ProductName.length; i++){
            map = new HashMap<String, String>();
            map.put("ProductName", ProductName[i]);

            mylist.add(map);
        }

        Adapter = new SimpleAdapter(this, mylist, R.layout.list_history,
                new String[] {"ProductName"}, new int[] {R.id.txtProductNameHistory});
        lv.setAdapter(Adapter);

    }

    public void historyOrderApplicator(){
        setContentView(R.layout.activity_history_order_applicator);

        pil = "order applicator";
        lv = (ListView) findViewById(R.id.lv_history);
        ProductName = new String[] {"Onduline®", "Onduvila®", "Onduvila®", "Onduline®", "Onduline®"};
        mylist = new ArrayList<HashMap<String,String>>();

        for (int i = 0; i < ProductName.length; i++){
            map = new HashMap<String, String>();
            map.put("ProductName", ProductName[i]);

            mylist.add(map);
        }

        Adapter = new SimpleAdapter(this, mylist, R.layout.list_history,
                new String[] {"ProductName"}, new int[] {R.id.txtProductNameHistory});
        lv.setAdapter(Adapter);
    }

    public void historyProjectApplicator(){
        setContentView(R.layout.activity_history_project_applicator);

        pil = "project applicator";
        lv = (ListView) findViewById(R.id.lv_history);
        ProductName = new String[] {"Onduline®", "Onduvila®", "Onduvila®", "Onduline®", "Onduline®"};
        mylist = new ArrayList<HashMap<String,String>>();

        for (int i = 0; i < ProductName.length; i++){
            map = new HashMap<String, String>();
            map.put("ProductName", ProductName[i]);

            mylist.add(map);
        }

        Adapter = new SimpleAdapter(this, mylist, R.layout.list_history,
                new String[] {"ProductName"}, new int[] {R.id.txtProductNameHistory});
        lv.setAdapter(Adapter);
    }
}
