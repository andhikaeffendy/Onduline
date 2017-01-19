package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegistrationFromActivity extends AppCompatActivity {

    String pil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        //setContentView(R.layout.activity_registration_from_tukang_bangunan);
    }

    public void onClickRegistrationForm(View v){
        Intent i;
        switch (v.getId()){
            case R.id.btnSubmitRegistrationForm:
                i = new Intent(this, VerificationPageActivity.class);
                startActivity(i);
                break;
        }
    }


    //function to set layout(produk, brosur, souvenir)
    public void setLayout(){
        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");
        switch (pil) {
            case "tukang bangunan":
                setContentView(R.layout.activity_registration_from_tukang_bangunan);
                break;
            case "retailer tradisional":
                setContentView(R.layout.activity_registration_from_retailer_tradisional);
                break;
        }
    }

}
