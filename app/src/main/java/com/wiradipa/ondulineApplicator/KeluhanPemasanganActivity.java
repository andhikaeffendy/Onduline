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

public class KeluhanPemasanganActivity extends AppCompatActivity {


    private Context context;
    private VerificationPageActivity.VerificationTask verivicationTask;
    private AppSession session;
    private InstalationGuideComplainTask instalationGuideComplainTask;
    private ProductComplaineTask productComplaineTask;

    private Button btn_instalationGuideComplain;
    private EditText et_instalationGuideComplain,et_productComplain;

    private String token, pil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");

        setLAyout();

        btn_instalationGuideComplain=(Button)findViewById(R.id.btn_instalationGuideComplain);
        et_instalationGuideComplain=(EditText) findViewById(R.id.et_instalationGuideComplain);
        et_productComplain=(EditText) findViewById(R.id.et_productComplain);



        context = this;
        session = new AppSession(context);
        session.checkSession();
        token = session.getToken();
    }

    public void popupSuccess(){
        new AlertDialog.Builder(this)
                .setTitle("PEngiriman sukses")
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

    public void setLAyout(){

        if (pil.equals("InstalationComplain")){

            setContentView(R.layout.activity_instalation_complain);
            Toast.makeText(this, "activity_instalation_guide", Toast.LENGTH_LONG).show();

        }else if (pil.equals("ProductComplain")){

            setContentView(R.layout.activity_product_complain);
            Toast.makeText(this, "activity_product_complain", Toast.LENGTH_LONG).show();

        }

    }

    public void onClickinstalationGuideComplain(View v){

        switch (v.getId()){
            case R.id.btn_instalationGuideComplain:

                instalationGuideComplainTask = new InstalationGuideComplainTask();
                instalationGuideComplainTask.execute((Void)null);

                break;
            case R.id.btn_produckComplain:

                Toast.makeText(this, "btn_produckComplain", Toast.LENGTH_LONG).show();
                productComplaineTask = new ProductComplaineTask();
                productComplaineTask.execute((Void)null);

                break;
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class InstalationGuideComplainTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String detail, support_type;

        InstalationGuideComplainTask() {
            apiWeb = new ApiWeb();
            detail=et_instalationGuideComplain.getText().toString();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = null;
            result = apiWeb.TechnicalSupportInstalationGuideComplain(token, detail,"installation_complaint");


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
//                Intent i;
//                i = new Intent(context, LoginActivity.class);
//                startActivity(i);

                //disini ada popup sukses..:D

            } else {


            }
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class ProductComplaineTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String detail, support_type;

        ProductComplaineTask () {
            apiWeb = new ApiWeb();
            detail=et_productComplain.getText().toString();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = null;
            result = apiWeb.TechnicalSupportProductComplain(token, detail,"product_complaint");


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

    }}
