package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MenuProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_product);
    }

    public void onClickMenuProduct(View v){

        Intent i;
        switch (v.getId()){
            case R.id.btnProductDetail:
                Toast.makeText(this, "btnProductDetail", Toast.LENGTH_LONG).show();
                i = new Intent(this, ProjectDetailActivity.class);
                startActivity(i);
                break;
            case R.id.btnSimulatorAtap:
                Toast.makeText(this, "btnSimulatorAtap", Toast.LENGTH_LONG).show();
                i = new Intent(this, SimulatorAtapActivity.class);
                startActivity(i);
                break;
            case R.id.btnCalculator:
                Toast.makeText(this, "btnCalculator", Toast.LENGTH_LONG).show();
                i = new Intent(this, CalculatorActivity.class);
                startActivity(i);
                break;
            case R.id.btnProjectRefrence:
                Toast.makeText(this, "btnProjectRefrence", Toast.LENGTH_LONG).show();
                i = new Intent(this, ProjectRefrenceActivity.class);
                startActivity(i);
                break;
        }
    }

}
