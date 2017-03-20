package com.wiradipa.ondulineApplicator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wiradipa.ondulineApplicator.lib.AppSession;

public class ChooseUserTypeActivity extends AppCompatActivity {


    private AppSession session;
    private Context context;
    private String emailRegister = null;
    private String hpnoRegister = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user_type);

        context = this;
        session = new AppSession(context);
        emailRegister = session.getEmailForm();
        hpnoRegister = session.getHpNoForm();

    }
    public void onClickChooseUser(View v){

        Intent i;
        switch (v.getId()){
            case R.id.btnRetailer:
                i = new Intent(this, ChooseStoreTypeActivity.class);
                startActivity(i);
//                Toast.makeText(ChooseUserTypeActivity.this, "btnRetailer", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnTukangBajaRingan:
                i = new Intent(this, RegistrationFromActivity.class);
                i.putExtra("pil", "tukang bangunan");
                startActivity(i);
//                Toast.makeText(ChooseUserTypeActivity.this, "btnAplikator", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnIndividu:
                i = new Intent(this, RegistrationFromActivity.class);
                i.putExtra("pil", "individu");
                startActivity(i);
//                Toast.makeText(ChooseUserTypeActivity.this, "btnAplikator", Toast.LENGTH_LONG).show();
//                Toast.makeText(ChooseUserTypeActivity.this, "btnIndividu", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnAktivasiAkun:
                if (!emailRegister.equals("null")){
                    i = new Intent(this, VerificationPageActivity.class);
//                    i.putExtra("hp_no", "");
                    startActivity(i);
                }

//                Toast.makeText(ChooseUserTypeActivity.this, "btnLanjut", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
