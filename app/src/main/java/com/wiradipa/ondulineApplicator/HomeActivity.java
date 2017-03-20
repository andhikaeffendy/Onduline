package com.wiradipa.ondulineApplicator;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.wiradipa.ondulineApplicator.lib.ApiWeb;
import com.wiradipa.ondulineApplicator.lib.AppSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity{

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    private Context context;
    private AppSession session;
    TextView txtProfilName, txtUserType, txt_totalPoint;
    ImageView imgProfil;

    private UpdateTask updateTask;
//    untuk menu gerak
    private ViewFlipper viewFlipper;
    private Animation fadeIn, fadeOut;

    private String pil, token, poin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        session = new AppSession(context);
        session.checkSession();
        pil=session.getUSERTYPE();
        token=session.getToken();
        //class ini memuat xml sesuai dengan user masuk sebagai aplikator atau retailer
        setLayout(pil);

        viewFlipper=(ViewFlipper)findViewById(R.id.viewFlipper);
        fadeIn= AnimationUtils.loadAnimation(this,R.anim.fade_in);
        fadeOut= AnimationUtils.loadAnimation(this,R.anim.fate_out);
        viewFlipper.setInAnimation(fadeIn);
        viewFlipper.setOutAnimation(fadeOut);

        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.startFlipping();

        if (!isNetworkAvailable()){
            popupNoInternet();
        }


    }


    // cek ada internet apa gak..
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void popupNoInternet(){
        new AlertDialog.Builder(this)
                .setTitle("Tidak Ada Koneksi Internet!")
                .setMessage("Periksa koneksi internet anda")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        int pid = android.os.Process.myPid();
                        android.os.Process.killProcess(pid);
                        System.exit(0);
                        finish();
                    }
                }).create().show();
    }

    //function to set layout(aplikator or retailer)
    public void setLayout(String pil){
//        Bundle extras = getIntent().getExtras();
//        pil = extras.getString("pil");
        switch (pil) {
            case "applicator":
                loginAplicator();
                break;
            case "retailer":
                loginRetailer();
                break;
            case "individu":
                loginIndividu();
                break;
        }
    }

    public void onClickHome(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btnOrderSaya:
//                Toast.makeText(this, "btnOrderSaya", Toast.LENGTH_LONG).show();
                i = new Intent(this, HistoryActivity.class);
                i.putExtra("pil", "order " + pil);
                startActivity(i);
                break;
            case R.id.btnProgram:
//                Toast.makeText(this, "btnProgram", Toast.LENGTH_LONG).show();
                if (pil.equals("applicator")){
//                    i = new Intent(this, ListProgram.class);

                    i = new Intent(this, DetilViewActivity.class);
                    i.putExtra("pil","Program");
                    i.putExtra("pilDetilView","ondulucky baja ringan");
                    startActivity(i);
                }else {
                    i = new Intent(this, DetilViewActivity.class);
                    i.putExtra("pil","Program");
                    i.putExtra("pilDetilView","toko");
                    startActivity(i);
                }
                break;
//            case R.id.btnProyekSaya:
////                Toast.makeText(this, "btnProyekSaya", Toast.LENGTH_LONG).show();
//                i = new Intent(this, HistoryActivity.class);
//                i.putExtra("pil","project " + pil); //---------------------
//                startActivity(i);
//                break;
            case R.id.btnTechnicalSupport:
//                Toast.makeText(this, "btnTechnicalSupport", Toast.LENGTH_LONG).show();
                i = new Intent(this, TechnicalSupportActivity.class);
                startActivity(i);
                break;
            case R.id.btnProduk:
//                Toast.makeText(this, "btnProduk", Toast.LENGTH_LONG).show();
                i = new Intent(this, OurProductActivity.class);
                i.putExtra("stat","");
                startActivity(i);
                break;
            case R.id.btnDukunganMarketing:
//                Toast.makeText(this, "btnDukunganMarketing", Toast.LENGTH_LONG).show();
                i = new Intent(this, MarketingSupportActivity.class);
                startActivity(i);
                break;
            case R.id.btnContactOnduline:
                i = new Intent(this, ContactOndulineActivity.class);
                startActivity(i);
                break;
            case R.id.btnCalculator:
                i = new Intent(this, CalculatorActivity.class);
                startActivity(i);
                break;
            case R.id.btnSimulatorAtap:
                i = new Intent(this, SimulatorAtapActivity.class);
                startActivity(i);
                break;
        }
    }



    public void popupLogout(){


        new AlertDialog.Builder(context)
                .setTitle("Keluar!")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setNegativeButton("tidak", null)
                .setPositiveButton("ya", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        session.logout();
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                        startActivity(intent);
                        finish();
                    }
                }).create().show();

    }

    @Override
    public void onBackPressed() {


//
//        new AlertDialog.Builder(this)
//                .setTitle("Keluar")
//                .setMessage("Apakah anda yakin ingin keluar?")
//                .setNegativeButton(android.R.string.no, null)
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        //MapsActivity.super.onBackPressed();
//                        //finish();
//                        // System.exit(0);
//
//                        Intent intent = new Intent(Intent.ACTION_MAIN);
//                        intent.addCategory(Intent.CATEGORY_HOME);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
//                        startActivity(intent);
//                        finish();
//                        System.exit(0);
//                    }
//                }).create().show();
//
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {  /*Closes the Appropriate Drawer*/

            exitApp();

//            drawer.closeDrawer(GravityCompat.END);
        } else {
//            super.onBackPressed();
            exitApp();
//    System.exit(0);
        }
    }

    public void exitApp(){
        new AlertDialog.Builder(this)
                .setTitle("Keluar!")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setNegativeButton("tidak", null)
                .setPositiveButton("ya", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        //MapsActivity.super.onBackPressed();
                        //finish();
                        // System.exit(0);

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_openRight) {
            drawer.openDrawer(GravityCompat.END);
            return true;
        }*/

        switch (id) {
            case R.id.action_openRight:
//                Toast.makeText(HomeActivity.this, "open nav right", Toast.LENGTH_LONG).show();
                drawer.openDrawer(GravityCompat.END);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /** fungsi yang digunakan bila user login sebagai aplikator  */
    public void loginAplicator(){

        /** xml untuk aplikator */
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tukang Baja Ringan");
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

//        get poin
        updateTask = new UpdateTask();
        updateTask.execute((Void)null);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_right_view);
        View header=navigationView.getHeaderView(0);
        txtProfilName = (TextView)header.findViewById(R.id.txtProfilName);
        txtUserType = (TextView)header.findViewById(R.id.txtUserType);
        imgProfil   = (ImageView)header.findViewById(R.id.imgProfil);
        imgProfil.setImageDrawable(getResources().getDrawable(R.drawable.avatar_applicator));
        txtProfilName.setText("Halo "+session.getName());
        txtUserType.setText(session.getUSERTYPE());

        NavigationView leftNavigationView = (NavigationView) findViewById(R.id.nav_left_view);
        leftNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent i;
                switch (item.getItemId()) {
                    case R.id.nav_produk:
//                        Toast.makeText(HomeActivity.this, "nav_produk", Toast.LENGTH_LONG).show();
                        i = new Intent(HomeActivity.this, OurProductActivity.class);
                        i.putExtra("stat","");
                        startActivity(i);
                        break;
                    case R.id.nav_marketingsupport:
//                        Toast.makeText(HomeActivity.this, "nav_marketingsupport", Toast.LENGTH_LONG).show();
                        i = new Intent(HomeActivity.this, MarketingSupportActivity.class);
                        startActivity(i);
                        break;
                    case R.id.nav_QandA:
//                        Toast.makeText(HomeActivity.this, "nav_QandA", Toast.LENGTH_LONG).show();
                        i = new Intent(HomeActivity.this, qnaActivity.class);
                        startActivity(i);
                        break;
                }

                return false;
            }
        });

        NavigationView rightNavigationView = (NavigationView) findViewById(R.id.nav_right_view);

        //edit text pada menu navigasi sebelah kanan
        Menu menuRight = navigationView.getMenu();
        menuRight.findItem(R.id.nav_TotalPoin).setTitle("Total Poin Anda : " + session.getPoin());
        rightNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent i;
                switch (item.getItemId()) {
                    case R.id.nav_TotalPoin:
//                        Toast.makeText(HomeActivity.this, "nav_TotalPoin", Toast.LENGTH_LONG).show();
//                        i = new Intent(HomeActivity.this, TotalOrderActivity.class);
//                        i.putExtra("pilListView","project");
//                        startActivity(i);
                        break;
                    case R.id.nav_sumOrder:
//                        Toast.makeText(HomeActivity.this, "nav_TotalOrder", Toast.LENGTH_LONG).show();
                        i = new Intent(HomeActivity.this, TotalOrderActivity.class);
                        i.putExtra("pilListView","order");
                        i.putExtra("pil",pil);
                        startActivity(i);
                        break;
//                    case R.id.nav_sumProjectPhotod:
////                        Toast.makeText(HomeActivity.this, "nav_History", Toast.LENGTH_LONG).show();
//                        i = new Intent(HomeActivity.this, TotalOrderActivity.class);
//                        i.putExtra("pilListView","project");
//                        startActivity(i);
//                        break;
                    case R.id.nav_Logout:
//                        Toast.makeText(HomeActivity.this, "nav_History", Toast.LENGTH_LONG).show();
                        popupLogout();
                        break;
                }

