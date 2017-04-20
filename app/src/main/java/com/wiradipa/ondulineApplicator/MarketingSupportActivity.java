package com.wiradipa.ondulineApplicator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wiradipa.ondulineApplicator.lib.AppSession;

public class MarketingSupportActivity extends AppCompatActivity {


    private Context context;
    private AppSession session;
    private RelativeLayout rl_PermintaanBrosur, rl_PermintaanSouvenir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_support);


        context = this;
        session = new AppSession(context);
        session.checkSession();

        rl_PermintaanBrosur = (RelativeLayout)findViewById(R.id.rl_PermintaanBrosur);
        rl_PermintaanSouvenir = (RelativeLayout)findViewById(R.id.rl_PermintaanSouvenir);

        if (session.getRetailerType().equals("Supermarket Bahan Bangunan")){
//            Toast.makeText(HomeActivity.this, session.getRetailerType()+ "\n Supermarket Bahan Bangunan", Toast.LENGTH_LONG).show();
            rl_PermintaanBrosur.setVisibility(View.VISIBLE);
            rl_PermintaanSouvenir.setVisibility(View.GONE);
        }

    }

    public void onClickMarketingSupport(View v){
        Intent i;
        switch (v.getId()) {
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
