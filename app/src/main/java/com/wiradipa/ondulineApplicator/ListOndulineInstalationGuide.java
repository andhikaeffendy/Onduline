package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ListOndulineInstalationGuide extends AppCompatActivity {

    private String  pil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");
        setLayout(pil);

//        setContentView(R.layout.activity_list_onduline_instalation_guide);
    }



    public void setLayout(String pil){

        switch (pil){
            case "ONDULINE":
                setContentView(R.layout.activity_list_onduline_instalation_guide);
                break;
            case "BARDOLINE":
                setContentView(R.layout.activity_list_bardoline_instalation_guide);
                break;
        }

    }

    public void onClickOndulineInstalationGuide(View v){

        Intent i ;
        switch (v.getId()){
            case R.id.btn_Tilt_5_10:
                i = new Intent(this, DetilViewActivity.class);
                i.putExtra("pilDetilView","ONDULINEINSTALATIONGUIDE5-10");
                i.putExtra("pil","ONDULINE 5°-10°");
                startActivity(i);
                break;
            case R.id.btn_Tilt_10_15:
                i = new Intent(this, DetilViewActivity.class);
                i.putExtra("pilDetilView","ONDULINEINSTALATIONGUIDE10-15");
                i.putExtra("pil","ONDULINE 10°-15°");
                startActivity(i);
                break;
            case R.id.btn_Tilt_15:
                i = new Intent(this, DetilViewActivity.class);
                i.putExtra("pilDetilView","ONDULINEINSTALATIONGUIDE15");
                i.putExtra("pil","ONDULINE 15° LEBIH");
                startActivity(i);
                break;
            case R.id.type_beaver:
                i = new Intent(this, DetilViewActivity.class);
                i.putExtra("pilDetilView","BARDOLINEINSTALATIONGUIDETYPEBEAVER");
                i.putExtra("pil","TYPE BEAVER");
                startActivity(i);
                break;
            case R.id.type_rectangular:
                i = new Intent(this, DetilViewActivity.class);
                i.putExtra("pilDetilView","BARDOLINEINSTALATIONGUIDETYPERECTANGULAR");
                i.putExtra("pil","TYPE RECTANGULAR");
                startActivity(i);
                break;

        }
    }
}
