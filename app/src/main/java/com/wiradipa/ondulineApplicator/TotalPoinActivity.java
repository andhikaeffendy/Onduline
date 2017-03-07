package com.wiradipa.ondulineApplicator;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wiradipa.ondulineApplicator.lib.ApiWeb;
import com.wiradipa.ondulineApplicator.lib.AppSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TotalPoinActivity extends AppCompatActivity {



    private View mProgressView;
    private View mFormView;

    private String[] statesName, citiesName;

    private Context context;
    private AppSession session;
    private TextView txt_NameUser,txt_headerTittle;
    private UpdateProjectTask updateProjectTask;
    private UpdateProjectDetilTask updateProjectDetilTask;
    private UpdateOrderTask updateOrderTask;
    private UpdateOrderDetilTask updateOrderDetilTask;
    private String pilListView;


    private ImageView img_tmp;
    protected ListView lv;
    protected ListAdapter adapter;
    SimpleAdapter Adapter;
    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> mylist;
    String[] list_id, ProductName, colorName, stateName, cityName, image, orderDate;
    private String productName, productColor, productWidth, productState, productCity, productAddress, productImage, productAmount, storeName;

    String pil, pil_detil;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        context = this;
        session = new AppSession(context);
        session.checkSession();
        token = session.getToken();

        setLayoutListView();

    }

    public void setLayoutListView(){
        Bundle extras = getIntent().getExtras();
        pilListView = extras.getString("pilListView");
        pil_detil = extras.getString("pil");

        switch (pilListView){
            case "project":
                OncreateProject();
                break;
            case "order":
                OncreateOrder();
                break;
        }

    }

    public void OncreateProject(){
        setContentView(R.layout.activity_total_poin);

        txt_NameUser = (TextView)findViewById(R.id.txt_NameUser);
        img_tmp     = (ImageView)findViewById(R.id.img_tmp);
        txt_headerTittle    = (TextView)findViewById(R.id.txt_headerTittle);

        txt_NameUser.setText("Halo " + session.getName());
        txt_headerTittle.setText("HISTORY PROYEK");

        lv = (ListView) findViewById(R.id.lv_totalPoin);
        mylist = new ArrayList<HashMap<String,String>>();

        updateProjectTask = new UpdateProjectTask();
        updateProjectTask.execute((Void)null);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                updateProjectDetilTask = new UpdateProjectDetilTask(mylist.get(position).get("list_id"));
                updateProjectDetilTask.execute((Void)null);
            }
        });

    }

    public void OncreateOrder(){
        setContentView(R.layout.activity_total_poin);

        txt_NameUser = (TextView)findViewById(R.id.txt_NameUser);
        img_tmp     = (ImageView)findViewById(R.id.img_tmp);
        txt_headerTittle    = (TextView)findViewById(R.id.txt_headerTittle);

        txt_NameUser.setText("Halo " + session.getName());
        txt_headerTittle.setText("HISTORY  ORDER");

        lv = (ListView) findViewById(R.id.lv_totalPoin);
        mylist = new ArrayList<HashMap<String,String>>();

        updateOrderTask= new UpdateOrderTask();
        updateOrderTask.execute((Void)null);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateOrderDetilTask= new UpdateOrderDetilTask(mylist.get(position).get("list_id"));
                updateOrderDetilTask.execute((Void)null);
            }
        });

    }

    public void showProjectDetil(String id, String productName,String productColor,String roofProductWidth,String state,String city,String address, String imageUrl){

        final Dialog dialog=new Dialog(context);
        dialog.setTitle("Detil Project");
        dialog.setContentView(R.layout.list_detil);

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);

        dialog.getWindow().setLayout(width, height);

//        Toast.makeText(context, "productName : " + productName, Toast.LENGTH_LONG).show();
        ImageView img_detil = (ImageView)dialog.findViewById(R.id.img_detil);
        TextView txt_productName = (TextView)dialog.findViewById(R.id.txt_productName);
        TextView txt_productColor = (TextView)dialog.findViewById(R.id.txt_productColor);
        TextView txt_roofProductWidth = (TextView)dialog.findViewById(R.id.txt_roofProductWidth);
        TextView txt_state = (TextView)dialog.findViewById(R.id.txt_state);
        TextView txt_city = (TextView)dialog.findViewById(R.id.txt_city);
        TextView txt_address = (TextView)dialog.findViewById(R.id.txt_address);
        txt_productName.setText(productName);
        txt_productColor.setText("Warna Atap : "+productColor);
        txt_roofProductWidth.setText("Lebar Atap : "+roofProductWidth);
        txt_state.setText(state);
        txt_city.setText(city);
        txt_address.setText(address);
        Picasso.with(dialog.getContext())
                .load("http://onduline-mobile.wiradipa.com"+imageUrl)
                .error( R.drawable.ic_error_outline_black_24dp )
                .placeholder( R.drawable.progress_animation )
                .resize(450, 150)
                .into(img_detil);
