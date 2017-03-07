package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuProductActivity extends AppCompatActivity {

    String pil;
    TextView txtMenuProductName;
    private LinearLayout btn_onduline, btn_onduvilla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_product);

        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");
        btn_onduline = (LinearLayout)findViewById(R.id.btn_onduline);
        btn_onduvilla = (LinearLayout)findViewById(R.id.btn_onduvilla);
        txtMenuProductName = (TextView)findViewById(R.id.txtMenuProductName);
        txtMenuProductName.setText(pil + "®");

        setLayoutMenu(pil);
    }

    public void onClickMenuProduct(View v){

        Intent i;
        switch (v.getId()){
            case R.id.btnProductDetail:
//                Toast.makeText(this, "btnProductDetail", Toast.LENGTH_LONG).show();
                i = new Intent(this, MenuDetailActivity.class);
                i.putExtra("pil", pil);
                startActivity(i);
                break;
            case R.id.btnSimulatorAtap:
//                Toast.makeText(this, "btnSimulatorAtap", Toast.LENGTH_LONG).show();
                i = new Intent(this, SimulatorAtapActivity.class);
                startActivity(i);
                break;
            case R.id.btnCalculator:
//                Toast.makeText(this, "btnCalculator", Toast.LENGTH_LONG).show();
                i = new Intent(this, CalculatorActivity.class);
                i.putExtra("pil", pil);
                startActivity(i);
                break;
            case R.id.btnProductDetailOnduline:
//                Toast.makeText(this, "btnCalculator", Toast.LENGTH_LONG).show();
                i = new Intent(this, MenuDetailActivity.class);
                i.putExtra("pil", pil);
                startActivity(i);
                break;
            case R.id.btnCalculatorOnduline:
//                Toast.makeText(this, "btnCalculator", Toast.LENGTH_LONG).show();
                i = new Intent(this, CalculatorActivity.class);
                i.putExtra("pil", pil);
                startActivity(i);
                break;
        }
    }



    public void setLayoutMenu(String s){

        Intent i;
        switch (s){
            case "ONDUVILLA":
                btn_onduline.setVisibility(View.GONE);
                btn_onduvilla.setVisibility(View.VISIBLE);
                break;
            case "ONDULINE":
                btn_onduline.setVisibility(View.VISIBLE);
                btn_onduvilla.setVisibility(View.GONE);
                break;
        }
    }

}
