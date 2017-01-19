package com.wiradipa.ondulineApplicator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MenuDetailActivity extends AppCompatActivity {


    String pil;
    TextView txtMenuProductName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);

        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");
        txtMenuProductName = (TextView)findViewById(R.id.txtMenuProductName);
        txtMenuProductName.setText(pil + "®");
    }
}
