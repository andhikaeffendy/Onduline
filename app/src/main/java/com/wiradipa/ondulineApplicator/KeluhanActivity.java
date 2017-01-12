package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class KeluhanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keluhan);
    }

    public void onClickKeluhan(View v){
        Intent i;
        switch (v.getId()) {
            case R.id.btnKeluhanPemasangan:
                Toast.makeText(this, "btnKeluhanPemasangan", Toast.LENGTH_LONG).show();
                i = new Intent(this, KeluhanPemasanganActivity.class);
                startActivity(i);
                break;
            case R.id.btnKeluhanProduk:
                Toast.makeText(this, "btnKeluhanProduk", Toast.LENGTH_LONG).show();
                //i = new Intent(this, ProgramActivity.class);
                //startActivity(i);
                break;
        }
    }
}
