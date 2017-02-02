package com.wiradipa.ondulineApplicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wiradipa.ondulineApplicator.lib.ApiWeb;
import com.wiradipa.ondulineApplicator.lib.AppSession;
import com.wiradipa.ondulineApplicator.lib.AutoCompleteAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddNewActivity extends AppCompatActivity {

/** fokus sama order aplikator dulu aja */

    private Context context;
    private AppSession session;
    private SubmitOrderApplicatorTask  mAuthOrderApplicatorTask = null;

    private ImageView img_AddNew;
    private TextView txt_NameUser, txt_DatePicker;
    private Button btn_DatePicker, btn_Submit;
    private EditText et_AddressStore;
    private Spinner spn_ProductType;
    private AutoCompleteTextView act_City, act_State;
    private LinearLayout btn_ImageAddNew;

    private AutoCompleteAdapter adapter_state, adapter_city;
    private String[] statesName, citiesName;
    private long[] statesIds, citiesIds;
    private long states_id, city_id;
    private UpdateTask updateTask;
    private View mProgressView;
    private View mFormView;
    String mCurrentPhotoPath;

    private Calendar calendar;
    private int year, month, day;


    String pil, nameUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        session = new AppSession(context);
        session.checkSession();
        nameUser=session.getName();

        setLayout();

    }



    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.wiradipa.ondulineApplicator.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }

    }

