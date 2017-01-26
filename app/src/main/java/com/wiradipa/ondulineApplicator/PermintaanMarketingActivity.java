package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class PermintaanMarketingActivity extends AppCompatActivity {

    String pil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //function to set layout(produk, brosur, souvenir)
        setLayout();

    }

    //function to set layout(produk, brosur, souvenir)
    public void setLayout(){
        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");
        switch (pil) {
            case "produk":
                setContentView(R.layout.activity_permintaan_marketing_produk);
                break;
            case "brosur":
                setContentView(R.layout.activity_permintaan_marketing_brosur);
                break;
            case "souvenir":
                setContentView(R.layout.activity_permintaan_marketing_souvenir);
                break;
        }
    }
}
