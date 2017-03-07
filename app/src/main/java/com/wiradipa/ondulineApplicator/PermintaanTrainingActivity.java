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
import android.widget.TextView;
import android.widget.Toast;

import com.wiradipa.ondulineApplicator.lib.ApiWeb;
import com.wiradipa.ondulineApplicator.lib.AppSession;
import com.wiradipa.ondulineApplicator.lib.AutoCompleteAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class PermintaanTrainingActivity extends AppCompatActivity {


    private Calendar calendar;
    private int year, month, day;

    private TextView txtisianTraining, txtisianTrainingView;
    private EditText et_phone, et_totalPeserta, et_address, et_keterangan;
    private AutoCompleteTextView act_city, act_state;

    private AutoCompleteAdapter adapter_state, adapter_city;

    private String[] statesName, citiesName;
    private long[] statesIds, citiesIds;
    long states_id, city_id;

    private UpdateTask updateTask;
    private UpdateCityTask updateCityTask;
    private TrainingRequestTask trainingRequestTask;


    private View mProgressView;
    private View mFormView;

    private Context context;
    private AppSession session;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permintaan_training);
        context = this;
        session = new AppSession(context);
        session.checkSession();
        token=session.getToken();

        et_address = (EditText)findViewById(R.id.et_address);//         wajib diisi
        et_phone = (EditText)findViewById(R.id.et_phone);//             harus diisi
        et_totalPeserta = (EditText)findViewById(R.id.et_totalPeserta);//             harus diisi
        et_keterangan = (EditText)findViewById(R.id.et_keterangan);//             harus diisi

        act_city = (AutoCompleteTextView)findViewById(R.id.act_city);//             wajib diisi
        act_state = (AutoCompleteTextView)findViewById(R.id.act_state);//           wajib diisi

        txtisianTraining=(TextView)findViewById(R.id.txtisianTraining);//                 wajib diisi
        txtisianTrainingView=(TextView)findViewById(R.id.txtisianTrainingView);//                 wajib diisi
        calendar = Calendar.getInstance();

        mFormView = findViewById(R.id.training_form);
        mProgressView = findViewById(R.id.training_progress);


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


    private boolean isPhoneEmpty(String phone) {
        //TODO: Replace this with your own logic
        return phone.equals("");
    }
    private boolean isTotalPesertaEmpty(String students) {
        //TODO: Replace this with your own logic
        return students.equals("");
    }
    private boolean isStateEmpty(String state) {
        //TODO: Replace this with your own logic
        return state.equals("");
    }
    private boolean isCityEmpty(String city) {
        //TODO: Replace this with your own logic
        return city.equals("");
    }
    private boolean isAddressEmpty(String address) {
        //TODO: Replace this with your own logic
        return address.equals("");
    }
    private boolean isKeteranganEmpty(String keterangan) {
        //TODO: Replace this with your own logic
        return keterangan.equals("");
    }

    public void onClickTraining(View view){
        switch (view.getId()) {
            case R.id.btn_submit:
                attemptTraining();
                break;
        }
    }


    /**
     * dalam satu fungsi ngecek semua edit text
     * Attempts to sign in or register the account specified by the login next_menu.
     * If there are next_menu errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptTraining() {
        if (trainingRequestTask!= null) {
            return;
        }

        // Reset errors.
        et_phone.setError(null);
        et_totalPeserta.setError(null);
        act_state.setError(null);
        act_city.setError(null);
        et_address.setError(null);
        et_keterangan.setError(null);

        String phone = et_phone.getText().toString();
        String totalPeserta = et_totalPeserta.getText().toString();
        String state = act_state.getText().toString();
        String city = act_city.getText().toString();
        String address = et_address.getText().toString();
        String keterangan = et_keterangan.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a not empty phone.
        if (isPhoneEmpty(phone)) {
            et_phone.setError(getString(R.string.error_field_required));
            focusView = et_phone;
            cancel = true;
        }
        // Check for a not empty total peserta.
        if (isTotalPesertaEmpty(totalPeserta)) {
            et_totalPeserta.setError(getString(R.string.error_field_required));
            focusView = et_totalPeserta;
            cancel = true;
        }
        // Check for a valid city.
        if (isCityEmpty(city)) {
            act_city.setError(getString(R.string.error_field_required));
            focusView = act_city;
            cancel = true;
        }

        // Check for a valid state.
        if (isStateEmpty(state)) {
            act_state.setError(getString(R.string.error_field_required));
            focusView = act_state;
            cancel = true;
        }

        // Check for a valid city.
        if (isCityEmpty(city)) {
            act_city.setError(getString(R.string.error_field_required));
            focusView = act_city;
            cancel = true;
        }
        // Check for a valid address.
        if (isAddressEmpty(address)) {
            et_address.setError(getString(R.string.error_field_required));
            focusView = et_address;
            cancel = true;
        }
//        // Check for a valid keterangan.
//        if (isKeteranganEmpty(keterangan)) {
//            et_keterangan.setError(getString(R.string.error_field_required));
//            focusView = et_keterangan;
//            cancel = true;
//        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // next_menu field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
//            disini kita show progreess apapun itu dan di setiap

            trainingRequestTask= new TrainingRequestTask();
            trainingRequestTask.execute((Void) null);

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

                        Intent intent = new Intent(context, TechnicalSupportActivity.class);
//                        intent.putExtra("pil",pil);
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




    /** set date :D */
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
//        Toast.makeText(getApplicationContext(), "ca",
//                Toast.LENGTH_SHORT)
//                .show();
    }

    private void showDate(int year, int month, int day) {
        txtisianTraining.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
        txtisianTrainingView.setText(new StringBuilder().append(day).append("-")
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


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class TrainingRequestTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String phone, totalPeserta, state, city, address, keterangan, date;

        TrainingRequestTask() {
            apiWeb = new ApiWeb();

            phone = et_phone.getText().toString();
            totalPeserta = et_totalPeserta.getText().toString();
            state = act_state.getText().toString();
            city = act_city.getText().toString();
            address = et_address.getText().toString();
            keterangan = et_keterangan.getText().toString();
            date = txtisianTraining.getText().toString();


        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = null;
            result = apiWeb.TechnicalSupportTrainingRequest(token,phone,date,totalPeserta,state,city,address,keterangan);

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
            trainingRequestTask = null;
            showProgress(false);
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
