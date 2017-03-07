package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MarketingSupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_support);
    }

    public void onClickMarketingSupport(View v){
        Intent i;
        switch (v.getId()) {
            case R.id.btnPermintaanProduk:
//                Toast.makeText(this, "btnPermintaanProduk", Toast.LENGTH_LONG).show();

                /** disini kita kirim data melalui intent untuk menentukan "PermintaanMarketingActivity" membuka xml yang mana*/

                i = new Intent(this, PermintaanMarketingActivity.class);
                i.putExtra("pil", "produk");
                startActivity(i);
                break;
            case R.id.btnPermintaanBrosur:
//                Toast.makeText(this, "btnPermintaanProduk", Toast.LENGTH_LONG).show();
                i = new Intent(this, PermintaanMarketingActivity.class);
                i.putExtra("pil", "brosur");
                startActivity(i);
                break;
            case R.id.btnPermintaanSouvenir:
//                Toast.makeText(this, "btnPermintaanSouvenir", Toast.LENGTH_LONG).show();
                i = new Intent(this, PermintaanMarketingActivity.class);
                i.putExtra("pil", "souvenir");
                startActivity(i);
                break;
        }
    }
}
