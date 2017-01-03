package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ChooseUserTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user_type);
    }
    public void onClickChooseUser(View v){

        Intent i;
        switch (v.getId()){
            case R.id.btnRetailer:
                /*
                i = new Intent(this, HomeActivity.class);
                startActivity(i);
                */
                Toast.makeText(ChooseUserTypeActivity.this, "btnRetailer", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnAplikator:
                i = new Intent(this, RegistrationFromActivity.class);
                startActivity(i);
                Toast.makeText(ChooseUserTypeActivity.this, "btnAplikator", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnIndividu:
                /*i = new Intent(this, HomeActivity.class);
                startActivity(i);
                */
                Toast.makeText(ChooseUserTypeActivity.this, "btnIndividu", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnLanjut:
                /*i = new Intent(this, HomeActivity.class);
                startActivity(i);
                */
                Toast.makeText(ChooseUserTypeActivity.this, "btnLanjut", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
