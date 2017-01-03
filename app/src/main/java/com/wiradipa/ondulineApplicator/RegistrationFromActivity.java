package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegistrationFromActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_from);
    }
    public void onClickRegistrationForm(View v){
        Intent i;
        switch (v.getId()){
            case R.id.btnSubmitRegistrationForm:
                i = new Intent(this, VerificationPageActivity.class);
                startActivity(i);
                break;
        }
    }
}
