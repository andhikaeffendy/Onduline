package com.wiradipa.ondulineApplicator;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wiradipa.ondulineApplicator.lib.ApiWeb;
import com.wiradipa.ondulineApplicator.lib.AutoCompleteAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class RegistrationFromActivity extends AppCompatActivity {

    String pil, gender;

    long states_id, city_id;

    private Calendar calendar;
    private int year, month, day;

    private TextView txtisianBirth;

    private EditText et_name, et_address, et_phone, et_email, et_distributor, et_association, et_owner, et_id, et_username, et_password, et_password_confirm, et_company;
    private AutoCompleteTextView act_city, act_state;

    private Context context;

    private String[] statesName, citiesName;
    private long[] statesIds, citiesIds;

    private AutoCompleteAdapter adapter_state, adapter_city;

    private UpdateTask updateTask;
    private RegisterTask registerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();

        context = this;

        et_name = (EditText)findViewById(R.id.et_name);
        et_address = (EditText)findViewById(R.id.et_address);
        et_phone = (EditText)findViewById(R.id.et_phone);
        et_email = (EditText)findViewById(R.id.et_email);
        et_distributor = (EditText)findViewById(R.id.et_distributor);
        et_association = (EditText)findViewById(R.id.et_association);
        et_owner = (EditText)findViewById(R.id.et_owner);
        et_id = (EditText)findViewById(R.id.et_id);
        et_username = (EditText)findViewById(R.id.et_username);
        et_password = (EditText)findViewById(R.id.et_password);
        et_password_confirm = (EditText)findViewById(R.id.et_password_confirm);
        act_city = (AutoCompleteTextView)findViewById(R.id.act_city);
        act_state = (AutoCompleteTextView)findViewById(R.id.act_state);
        et_company = (EditText)findViewById(R.id.et_company);

        txtisianBirth=(TextView)findViewById(R.id.txtisianBirth);
        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        act_state.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                states_id = adapter_state.getItemId(act_state.getText().toString());
            }
        });

        updateTask = new UpdateTask();
        updateTask.execute((Void)null);
        //setContentView(R.layout.activity_registration_form_tukang_bangunan);
    }

    public void onClickRegistrationForm(View v){
        Intent i;
        switch (v.getId()){
            case R.id.btnSubmitRegistrationForm:
                i = new Intent(this, VerificationPageActivity.class);
                startActivity(i);
                break;
        }
    }


    //function to set layout(produk, brosur, souvenir)
    public void setLayout(){
        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");
        switch (pil) {
            case "tukang bangunan":
                setContentView(R.layout.activity_registration_form_tukang_bangunan);
                break;
            case "retailer tradisional":
                setContentView(R.layout.activity_registration_form_retailer_tradisional);
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
                    gender="SIM";;
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
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    private void showDate(int year, int month, int day) {
        txtisianBirth.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
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
                    result = apiWeb.GetCities();
                    if(result==null){
                        return false;
                    }
                    json = new JSONObject(result);
                    status = json.getString("status");
                    if(status.compareToIgnoreCase("success")==0){
                        cityJson = json.getJSONArray("data");
                        if(parsingState(stateJson)&&parsingCity(cityJson)){

                        }
                    }
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
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
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
    public class RegisterTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String username, password, email, name, address, hp_no, birth_date,distribution_name, owner_name, id_no, id_no_type, company_name, association_name;

        RegisterTask() {
            apiWeb = new ApiWeb();
            username = et_username.getText().toString();
            password = et_password.getText().toString();
            email = et_email.getText().toString();
            name = et_name.getText().toString();
            address = et_address.getText().toString();
            hp_no = et_phone.getText().toString();
            birth_date = txtisianBirth.getText().toString();
            id_no = et_id.getText().toString();
            id_no_type = gender;
            if(pil.compareToIgnoreCase("tukang bangunan")==0){
                company_name = et_company.getText().toString();
                association_name = et_association.getText().toString();
            } else {
                distribution_name = et_distributor.getText().toString();
                owner_name = et_owner.getText().toString();
            }


        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = null;
            if(pil.compareToIgnoreCase("tukang bangunan")==0){
                result = apiWeb.RegisterApplicator("applicator", username, password, email, name, address, ""+states_id, ""+city_id, hp_no, company_name, birth_date, association_name, id_no, id_no_type);
            } else if (pil.compareToIgnoreCase("retailer tradisional")==0) {
                result = apiWeb.RegisterRetailer("retailer",username, password, email, name, address, ""+states_id, ""+city_id, hp_no, birth_date, distribution_name, owner_name, id_no, id_no_type);
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
            } else {

            }
        }

    }
}
