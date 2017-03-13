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
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.wiradipa.ondulineApplicator.lib.FixedHoloDatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AddNewProjectAndOrderActivity extends AppCompatActivity {

    /** fokus sama order aplikator dulu aja */

    private Context context;
    private AppSession session;
    private SubmitOrderApplicatorTask  mAuthOrderApplicatorTask = null;
    private SubmitNewProjectApplicatorTask mAuthNewProjectApplicatorTask=null;
    private Spinner spn_product_demand_color, spn_product_demand_brand, spn_ProjectType;
    private ArrayAdapter<String> adapter_product_demand_color, adapter_product_demand_brand, adapter_project_type;
    private HashMap<Integer,Long> spinnerMapProduct, spinnerMapColor, spinnerMapProjectType;
    private UpdateGetProductTask updateGetProductTask;
    private UpdateGetProductColorTask updateGetProductColorTask;
    private UpdateProjectTypeTask updateProjectTypeTask;

    private ImageView img_AddNew;

    private TextView txt_NameUser, txt_DatePicker,txt_DatePickerView;
    private Button btn_DatePicker, btn_Submit;
    private EditText et_AddressStore, et_amount,et_distributor,et_ProjectAddress,et_SpaciousRoof,et_StoreName,et_address,et_onduvilla_amount,et_onduline_amount,et_onduclair_amount;
    private AutoCompleteTextView act_City, act_State;
    private LinearLayout btn_ImageAddNew;
    private String token, state_id;
    private Bitmap imageBitmapShare;

    private AutoCompleteAdapter adapter_state, adapter_city;
    private String[] statesName, citiesName;
    private long[] statesIds, citiesIds;
    private long states_id, city_id;
    private UpdateTask updateTask;
    private UpdateCityTask updateCityTask;
    private View mProgressView;
    private View mFormView;
    String mCurrentPhotoPath;

    private String[] souvenirName, productName,colorName;
    private long[] souvenirIds, productIds,colorIds ;

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

    public void popupSuccess(){
        new AlertDialog.Builder(this)
                .setTitle("Pengiriman Sukses!")
                .setMessage("Selamat pengiriman anda telah terkirim")
                .setPositiveButton("kembali", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        //MapsActivity.super.onBackPressed();
                        //finish();
                        // System.exit(0);

                        Intent intent = new Intent(context, AddNewProjectAndOrderActivity.class);
                        intent.putExtra("pil",pil);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***

                        startActivity(intent);
                        finish();
                    }
                }).create().show();

    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

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
                List<ResolveInfo> resolvedIntentActivities = getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
                    String packageName = resolvedIntentInfo.activityInfo.packageName;

                    grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            }

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();


//          ========================================================================================


		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
            int targetW = img_AddNew.getWidth();
            int targetH = img_AddNew.getHeight();

		/* Get the size of the image */
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
            int scaleFactor = 1;
            if ((targetW > 0) || (targetH > 0)) {
                scaleFactor = Math.min(photoW/targetW, photoH/targetH);
            }

		/* Set bitmap options to scale the image decode target */
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
//            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);