//            Toast.makeText(context, "url : " + imageUrl  , Toast.LENGTH_SHORT).show();

        Button btn_addItem = (Button)dialog.findViewById(R.id.btn_back);
        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        dialog.show();
    }


    public void showOrderDetil(String id, String productName,String productColor, String storeName,String amount, String state,String city, String imageUrl){

        final Dialog dialog=new Dialog(context);
        dialog.setTitle("Detil Order");
        dialog.setContentView(R.layout.list_detil_order);

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);

        dialog.getWindow().setLayout(width, height);

//        Toast.makeText(context, "productName : " + productName, Toast.LENGTH_LONG).show();
        ImageView img_detil = (ImageView)dialog.findViewById(R.id.img_detil);
        TextView txt_productName = (TextView)dialog.findViewById(R.id.txt_productName);
        TextView txt_productColor = (TextView)dialog.findViewById(R.id.txt_productColor);
        TextView txt_state = (TextView)dialog.findViewById(R.id.txt_state);
        TextView txt_city = (TextView)dialog.findViewById(R.id.txt_city);
        TextView txt_roofProductTotal = (TextView)dialog.findViewById(R.id.txt_roofProductTotal);

        if (pil_detil.equals("applicator")){
            TextView txt_storeName = (TextView)dialog.findViewById(R.id.txt_storeName);
            txt_storeName.setText("Nama Toko : "+storeName);
        }else {
            TextView txt_storeName = (TextView)dialog.findViewById(R.id.txt_storeName);
            txt_storeName.setVisibility(View.GONE);
        }


        txt_productName.setText(productName);
        txt_productColor.setText("Warna Atap : "+productColor);
        txt_roofProductTotal.setText("Jumlah : "+amount);
        txt_state.setText(state);
        txt_city.setText(city);
        Picasso.with(dialog.getContext())
                .load("http://onduline-mobile.wiradipa.com"+imageUrl)
                .error( R.drawable.ic_error_outline_black_24dp )
                .placeholder( R.drawable.progress_animation )
                .resize(450, 150).into(img_detil);