//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.wiradipa.ondulineApplicator.fileprovider",
//                        photoFile);
//                List<ResolveInfo> resolvedIntentActivities = getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
//
//                for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
//                    String packageName = resolvedIntentInfo.activityInfo.packageName;
//
//                    grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                }
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//            }
//
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//
//        }
//    }
//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
            Bitmap imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            img_AddNew.setImageBitmap(imageBitmap);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }



    private boolean isImageValid(String image) {
        //TODO: Replace this with your own logic
        return image.equals("");
        // ---------------------------- sementara kosong dulu ---------- bsok harus kelar dan harus ada, penting!
    }

    private boolean isProductTypeValid(String product) {
        //TODO: Replace this with your own logic
        return product.equals("");
    }

    //kyknya ini ga penting dah.. hehee
    private boolean isDatePickerValid(String DatePicker) {
        //TODO: Replace this with your own logic
        return DatePicker.equals("");
    }

    private boolean isAddressStoreValid(String AddressStore) {
        //TODO: Replace this with your own logic
        return AddressStore.equals("");
    }

    private boolean isCityValid(String City) {
        //TODO: Replace this with your own logic
        return City.equals("");
    }

    private boolean isStateValid(String State) {
        //TODO: Replace this with your own logic
        return State.equals("");
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
     * Attempts to sign in or register the account specified by the login next_menu.
     * If there are next_menu errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSubmitOrderApplicator() {
        if (mAuthOrderApplicatorTask != null) {
            return;
        }

        // Reset errors.
        et_AddressStore.setError(null);
        act_City.setError(null);
        act_State.setError(null);

        // Store values at the time of the submit attempt.
        String addressStore = et_AddressStore.getText().toString();
        String city = act_City.getText().toString();
        String state = act_State.getText().toString();

        boolean cancel = false;
        View focusView = null;
//
        // Check for a valid address store.
        if (isAddressStoreValid(addressStore)) {
            et_AddressStore.setError(getString(R.string.error_field_required));
            focusView = et_AddressStore;
            cancel = true;
        }
        // Check for a valid address store.
        if (isCityValid(city)) {
            act_City.setError(getString(R.string.error_field_required));
            focusView = act_City;
            cancel = true;
        }
        // Check for a valid address store.
        if (isStateValid(state)) {
            act_State.setError(getString(R.string.error_field_required));
            focusView = act_State;
            cancel = true;
        }

//        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            editTextLoginEmail.setError(getString(R.string.error_invalid_password));
//            focusView = editTextLoginPassword;
//            cancel = true;
//        }
//
//        // Check for a valid email address.
//        if (TextUtils.isEmpty(email)) {
//            editTextLoginEmail.setError(getString(R.string.error_field_required));
//            focusView = editTextLoginEmail;
//            cancel = true;
//        } else if (!isEmailValid(email)) {
//            editTextLoginEmail.setError(getString(R.string.error_invalid_email));
//            focusView = editTextLoginEmail;
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

            //Toast.makeText(this, "password : " + password , Toast.LENGTH_LONG).show();
            mAuthOrderApplicatorTask  = new SubmitOrderApplicatorTask();
            mAuthOrderApplicatorTask .execute((Void) null);
        }
    }





    //function to set layout(produk, brosur, souvenir)
    public void setLayout(){
        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");
        switch (pil) {
            case "order retailer":
                setContentView(R.layout.activity_add_new_order_retailer);
                break;
            case "order applicator":
                setContentView(R.layout.activity_add_new_order_applicator);
                OnCreateorderApplicator();
                break;
            case "project applicator":
                setContentView(R.layout.activity_add_new_project_applicator);
                break;
        }
    }

    public void OnCreateorderApplicator(){

        img_AddNew      = (ImageView)findViewById(R.id.img_AddNew);
        txt_NameUser    = (TextView)findViewById(R.id.txt_NameUser);
        txt_DatePicker  = (TextView)findViewById(R.id.txt_DatePicker);
        btn_DatePicker  = (Button) findViewById(R.id.btn_DatePicker);
        btn_Submit      = (Button) findViewById(R.id.btn_submit);
        et_AddressStore = (EditText) findViewById(R.id.et_AddressStore);
        spn_ProductType = (Spinner) findViewById(R.id.spn_ProductType);// onduline, onduvilla, bituline, bardoline
        act_City        = (AutoCompleteTextView) findViewById(R.id.act_city);
        act_State       = (AutoCompleteTextView) findViewById(R.id.act_state);
        btn_ImageAddNew = (LinearLayout)findViewById(R.id.btn_ImageAddNew);

        calendar = Calendar.getInstance();

        mFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);


        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //submit order aplikator
                attemptSubmitOrderApplicator();
            }
        });
        btn_ImageAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        updateTask = new UpdateTask();
        updateTask.execute((Void)null);
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
        txt_DatePicker.setText(new StringBuilder().append(year).append("-")
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
                        return true;
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
            parsingState(stateJson);
            parsingCity(cityJson);
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }


    public class SubmitOrderApplicatorTask extends AsyncTask<Void, Void, Boolean> {

//        private final String username;
//        private final String mPassword;
        private String name;
        private String email;
        private String usertype;
        private ApiWeb apiWeb;
        private String errorMessage = getString(R.string.error_incorrect_password);
        private String token = "";
        private long userid;

        SubmitOrderApplicatorTask() {
//            username = email;
//            mPassword = password;
            apiWeb = new ApiWeb();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
//
////            String result = apiWeb.Login();
//            if(result==null){
//                errorMessage = getString(R.string.error_server);
//                return false;
//            }
//            try {
//                JSONObject json = new JSONObject(result);
//                String status = json.getString("status");
//                if(status.compareToIgnoreCase("success")==0){
//                    userid = json.getLong("user_id");
//                    token = json.getString("token");
//                    name = json.getString("name");
//                    email = json.getString("email");
//                    usertype = json.getString("user_type");
//                    return true;
//                }
//                if(json.has("message"))errorMessage = json.getString("message");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthOrderApplicatorTask = null;
            showProgress(false);

            if (success) {
//                session.login(userid, username, usertype, token, name, email);

//                if success then popup and go to historu order

//                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
//                startActivity(i);
                finish();
            } else {

//                error enaknya di popup

//                editTextLoginEmail.setError(errorMessage);
//                editTextLoginEmail.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthOrderApplicatorTask = null;
            showProgress(false);
        }
    }

}
