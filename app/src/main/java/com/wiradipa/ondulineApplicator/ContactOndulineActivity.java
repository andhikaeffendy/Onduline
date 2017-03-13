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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.wiradipa.ondulineApplicator.lib.ApiWeb;
import com.wiradipa.ondulineApplicator.lib.AppSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import javax.security.auth.Subject;

public class ContactOndulineActivity extends AppCompatActivity {


    private VerificationPageActivity.VerificationTask verivicationTask;
    private KeluhanPemasanganActivity.InstalationGuideComplainTask instalationGuideComplainTask;
    private KeluhanPemasanganActivity.ProductComplaineTask productComplaineTask;

    private ContactUsTask contactUsTask;


    private Context context;
    private AppSession session;
    private Button btn_instalationGuideComplain;
    private EditText et_contactUs;
    private Spinner spn_contactUs;
    private View mProgressView;
    private View mFormView;
    private String token, pil;


    protected ListAdapter adapter;
    ArrayAdapter Adapter;
    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> mylist;
    String[] subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_onduline);
        context = this;

        spn_contactUs = (Spinner) findViewById(R.id.spn_contactUs);
        et_contactUs = (EditText)findViewById(R.id.et_contactUs);
        mFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.submit_progress);

        subject = new String[] {"Harga", "Distributor", "Permintaan Katalog", "Teknis", "Lain-lain"};


        mylist = new ArrayList<HashMap<String,String>>();

        for (int i = 0; i < subject.length; i++){
            map = new HashMap<String, String>();
            map.put("list", subject[i]);
            mylist.add(map);
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        Adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, subject);
        // Specify the layout to use when the list of choices appears
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spn_contactUs.setAdapter(Adapter);


    }

    public void onClickContactUs(View v){

        switch (v.getId()){
            case R.id.btn_contactUs:
                attemptSubmitcontactUs();

                break;
        }
    }


    private boolean et_NotEmpty(String et) {
        //TODO: Replace this with your own logic
        return et.equals("");
    }

    /**
     * Attempts to sign in or register the account specified by the login next_menu.
     * If there are next_menu errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSubmitcontactUs() {
        if ( contactUsTask!= null) {
            return;
        }

        // Reset errors.
        et_contactUs.setError(null);
//        img_AddNew.setError

        // Store values at the time of the submit attempt.
        String et     = et_contactUs.getText().toString();

        boolean cancel = false;
        View focusView = null;
//

        if (et_NotEmpty(et)) {
            et_contactUs.setError(getString(R.string.error_field_required));
            focusView = et_contactUs;
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


            contactUsTask = new ContactUsTask();
            contactUsTask.execute((Void)null);
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
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        //MapsActivity.super.onBackPressed();
                        //finish();
                        // System.exit(0);

                        Intent intent = new Intent(context, ContactOndulineActivity.class);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***

                        startActivity(intent);
                        finish();
                    }
                }).create().show();
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

                        Intent intent = new Intent(context, HomeActivity.class);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***

                        startActivity(intent);
                        finish();
                    }
                }).create().show();

    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class ContactUsTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String detail, subject_type;

        ContactUsTask() {
            apiWeb = new ApiWeb();
            detail          =   et_contactUs.getText().toString();
            subject_type    =   spn_contactUs.getSelectedItem().toString();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = null;
//            result = apiWeb.TechnicalSupportInstalationGuideComplain(token, detail,"installation_complaint");


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
                contactUsTask=null;

            }
        }
    }


}