//                <item
//                android:id="@+id/nav_sumProjectPhotod"
//                android:title="@string/sum_project_photos" />
//                <item
//                android:id="@+id/nav_sumOrder"
//                android:title="@string/sum_order" />
//                <item
//                android:id="@+id/nav_TotalPoin"
//                android:title="Total Poin" />
//                <item
//                android:id="@+id/nav_Logout"
//                android:title="Logout" />
                return false;
            }
        });

    }

    /** fungsi yang digunakan bila user login sebagai Retailer  */
    public void loginRetailer(){

        /** xml untuk retailer */
        setContentView(R.layout.activity_home_retailer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(session.getRetailerType());
        toolbar.setTitle("Toko Bangunan");
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

//        get poin
        updateTask = new UpdateTask();
        updateTask.execute((Void)null);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_right_view);
        View header=navigationView.getHeaderView(0);
        txtProfilName = (TextView)header.findViewById(R.id.txtProfilName);
        txtUserType = (TextView)header.findViewById(R.id.txtUserType);
        imgProfil   = (ImageView)header.findViewById(R.id.imgProfil);
        imgProfil.setImageDrawable(getResources().getDrawable(R.drawable.avatar_retailer));
        txtProfilName.setText("Halo "+session.getName());
        txtUserType.setText(session.getRetailerType());

        NavigationView leftNavigationView = (NavigationView) findViewById(R.id.nav_left_view);
        leftNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent i;
                switch (item.getItemId()) {
                    case R.id.nav_produk:
//                        Toast.makeText(HomeActivity.this, "nav_produk", Toast.LENGTH_LONG).show();
                        i = new Intent(HomeActivity.this, OurProductActivity.class);
                        i.putExtra("stat","");
                        startActivity(i);
                        break;
                    case R.id.nav_marketingsupport:
//                        Toast.makeText(HomeActivity.this, "nav_marketingsupport", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_QandA:
//                        Toast.makeText(HomeActivity.this, "nav_QandA", Toast.LENGTH_LONG).show();
                        i = new Intent(HomeActivity.this, qnaActivity.class);
                        startActivity(i);
                        break;
                    case R.id.nav_Dukungan:
//                        Toast.makeText(HomeActivity.this, "nav_Dukungan", Toast.LENGTH_LONG).show();
                        i = new Intent(HomeActivity.this, TechnicalSupportActivity.class);
                        startActivity(i);
                        break;
                }

                return false;
            }
        });

        NavigationView rightNavigationView = (NavigationView) findViewById(R.id.nav_right_view);
        //edit text pada menu navigasi sebelah kanan
        Menu menuRight = navigationView.getMenu();
        menuRight.findItem(R.id.nav_TotalPoin).setTitle("Total Poin Anda : " + session.getPoin());
        rightNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent i;
                switch (item.getItemId()) {
                    case R.id.nav_TotalPoin:
//                        Toast.makeText(HomeActivity.this, "nav_TotalPoin", Toast.LENGTH_LONG).show();
//                        i = new Intent(HomeActivity.this, TotalOrderActivity.class);
//                        i.putExtra("pilListView","project");
//                        startActivity(i);
                        break;
                    case R.id.nav_TotalOrder:
//                        Toast.makeText(HomeActivity.this, "nav_TotalOrder", Toast.LENGTH_LONG).show();
                        i = new Intent(HomeActivity.this, TotalOrderActivity.class);
                        i.putExtra("pilListView","order");
                        i.putExtra("pil",pil);
                        startActivity(i);
                        break;
                    case R.id.nav_History:
//                        Toast.makeText(HomeActivity.this, "nav_History", Toast.LENGTH_LONG).show();
//                        i = new Intent(HomeActivity.this, HistoryActivity.class);
//                        i.putExtra("pil","project " + pil);
//                        startActivity(i);
                        break;
                    case R.id.nav_Logout:
//                        Toast.makeText(HomeActivity.this, "nav_History", Toast.LENGTH_LONG).show();
                        popupLogout();
                        break;
                }

                return false;
            }
        });


    }


    /** fungsi yang digunakan bila user login sebagai Individu*/
    public void loginIndividu(){

        /** xml untuk retailer */
        setContentView(R.layout.activity_home_individu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Individu");
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_right_view);
        View header=navigationView.getHeaderView(0);
        txtProfilName = (TextView)header.findViewById(R.id.txtProfilName);
        txtUserType = (TextView)header.findViewById(R.id.txtUserType);
        imgProfil   = (ImageView)header.findViewById(R.id.imgProfil);
        imgProfil.setImageDrawable(getResources().getDrawable(R.drawable.avatar_individu));
        txtProfilName.setText("Halo "+session.getName());
        txtUserType.setText(session.getUSERTYPE());

        NavigationView leftNavigationView = (NavigationView) findViewById(R.id.nav_left_view);
        leftNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent i;
                switch (item.getItemId()) {
                    case R.id.nav_produk:
//                        Toast.makeText(HomeActivity.this, "nav_produk", Toast.LENGTH_LONG).show();
                        i = new Intent(HomeActivity.this, OurProductActivity.class);
                        i.putExtra("stat","");
                        startActivity(i);
                        break;
                    case R.id.nav_marketingsupport:
//                        Toast.makeText(HomeActivity.this, "nav_marketingsupport", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_QandA:
//                        Toast.makeText(HomeActivity.this, "nav_QandA", Toast.LENGTH_LONG).show();
                        i = new Intent(HomeActivity.this, qnaActivity.class);
                        startActivity(i);
                        break;
                    case R.id.nav_Dukungan:
//                        Toast.makeText(HomeActivity.this, "nav_Dukungan", Toast.LENGTH_LONG).show();
                        i = new Intent(HomeActivity.this, TechnicalSupportActivity.class);
                        startActivity(i);
                        break;
                }

                return false;
            }
        });

        NavigationView rightNavigationView = (NavigationView) findViewById(R.id.nav_right_view);
        rightNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent i;
                switch (item.getItemId()) {
                    case R.id.nav_TotalPoin:
//                        Toast.makeText(HomeActivity.this, "nav_TotalPoin", Toast.LENGTH_LONG).show();
                        i = new Intent(HomeActivity.this, TotalOrderActivity.class);
                        startActivity(i);
                        break;
                    case R.id.nav_TotalOrder:
//                        Toast.makeText(HomeActivity.this, "nav_TotalOrder", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_History:
//                        Toast.makeText(HomeActivity.this, "nav_History", Toast.LENGTH_LONG).show();
                        i = new Intent(HomeActivity.this, HistoryActivity.class);
                        i.putExtra("pil","project " + pil);
                        startActivity(i);
                        break;
                    case R.id.nav_Logout:
//                        Toast.makeText(HomeActivity.this, "nav_History", Toast.LENGTH_LONG).show();
                        popupLogout();
                        break;
                }

                return false;
            }
        });


    }


//    coba get point

//    private boolean parsingState(JSONArray data){
//        try {
//            total_point = new String[data.length()];
//            for(int i=0;i<data.length();i++){
//                JSONObject jason = data.getJSONObject(i);
//                statesIds[i] = jason.getLong("id");
//                statesName[i] = jason.getString("name");
//            }
//            adapter_state = new AutoCompleteAdapter(context, android.R.layout.simple_list_item_1, statesName, statesIds);
//            act_state.setAdapter(adapter_state);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UpdateTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String errorMessage = "Koneksi Error";
        private String statusError = "Koneksi Error";
        private String userPoint;
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

            String result = apiWeb.getPoint(token);
            if(result==null){
                return false;
            }
            try {
                JSONObject json = new JSONObject(result);
                String status = json.getString("status");
                if(status.compareToIgnoreCase("success")==0){
                    userPoint = json.getString("total_point");

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
            session.setPoin(userPoint);
//            Toast.makeText(context, "poin anda : " + poin, Toast.LENGTH_SHORT).show();
//            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

}
