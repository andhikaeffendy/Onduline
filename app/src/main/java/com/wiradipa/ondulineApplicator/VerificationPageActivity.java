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
import android.widget.TextView;

import com.wiradipa.ondulineApplicator.lib.ApiWeb;
import com.wiradipa.ondulineApplicator.lib.AppSession;

import org.json.JSONException;
import org.json.JSONObject;

public class VerificationPageActivity extends AppCompatActivity {

    private String email, hp_no;
    private Context context;
    private EditText et_activation_code,et_activation_email,et_activation_phone;
    private VerificationTask verivicationTask;
    private ResendCode resendCode;
    private TextView textView;
    private Button btnReSendCode;
    private View mProgressView;
    private View mFormView;
    private AppSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_page);

        et_activation_code  =(EditText)findViewById(R.id.et_activation_code);
        et_activation_email =(EditText)findViewById(R.id.et_activation_email);
        et_activation_phone  =(EditText)findViewById(R.id.et_activation_phone);
        mFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.submit_progress);
        textView            =(TextView)findViewById(R.id.textView);
        btnReSendCode       =(Button)findViewById(R.id.btnReSendCode);

        context = this;
        session = new AppSession(context);

        Bundle extras = getIntent().getExtras();
//        email = extras.getString("email");
        hp_no = extras.getString("hp_no");
        textView.setText(getString(R.string.send_sms_to_no) +" "+ hp_no);

        et_activation_email.setText(session.getEmailForm());
        et_activation_phone.setText(hp_no);

//        if(email.equals("")){
//
//        }else{
////            et_activation_email.setText(email);
//            et_activation_email.setText(session.getEmailForm());
//            et_activation_phone.setText(hp_no);
//        }

        btnReSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                resendCode = new ResendCode();
//                resendCode.execute((Void)null);

                attemptSubmitresendCode();
            }
        });

    }
    public void popupSuccess(){
        new AlertDialog.Builder(this)
                .setTitle("Verifikasi sukses!")
                .setMessage("Selamat akun anda telah aktif, lanjutkan ke halaman Login")
                .setPositiveButton("lanjutkan", new DialogInterface.OnClickListener() {

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

    public void popupResendSuccess(){
        new AlertDialog.Builder(this)
                .setTitle("Kirim Ulang Kode sukses!")
                .setMessage("Tunggu sms kode aktivasi kembali sekitar 1 sampai 5 menit.")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        //MapsActivity.super.onBackPressed();
                        //finish();
                        // System.exit(0);

////                        Intent intent = new Intent(context, LoginActivity.class);
//                        intent.addCategory(Intent.CATEGORY_HOME);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
//
//                        startActivity(intent);
//                        finish();
                    }
                }).create().show();
    }

    public void popupAllert(String allert){
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(allert)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

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

    public void onClickVerivication(View v){

        switch (v.getId()){
            case R.id.btnActivation:

                attemptSubmitverivicationTask();
//                verivicationTask = new VerificationTask();
//                verivicationTask.execute((Void)null);

//                i = new Intent(this, LoginActivity.class);
//                startActivity(i);
                break;
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


    private boolean isActivationcodeNotEmpty(String et) {
        //TODO: Replace this with your own logic
        return et.equals("");
    }
    private boolean isEmailNotEmpty(String et) {
        //TODO: Replace this with your own logic
        return et.equals("");
    }
    private boolean isPhoneNotEmpty(String et) {
        //TODO: Replace this with your own logic
        return et.equals("");
    }

    /**
     * Attempts to sign in or register the account specified by the login next_menu.
     * If there are next_menu errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSubmitverivicationTask() {
        if ( verivicationTask!= null) {
            return;
        }

        // Reset errors.
        et_activation_code.setError(null);
        et_activation_email.setError(null);
//        img_AddNew.setError

        // Store values at the time of the submit attempt.
        String activation_code  = et_activation_code.getText().toString();
        String email            = et_activation_email.getText().toString();

        boolean cancel = false;
        View focusView = null;
//

        if (isActivationcodeNotEmpty(activation_code)) {
            et_activation_code.setError(getString(R.string.error_field_required));
            focusView = et_activation_code;
            cancel = true;
        }
        if (isEmailNotEmpty(email)) {
            et_activation_email.setError(getString(R.string.error_field_required));
            focusView = et_activation_email;
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


            verivicationTask = new VerificationTask();
            verivicationTask.execute((Void)null);

//            instalationGuideComplainTask = new InstalationGuideComplainTask();
//            instalationGuideComplainTask.execute((Void)null);

        }
    }



    /**
     * Attempts to sign in or register the account specified by the login next_menu.
     * If there are next_menu errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSubmitresendCode() {
        if ( verivicationTask!= null) {
            return;
        }

        // Reset errors.
        et_activation_phone.setError(null);
//        img_AddNew.setError

        // Store values at the time of the submit attempt.
        String phone    = et_activation_phone.getText().toString();

        boolean cancel = false;
        View focusView = null;
//

        if (isPhoneNotEmpty(phone)) {
            et_activation_phone.setError("masukan no telepon yang dituju");
            focusView = et_activation_phone;
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


            resendCode = new ResendCode();
            resendCode.execute((Void)null);

//            verivicationTask = new VerificationTask();
//            verivicationTask.execute((Void)null);

//            instalationGuideComplainTask = new InstalationGuideComplainTask();
//            instalationGuideComplainTask.execute((Void)null);

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
                showProgress(false);
                verivicationTask=null;
                resendCode=null;


            } else {

                popupAllert(errorMessage);
                showProgress(false);
                verivicationTask=null;
                resendCode=null;

            }
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class ResendCode extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String activation_email,activation_phone;

        ResendCode() {
            apiWeb = new ApiWeb();
            activation_email = et_activation_email.getText().toString();
            activation_phone = et_activation_phone.getText().toString();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = null;
//            result = apiWeb.ResendActivatiionCode(activation_email);
            result = apiWeb.ResendActivatiionCode(activation_email,activation_phone);


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
                popupResendSuccess();
                showProgress(false);
                verivicationTask=null;
                resendCode=null;

            } else {

                popupAllert(errorMessage);
                showProgress(false);
                verivicationTask=null;
                resendCode=null;

            }
        }
    }

}
