package com.wiradipa.ondulineApplicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wiradipa.ondulineApplicator.lib.ApiWeb;
import com.wiradipa.ondulineApplicator.lib.AppSession;
import com.wiradipa.ondulineApplicator.lib.AutoCompleteAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.acl.Owner;
import java.util.Calendar;

public class RegistrationFromActivity extends AppCompatActivity {

    String pil, gender, retailer_type;


    private Calendar calendar;
    private int year, month, day;

    private TextView txtisianBirth, txtisianBirthView;

    private EditText et_name, et_address, et_phone, et_email, et_distributor, et_association, et_owner, et_id, et_username, et_password, et_password_confirm, et_company;

    private Context context;

    private String[] statesName, citiesName;
    private long[] statesIds, citiesIds;

    private AutoCompleteTextView act_city, act_state;
    private AutoCompleteAdapter adapter_state, adapter_city;
    long states_id, city_id;

    private UpdateTask updateTask;
    private UpdateCityTask updateCityTask;
    private RegisterApplicatorTask registerApplicatorTask= null;
    private View mProgressView;
    private View mFormView;
    private AppSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        session = new AppSession(context);
        setLayout();

    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.length()>4;
    }

    private boolean isUsernameValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    private boolean isUsernameEmpty(String user) {
        //TODO: Replace this with your own logic
        return user.equals("");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    private boolean isPasswordEmpty(String password) {
        //TODO: Replace this with your own logic
        return password.equals("");
    }

    private boolean isPasswordConfirmValid(String password, String passwordConfirm) {
        //TODO: Replace this with your own logic
        return password.equals(passwordConfirm);
    }

    private boolean isPasswordConfirmEmpty(String passwordConfirm) {
        //TODO: Replace this with your own logic
        return passwordConfirm.equals("");
    }

    private boolean isNameValid(String name) {
        //TODO: Replace this with your own logic
        return name.equals("");
    }
//
    private boolean isAddressEmpty(String address) {
        //TODO: Replace this with your own logic
        return address.equals("");
    }

    private boolean isPhoneValid(String phone) {
        //TODO: Replace this with your own logic
        return phone.equals("");
    }

    private boolean isDistributorValid(String distributor) {
        //TODO: Replace this with your own logic
        return distributor.equals("");
    }

    private boolean isOwnerValid(String owner) {
        //TODO: Replace this with your own logic
        return owner.equals("");
    }

    private boolean isCityValid(String city) {
        //TODO: Replace this with your own logic
        return city.equals("");
    }

    private boolean isStateValid(String state) {
        //TODO: Replace this with your own logic
        return state.equals("");
    }
//
//    private boolean isCompanyValid(String company) {
//        //TODO: Replace this with your own logic
//        return company.equals("");
//    }

    private boolean isIdValid(String id) {
        //TODO: Replace this with your own logic
        return id.equals("");
    }
//
//    private boolean isAssociationValid(String as) {
//        //TODO: Replace this with your own logic
//        return as.equals("");
//    }


    public void popupSuccess(final String email_regis, final String no_hp_regis){
        new AlertDialog.Builder(this)
                .setTitle("Registrasi Sukses")
                .setMessage("Selamat registrasi anda berhasil, lanjutkan ke halaman aktivasi?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(context, VerificationPageActivity.class);
                        intent.putExtra("email", email_regis);
                        session.setEmailForm(email_regis);
                        intent.putExtra("hp_no", no_hp_regis);
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
                        finish();
                    }
                }).create().show();

    }

    public void OnCreateRegistrasiApplicator(){


        et_name = (EditText)findViewById(R.id.et_name);//               wajib diisi
        et_address = (EditText)findViewById(R.id.et_address);//         wajib diisi
        et_phone = (EditText)findViewById(R.id.et_phone);//             harus diisi
        et_email = (EditText)findViewById(R.id.et_email); //            email min min 4 karater harus ada @
        et_distributor = (EditText)findViewById(R.id.et_distributor);// wajib diisi
        et_association = (EditText)findViewById(R.id.et_association);// wajib diisi
        et_owner = (EditText)findViewById(R.id.et_owner);//             wajib diisi
        et_id = (EditText)findViewById(R.id.et_id);
        et_username = (EditText)findViewById(R.id.et_username);//       username minimal 4 karakter dan harus terdiri dari huruf dan angka
        et_password = (EditText)findViewById(R.id.et_password);//       password minimal 4 karakter harus terdiri dari huruf dan angka
        et_password_confirm = (EditText)findViewById(R.id.et_password_confirm);//   harus sama dengan password
        act_city = (AutoCompleteTextView)findViewById(R.id.act_city);//             wajib diisi
        act_state = (AutoCompleteTextView)findViewById(R.id.act_state);//           wajib diisi
        et_company = (EditText)findViewById(R.id.et_company);//                     wajib diisi

        txtisianBirth=(TextView)findViewById(R.id.txtisianBirth);//                 wajib diisi
        txtisianBirthView=(TextView)findViewById(R.id.txtisianBirthView);//                 wajib diisi
        calendar = Calendar.getInstance();

        mFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);


        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        act_state.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                states_id = adapter_state.getItemId(act_state.getText().toString());
                updateCityTask = new UpdateCityTask(states_id+"");
                updateCityTask.execute((Void)null);
            }
        });

        updateTask = new UpdateTask();
        updateTask.execute((Void)null);
    }

    public void OnCreateRegistrasiRetailer(){


        mFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
        et_name = (EditText)findViewById(R.id.et_name);//               wajib diisi
        et_address = (EditText)findViewById(R.id.et_address);//         wajib diisi
        act_city = (AutoCompleteTextView)findViewById(R.id.act_city);//             wajib diisi
        act_state = (AutoCompleteTextView)findViewById(R.id.act_state);//           wajib diisi
        et_phone = (EditText)findViewById(R.id.et_phone);//             harus diisi
        et_email = (EditText)findViewById(R.id.et_email); //            email min min 4 karater harus ada @

        txtisianBirth=(TextView)findViewById(R.id.txtisianBirth);//                 wajib diisi
        txtisianBirthView=(TextView)findViewById(R.id.txtisianBirthView);//                 wajib diisi
        et_distributor = (EditText)findViewById(R.id.et_distributor);// wajib diisi
        et_owner = (EditText)findViewById(R.id.et_owner);//             wajib diisi
        et_id = (EditText)findViewById(R.id.et_id);
        et_username = (EditText)findViewById(R.id.et_username);//       username minimal 4 karakter dan harus terdiri dari huruf dan angka
        et_password = (EditText)findViewById(R.id.et_password);//       password minimal 4 karakter harus terdiri dari huruf dan angka
        et_password_confirm = (EditText)findViewById(R.id.et_password_confirm);//   harus sama dengan password

        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        act_state.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                states_id = adapter_state.getItemId(act_state.getText().toString());
                updateCityTask = new UpdateCityTask(states_id+"");
                updateCityTask.execute((Void)null);
            }
        });

        updateTask = new UpdateTask();
        updateTask.execute((Void)null);
    }

    public void OnCreateRegistrasiIndividu(){

        mFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
        et_name = (EditText)findViewById(R.id.et_name);//               wajib diisi
        et_address = (EditText)findViewById(R.id.et_address);//         wajib diisi
        act_city = (AutoCompleteTextView)findViewById(R.id.act_city);//             wajib diisi
        act_state = (AutoCompleteTextView)findViewById(R.id.act_state);//           wajib diisi
        et_phone = (EditText)findViewById(R.id.et_phone);//             harus diisi
        et_email = (EditText)findViewById(R.id.et_email); //            email min min 4 karater harus ada @

        txtisianBirth=(TextView)findViewById(R.id.txtisianBirth);//                 wajib diisi
        txtisianBirthView=(TextView)findViewById(R.id.txtisianBirthView);//                 wajib diisi
        et_id = (EditText)findViewById(R.id.et_id);
        et_username = (EditText)findViewById(R.id.et_username);//       username minimal 4 karakter dan harus terdiri dari huruf dan angka
        et_password = (EditText)findViewById(R.id.et_password);//       password minimal 4 karakter harus terdiri dari huruf dan angka
        et_password_confirm = (EditText)findViewById(R.id.et_password_confirm);//   harus sama dengan password

        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        act_state.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                states_id = adapter_state.getItemId(act_state.getText().toString());
                updateCityTask = new UpdateCityTask(states_id+"");
                updateCityTask.execute((Void)null);
            }
        });

        updateTask = new UpdateTask();
        updateTask.execute((Void)null);
    }


    /**
     * dalam satu fungsi ngecek semua edit text
     * Attempts to sign in or register the account specified by the login next_menu.
     * If there are next_menu errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {
        if (registerApplicatorTask!= null) {
            return;
        }

        // Reset errors.
        et_email.setError(null);
        et_password.setError(null);
        et_password_confirm.setError(null);
        et_username.setError(null);

        et_name.setError(null);
//        et_company.setError(null);
//        et_owner.setError(null);
//        et_distributor.setError(null); // perusahaan
//        et_address.setError(null);
        act_city.setError(null);
        act_state.setError(null);
        et_phone.setError(null);
//        et_association.setError(null);
        et_id.setError(null);

        // Store values at the time of the login attempt.
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        String passwordConfirm = et_password_confirm.getText().toString();
        String username = et_username.getText().toString();
        String name = et_name.getText().toString();
//        String company = et_company.getText().toString();
//        String owner = et_owner.getText().toString();
//        String distributor = et_distributor.getText().toString(); // perusahaan
//        String address = et_address.getText().toString();
        String city = act_city.getText().toString();
        String state = act_state.getText().toString();
        String phone = et_phone.getText().toString();
//        String association = et_association.getText().toString();
        String id = et_id.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (isPasswordEmpty(password)) {
            et_password.setError(getString(R.string.error_field_required));
            focusView = et_password;
            cancel = true;
        }else if (!isPasswordValid(password)) {
            et_password.setError(getString(R.string.error_invalid_password));
            focusView = et_password;
            cancel = true;
        }

        // Check for a valid username.  ============================================================ kedepannya if username sudah pernah di pakai maka tampilakan sudah terpakai
        if (isUsernameEmpty(username)) {
            et_username.setError(getString(R.string.error_field_required));
            focusView = et_username;
            cancel = true;
        }else if (!isUsernameValid(username)) {
            et_username.setError(getString(R.string.error_field_required));
            focusView = et_username;
            cancel = true;
        }

        // Check for a valid name.
        if (isNameValid(name)) {
            et_name.setError(getString(R.string.error_field_required));
            focusView = et_name;
            cancel = true;
        }
//
//        // Check for a valid address.
//        if (isAddressValid(address)) {
//            et_address.setError(getString(R.string.error_field_required));
//            focusView = et_address;
//            cancel = true;
//        }

        // Check for a valid city.
        if (isCityValid(city)) {
            act_city.setError(getString(R.string.error_field_required));
            focusView = act_city;
            cancel = true;
        }

        // Check for a valid state.
        if (isStateValid(state)) {
            act_state.setError(getString(R.string.error_field_required));
            focusView = act_state;
            cancel = true;
        }

        // Check for a valid phone.
        if (isPhoneValid(phone)) {
            et_phone.setError(getString(R.string.error_field_required));
            focusView = et_phone;
            cancel = true;
        }

//        // Check for a valid association.
//        if (isAssociationValid(association)) {
//            et_association.setError(getString(R.string.error_field_required));
//            focusView = et_association;
//            cancel = true;
//        }
//
//        // Check for a valid company.
//        if (isCompanyValid(company)) {
//            et_company.setError(getString(R.string.error_field_required));
//            focusView = et_company;
//            cancel = true;
//        }
//
//        // Check for a valid distributor.
//        if (isDistributorValid(distributor)) {
//            et_distributor.setError(getString(R.string.error_field_required));
//            focusView = et_distributor;
//            cancel = true;
//        }
//
//        // Check for a valid id.
//        if (isOwnerValid(owner)) {
//            et_owner.setError(getString(R.string.error_field_required));
//            focusView = et_owner;
//            cancel = true;
//        }

        // Check for a valid id.
        if (isIdValid(id)) {
            et_id.setError(getString(R.string.error_field_required));
            focusView = et_id;
            cancel = true;
        }

        // Check for a valid re password.
        if (isPasswordConfirmEmpty(passwordConfirm)) {
            et_password_confirm.setError(getString(R.string.error_field_required));
            focusView = et_password_confirm;
            cancel = true;
        } else if (!isPasswordConfirmValid(password,passwordConfirm)) {
            et_password_confirm.setError(getString(R.string.error_invalid_password_confirm));
            focusView = et_password_confirm;
            cancel = true;
        }


//        // Check for a valid Re password, if the user entered one.
//        if (!TextUtils.isEmpty(passwordConfirm) && !isPasswordConfirmValid(password,passwordConfirm)) {
//            et_password_confirm.setError(getString(R.string.error_invalid_password_confirm));
//            focusView = et_password;
//            cancel = true;
//        }



        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            et_email.setError(getString(R.string.error_field_required));
            focusView = et_email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            et_email.setError(getString(R.string.error_invalid_email));
            focusView = et_email;
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
            registerApplicatorTask= new RegisterApplicatorTask();
            registerApplicatorTask.execute((Void) null);
        }
    }


    /**
     * dalam satu fungsi ngecek semua edit text
     * Attempts to sign in or register the account specified by the login next_menu.
     * If there are next_menu errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegisterRetailer() {
        if (registerApplicatorTask!= null) {
            return;
        }

        // Reset errors.
        et_email.setError(null);
        et_password.setError(null);
        et_password_confirm.setError(null);
        et_username.setError(null);

        et_name.setError(null);
//        et_company.setError(null);
        et_owner.setError(null);
//        et_distributor.setError(null); // perusahaan
        et_address.setError(null);
        act_city.setError(null);
        act_state.setError(null);
        et_phone.setError(null);
//        et_association.setError(null);
        et_id.setError(null);

        // Store values at the time of the login attempt.
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        String passwordConfirm = et_password_confirm.getText().toString();
        String username = et_username.getText().toString();
        String name = et_name.getText().toString();
//        String company = et_company.getText().toString();
        String owner = et_owner.getText().toString();
//        String distributor = et_distributor.getText().toString(); // perusahaan
        String address = et_address.getText().toString();
        String city = act_city.getText().toString();
        String state = act_state.getText().toString();
        String phone = et_phone.getText().toString();
//        String association = et_association.getText().toString();
        String id = et_id.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (isPasswordEmpty(password)) {
            et_password.setError(getString(R.string.error_field_required));
            focusView = et_password;
            cancel = true;
        }else if (!isPasswordValid(password)) {
            et_password.setError(getString(R.string.error_invalid_password));
            focusView = et_password;
            cancel = true;
        }

        // Check for a valid username.  ============================================================ kedepannya if username sudah pernah di pakai maka tampilakan sudah terpakai
        if (isUsernameEmpty(username)) {
            et_username.setError(getString(R.string.error_field_required));
            focusView = et_username;
            cancel = true;
        }else if (!isUsernameValid(username)) {
            et_username.setError(getString(R.string.error_field_required));
            focusView = et_username;
            cancel = true;
        }

        // Check for a valid name.
        if (isNameValid(name)) {
            et_name.setError(getString(R.string.error_field_required));
            focusView = et_name;
            cancel = true;
        }

        // Check for a valid city.
        if (isCityValid(city)) {
            act_city.setError(getString(R.string.error_field_required));
            focusView = act_city;
            cancel = true;
        }

        // Check for a valid state.
        if (isStateValid(state)) {
            act_state.setError(getString(R.string.error_field_required));
            focusView = act_state;
            cancel = true;
        }

        // Check for a valid phone.
        if (isPhoneValid(phone)) {
            et_phone.setError(getString(R.string.error_field_required));
            focusView = et_phone;
            cancel = true;
        }

        // Check for a Address.
        if (isAddressEmpty(address)) {
            et_address.setError(getString(R.string.error_field_required));
            focusView = et_address;
            cancel = true;
        }

        // Check for a valid distributor.
//        if (isDistributorValid(distributor)) {
//            et_distributor.setError(getString(R.string.error_field_required));
//            focusView = et_distributor;
//            cancel = true;
//        }

        // Check for a valid id.
        if (isOwnerValid(owner)) {
            et_owner.setError(getString(R.string.error_field_required));
            focusView = et_owner;
            cancel = true;
        }

        // Check for a valid id.
        if (isIdValid(id)) {
            et_id.setError(getString(R.string.error_field_required));
            focusView = et_id;
            cancel = true;
        }

        // Check for a valid re password.
        if (isPasswordConfirmEmpty(passwordConfirm)) {
            et_password_confirm.setError(getString(R.string.error_field_required));
            focusView = et_password_confirm;
            cancel = true;
        } else if (!isPasswordConfirmValid(password,passwordConfirm)) {
            et_password_confirm.setError(getString(R.string.error_invalid_password_confirm));
            focusView = et_password_confirm;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            et_email.setError(getString(R.string.error_field_required));
            focusView = et_email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            et_email.setError(getString(R.string.error_invalid_email));
            focusView = et_email;
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
            registerApplicatorTask= new RegisterApplicatorTask();
            registerApplicatorTask.execute((Void) null);
        }
    }

    /**
     * dalam satu fungsi ngecek semua edit text
     * Attempts to sign in or register the account specified by the login next_menu.
     * If there are next_menu errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegisterIndividu() {
        if (registerApplicatorTask!= null) {
            return;
        }

        // Reset errors.
        et_email.setError(null);                //email
        et_password.setError(null);             //email
        et_password_confirm.setError(null);     //password_confirmation
        et_username.setError(null);             //username
        et_name.setError(null);                 //name
        et_address.setError(null);              //address
        act_city.setError(null);                //city_id
        act_state.setError(null);               //states_id
        et_phone.setError(null);                //hp_no
        et_id.setError(null);                   //id_no

        // Store values at the time of the login attempt.
        String email = et_email.getText().toString();                       //email
        String password = et_password.getText().toString();                 //email
        String passwordConfirm = et_password_confirm.getText().toString();  //password_confirmation
        String username = et_username.getText().toString();                 //username
        String name = et_name.getText().toString();                         //name
        String address = et_address.getText().toString();                   //address
        String city = act_city.getText().toString();                        //city_id
        String state = act_state.getText().toString();                      //states_id
        String phone = et_phone.getText().toString();                       //hp_no
        String id = et_id.getText().toString();                             //id_no


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (isPasswordEmpty(password)) {
            et_password.setError(getString(R.string.error_field_required));
            focusView = et_password;
            cancel = true;
        }else if (!isPasswordValid(password)) {
            et_password.setError(getString(R.string.error_invalid_password));
            focusView = et_password;
            cancel = true;
        }

        // Check for a valid username.  ============================================================ kedepannya if username sudah pernah di pakai maka tampilakan sudah terpakai
        if (isUsernameEmpty(username)) {
            et_username.setError(getString(R.string.error_field_required));
            focusView = et_username;
            cancel = true;
        }else if (!isUsernameValid(username)) {
            et_username.setError(getString(R.string.error_field_required));
            focusView = et_username;
            cancel = true;
        }

        // Check for a valid name.
        if (isNameValid(name)) {
            et_name.setError(getString(R.string.error_field_required));
            focusView = et_name;
            cancel = true;
        }

        // Check for a valid address.
        if (isAddressEmpty(address)) {
            et_address.setError(getString(R.string.error_field_required));
            focusView = et_address;
            cancel = true;
        }

        // Check for a valid city.
        if (isCityValid(city)) {
            act_city.setError(getString(R.string.error_field_required));
            focusView = act_city;
            cancel = true;
        }

        // Check for a valid state.
        if (isStateValid(state)) {
            act_state.setError(getString(R.string.error_field_required));
            focusView = act_state;
            cancel = true;
        }

        // Check for a valid phone.
        if (isPhoneValid(phone)) {
            et_phone.setError(getString(R.string.error_field_required));
            focusView = et_phone;
            cancel = true;
        }


        // Check for a valid id.
        if (isIdValid(id)) {
            et_id.setError(getString(R.string.error_field_required));
            focusView = et_id;
            cancel = true;
        }

        // Check for a valid re password.
        if (isPasswordConfirmEmpty(passwordConfirm)) {
            et_password_confirm.setError(getString(R.string.error_field_required));
            focusView = et_password_confirm;
            cancel = true;
        } else if (!isPasswordConfirmValid(password,passwordConfirm)) {
            et_password_confirm.setError(getString(R.string.error_invalid_password_confirm));
            focusView = et_password_confirm;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            et_email.setError(getString(R.string.error_field_required));
            focusView = et_email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            et_email.setError(getString(R.string.error_invalid_email));
            focusView = et_email;
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
            registerApplicatorTask= new RegisterApplicatorTask();
            registerApplicatorTask.execute((Void) null);
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


    public void onClickRegistrationForm(View v){
        Intent i;
        switch (v.getId()){
            case R.id.btnSubmitRegistrationForm:
                if (pil.equals("tukang bangunan")){
                    attemptRegister();
                }else if (pil.equals("retailer")){
                    attemptRegisterRetailer();
                }

                break;
            case R.id.btnSubmitRegistrationFormIndividu:
                attemptRegisterIndividu();
                break;
        }
    }


    //function to set layout(produk, brosur, souvenir)
    public void setLayout(){
        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");
        retailer_type = extras.getString("retailer_type");
        switch (pil) {
            case "tukang bangunan":
                setContentView(R.layout.activity_registration_form_tukang_bangunan);
                OnCreateRegistrasiApplicator();
                break;
            case "retailer":
                setContentView(R.layout.activity_registration_form_retailer_tradisional);
                OnCreateRegistrasiRetailer();
                break;
            case "individu":
                setContentView(R.layout.activity_registration_form_individu);
                OnCreateRegistrasiIndividu();
                break;
        }
    }



    /** Radio Button */
    public void onRBSignupClicked(View v){
        // Is the button now checked?
        boolean checked = ((RadioButton) v).isChecked();

        // Check which radio button was clicked
        switch(v.getId()) {
            case R.id.rbKTP:
                if (checked)
                    gender="KTP";
                break;
            case R.id.rbSIM:
                if (checked)
                    gender="SIM";
                break;
            default:
                gender="";
                break;
        }

    }



    /** set date :D */
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
//        Toast.makeText(getApplicationContext(), "ca",
//                Toast.LENGTH_SHORT)
//                .show();
    }

    private void showDate(int year, int month, int day) {
        txtisianBirth.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
        txtisianBirthView.setText(new StringBuilder().append(day).append("-")
                .append(month).append("-").append(year));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UpdateTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String statusError = "Koneksi Error";
        private ProgressDialog pg;
        private JSONArray stateJson;
        private JSONArray cityJson;

        UpdateTask() {
            apiWeb = new ApiWeb();
            pg = new ProgressDialog(context);
            pg.setTitle("Ambil Data");
            pg.setMessage("Ambil Data");
            pg.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = apiWeb.GetStates();
            if(result==null){
                return false;
            }
            try {
                JSONObject json = new JSONObject(result);
                String status = json.getString("status");
                if(status.compareToIgnoreCase("success")==0){
                    stateJson = json.getJSONArray("data");
                    return true;

                }
//                if(json.has("message"))errorMessage = json.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            pg.dismiss();
            parsingState(stateJson);
//            parsingCity(cityJson);
//            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    public class UpdateCityTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String statusError = "Koneksi Error";
        private ProgressDialog pg;
        //        private JSONArray stateJson;
        private JSONArray cityJson;
        private String stateId;

        UpdateCityTask(String state_id) {
            apiWeb = new ApiWeb();
            pg = new ProgressDialog(context);
            pg.setTitle("Ambil Data");
            pg.setMessage("Ambil Data");
            pg.show();
            stateId=state_id;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = apiWeb.GetCities(stateId);
            if(result==null){
                return false;
            }
            try {
                JSONObject json = new JSONObject(result);
                String status = json.getString("status");
                if(status.compareToIgnoreCase("success")==0){
                    cityJson = json.getJSONArray("data");
                    return true;

//
//                    result = apiWeb.GetCities();
//                    if(result==null){
//                        return false;
//                    }
//                    json = new JSONObject(result);
//                    status = json.getString("status");
//                    if(status.compareToIgnoreCase("success")==0){
//                        cityJson = json.getJSONArray("data");
//                        return true;
//                    }
                }
                if(json.has("message"))errorMessage = json.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            pg.dismiss();
//            parsingState(stateJson);
            parsingCity(cityJson);
//            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }


    private boolean parsingState(JSONArray data){
        try {
            statesName = new String[data.length()];
            statesIds = new long[data.length()];
            for(int i=0;i<data.length();i++){
                JSONObject jason = data.getJSONObject(i);
                statesIds[i] = jason.getLong("id");
                statesName[i] = jason.getString("name");
            }
            adapter_state = new AutoCompleteAdapter(context, android.R.layout.simple_list_item_1, statesName, statesIds);
            act_state.setAdapter(adapter_state);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean parsingCity(JSONArray data){
        try {
            citiesName = new String[data.length()];
            citiesIds = new long[data.length()];
            for(int i=0;i<data.length();i++){
                JSONObject jason = data.getJSONObject(i);
                citiesIds[i] = jason.getLong("id");
                citiesName[i] = jason.getString("name");
            }
            adapter_city = new AutoCompleteAdapter(context, android.R.layout.simple_list_item_1, citiesName, citiesIds);
            act_city.setAdapter(adapter_city);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class RegisterApplicatorTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String statusError = "Koneksi Error";
        private String retailerType, username, password, password_comfirmation, email, name, address, hp_no, birth_date,distributor_name, owner_name, id_no, id_no_type, company_name, association_name;

        RegisterApplicatorTask() {
            apiWeb = new ApiWeb();
            username = et_username.getText().toString();
            password = et_password.getText().toString();
            password_comfirmation = et_password_confirm.getText().toString();
            email = et_email.getText().toString();
            name = et_name.getText().toString();
            address = et_address.getText().toString();
            hp_no = et_phone.getText().toString();
            birth_date = txtisianBirth.getText().toString();
            id_no = et_id.getText().toString();
            id_no_type = gender;
            if (pil.compareToIgnoreCase("individu")==0){

            }else if(pil.compareToIgnoreCase("tukang bangunan")==0){
                company_name = et_company.getText().toString();
                association_name = et_association.getText().toString();
            } else {
                distributor_name = et_distributor.getText().toString();
                owner_name = et_owner.getText().toString();
                retailerType = retailer_type;
            }


        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = null;
            if(pil.compareToIgnoreCase("tukang bangunan")==0){
                result = apiWeb.RegisterApplicator("applicator", username, password, password_comfirmation, email, name, address, ""+states_id, ""+city_id, hp_no, company_name, birth_date, association_name, id_no, id_no_type);
            } else if (pil.compareToIgnoreCase("retailer")==0) {
                result = apiWeb.RegisterRetailer(retailerType,"retailer",username, password, password_comfirmation,email, name, address, ""+states_id, ""+city_id, hp_no, birth_date, distributor_name, owner_name, id_no, id_no_type);
                // retailer type ada tradisional dll ada 3
            }else if (pil.compareToIgnoreCase("individu")==0) {//user_type, username, password, password_confirmation, email, name, address, states_id, city_id, hp_no, birth_date, id_no, id_no_type
                result = apiWeb.RegisterIndividu("individu",username, password, password_comfirmation, email, name, address, ""+states_id, ""+city_id, hp_no, birth_date, id_no, id_no_type);
                // retailer type ada tradisional dll ada 3
            }

            if(result==null){
                return false;
            }
            try {
                JSONObject json = new JSONObject(result);
                String status = json.getString("status");
                if(status.compareToIgnoreCase("success")==0){

                    return true;
                }
//                else if (status.compareToIgnoreCase("fail") == 0) {
//
//                    statusError = json.getString("message");
//
//                }
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

                popupSuccess(email,hp_no);
                showProgress(false);
//                Intent i;
//                i = new Intent(context, VerificationPageActivity.class);
//                i.putExtra("email", email);
//                i.putExtra("hp_no", hp_no);
//                startActivity(i);

            } else {

                popupAllert(errorMessage);
                showProgress(false);
//                editTextLoginEmail.setError(errorMessage);
//                editTextLoginEmail.requestFocus();

            }
        }

    }
}
