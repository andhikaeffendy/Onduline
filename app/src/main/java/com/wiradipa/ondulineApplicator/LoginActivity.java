package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    EditText editTextLoginEmail,editTextLoginPassword;
    Button btnLogin;
    String pil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLoginEmail = (EditText)findViewById(R.id.editTextLoginEmail);
        editTextLoginPassword = (EditText)findViewById(R.id.editTextLoginPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);

    }

    public void onClickLogin(View v){
        Intent i;
        i = new Intent(this, ProgramActivity.class);

        switch (v.getId()){
            case R.id.btnLogin:
//                i = new Intent(this, HomeActivity.class);
                switch (editTextLoginEmail.getText().toString()){
                    case "app@gmail.com":
                        pil="applicator";
                        i.putExtra("pil", pil);
                        startActivity(i);
                        break;
                    case "retail@gmail.com":
                        pil="retailer";
                        i.putExtra("pil", pil);
                        startActivity(i);
                        break;
                    default:
                        Toast.makeText(this, "email tidak terdaftar", Toast.LENGTH_LONG).show();
                        break;
                }

                break;
            case R.id.btnRegister:
                i = new Intent(this, ChooseUserTypeActivity.class);
                startActivity(i);
                break;
        }
    }
}
