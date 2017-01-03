package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class VerificationPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_page);
    }

    public void onClickVerivication(View v){
        Intent i;
        switch (v.getId()){
            case R.id.btnActivation:
                i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;
        }
    }
}
