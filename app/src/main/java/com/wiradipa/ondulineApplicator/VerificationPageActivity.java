package com.wiradipa.ondulineApplicator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.wiradipa.ondulineApplicator.lib.ApiWeb;

import org.json.JSONException;
import org.json.JSONObject;

public class VerificationPageActivity extends AppCompatActivity {

    private String email;
    private Context context;
    private EditText et_activation_code,et_activation_email;
    private VerificationTask verivicationTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_page);

        et_activation_code=(EditText)findViewById(R.id.et_activation_code);
        et_activation_email=(EditText)findViewById(R.id.et_activation_email);

        context = this;

        Bundle extras = getIntent().getExtras();
        email = extras.getString("email");
        if(email.equals("")){

        }else{
            et_activation_email.setText(email);
        }


    }
    public void popupSuccess(){
        new AlertDialog.Builder(this)
                .setTitle("Verifikasi sukses")
                .setMessage("Selamat akun anda telah aktif")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        //MapsActivity.super.onBackPressed();
                        //finish();
                        // System.exit(0);

                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***

                        startActivity(intent);
                        finish();
                    }
                }).create().show();

    }

    public void onClickVerivication(View v){
        Intent i;
        switch (v.getId()){
            case R.id.btnActivation:

                verivicationTask = new VerificationTask();
                verivicationTask.execute((Void)null);

//                i = new Intent(this, LoginActivity.class);
//                startActivity(i);
                break;
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class VerificationTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String activation_email,activation_code;



        VerificationTask() {
            apiWeb = new ApiWeb();
            activation_email = et_activation_email.getText().toString();
            activation_code = et_activation_code.getText().toString();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = null;
            result = apiWeb.Activation(activation_code,activation_email);


            if(result==null){
                return false;
            }
            try {
                JSONObject json = new JSONObject(result);
                String status = json.getString("status");
                if(status.compareToIgnoreCase("success")==0){

                    return true;
                }
                if(json.has("message"))errorMessage = json.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                //SESSION

                popupSuccess();
//
//                Intent i;
//                i = new Intent(context, LoginActivity.class);
//                startActivity(i);

            } else {


            }
        }

    }
}
