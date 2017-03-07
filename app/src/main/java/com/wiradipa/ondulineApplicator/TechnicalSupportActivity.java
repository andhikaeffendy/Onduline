package com.wiradipa.ondulineApplicator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wiradipa.ondulineApplicator.lib.AppSession;

public class TechnicalSupportActivity extends AppCompatActivity {


    private Context context;
    private AppSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        context = this;
        session = new AppSession(context);
        session.checkSession();

        setLayout(session.getUSERTYPE());

//        setContentView(R.layout.activity_technical_support);


    }

    public void setLayout(String pil){

        String retailertype = session.getRetailerType();

//        case "individu":

        if(pil.equals("applicator")){
            setContentView(R.layout.activity_technical_support);

        }else if (pil.equals("retailer")){

            if (retailertype.equals("Toko Bahan Bangunan / Toko Tradisional")){

                setContentView(R.layout.activity_technical_support_2menu);

            }else if (retailertype.equals("Toko Baja Ringan / Depo keramik")){

                setContentView(R.layout.activity_technical_support_3menu);

            }else if (retailertype.equals("Supermarket Bahan Bangunan")){

                setContentView(R.layout.activity_technical_support_3menu);

            }

        }else if(pil.equals("individu")){

            setContentView(R.layout.activity_technical_support_2menu);

        }

    }

    public void onClickTechnicalSupport(View v){
        Intent i;
        switch (v.getId()) {
            case R.id.btnCaraPemasangan:
//                Toast.makeText(this, "btnCaraPemasangan", Toast.LENGTH_LONG).show();
//                i = new Intent(this, CaraPemasanganActivity.class);

                i = new Intent(this, OurProductActivity.class);
                i.putExtra("stat","instalation guide");
                startActivity(i);
                break;
            case R.id.btnKeluhan:
//                Toast.makeText(this, "btnKeluhan", Toast.LENGTH_LONG).show();
                i = new Intent(this, KeluhanActivity.class);
                startActivity(i);
                break;
            case R.id.btnSupervisiProyek:
//                Toast.makeText(this, "btnSupervisiProyek", Toast.LENGTH_LONG).show();
                i = new Intent(this, SupervisiProyekActivity.class);
                startActivity(i);
                break;
            case R.id.btnTraining:
//                Toast.makeText(this, "btnTraining", Toast.LENGTH_LONG).show();
                i = new Intent(this, PermintaanTrainingActivity.class);
                startActivity(i);
                break;
        }
    }
}
