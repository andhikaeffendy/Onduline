package com.wiradipa.ondulineApplicator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wiradipa.ondulineApplicator.lib.ApiWeb;
import com.wiradipa.ondulineApplicator.lib.AppSession;

import org.json.JSONException;
import org.json.JSONObject;

public class InstalationGuideActivity extends AppCompatActivity {


    private Context context;
    private AppSession session;
    private InstalationGuideTask instalationGuideTask;

    private Button btn_instalationGuide;
    private EditText et_instalationGuide;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_instalation_guide);

        btn_instalationGuide=(Button)findViewById(R.id.btn_instalationGuide);
        et_instalationGuide=(EditText) findViewById(R.id.et_instalationGuide);



        context = this;
        session = new AppSession(context);
        session.checkSession();
        token = session.getToken();
    }

    public void popupSuccess(){
        new AlertDialog.Builder(this)
                .setTitle("Pengiriman sukses!")
                .setMessage("Selamat pesan anda telah terkirim")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        //MapsActivity.super.onBackPressed();
                        //finish();
                        // System.exit(0);

                        Intent intent = new Intent(context, TechnicalSupportActivity.class);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***

                        startActivity(intent);
                        finish();
                    }
                }).create().show();

    }


    public void popupAllert(String allert){
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(allert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        //MapsActivity.super.onBackPressed();
                        //finish();
                        // System.exit(0);

//                        Intent intent = new Intent(context, SupervisiProyekActivity.class);
//                        intent.addCategory(Intent.CATEGORY_HOME);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
//
//                        startActivity(intent);
//                        finish();
                    }
                }).create().show();
    }


    public void onClickinstalationGuide(View v){

        switch (v.getId()){
            case R.id.btn_instalationGuide:

//                Toast.makeText(this, "btn_instalationGuide", Toast.LENGTH_LONG).show();
                instalationGuideTask = new InstalationGuideTask();
                instalationGuideTask.execute((Void)null);

                break;

        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class InstalationGuideTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String detail, support_type;

        InstalationGuideTask() {
            apiWeb = new ApiWeb();
            detail=et_instalationGuide.getText().toString();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = null;
            result = apiWeb.TechnicalSupportInstalationGuide(token, detail,"installation");


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

                //disini ada popup sukses..:D

            } else {

                popupAllert(errorMessage);

            }
        }

    }



}