//            Toast.makeText(context, "url : " + imageUrl  , Toast.LENGTH_SHORT).show();

        Button btn_addItem = (Button)dialog.findViewById(R.id.btn_back);
        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        dialog.show();
    }


    private boolean parsinglistView(JSONArray data){
        try {
            ProductName = new String[data.length()];
            colorName = new String[data.length()];
            stateName = new String[data.length()];
            cityName = new String[data.length()];
            image = new String[data.length()];
            list_id = new String[data.length()];

            for(int i=0;i<data.length();i++){
                JSONObject jason = data.getJSONObject(i);
                list_id[i] = jason.getString("id");
                ProductName[i] = jason.getString("product_name");
                colorName[i] = jason.getString("color_name");
                cityName[i] = jason.getString("city_name");
                stateName[i] = jason.getString("state_name");
                image[i] = jason.getString("receipt_url");
            }
            for (int i = 0; i < ProductName.length; i++){
                map = new HashMap<String, String>();
                map.put("list_id", list_id[i]);
                map.put("ProductName", ProductName[i]);
                map.put("colorName", colorName[i]);
                map.put("stateName", stateName[i]);
                map.put("cityName", cityName[i]);
                map.put("img", image[i]);


                mylist.add(map);
            }

            Adapter = new SimpleAdapter(this, mylist, R.layout.list_project_refrence,
                    new String[] {"img","list_id", "ProductName","colorName","stateName","cityName"}, new int[] {R.id.txt_imgUrl, R.id.txt_id, R.id.txt_product, R.id.txt_ColorName,R.id.txt_state,R.id.txt_city});
            lv.setAdapter(Adapter);


        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private boolean parsingOrderlistView(JSONArray data){
        try {
            stateName = new String[data.length()];
            cityName = new String[data.length()];
            orderDate = new String[data.length()];
            list_id = new String[data.length()];

            for(int i=0;i<data.length();i++){
                JSONObject jason = data.getJSONObject(i);
                list_id[i] = jason.getString("id");
                cityName[i] = jason.getString("city_name");
                stateName[i] = jason.getString("state_name");
                orderDate[i] = jason.getString("order_date");

//                Toast.makeText(context, "city_name : " + jason.getString("city_name"), Toast.LENGTH_LONG).show();
            }
            for (int i = 0; i < list_id.length; i++){
                map = new HashMap<String, String>();
                map.put("list_id", list_id[i]);
                map.put("stateName", stateName[i]);
                map.put("cityName", cityName[i]);
                map.put("order_date", orderDate[i]);

                mylist.add(map);
            }

            Adapter = new SimpleAdapter(this, mylist, R.layout.list_refrence_oder,
                    new String[] {"list_id","stateName","cityName","order_date"},
                    new int[] {R.id.txt_id,R.id.txt_state,R.id.txt_city, R.id.txt_OrdeDate});
            lv.setAdapter(Adapter);


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
    public class UpdateProjectTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String statusError = "Koneksi Error";
        private ProgressDialog pg;
        private JSONArray stateJson;
        private JSONArray cityJson;

        UpdateProjectTask() {
            apiWeb = new ApiWeb();
            pg = new ProgressDialog(context);
            pg.setTitle("Ambil Data");
            pg.setMessage("Ambil Data");
            pg.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = apiWeb.GetProjetc(token);
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
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            pg.dismiss();
            parsinglistView(stateJson);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UpdateProjectDetilTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String statusError = "Koneksi Error";
        private ProgressDialog pg;
        private JSONArray stateJson;
        private JSONArray cityJson;
        private String list_id;

        UpdateProjectDetilTask(String id) {
            apiWeb = new ApiWeb();
            pg = new ProgressDialog(context);
            pg.setTitle("Ambil Data");
            pg.setMessage("Ambil Data");
            pg.show();
            list_id = id;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = apiWeb.GetProjectDetil(list_id, token);
            if(result==null){
                return false;
            }
            try {
                JSONObject json = new JSONObject(result);
                String status = json.getString("product_name");
                if(!status.equals("")){
                    productName = json.getString("product_name");
                    productColor = json.getString("color_name");
                    productWidth = json.getString("roof_width");
                    productState = json.getString("state_name");
                    productCity = json.getString("city_name");
                    productAddress = json.getString("address");
                    productImage = json.getString("receipt_url");

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
            showProjectDetil(list_id,productName,productColor,productWidth,productState,productCity,productAddress,productImage);

        }
    }



    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UpdateOrderTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String statusError = "Koneksi Error";
        private ProgressDialog pg;
        private JSONArray stateJson;
        private JSONArray cityJson;

        UpdateOrderTask() {
            apiWeb = new ApiWeb();
            pg = new ProgressDialog(context);
            pg.setTitle("Ambil Data");
            pg.setMessage("Ambil Data");
            pg.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = apiWeb.GetOrders(token);
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
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            pg.dismiss();
            parsingOrderlistView(stateJson);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UpdateOrderDetilTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String statusError = "Koneksi Error";
        private ProgressDialog pg;
        private JSONArray stateJson;
        private JSONArray cityJson;
        private String list_id;

        UpdateOrderDetilTask(String id) {
            apiWeb = new ApiWeb();
            pg = new ProgressDialog(context);
            pg.setTitle("Ambil Data");
            pg.setMessage("Ambil Data");
            pg.show();
            list_id = id;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = apiWeb.GetOrderDetil(list_id, token);
            if(result==null){
                return false;
            }
            try {
                JSONObject json = new JSONObject(result);
                String status = json.getString("product_name");
                if(!status.equals("")){
                    productName = json.getString("product_name");
                    productColor = json.getString("color_name");
                    productState = json.getString("state_name");
                    productCity = json.getString("city_name");
                    productImage = json.getString("receipt_url");
                    productAmount = json.getString("amount");
                    storeName = json.getString("store_name");

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
            showOrderDetil(list_id,productName,productColor,storeName,productAmount,productState,productCity,productImage);

        }
    }

}
