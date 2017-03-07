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
import android.widget.EditText;

import com.wiradipa.ondulineApplicator.lib.ApiWeb;
import com.wiradipa.ondulineApplicator.lib.AppSession;

import org.json.JSONException;
import org.json.JSONObject;

public class SupervisiProyekActivity extends AppCompatActivity {


    private Context context;
    private AppSession session;
    private SupervisiProjectTask supervisiProjectTask;
    private View mProgressView;
    private View mFormView;

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

        mFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);

        context = this;
        session = new AppSession(context);
        session.checkSession();
        token = session.getToken();

    }


    private boolean isProjectNameNotEmpty(String address) {
        //TODO: Replace this with your own logic
        return address.equals("");
    }
    private boolean isProjectAddressNotEmpty(String address) {
        //TODO: Replace this with your own logic
        return address.equals("");
    }
    private boolean isProjectPhoneNotEmpty(String address) {
        //TODO: Replace this with your own logic
        return address.equals("");
    }
    private boolean isProjectOwnerNameNotEmpty(String address) {
        //TODO: Replace this with your own logic
        return address.equals("");
    }
    private boolean isProjectDetailNotEmpty(String address) {
        //TODO: Replace this with your own logic
        return address.equals("");
    }


    /**
     * Attempts to sign in or register the account specified by the login next_menu.
     * If there are next_menu errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSubmiSupervisiProyek() {
        if (supervisiProjectTask != null) {
            return;
        }

        // Reset errors.
        et_supervisionProjectNama.setError(null);
        et_supervisionProjectAddress.setError(null);
        et_supervisionProjectPhone.setError(null);
        et_supervisionProjectOwnerName.setError(null);
        et_supervisionProjectDetail.setError(null);
//        img_AddNew.setError

        // Store values at the time of the submit attempt.
        String name     = et_supervisionProjectNama.getText().toString();
        String address  = et_supervisionProjectAddress.getText().toString();
        String phone    = et_supervisionProjectPhone.getText().toString();
        String ownerName= et_supervisionProjectOwnerName.getText().toString();
        String detail   = et_supervisionProjectDetail.getText().toString();

        boolean cancel = false;
        View focusView = null;
//

        if (isProjectNameNotEmpty(name)) {
            et_supervisionProjectNama.setError(getString(R.string.error_field_required));
            focusView = et_supervisionProjectNama;
            cancel = true;
        }
        if (isProjectAddressNotEmpty(address)) {
            et_supervisionProjectAddress.setError(getString(R.string.error_field_required));
            focusView = et_supervisionProjectAddress;
            cancel = true;
        }
        if (isProjectPhoneNotEmpty(phone)) {
            et_supervisionProjectPhone.setError(getString(R.string.error_field_required));
            focusView = et_supervisionProjectPhone;
            cancel = true;
        }
        if (isProjectOwnerNameNotEmpty(ownerName)) {
            et_supervisionProjectOwnerName.setError(getString(R.string.error_field_required));
            focusView = et_supervisionProjectOwnerName;
            cancel = true;
        }
        if (isProjectDetailNotEmpty(detail)) {
            et_supervisionProjectDetail.setError(getString(R.string.error_field_required));
            focusView = et_supervisionProjectDetail;
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

            supervisiProjectTask = new SupervisiProjectTask();
            supervisiProjectTask.execute((Void)null);
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


    public void onClickSupervisionProject(View v){

        switch (v.getId()){
            case R.id.btn_supervisionProject:

                attemptSubmiSupervisiProyek();
//                supervisiProjectTask = new SupervisiProjectTask();
//                supervisiProjectTask.execute((Void)null);
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

            } else {
                popupAllert(errorMessage);

            }
        }

    }
}
