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
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wiradipa.ondulineApplicator.lib.ApiWeb;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button btnForgotPassword;
    EditText et_forgot_password;
    Context context;
    ForgotPasswordTask forgotPasswordTask;
    private View mProgressView;
    private View mFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        context = this;


        et_forgot_password = (EditText)findViewById(R.id.et_forgot_password);
        btnForgotPassword= (Button) findViewById(R.id.btnForgotPassword);
        mFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.submit_progress);

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attemptSubmitforgotPasswordTask();
//                forgotPasswordTask = new ForgotPasswordTask();
//                forgotPasswordTask.execute((Void)null);
//
//                if (et_forgot_password.getText().toString().equals("")){
//
//                    forgotPasswordTask = new ForgotPasswordTask();
//                    forgotPasswordTask.execute((Void)null);
//                }
            }
        });
    }


    private boolean isForgotPasswordNotEmpty(String et) {
        //TODO: Replace this with your own logic
        return et.equals("");
    }


    /**
     * Attempts to sign in or register the account specified by the login next_menu.
     * If there are next_menu errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSubmitforgotPasswordTask() {
        if ( forgotPasswordTask!= null) {
            return;
        }

        // Reset errors.
        et_forgot_password.setError(null);
//        img_AddNew.setError

        // Store values at the time of the submit attempt.
        String et     = et_forgot_password.getText().toString();

        boolean cancel = false;
        View focusView = null;
//

        if (isForgotPasswordNotEmpty(et)) {
            et_forgot_password.setError(getString(R.string.error_field_required));
            focusView = et_forgot_password;
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



            forgotPasswordTask = new ForgotPasswordTask();
            forgotPasswordTask.execute((Void)null);

//
//            instalationGuideComplainTask = new InstalationGuideComplainTask();
//            instalationGuideComplainTask.execute((Void)null);

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


    public void popupSuccess(){
        new AlertDialog.Builder(this)
                .setTitle("Permintaan terikirm")
                .setMessage("Periksa email anda untuk mendapatkan password baru anda")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        //MapsActivity.super.onBackPressed();
                        //finish();
                        // System.exit(0);

//                        Intent intent = new Intent(context, LoginActivity.class);
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
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        //MapsActivity.super.onBackPressed();
                        //finish();
                        // System.exit(0);

//                        Intent intent = new Intent(context, AddNewActivity.class);
//                        intent.putExtra("pil",pil);
//                        intent.addCategory(Intent.CATEGORY_HOME);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
//
//                        startActivity(intent);
//                        finish();
                    }
                }).create().show();
    }




    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class ForgotPasswordTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String email;

        ForgotPasswordTask() {
            apiWeb = new ApiWeb();
            email = et_forgot_password.getText().toString();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = null;
//            result = apiWeb.ResendActivatiionCode(activation_email);
            result = apiWeb.ForgotPassword(email);


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

            } else {

                popupAllert(errorMessage);
                showProgress(false);

            }
        }
    }

}
