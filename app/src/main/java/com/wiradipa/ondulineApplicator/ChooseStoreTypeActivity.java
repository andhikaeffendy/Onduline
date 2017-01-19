package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ChooseStoreTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_store_type);
    }
    //onClickChooseStore
    public void onClickChooseStore(View v){

        Intent i;
        switch (v.getId()){
            case R.id.btnTokoTradisional:
                i = new Intent(this, RegistrationFromActivity.class);
                i.putExtra("pil", "retailer tradisional");
                startActivity(i);
                Toast.makeText(this, "btnTokoTradisional", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnBajaRingan:
                /*i = new Intent(this, RegistrationFromActivity.class);
                startActivity(i);*/
                Toast.makeText(this, "btnBajaRingan", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnSupermarket:
                /*i = new Intent(this, HomeActivity.class);
                startActivity(i);*/
                Toast.makeText(this, "btnSupermarket", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
