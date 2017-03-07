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
        i = new Intent(this, RegistrationFromActivity.class);
        switch (v.getId()){
            case R.id.btnTokoTradisional:
                i.putExtra("pil", "retailer");
                i.putExtra("retailer_type", "Toko Bahan Bangunan / Toko Tradisional");
                startActivity(i);
//                Toast.makeText(this, "btnTokoTradisional", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnBajaRingan:
                i.putExtra("pil", "retailer");
                i.putExtra("retailer_type", "Toko Baja Ringan / Depo keramik");
                startActivity(i);
//                Toast.makeText(this, "btnBajaRingan", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnSupermarket:
                i.putExtra("pil", "retailer");
                i.putExtra("retailer_type", "Supermarket Bahan Bangunan");
                startActivity(i);
//                Toast.makeText(this, "btnSupermarket", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
