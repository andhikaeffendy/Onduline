package com.wiradipa.ondulineApplicator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wiradipa.ondulineApplicator.lib.AppSession;

public class HomeActivity extends AppCompatActivity{

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    private Context context;
    private AppSession session;

    String pil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        session = new AppSession(context);
        session.checkSession();
        pil=session.getUSERTYPE();
        //class ini memuat xml sesuai dengan user masuk sebagai aplikator atau retailer
        setLayout(pil);
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
        }
    }

    public void onClickHome(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btnOrderSaya:
                Toast.makeText(this, "btnOrderSaya", Toast.LENGTH_LONG).show();
                i = new Intent(this, HistoryActivity.class);
                i.putExtra("pil", "order " + pil);
                startActivity(i);
                break;
            case R.id.btnProgram:
                Toast.makeText(this, "btnProgram", Toast.LENGTH_LONG).show();
                i = new Intent(this, ProgramActivity.class);
                startActivity(i);
                break;
            case R.id.btnProyekSaya:
                Toast.makeText(this, "btnProyekSaya", Toast.LENGTH_LONG).show();
                i = new Intent(this, HistoryActivity.class);
                i.putExtra("pil","project " + pil); //---------------------
                startActivity(i);
                break;
            case R.id.btnTechnicalSupport:
                Toast.makeText(this, "btnTechnicalSupport", Toast.LENGTH_LONG).show();
                i = new Intent(this, TechnicalSupportActivity.class);
                startActivity(i);
                break;
            case R.id.btnProduk:
                Toast.makeText(this, "btnProduk", Toast.LENGTH_LONG).show();
                i = new Intent(this, OurProductActivity.class);
                startActivity(i);
                break;
            case R.id.btnDukunganMarketing:
                Toast.makeText(this, "btnDukunganMarketing", Toast.LENGTH_LONG).show();
                i = new Intent(this, MarketingSupportActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {  /*Closes the Appropriate Drawer*/
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
            System.exit(0);
        }
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
                Toast.makeText(HomeActivity.this, "open nav right", Toast.LENGTH_LONG).show();
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
        toolbar.setTitle("Aplicator");
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView leftNavigationView = (NavigationView) findViewById(R.id.nav_left_view);
        leftNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent i;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Toast.makeText(HomeActivity.this, "nav_home", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_produk:
//                        Toast.makeText(HomeActivity.this, "nav_produk", Toast.LENGTH_LONG).show();
                        i = new Intent(HomeActivity.this, OurProductActivity.class);
                        startActivity(i);
                        break;
                    case R.id.nav_marketingsupport:
                        Toast.makeText(HomeActivity.this, "nav_marketingsupport", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_QandA:
                        Toast.makeText(HomeActivity.this, "nav_QandA", Toast.LENGTH_LONG).show();
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
                        i = new Intent(HomeActivity.this, TotalPoinActivity.class);
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
                        session.logout();
                        break;
                }

                return false;
            }
        });

    }

    /** fungsi yang digunakan bila user login sebagai Retailer  */
    public void loginRetailer(){

        /** xml untuk retailer */
        setContentView(R.layout.activity_home_retailer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Retailer");
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView leftNavigationView = (NavigationView) findViewById(R.id.nav_left_view);
        leftNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent i;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Toast.makeText(HomeActivity.this, "nav_home", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_produk:
                        Toast.makeText(HomeActivity.this, "nav_produk", Toast.LENGTH_LONG).show();
                        i = new Intent(HomeActivity.this, OurProductActivity.class);
                        startActivity(i);
                        break;
                    case R.id.nav_marketingsupport:
                        Toast.makeText(HomeActivity.this, "nav_marketingsupport", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_QandA:
                        Toast.makeText(HomeActivity.this, "nav_QandA", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_Dukungan:
                        Toast.makeText(HomeActivity.this, "nav_Dukungan", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(HomeActivity.this, "nav_TotalPoin", Toast.LENGTH_LONG).show();
                        i = new Intent(HomeActivity.this, TotalPoinActivity.class);
                        startActivity(i);
                        break;
                    case R.id.nav_TotalOrder:
                        Toast.makeText(HomeActivity.this, "nav_TotalOrder", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_History:
                        Toast.makeText(HomeActivity.this, "nav_History", Toast.LENGTH_LONG).show();
                        i = new Intent(HomeActivity.this, HistoryActivity.class);
                        i.putExtra("pil","project " + pil);
                        startActivity(i);
                        break;
                }

                return false;
            }
        });


    }
}
