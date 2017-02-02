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
import com.wiradipa.ondulineApplicator.lib.AppSession;

import org.json.JSONException;
import org.json.JSONObject;

public class SupervisiProyekActivity extends AppCompatActivity {


    private Context context;
    private AppSession session;
    private SupervisiProjectTask supervisiProjectTask;

    private EditText et_supervisionProjectNama, et_supervisionProjectAddress, et_supervisionProjectPhone, et_supervisionProjectOwnerName, et_supervisionProjectDetail;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisi_proyek);

        et_supervisionProjectNama = (EditText)findViewById(R.id.et_supervisionProjectNama);
        et_supervisionProjectAddress = (EditText)findViewById(R.id.et_supervisionProjectAddress);
        et_supervisionProjectPhone = (EditText)findViewById(R.id.et_supervisionProjectPhone);
        et_supervisionProjectOwnerName = (EditText)findViewById(R.id.et_supervisionProjectOwnerName);
        et_supervisionProjectDetail = (EditText)findViewById(R.id.et_supervisionProjectDetail);


        context = this;
        session = new AppSession(context);
        session.checkSession();
        token = session.getToken();

    }


    public void popupSuccess(){
        new AlertDialog.Builder(this)
                .setTitle("Pngiriman sukses")
                .setMessage("Selamat pesan anda telah terkirim")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

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


    public void onClickSupervisionProject(View v){

        switch (v.getId()){
            case R.id.btn_supervisionProject:

                supervisiProjectTask = new SupervisiProjectTask();
                supervisiProjectTask.execute((Void)null);
                break;
        }

    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class SupervisiProjectTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String Nama, Address, Phone, OwnerName, Detail;

        SupervisiProjectTask () {
            apiWeb = new ApiWeb();
            Nama = et_supervisionProjectNama.getText().toString();
            Phone = et_supervisionProjectPhone.getText().toString();
            Address = et_supervisionProjectAddress.getText().toString();
            OwnerName = et_supervisionProjectOwnerName.getText().toString();
            Detail=et_supervisionProjectDetail.getText().toString();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = null;
            result = apiWeb.TechnicalSupportSupervisiProject(token,Nama,Address,Phone,OwnerName,Detail);


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


            }
        }

    }
}
