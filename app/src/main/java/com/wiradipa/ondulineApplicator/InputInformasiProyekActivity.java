package com.wiradipa.ondulineApplicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import com.wiradipa.ondulineApplicator.lib.ApiWeb;
import com.wiradipa.ondulineApplicator.lib.AppSession;
import com.wiradipa.ondulineApplicator.lib.AutoCompleteAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class InputInformasiProyekActivity extends AppCompatActivity {

    private Context context;
    private AppSession session;
    private EditText et_projectName,et_projectAddress,et_roofWidth;
    private Spinner spn_rooftype, spn_product_demand_brand;
    private HashMap<Integer,Long> spinnerMapProduct;
    private ArrayAdapter<String> adapter_product_demand_brand;
    private String[] listRoofType;
    private ArrayAdapter Adapter;
    private AutoCompleteTextView act_City, act_State;
    private AutoCompleteAdapter adapter_state = null;
    private AutoCompleteAdapter adapter_city= null;
    private String[] statesName, citiesName;
    private long[] statesIds, citiesIds;
    private long state_id, city_id;
    private boolean bool_state = false;
    private boolean bool_city = false;
    private UpdateStateTask updateStateTask;
    private UpdateCityTask updateCityTask;
    private UpdateGetProductTask updateGetProductTask;
    private View mProgressView;
    private View mFormView;

    private InputInformasiProyekTask inputInformasiProyekTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_informasi_proyek);

        context = this;
        session = new AppSession(context);
        session.checkSession();

        act_City        = (AutoCompleteTextView) findViewById(R.id.act_city);
        act_State       = (AutoCompleteTextView) findViewById(R.id.act_state);
        spn_product_demand_brand = (Spinner)findViewById(R.id.spn_product_demand_brand);
        et_projectName  = (EditText) findViewById(R.id.et_projectName);
        et_projectAddress  = (EditText) findViewById(R.id.et_projectAddress);
        et_roofWidth  = (EditText) findViewById(R.id.et_roofWidth);



        mFormView = findViewById(R.id.submit_form);
        mProgressView = findViewById(R.id.submit_progress);

        updateStateTask = new UpdateStateTask();
        updateStateTask.execute((Void)null);

        updateGetProductTask = new UpdateGetProductTask();
        updateGetProductTask.execute((Void)null);

        act_State.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//              kondisi dimana state telah dipilih
                bool_state = true;
                state_id = adapter_state.getItemId(act_State.getText().toString());
                updateCityTask = new UpdateCityTask(state_id+"");
                updateCityTask.execute((Void)null);
            }
        });
        act_City.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//              kondisi dimana city telah dipilih
                bool_city= true;
                city_id = adapter_city.getItemId(act_City.getText().toString());
            }
        });

    }

    public void onClickProjectInformation(View v){
        switch (v.getId()){
            case R.id.btnSubmitProjectInformation:
                attemptInputInformasiProyek();
                break;
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
            act_State.setAdapter(adapter_state);
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
            act_City.setAdapter(adapter_city);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // kebutuhan untuk get data produk

    private boolean parsingProduct(JSONArray data){
        try {

            String[] spinnerArray = new String[data.length()];
            spinnerMapProduct = new HashMap<Integer, Long>();
//            productName = new String[data.length()];
//            productIds = new long[data.length()];
            for(int i=0;i<data.length();i++){
                JSONObject jason = data.getJSONObject(i);
                spinnerMapProduct.put(i,jason.getLong("id"));
                spinnerArray[i] = jason.getString("name");
            }
            // Create an ArrayAdapter using the string array and a default spinner layout
            adapter_product_demand_brand = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, spinnerArray);
            // Specify the layout to use when the list of choices appears
            adapter_product_demand_brand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spn_product_demand_brand.setAdapter(adapter_product_demand_brand);

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
    public class UpdateStateTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private ProgressDialog pg;
        private JSONArray stateJson;
        private JSONArray cityJson;

        UpdateStateTask() {
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


    private boolean isAddressEmpty(String address) {
        //TODO: Replace this with your own logic
        return address.equals("");
    }
    private boolean isRoofWidthEmpty(String address) {
        //TODO: Replace this with your own logic
        return address.equals("");
    }
    private boolean isProjectNameEmpty(String address) {
        //TODO: Replace this with your own logic
        return address.equals("");
    }
    private boolean isCityValid(String city) {
        //TODO: Replace this with your own logic
        return city.equals("");
    }

    private boolean isStateValid(String state) {
        //TODO: Replace this with your own logic
        return state.equals("");
    }

    /**
     * dalam satu fungsi ngecek semua edit text
     * Attempts to sign in or register the account specified by the login next_menu.
     * If there are next_menu errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptInputInformasiProyek() {
        if (inputInformasiProyekTask!= null) {
            return;
        }

        // Reset errors.
        act_City.setError(null);
        act_State.setError(null);
        et_projectName.setError(null);
        et_projectAddress.setError(null);
        et_roofWidth.setError(null);

        // Store values at the time of the login attempt.
        String city = act_City.getText().toString();
        String state = act_State.getText().toString();
        String projectName = et_projectName.getText().toString();
        String projectAddress = et_projectAddress.getText().toString();
        String roofWidth = et_roofWidth.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // cek addres bila kosong
        if (isAddressEmpty(projectAddress)) {
            et_projectAddress.setError(getString(R.string.error_field_required));
            focusView = et_projectAddress;
            cancel = true;
        }
        // cek projectName bila kosong
        if (isProjectNameEmpty(projectName)) {
            et_projectName.setError(getString(R.string.error_field_required));
            focusView = et_projectName;
            cancel = true;
        }
        // cek roofWidth bila kosong
        if (isRoofWidthEmpty(roofWidth)) {
            et_roofWidth.setError(getString(R.string.error_field_required));
            focusView = et_roofWidth;
            cancel = true;
        }


        // Check for a valid city store.
        if (!bool_city) {
            act_City.setError(getString(R.string.error_field_city_required));
            focusView = act_City;
            cancel = true;
        }
        // Check for a valid state store.
        if (!bool_state) {
            act_State.setError(getString(R.string.error_field_state_required));
            focusView = act_State;
            cancel = true;
        }

        // Check for a valid city.
        if (isCityValid(city)) {
            act_City.setError(getString(R.string.error_field_required));
            focusView = act_City;
            cancel = true;
        }

        // Check for a valid state.
        if (isStateValid(state)) {
            act_State.setError(getString(R.string.error_field_required));
            focusView = act_State;
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

            inputInformasiProyekTask= new InputInformasiProyekTask();
            inputInformasiProyekTask.execute((Void) null);
        }
    }

    /**
     * get brand dari database
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UpdateGetProductTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private ProgressDialog pg;
        private JSONArray ProductsJson;

        UpdateGetProductTask() {
            apiWeb = new ApiWeb();
            pg = new ProgressDialog(context);
            pg.setTitle("Ambil Data");
            pg.setMessage("Ambil Data");
            pg.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = apiWeb.Getproducts();
            if(result==null){
                return false;
            }
            try {
                JSONObject json = new JSONObject(result);
                String status = json.getString("status");
                if(status.compareToIgnoreCase("success")==0){
                    ProductsJson = json.getJSONArray("data");
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
            parsingProduct(ProductsJson);
//            parsingProducts(stateJson);
//            parsingCity(cityJson);
//            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class InputInformasiProyekTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String statusError = "Koneksi Error";
        private String token, project_name, roof_type, address,roof_width, productId;

        InputInformasiProyekTask() {
            apiWeb = new ApiWeb();
            token           = session.getToken();
            project_name    = et_projectName.getText().toString();
//            roof_type       = spn_rooftype.getSelectedItem().toString();
            address         = et_projectAddress.getText().toString();
            roof_width      = et_roofWidth.getText().toString();
            productId       = spinnerMapProduct.get(spn_product_demand_brand.getSelectedItemPosition()) + "";


        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            String result = null;

            result  = apiWeb.inputProjectInformation(token,project_name,productId,address,""+city_id,""+state_id,roof_width);

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
                inputInformasiProyekTask=null;
                bool_city = bool_state = false;
//                Intent i;
//                i = new Intent(context, VerificationPageActivity.class);
//                i.putExtra("email", email);
//                i.putExtra("hp_no", hp_no);
//                startActivity(i);

            } else {

                popupAllert(errorMessage);
                showProgress(false);
                inputInformasiProyekTask=null;
//                editTextLoginEmail.setError(errorMessage);
//                editTextLoginEmail.requestFocus();

            }
        }
    }
    public void popupSuccess(){
        new AlertDialog.Builder(this)
                .setTitle("Input Informasi Proyek Sukses!")
                .setMessage("Selamat Input Informasi Proyek  anda berhasil")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(context, InputInformasiProyekActivity.class);
//                        session.setEmailForm(email_regis);
//                        session.setHpNoForm(no_hp_regis);
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
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
//                        finish();
                    }
                }).create().show();

    }
}