//            ======================================================================================
            Bitmap imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            imageBitmapShare = imageBitmap;
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



    private boolean isImageValid(String mCurrentPhotoPath) {
        //TODO: Replace this with your own logic
        return mCurrentPhotoPath.equals(null);
        // ---------------------------- sementara kosong dulu ---------- bsok harus kelar dan harus ada, penting!
    }

    private boolean isProjectAddressValid(String address) {
        //TODO: Replace this with your own logic
        return address.equals("");
    }
    private boolean isSpaciousRoofValid(String large) {
        //TODO: Replace this with your own logic
        return large.equals("");
    }
    private boolean isProductTypeValid(String product) {
        //TODO: Replace this with your own logic
        return product.equals("");
    }

    private boolean isAddressValid(String address) {
        //TODO: Replace this with your own logic
        return address.equals("");
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
    private boolean isStoreNameValid(String StoreName) {
        //TODO: Replace this with your own logic
        return StoreName.equals("");
    }
    private boolean isamountValid(String Amount) {
        //TODO: Replace this with your own logic
        return Amount.equals("");
    }
    private boolean isdistributorValid(String distributor) {
        //TODO: Replace this with your own logic
        return distributor.equals("");
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
        et_StoreName.setError(null);
//        et_amount.setError(null);
        act_City.setError(null);
        act_State.setError(null);
//        img_AddNew.setError

        // Store values at the time of the submit attempt.
        String addressStore = et_AddressStore.getText().toString();
        String storeName = et_StoreName.getText().toString();
//        String amount= et_amount.getText().toString();
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
        if (isStoreNameValid(storeName)) {
            et_StoreName.setError(getString(R.string.error_field_required));
            focusView = et_StoreName;
            cancel = true;
        }
//        // Check for a valid Amount.
//        if (isamountValid(amount)) {
//            et_amount.setError(getString(R.string.error_field_required));
//            focusView = et_amount;
//            cancel = true;
//        }
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
            mAuthOrderApplicatorTask.execute((Void) null);
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login next_menu.
     * If there are next_menu errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSubmitNewProjectApplicator() {
        if (mAuthOrderApplicatorTask != null) {
            return;
        }

        // Reset errors.
        et_ProjectAddress.setError(null);
        et_SpaciousRoof.setError(null);
        act_City.setError(null);
        act_State.setError(null);

        String city = act_City.getText().toString();
        String state = act_State.getText().toString();
        String ProjectAddress = et_ProjectAddress.getText().toString();
        String SpaciousRoof = et_SpaciousRoof.getText().toString();

        boolean cancel = false;
        View focusView = null;
//
        // Check for a valid address store.
        if (isSpaciousRoofValid(SpaciousRoof)) {
            et_SpaciousRoof.setError(getString(R.string.error_field_required));
            focusView = et_SpaciousRoof;
            cancel = true;
        }
        // Check for a valid address store.
        if (isProjectAddressValid(ProjectAddress)) {
            et_ProjectAddress.setError(getString(R.string.error_field_required));
            focusView = et_ProjectAddress;
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
            mAuthNewProjectApplicatorTask   = new SubmitNewProjectApplicatorTask();
            mAuthNewProjectApplicatorTask.execute((Void) null);
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login next_menu.
     * If there are next_menu errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSubmitOrderRetailer() {
        if (mAuthOrderApplicatorTask != null) {
            return;
        }

        // Reset errors.
        et_distributor.setError(null);
//        et_amount.setError(null);
        act_City.setError(null);
        act_State.setError(null);
//        et_address.setError(null);

        // Store values at the time of the submit attempt.
        String distributor= et_distributor.getText().toString();
//        String amount= et_amount.getText().toString();
        String city = act_City.getText().toString();
        String state = act_State.getText().toString();
//        String address = et_address.getText().toString();

        boolean cancel = false;
        View focusView = null;
//
        // Check for a valid address store.
        if (isdistributorValid(distributor)) {
            et_distributor.setError(getString(R.string.error_field_required));
            focusView = et_distributor;
            cancel = true;
        }
        // Check for a valid Amount.
//        if (isamountValid(amount)) {
//            et_amount.setError(getString(R.string.error_field_required));
//            focusView = et_amount;
//            cancel = true;
//        }
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
        // Check for a valid address store.
//        if (isAddressValid(address)) {
//            et_address.setError(getString(R.string.error_field_required));
//            focusView = et_address;
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

    public void popupAllert(String allert){
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(allert)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

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



    //function to set layout(produk, brosur, souvenir)
    public void setLayout(){
        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");
        switch (pil) {
            case "order retailer":
                setContentView(R.layout.activity_add_new_order_retailer);
                OnCreateorderRetailer();
                break;
            case "order applicator":
                setContentView(R.layout.activity_add_new_order_applicator);
                OnCreateorderApplicator();
                break;
            case "project applicator":
                setContentView(R.layout.activity_add_new_project_applicator);
                OnCreateNewProjectApplicator();
                break;
        }
    }

    public void OnCreateNewProjectApplicator(){

        img_AddNew      = (ImageView)findViewById(R.id.img_AddNew);
        txt_NameUser    = (TextView)findViewById(R.id.txt_NameUser);
        txt_DatePicker=(TextView)findViewById(R.id.txtisianBirth);//                 wajib diisi
        txt_DatePickerView=(TextView)findViewById(R.id.txtisianBirthView);//                 wajib diisi
        btn_Submit      = (Button) findViewById(R.id.btn_submit);
        et_ProjectAddress = (EditText) findViewById(R.id.et_ProjectAddress);
        et_SpaciousRoof = (EditText) findViewById(R.id.et_SpaciousRoof);
        spn_ProjectType = (Spinner) findViewById(R.id.spn_ProjectType);// onduline, onduvilla, bituline, bardoline
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


//        ------------------------------------------------------------------------------------------

//        Spinner spn_product_demand_color, spn_product_demand_brand;
        spn_product_demand_color = (Spinner)findViewById(R.id.spn_product_demand_color);
        spn_product_demand_brand = (Spinner)findViewById(R.id.spn_product_demand_brand);


        updateProjectTypeTask = new UpdateProjectTypeTask();
        updateProjectTypeTask.execute((Void)null);
        updateGetProductTask = new UpdateGetProductTask();
        updateGetProductTask.execute((Void)null);


        spn_product_demand_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Long product_id = spinnerMapProduct.get(spn_product_demand_brand.getSelectedItemPosition());
                updateGetProductColorTask = new UpdateGetProductColorTask(product_id+"");
                updateGetProductColorTask.execute((Void)null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //submit order aplikator
                attemptSubmitNewProjectApplicator();
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

        act_State.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                states_id = adapter_state.getItemId(act_State.getText().toString());
                updateCityTask = new UpdateCityTask(states_id+"");
                updateCityTask.execute((Void)null);
            }
        });
        context = this;
        session = new AppSession(context);
        session.checkSession();
        token = session.getToken();
        txt_NameUser.setText("Halo " + session.getName());
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

    private boolean parsingProductcolors(JSONArray data){
        try {

            String[] spinnerArray = new String[data.length()];
            spinnerMapColor = new HashMap<Integer, Long>();
            colorName = new String[data.length()];
            colorIds = new long[data.length()];
            for(int i=0;i<data.length();i++){
                JSONObject jason = data.getJSONObject(i);
                spinnerMapColor.put(i,jason.getLong("id"));
                spinnerArray[i] = jason.getString("name");
            }
            // Create an ArrayAdapter using the string array and a default spinner layout
            adapter_product_demand_color = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, spinnerArray);
            // Specify the layout to use when the list of choices appears
            adapter_product_demand_color.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spn_product_demand_color.setAdapter(adapter_product_demand_color);

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    public class UpdateGetProductColorTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private ProgressDialog pg;
        //        private JSONArray stateJson;
        private JSONArray colorJson;
        private String stateId;

        UpdateGetProductColorTask (String state_id) {
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

            String result = apiWeb.GetproductColors(stateId);
            if(result==null){
                return false;
            }
            try {
                JSONObject json = new JSONObject(result);
                String status = json.getString("status");
                if(status.compareToIgnoreCase("success")==0){
                    colorJson = json.getJSONArray("data");
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

            parsingProductcolors(colorJson);

//            parsingState(stateJson);
//            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }


    /**
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
            parsingProduct(ProductsJson);
//            parsingProducts(stateJson);
//            parsingCity(cityJson);
//            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    //==============================================================================================


    public void OnCreateorderApplicator(){

        img_AddNew      = (ImageView)findViewById(R.id.img_AddNew);
        txt_NameUser    = (TextView)findViewById(R.id.txt_NameUser);
        txt_DatePicker =(TextView)findViewById(R.id.txtisianBirth);//                 wajib diisi
        txt_DatePickerView=(TextView)findViewById(R.id.txtisianBirthView);//                 wajib diisi
        btn_DatePicker  = (Button) findViewById(R.id.btn_DatePicker);
        btn_Submit      = (Button) findViewById(R.id.btn_submit);
        et_AddressStore = (EditText) findViewById(R.id.et_AddressStore);
        et_StoreName    = (EditText) findViewById(R.id.et_StoreName);
//        et_amount       = (EditText) findViewById(R.id.et_amount);
        act_City        = (AutoCompleteTextView) findViewById(R.id.act_city);
        act_State       = (AutoCompleteTextView) findViewById(R.id.act_state);
        btn_ImageAddNew = (LinearLayout)findViewById(R.id.btn_ImageAddNew);
        et_onduvilla_amount  = (EditText) findViewById(R.id.et_onduvilla_amount);
        et_onduline_amount   = (EditText) findViewById(R.id.et_onduline_amount);
        et_onduclair_amount  = (EditText) findViewById(R.id.et_onduclair_amount);

        calendar = Calendar.getInstance();

        mFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);


        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

//        ------------------------------------------------------------------------------------------

//        Spinner spn_product_demand_color, spn_product_demand_brand;
        spn_product_demand_color = (Spinner)findViewById(R.id.spn_product_demand_color);
        spn_product_demand_brand = (Spinner)findViewById(R.id.spn_product_demand_brand);


        updateGetProductTask = new UpdateGetProductTask();
        updateGetProductTask.execute((Void)null);


        spn_product_demand_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Long product_id = spinnerMapProduct.get(spn_product_demand_brand.getSelectedItemPosition());
                updateGetProductColorTask = new UpdateGetProductColorTask(product_id+"");
                updateGetProductColorTask.execute((Void)null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        ------------------------------------------------------------------------------------------

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

        act_State.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                states_id = adapter_state.getItemId(act_State.getText().toString());
                updateCityTask = new UpdateCityTask(states_id+"");
                updateCityTask.execute((Void)null);
            }
        });
        context = this;
        session = new AppSession(context);
        session.checkSession();
        token = session.getToken();
        txt_NameUser.setText("Halo " + session.getName());
    }

    public void OnCreateorderRetailer(){

        img_AddNew      = (ImageView)findViewById(R.id.img_AddNew);
        txt_NameUser    = (TextView)findViewById(R.id.txt_NameUser);
        txt_DatePicker  =(TextView)findViewById(R.id.txtisianBirth);//                 wajib diisi
        txt_DatePickerView=(TextView)findViewById(R.id.txtisianBirthView);//                 wajib diisi
        btn_DatePicker  = (Button) findViewById(R.id.btn_DatePicker);
        btn_Submit      = (Button) findViewById(R.id.btn_submit);
        et_distributor  = (EditText) findViewById(R.id.et_distributor);
        et_address  = (EditText) findViewById(R.id.et_address);
//        spn_ProductType = (Spinner) findViewById(R.id.spn_product_demand_brand);// onduline, onduvilla, bituline, bardoline
//        spn_ProductColorType= (Spinner) findViewById(R.id.spn_product_demand_color);// onduline, onduvilla, bituline, bardoline
        et_amount       = (EditText) findViewById(R.id.et_amount);
        act_City        = (AutoCompleteTextView) findViewById(R.id.act_city);
        act_State       = (AutoCompleteTextView) findViewById(R.id.act_state);
        btn_ImageAddNew = (LinearLayout)findViewById(R.id.btn_ImageAddNew);
        et_onduvilla_amount  = (EditText) findViewById(R.id.et_onduvilla_amount);
        et_onduline_amount   = (EditText) findViewById(R.id.et_onduline_amount);
        et_onduclair_amount  = (EditText) findViewById(R.id.et_onduclair_amount);

//            et_onduvilla_amount,et_onduline_amount,et_onduclair_amount



        calendar = Calendar.getInstance();

        mFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);


        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);


//        ------------------------------------------------------------------------------------------

//        Spinner spn_product_demand_color, spn_product_demand_brand;
        spn_product_demand_color = (Spinner)findViewById(R.id.spn_product_demand_color);
        spn_product_demand_brand = (Spinner)findViewById(R.id.spn_product_demand_brand);


        updateGetProductTask = new UpdateGetProductTask();
        updateGetProductTask.execute((Void)null);


        spn_product_demand_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Long product_id = spinnerMapProduct.get(spn_product_demand_brand.getSelectedItemPosition());
                updateGetProductColorTask = new UpdateGetProductColorTask(product_id+"");
                updateGetProductColorTask.execute((Void)null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        ------------------------------------------------------------------------------------------



        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //submit order aplikator
                attemptSubmitOrderRetailer();
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

        act_State.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                states_id = adapter_state.getItemId(act_State.getText().toString());
                updateCityTask = new UpdateCityTask(states_id+"");
                updateCityTask.execute((Void)null);
            }
        });
        context = this;
        session = new AppSession(context);
        session.checkSession();
        token = session.getToken();
        txt_NameUser.setText("Halo " + session.getName());
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
        txt_DatePicker.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
        txt_DatePickerView.setText(new StringBuilder().append(day).append("-")
                .append(month).append("-").append(year));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new FixedHoloDatePickerDialog(this,
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



    private boolean parsingProjectType(JSONArray data){
        try {

            String[] spinnerArray = new String[data.length()];
            spinnerMapProjectType= new HashMap<Integer, Long>();
            for(int i=0;i<data.length();i++){
                JSONObject jason = data.getJSONObject(i);
                spinnerMapProjectType.put(i,jason.getLong("id"));
                spinnerArray[i] = jason.getString("name");
            }
            // Create an ArrayAdapter using the string array and a default spinner layout
            adapter_project_type= new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, spinnerArray);
            // Specify the layout to use when the list of choices appears
            adapter_project_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spn_ProjectType.setAdapter(adapter_project_type);


//            Toast.makeText(context, "project type : " + spinnerMapProjectType.get(spn_ProjectType.getSelectedItemPosition()) , Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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


    public class SubmitOrderApplicatorTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
//        private String productId;
        private String orderDate;
//        private String address;
        private String distributor;
        private String store_name;
        private String colorId;
//        private String amount;
        private String receipt;
        private String city_id;
        private String state_id;
        private String onduvilla_amount;
        private String onduline_amount;
        private String onduclair_amount;
        private String errorMessage = "Koneksi Error";

        SubmitOrderApplicatorTask() {

            if (pil.compareToIgnoreCase("order applicator")==0){
                apiWeb      = new ApiWeb();
                token       = session.getToken();
                receipt     =  mCurrentPhotoPath;
//                productId   = spinnerMapProduct.get(spn_product_demand_brand.getSelectedItemPosition()) + "";
                orderDate   = txt_DatePicker.getText().toString();
                distributor = "";
                store_name  = et_StoreName.getText().toString();
//                address     = et_AddressStore.getText().toString();
                city_id     = adapter_city.getItemId(act_City.getText().toString())+"";
                state_id    = adapter_state.getItemId(act_State.getText().toString())+"";
                colorId     = "";     //colorId
//                colorId     = spinnerMapColor.get(spn_product_demand_color.getSelectedItemPosition()) + "";
//                amount      = et_amount.getText().toString();
                onduvilla_amount    = et_onduvilla_amount.getText().toString();
                onduline_amount     = et_onduline_amount.getText().toString();
                onduclair_amount    = et_onduclair_amount.getText().toString();
            }else if (pil.compareToIgnoreCase("order retailer")==0){
                apiWeb      = new ApiWeb();                                                                     //apiWeb
                token       = session.getToken();                                                               //token
                receipt     = mCurrentPhotoPath;                                                               //receipt
//                productId   = spinnerMapProduct.get(spn_product_demand_brand.getSelectedItemPosition()) + "";   //productId
                orderDate   = txt_DatePicker.getText().toString();                                              //orderDate
                distributor = et_distributor.getText().toString();                                              //distributor
                store_name  = "";                                                                               //store_name
//                address     = "";                                             //address
                city_id     = adapter_city.getItemId(act_City.getText().toString())+"";                         //city_id
                state_id    = adapter_state.getItemId(act_State.getText().toString())+"";                       //state_id
//                colorId     = spinnerMapColor.get(spn_product_demand_color.getSelectedItemPosition()) + "";     //colorId
                colorId     = "";     //colorId
//                amount      = et_amount.getText().toString();
                onduvilla_amount    = et_onduvilla_amount.getText().toString();
                onduline_amount     = et_onduline_amount.getText().toString();
                onduclair_amount    = et_onduclair_amount.getText().toString();
            }else {

//                project applicator

            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = null;
//            result = apiWeb.Order(token,receipt,productId,orderDate,address,distributor,store_name,colorId,amount,city_id,state_id);
            result = apiWeb.Order(token,receipt,orderDate,distributor,store_name,colorId,city_id,state_id,onduvilla_amount,onduline_amount,onduclair_amount);


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
            mAuthOrderApplicatorTask = null;
            showProgress(false);

            if (success) {

                popupSuccess();
                showProgress(false);

            } else {
                popupAllert(errorMessage);
                showProgress(false);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthOrderApplicatorTask = null;
            showProgress(false);
        }
    }


    public class SubmitNewProjectApplicatorTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String projectType;
        private String projectAddress;
        private String city_id;
        private String state_id;
        private String productId;
        private String colorId;
        private String spaciousRoof;
        private String receipt;// image
        private String projectDate;

        private String errorMessage = "Koneksi Error";

        SubmitNewProjectApplicatorTask() {

            apiWeb          = new ApiWeb();
//            projectType     = "1";//---------------------?? ini maksutnya tipe apa?
            projectType     = spinnerMapProjectType.get(spn_ProjectType.getSelectedItemPosition()) + "";
            projectAddress  = et_ProjectAddress.getText().toString();
            city_id         = adapter_city.getItemId(act_City.getText().toString())+"";
            state_id        = adapter_state.getItemId(act_State.getText().toString())+"";
//            productId       = "1";//--------------------- maksutnya onduline dan apalah itu
//            colorId         = "1";

            productId       = spinnerMapProduct.get(spn_product_demand_brand.getSelectedItemPosition()) + "";
            colorId         = spinnerMapColor.get(spn_product_demand_color.getSelectedItemPosition()) + "";
            spaciousRoof    = et_SpaciousRoof.getText().toString();
            receipt         =  mCurrentPhotoPath;
            projectDate     = txt_DatePicker.getText().toString();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = null;
            result = apiWeb.NewProject(token,receipt,projectType,projectAddress,projectDate,city_id,state_id,productId,colorId,spaciousRoof);


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
            mAuthOrderApplicatorTask = null;
            showProgress(false);

            if (success) {

                popupSuccess();
                showProgress(false);

            } else {

                popupAllert(errorMessage);
                showProgress(false);
                mAuthOrderApplicatorTask = null;

            }
        }

        @Override
        protected void onCancelled() {
            mAuthOrderApplicatorTask = null;
            showProgress(false);
        }
    }

    //==============================================================================================

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UpdateProjectTypeTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String statusError = "Koneksi Error";
        private ProgressDialog pg;
        private JSONArray projectTypeJson;

        UpdateProjectTypeTask() {
            apiWeb = new ApiWeb();
            pg = new ProgressDialog(context);
            pg.setTitle("Ambil Data");
            pg.setMessage("Ambil Data");
            pg.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = apiWeb.GetProjectTypes();
            if(result==null){
                return false;
            }
            try {
                JSONObject json = new JSONObject(result);
                String status = json.getString("status");
                if(status.compareToIgnoreCase("success")==0){
                    projectTypeJson = json.getJSONArray("data");
                    return true;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            pg.dismiss();
            parsingProjectType(projectTypeJson);
//            parsingCity(cityJson);
//            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

}
