package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickLogin(View v){
        Intent i;
        switch (v.getId()){
            case R.id.btnLogin:
                i = new Intent(this, HomeActivity.class);
                startActivity(i);
                break;
            case R.id.btnRegister:
                i = new Intent(this, ChooseUserTypeActivity.class);
                startActivity(i);
                break;
        }
    }
}
