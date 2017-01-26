package com.wiradipa.ondulineApplicator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddNewActivity extends AppCompatActivity {


    String pil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayout();

    }


    //function to set layout(produk, brosur, souvenir)
    public void setLayout(){
        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");
        switch (pil) {
            case "order retailer":
                setContentView(R.layout.activity_add_new_order_retailer);;
                break;
            case "order applicator":
                setContentView(R.layout.activity_add_new_order_applicator);;
                break;
            case "project applicator":
                setContentView(R.layout.activity_add_new_project_applicator);
                break;
        }
    }

}
