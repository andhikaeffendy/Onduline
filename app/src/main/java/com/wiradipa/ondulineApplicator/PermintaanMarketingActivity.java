package com.wiradipa.ondulineApplicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.wiradipa.ondulineApplicator.lib.ApiWeb;
import com.wiradipa.ondulineApplicator.lib.AppSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PermintaanMarketingActivity extends AppCompatActivity {


    private EditText et_totalNok, et_totalScrup, et_totalNail, et_etc;
    private Spinner spn_product_demand_color, spn_product_demand_brand;
    private ArrayAdapter<String> adapter_product_demand_color, adapter_product_demand_brand;
    private HashMap<Integer,Long> spinnerMapProduct, spinnerMapColor;
    private Context context;
    private Button btn_submit;
    private String[] souvenirName, productName,colorName;
    private long[] souvenirIds, productIds,colorIds ;
    private View mProgressView;
    private View mFormView;


    private AppSession session;
    private OrderSuvenirTask orderSuvenirTask;
    private UpdateSouvenirTask updateSouvenirTask;
    private OrderBrosurTask orderBrosurTask;
    private UpdateBrosurTask updateBrosurTask;
    private OrderProductTask orderProductTask;
    private UpdateProductTask updateProductTask;
    private UpdateGetProductTask updateGetProductTask;
    private UpdateGetProductColorTask updateGetProductColorTask;
    private String token;

//    variable for listview
    private ListView lv_Marketing;
    private SimpleAdapter Adapter;
    private HashMap<String, String> map;
    private ArrayList<HashMap<String, String>> mylist;
    private String[] itemName, itemQuantity,itemKey, itemStatus,itemImg;
    private int[] orderItemKeys,orderItemQty;

    String pil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        session = new AppSession(context);
        session.checkSession();
        token = session.getToken();

        setLayout();


        mFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);




    }

    //function to set layout(produk, brosur, souvenir)
    public void setLayout(){
        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");
        switch (pil) {
            case "produk":
                setContentView(R.layout.activity_permintaan_marketing_produk);
                OncreateProduct();
                break;
            case "brosur":
                setContentView(R.layout.activity_permintaan_marketing_brosur);
                OnCreateBrosur();
                break;
            case "souvenir":
                setContentView(R.layout.activity_permintaan_marketing_souvenir);
                OnCreateSouvenir();
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


    private boolean isTotalNokEmpty(String totalNok) {
        //TODO: Replace this with your own logic
        return totalNok.equals("");
    }
    private boolean isTotalScrupEmpty(String totalScrup) {
        //TODO: Replace this with your own logic
        return totalScrup.equals("");
    }
    private boolean isTotalNailEmpty(String totalNail) {
        //TODO: Replace this with your own logic
        return totalNail.equals("");
    }


    /**
     * Attempts to sign in or register the account specified by the login next_menu.
     * If there are next_menu errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSubmitPermintaanProduct() {
        if (orderProductTask != null) {
            return;
        }

        // Reset errors.
        et_totalNail.setError(null);
        et_totalNok.setError(null);
        et_totalScrup.setError(null);
//        img_AddNew.setError

        // Store values at the time of the submit attempt.
        String totalNail = et_totalNail.getText().toString();
        String totalNok= et_totalNok.getText().toString();
        String totalScrup= et_totalScrup.getText().toString();

        boolean cancel = false;
        View focusView = null;
//
        // Check for a valid address store.
        if (isTotalNailEmpty(totalNail)) {
            et_totalNail.setError(getString(R.string.error_field_required));
            focusView = et_totalNail;
            cancel = true;
        }
        // Check for a valid address store.
        if (isTotalNokEmpty(totalNok)) {
            et_totalNok.setError(getString(R.string.error_field_required));
            focusView = et_totalNail;
            cancel = true;
        }
        // Check for a valid address store.
        if (isTotalScrupEmpty(totalScrup)) {
            et_totalScrup.setError(getString(R.string.error_field_required));
            focusView = et_totalScrup;
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
            orderProductTask    =   new OrderProductTask();
            orderProductTask.execute((Void)null);
        }
    }

    public void OncreateProduct(){

//        et_totalNok, et_totalScrup, et_totalNail, et_etc
        et_totalNok =(EditText)findViewById(R.id.et_totalNok);
        et_totalNail =(EditText)findViewById(R.id.et_totalNail);
        et_totalScrup =(EditText)findViewById(R.id.et_totalScrup);
        et_etc =(EditText)findViewById(R.id.et_etc);

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
        btn_submit      = (Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSubmitPermintaanProduct();
            }
        });
    }

    public void OnCreateSouvenir(){

//        coba get souvenir data
        updateSouvenirTask  =   new UpdateSouvenirTask();
        updateSouvenirTask.execute((Void)null);

        lv_Marketing = (ListView)findViewById(R.id.lv_Marketing);
        btn_submit      = (Button)findViewById(R.id.btn_submit);
        btn_submit.setEnabled(false);

        lv_Marketing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //show input box
                showInputBox(mylist.get(position).get("ItemName"),position);
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showProgress(true);
                orderSuvenirTask    =   new OrderSuvenirTask(orderItemKeys.length,orderItemKeys,orderItemQty);
                orderSuvenirTask.execute((Void)null);
            }
        });

    }

    public void OnCreateBrosur(){

//        coba get souvenir data
        updateBrosurTask    =   new UpdateBrosurTask();
        updateBrosurTask.execute((Void)null);

        lv_Marketing = (ListView)findViewById(R.id.lv_Marketing);
        btn_submit      = (Button)findViewById(R.id.btn_submit);
        btn_submit.setEnabled(false);

        lv_Marketing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //show input box
                showInputBox(mylist.get(position).get("ItemName"),position);
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showProgress(true);
                orderBrosurTask =   new OrderBrosurTask(orderItemKeys.length,orderItemKeys,orderItemQty);
                orderBrosurTask.execute((Void)null);
            }
        });

    }



    public void showInputBox(String itemName, final int index){

        final Dialog dialog=new Dialog(context);
        dialog.setTitle("Tambah Pesanan");
        dialog.setContentView(R.layout.add_item);
        TextView txt_addItemName = (TextView)dialog.findViewById(R.id.txt_addItemName);
        txt_addItemName.setText(itemName);
        final EditText et_ItemQuantity = (EditText)dialog.findViewById(R.id.et_ItemQuantity);
//        et_ItemQuantity.setText("0");
        Button btn_addItem = (Button)dialog.findViewById(R.id.btn_addItem);
        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_ItemQuantity.getText().toString().equals("0")){
                    mylist.get(index).put("ItemQuantity","0");
                    mylist.get(index).put("ItemStatus","Tambah Pesanan");
                    mylist.get(index).put("ItemImg",Integer.toString(R.drawable.ic_check_box_outline_blank_red_24dp));
                }else if (et_ItemQuantity.getText().toString().equals("")){
                    mylist.get(index).put("ItemQuantity","0");
                    mylist.get(index).put("ItemStatus","Tambah Pesanan");
                    mylist.get(index).put("ItemImg",Integer.toString(R.drawable.ic_check_box_outline_blank_red_24dp));
                }else {
                    mylist.get(index).put("ItemQuantity",et_ItemQuantity.getText().toString());
                    mylist.get(index).put("ItemStatus",et_ItemQuantity.getText().toString());
                    mylist.get(index).put("ItemImg",Integer.toString(R.drawable.ic_check_box_outline_blank_black_24dp));
                }


                Adapter = new SimpleAdapter(context, mylist, R.layout.list_marketing,
                        new String[] {"ItemName","ItemQuantity", "ItemKey", "ItemStatus", "ItemImg"},
                        new int[] {R.id.txt_ItemName, R.id.txt_quantity, R.id.txt_itemKey, R.id.txt_statusItem, R.id.img_checkbox});
                lv_Marketing.setAdapter(Adapter);


//                get all data pesanan send to array
                int[] itemkeyId, quantityItem;
                itemkeyId = new int[mylist.size()];
                quantityItem = new int[mylist.size()];

                for (int i =0; i<mylist.size(); i++){
                    int quantityTmp = Integer.parseInt(mylist.get(i).get("ItemQuantity"));
                    if(quantityTmp!=0){
                        itemkeyId[i]=Integer.parseInt(mylist.get(i).get("ItemKey"));
                        quantityItem[i]=quantityTmp;//--------------------------------------------------------------------------------------------------------------

                        btn_submit.setEnabled(true);
//                        Toast.makeText(context, "id : " + itemkeyId[i]+", qty : "+quantityItem[i], Toast.LENGTH_LONG).show();
                    }
                }

//                private String[] orderItemKeys,orderItemQty;
                orderItemKeys = new int[itemkeyId.length];
                orderItemQty = new int[itemkeyId.length];

//                Toast.makeText(context, "orderItemKeys length : " + orderItemKeys.length, Toast.LENGTH_LONG).show();
                orderItemKeys = itemkeyId;
                orderItemQty  = quantityItem;

                dialog.dismiss();

            }
        });

        dialog.show();
    }

    public void popupSuccess(){
        new AlertDialog.Builder(this)
                .setTitle("Permintaan "+pil+" Sukses!")
                .setMessage("Selamat "+pil+" anda telah terkirim")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(context, MarketingSupportActivity.class);
                        intent.putExtra("pil",pil);
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


    public String parsingParam(int length, int[]  id, int[] qty){
        try {
            JSONObject result = new JSONObject();
            JSONArray orders = new JSONArray();

//            perulangan untuk cek mana aja yg di pilih dan jumlahnya,.
            for (int i = 0; i <length; i++) {
                JSONObject order = new JSONObject();
                order.put("souvenir_id", id[i]);
                order.put("quantity", qty[i]);
            }

            result.put("souvenir_orders_attributes", orders);

            return result.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String parsingBrochuresParam(int length, int[]  id, int[] qty){
        try {
            JSONObject result = new JSONObject();
            JSONArray orders = new JSONArray();

//            perulangan untuk cek mana aja yg di pilih dan jumlahnya,.
            for (int i = 0; i <length; i++) {
                JSONObject order = new JSONObject();
                order.put("product_id", id[i]);
                order.put("quantity", qty[i]);
            }

            result.put("brochure_orders_attributes", orders);

            return result.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }


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

    //    parsing souvenir
    private boolean parsingItem(JSONArray data){
        try {
            souvenirName = new String[data.length()];
            souvenirIds = new long[data.length()];


            itemKey     = new String[data.length()];
            itemName    = new String[data.length()];
            itemStatus  = new String[data.length()];
            itemQuantity= new String[data.length()];
            itemImg     = new String[data.length()];

            for(int i=0;i<data.length();i++){
                JSONObject jason = data.getJSONObject(i);

                int tmp = jason.getInt("id");
                itemKey[i] = tmp + "";
                itemName[i] = jason.getString("name");
                itemStatus[i] = "Tambah Pesanan";
                itemQuantity[i] = "0";
                itemImg[i] = Integer.toString(R.drawable.ic_check_box_outline_blank_red_24dp);
            }
            mylist = new ArrayList<HashMap<String,String>>();
            for (int i = 0; i < itemName.length; i++){
                map = new HashMap<String, String>();

                map.put("ItemName", itemName[i]);
                map.put("ItemQuantity", itemQuantity[i]);
                map.put("ItemStatus", itemStatus[i]);
                map.put("ItemKey", itemKey[i]);
                map.put("ItemImg", itemImg[i]);

                mylist.add(map);
            }

            Adapter = new SimpleAdapter(this, mylist, R.layout.list_marketing,
                    new String[] {"ItemName","ItemQuantity", "ItemKey", "ItemStatus", "ItemImg"},
                    new int[] {R.id.txt_ItemName, R.id.txt_quantity, R.id.txt_itemKey, R.id.txt_statusItem, R.id.img_checkbox});
            lv_Marketing.setAdapter(Adapter);

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


//    ==============================================================================================
//    ==============================================================================================

//    update data brosur
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UpdateBrosurTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private ProgressDialog pg;
        private JSONArray souvenirJson;
        private JSONArray cityJson;

        UpdateBrosurTask () {
            apiWeb = new ApiWeb();
            pg = new ProgressDialog(context);
            pg.setTitle("Ambil Data");
            pg.setMessage("Ambil Data");
            pg.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = apiWeb.Getbrochures();
            if(result==null){
                return false;
            }
            try {
                JSONObject json = new JSONObject(result);
                String status = json.getString("status");
                if(status.compareToIgnoreCase("success")==0){
                    souvenirJson = json.getJSONArray("data");
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
            parsingItem(souvenirJson);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class OrderBrosurTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String brochure_orders_attributes;

        OrderBrosurTask(int length, int[]  id, int[] qty) {
            apiWeb = new ApiWeb();
            brochure_orders_attributes = parsingBrochuresParam(length, id, qty);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = null;

           result = apiWeb.OrderBrosur(token,brochure_orders_attributes);      //  ini belum di bikin di apiweb


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
                //disini ada popup sukses..:D

            } else {

                popupAllert(errorMessage);
                showProgress(false);

            }
        }

    }


//    ==============================================================================================
//    ==============================================================================================

//    update data souvenir
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UpdateSouvenirTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private ProgressDialog pg;
        private JSONArray souvenirJson;
        private JSONArray cityJson;

        UpdateSouvenirTask() {
            apiWeb = new ApiWeb();
            pg = new ProgressDialog(context);
            pg.setTitle("Ambil Data");
            pg.setMessage("Ambil Data");
            pg.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = apiWeb.Getsouvenirs();
            if(result==null){
                return false;
            }
            try {
                JSONObject json = new JSONObject(result);
                String status = json.getString("status");
                if(status.compareToIgnoreCase("success")==0){
                    souvenirJson = json.getJSONArray("data");
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
            parsingItem(souvenirJson);
        }
    }




    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class OrderSuvenirTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String souvenir_orders_attributes;

        OrderSuvenirTask(int length, int[]  id, int[] qty) {
            apiWeb = new ApiWeb();
            souvenir_orders_attributes = parsingParam(length, id, qty);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = null;
            result = apiWeb.OrderSuvenir(token,souvenir_orders_attributes);


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
                //disini ada popup sukses..:D

            } else {

                popupAllert(errorMessage);
                showProgress(false);

            }
        }

    }

//    ==============================================================================================
//    ==============================================================================================

//    update data product
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UpdateProductTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private ProgressDialog pg;
        private JSONArray souvenirJson;
        private JSONArray cityJson;

        UpdateProductTask () {
            apiWeb = new ApiWeb();
            pg = new ProgressDialog(context);
            pg.setTitle("Ambil Data");
            pg.setMessage("Ambil Data");
            pg.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = apiWeb.Getsouvenirs();
            if(result==null){
                return false;
            }
            try {
                JSONObject json = new JSONObject(result);
                String status = json.getString("status");
                if(status.compareToIgnoreCase("success")==0){
                    souvenirJson = json.getJSONArray("data");
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
            parsingItem(souvenirJson);
        }
    }




    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class OrderProductTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String souvenir_orders_attributes, nok, nail, scrup, etc, product_id, color_id;

        OrderProductTask() {
            apiWeb = new ApiWeb();
            nok = et_totalNok.getText().toString();
            nail = et_totalNail.getText().toString();
            scrup = et_totalScrup.getText().toString();
            etc = et_etc.getText().toString();
            product_id = spinnerMapProduct.get(spn_product_demand_brand.getSelectedItemPosition()) + "";
            color_id= spinnerMapColor.get(spn_product_demand_color.getSelectedItemPosition()) + "";
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = null;
            result = apiWeb.OrderProduct(token,product_id,color_id,nok,scrup,nail,etc);


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
                //disini ada popup sukses..:D

            } else {

                popupAllert(errorMessage);
                showProgress(false);

            }
        }

    }

}
