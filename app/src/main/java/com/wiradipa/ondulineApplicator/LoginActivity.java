package com.wiradipa.ondulineApplicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wiradipa.ondulineApplicator.lib.ApiWeb;
import com.wiradipa.ondulineApplicator.lib.AppSession;
import com.wiradipa.ondulineApplicator.lib.calc_formula;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {


    private String pil;
    private calc_formula formula;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private Context context;
    private UserLoginTask mAuthTask = null;
    private AppSession session;

    // UI references.
    private EditText editTextLoginEmail,editTextLoginPassword;
    private Button btnLogin;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLoginEmail = (EditText)findViewById(R.id.editTextLoginEmail);
        editTextLoginPassword = (EditText)findViewById(R.id.editTextLoginPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        context = this;
        session = new AppSession(this);
        if(session.is_login()){
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            finish();
        }

        editTextLoginPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.length()>4;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Attempts to sign in or register the account specified by the login next_menu.
     * If there are next_menu errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        editTextLoginEmail.setError(null);
        editTextLoginPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = editTextLoginEmail.getText().toString();
        String password = editTextLoginPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            editTextLoginEmail.setError(getString(R.string.error_invalid_password));
            focusView = editTextLoginPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            editTextLoginEmail.setError(getString(R.string.error_field_required));
            focusView = editTextLoginEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            editTextLoginEmail.setError(getString(R.string.error_invalid_email));
            focusView = editTextLoginEmail;
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

            //Toast.makeText(this, "password : " + password , Toast.LENGTH_LONG).show();
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
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

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }



    //    sementara untuk activation
    public void activation(View v){
        Intent i;
        i = new Intent(this, VerificationPageActivity.class);

        switch (v.getId()){
            case R.id.linkSignup:
                i.putExtra("email", "");
                startActivity(i);
                break;

        }
    }


    public void onClickLogin(View v){
        Intent i;
        i = new Intent(this, ProgramActivity.class);

        switch (v.getId()){
            case R.id.btnLogin:

                attemptLogin();

//                switch (editTextLoginEmail.getText().toString()){
//                    case "app@gmail.com":
//                        pil="applicator";
//                        i.putExtra("pil", pil);
//                        startActivity(i);
//                        break;
//                    case "retail@gmail.com":
//                        pil="retailer";
//                        i.putExtra("pil", pil);
//                        startActivity(i);
//                        break;
//                    default:
//                        Toast.makeText(this, "email tidak terdaftar", Toast.LENGTH_LONG).show();
//                        break;
//                }

                break;
            case R.id.btnRegister:
                i = new Intent(this, ChooseUserTypeActivity.class);;
                startActivity(i);
                break;
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String username;
        private final String mPassword;
        private String name;
        private String email;
        private String usertype;
        private ApiWeb apiWeb;
        private String errorMessage = getString(R.string.error_incorrect_password);
        private String token = "";
        private long userid;

        UserLoginTask(String email, String password) {
            username = email;
            mPassword = password;
            apiWeb = new ApiWeb();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = apiWeb.Login(username,mPassword);
            if(result==null){
                errorMessage = getString(R.string.error_server);
                return false;
            }
            try {
                JSONObject json = new JSONObject(result);
                String status = json.getString("status");
                if(status.compareToIgnoreCase("success")==0){
                    userid = json.getLong("user_id");
                    token = json.getString("token");
                    name = json.getString("name");
                    email = json.getString("email");
                    usertype = json.getString("user_type");
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
            mAuthTask = null;
            showProgress(false);

            if (success) {
                session.login(userid, username, usertype, token, name, email);
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            } else {
                editTextLoginEmail.setError(errorMessage);
                editTextLoginEmail.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
