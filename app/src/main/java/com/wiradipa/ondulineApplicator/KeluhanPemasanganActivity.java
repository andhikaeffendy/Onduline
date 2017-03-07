package com.wiradipa.ondulineApplicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
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
    private View mProgressView;
    private View mFormView;

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

        mFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.submit_progress);


        context = this;
        session = new AppSession(context);
        session.checkSession();
        token = session.getToken();
    }


    private boolean isInstalationGuideComplainNotEmpty(String et) {
        //TODO: Replace this with your own logic
        return et.equals("");
    }
    private boolean isProductComplainNotEmpty(String et) {
        //TODO: Replace this with your own logic
        return et.equals("");
    }



    /**
     * Attempts to sign in or register the account specified by the login next_menu.
     * If there are next_menu errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSubmitInstalationGuideComplain() {
        if ( instalationGuideComplainTask!= null) {
            return;
        }

        // Reset errors.
        et_instalationGuideComplain.setError(null);
//        img_AddNew.setError

        // Store values at the time of the submit attempt.
        String et     = et_instalationGuideComplain.getText().toString();

        boolean cancel = false;
        View focusView = null;
//

        if (isInstalationGuideComplainNotEmpty(et)) {
            et_instalationGuideComplain.setError(getString(R.string.error_field_required));
            focusView = et_instalationGuideComplain;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // next_menu field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
//            disini kita show progreess apapun itu dan di setiap


            instalationGuideComplainTask = new InstalationGuideComplainTask();
            instalationGuideComplainTask.execute((Void)null);

        }
    }


    /**
     * Attempts to sign in or register the account specified by the login next_menu.
     * If there are next_menu errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSubmitproductComplain() {
        if ( productComplaineTask!= null) {
            return;
        }

        // Reset errors.
        et_productComplain.setError(null);
//        img_AddNew.setError

        // Store values at the time of the submit attempt.
        String et     = et_productComplain.getText().toString();

        boolean cancel = false;
        View focusView = null;
//

        if (isProductComplainNotEmpty(et)) {
            et_productComplain.setError(getString(R.string.error_field_required));
            focusView = et_productComplain;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // next_menu field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
//            disini kita show progreess apapun itu dan di setiap


            productComplaineTask = new ProductComplaineTask();
            productComplaineTask.execute((Void)null);
        }
    }


    /**
     * Shows the progress UI and hides the login next_menu.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
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

                        Intent intent = new Intent(context, SupervisiProyekActivity.class);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***

                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }

    public void popupSuccess(){
        new AlertDialog.Builder(this)
                .setTitle("Pengiriman sukses")
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
//            Toast.makeText(this, "activity_instalation_guide", Toast.LENGTH_LONG).show();

        }else if (pil.equals("ProductComplain")){

            setContentView(R.layout.activity_product_complain);
//            Toast.makeText(this, "activity_product_complain", Toast.LENGTH_LONG).show();

        }

    }

    public void onClickinstalationGuideComplain(View v){

        switch (v.getId()){
            case R.id.btn_instalationGuideComplain:

                attemptSubmitInstalationGuideComplain();
//                instalationGuideComplainTask = new InstalationGuideComplainTask();
//                instalationGuideComplainTask.execute((Void)null);

                break;
            case R.id.btn_produckComplain:

                attemptSubmitproductComplain();
//                Toast.makeText(this, "btn_produckComplain", Toast.LENGTH_LONG).show();
//                productComplaineTask = new ProductComplaineTask();
//                productComplaineTask.execute((Void)null);

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
                showProgress(false);
//                Intent i;
//                i = new Intent(context, LoginActivity.class);
//                startActivity(i);

                //disini ada popup sukses..:D

            } else {

                popupAllert(errorMessage);
                showProgress(false);

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
                showProgress(false);
//
//                Intent i;
//                i = new Intent(context, LoginActivity.class);
//                startActivity(i);

                //disini ada popup sukses..:D

            } else {

                popupAllert(errorMessage);
                showProgress(false);

            }
        }

    }}
