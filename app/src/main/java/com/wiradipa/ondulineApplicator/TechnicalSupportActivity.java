package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class TechnicalSupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_support);
    }

    public void onClickTechnicalSupport(View v){
        Intent i;
        switch (v.getId()) {
            case R.id.btnCaraPemasangan:
                Toast.makeText(this, "btnCaraPemasangan", Toast.LENGTH_LONG).show();
                i = new Intent(this, CaraPemasanganActivity.class);
                startActivity(i);
                break;
            case R.id.btnKeluhan:
                Toast.makeText(this, "btnKeluhan", Toast.LENGTH_LONG).show();
                i = new Intent(this, KeluhanActivity.class);
                startActivity(i);
                break;
            case R.id.btnSupervisiProyek:
                Toast.makeText(this, "btnSupervisiProyek", Toast.LENGTH_LONG).show();
                i = new Intent(this, SupervisiProyekActivity.class);
                startActivity(i);
                break;
            case R.id.btnTraining:
                Toast.makeText(this, "btnTraining", Toast.LENGTH_LONG).show();
                i = new Intent(this, PermintaanTrainingActivity.class);
                startActivity(i);
                break;
        }
    }
}
