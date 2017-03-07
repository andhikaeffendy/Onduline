package com.wiradipa.ondulineApplicator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class SimulatorAtapActivity extends AppCompatActivity {

    Spinner spnProduk, spnAtap, spnWarna;
    ArrayAdapter<CharSequence> adapterSpnProduk, adapterSpnAtap, adapterSpnWarna;
    ImageView imgSimulator;
    LinearLayout LinearColorButton;
    String imgChooser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator_atap);

        imgSimulator = (ImageView)findViewById(R.id.imgSimulator);
//        spnProduk = (Spinner)findViewById(R.id.spnProdukSimulatorAtap);
        spnAtap = (Spinner)findViewById(R.id.spnAtapSimulatorAtap);
        LinearColorButton = (LinearLayout)findViewById(R.id.LinearColorButton);

        LinearColorButton.setVisibility(View.GONE);

//        spnAtap.setEnabled(false);

        // Create an ArrayAdapter using the string array and a default spinner layout
//        adapterSpnProduk = ArrayAdapter.createFromResource(this, R.array.pilih_produk_array, android.R.layout.simple_spinner_item);
        adapterSpnAtap = ArrayAdapter.createFromResource(this, R.array.pilih_atap_onduline_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
//        adapterSpnProduk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSpnAtap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
//        spnProduk.setAdapter(adapterSpnProduk);
        spnAtap.setAdapter(adapterSpnAtap);

//        spnProduk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//               Toast.makeText(SimulatorAtapActivity.this, spnProduk.getSelectedItem().toString() , Toast.LENGTH_LONG).show();
//                //setRoofTypeSpinner(spnProduk.getSelectedItem().toString());
//                simulatorCOntroller(spnProduk.getSelectedItem().toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        spnAtap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(SimulatorAtapActivity.this, spnAtap.getSelectedItem().toString() , Toast.LENGTH_LONG).show();
                simulatorCOntroller(spnAtap.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void simulatorCOntroller(String stat){
        switch (stat){
            case "- Pilih Produk -":
                LinearColorButton.setVisibility(View.GONE);
                spnAtap.setEnabled(false);
                break;
            case "ONDUVILA":
                LinearColorButton.setVisibility(View.GONE);
                adapterSpnAtap = ArrayAdapter.createFromResource(this,
                        R.array.pilih_atap_onduvila_array, android.R.layout.simple_spinner_item);
                spnAtap.setEnabled(true);
                break;
            case "ONDUVILLA":
                LinearColorButton.setVisibility(View.GONE);
                //Bila Ada
                break;
            case "BARDOLINE":
                LinearColorButton.setVisibility(View.GONE);
                //Bila Ada
                break;
            case "BITULINE":
                LinearColorButton.setVisibility(View.GONE);
                //Bila Ada
                break;
            case "- Pilih Atap -":
                LinearColorButton.setVisibility(View.GONE);
                break;
            case "Modern Roof":
                LinearColorButton.setVisibility(View.VISIBLE);
                imgChooser=stat;
                imgSimulator.setImageResource(R.drawable.onduvilla_3d_modern_house_h3_ebony_black_v1);
                break;
            case "Round Roof":
                LinearColorButton.setVisibility(View.VISIBLE);
                imgSimulator.setImageResource(R.drawable.maisonronde_h1_ebony_black_v1);
                imgChooser=stat;
                break;
            case "Traditional Roof":
                LinearColorButton.setVisibility(View.VISIBLE);
                imgSimulator.setImageResource(R.drawable.onduvilla_3d_traditional_house_h3_ebony_black_v2_tm_2016);
                imgChooser=stat;
                break;
        }

    }

    public void setImageSimulator(String produk){
        switch (produk){
            case "SHADED RED":
                imgSimulator.setImageResource(R.drawable.odv_shaded_red);
                break;
            case "SHADED GREEN":
                imgSimulator.setImageResource(R.drawable.odv_shaded_green);
                break;
            case "SHADED BROWN":
                imgSimulator.setImageResource(R.drawable.odv_shaded_brown_3d);
                break;
            case "ANTHRACITE BLACK":
                imgSimulator.setImageResource(R.drawable.odv_antrachite_black);
                break;
            case "FOREST GREEN":
                imgSimulator.setImageResource(R.drawable.odv_forest_green);
                break;
            case "EBONY BLACK":
                imgSimulator.setImageResource(R.drawable.odv_ebony_black);
                break;
            case "TERRA COTA 3D":
                imgSimulator.setImageResource(R.drawable.odv_terracota_3d);
                break;
            case "CLASSIC RED 3D":
                imgSimulator.setImageResource(R.drawable.odv_classic_red);
                break;
            case "BROWN":
                imgSimulator.setImageResource(R.drawable.odl_brown);
                break;
            case "BLACK":
                imgSimulator.setImageResource(R.drawable.odl_black);
                break;
            case "RED":
                imgSimulator.setImageResource(R.drawable.odl_red);
                break;
            case "GREEN":
                imgSimulator.setImageResource(R.drawable.odl_green);
                break;


            case "EBONY BLACK Modern Roof":
                imgSimulator.setImageResource(R.drawable.onduvilla_3d_modern_house_h3_ebony_black_v1);
                break;
            case "TERRACOTA Modern Roof":
                imgSimulator.setImageResource(R.drawable.onduvilla_3d_modern_house_g3_terracotta_v2_tm_2016);
                break;
            case "FOREST GREEN Modern Roof":
                imgSimulator.setImageResource(R.drawable.onduvilla_3d_modern_house_f3_forest_green_v2_tm_2016);
                break;
            case "CLASSIC RED Modern Roof":
                imgSimulator.setImageResource(R.drawable.onduvilla_3d_modern_house_e3_classic_red_v2_tm_2016);
                break;
            case "ANTHRACITE BLACK Modern Roof":
                imgSimulator.setImageResource(R.drawable.onduvilla_3d_modern_house_d3_antracite_black_v1_tm_2016);
                break;
            case "SHADED BROWN Modern Roof":
                imgSimulator.setImageResource(R.drawable.onduvilla_3d_modern_house_c3_shaded_brown_v1_tm_2016);
                break;
            case "SHADED GREEN Modern Roof":
                imgSimulator.setImageResource(R.drawable.onduvilla_3d_modern_house_b3_shaded_green_v1_tm_2016);
                break;
            case "SHADED RED Modern Roof":
                imgSimulator.setImageResource(R.drawable.onduvilla_3d_modern_house_a3_shaded_red_v1);
                break;


            case "EBONY BLACK Round Roof":
                imgSimulator.setImageResource(R.drawable.maisonronde_h1_ebony_black_v1);
                break;
            case "TERRACOTA Round Roof":
                imgSimulator.setImageResource(R.drawable.maisonronde_g1_terracotta_v1);
                break;
            case "FOREST GREEN Round Roof":
                imgSimulator.setImageResource(R.drawable.maisonronde_f1_forest_green_v1);
                break;
            case "CLASSIC RED Round Roof":
                imgSimulator.setImageResource(R.drawable.maisonronde_e1_classic_red_v1);
                break;
            case "ANTHRACITE BLACK Round Roof":
                imgSimulator.setImageResource(R.drawable.maisonronde_d1_antracite_black_v1);
                break;
            case "SHADED BROWN Round Roof":
                imgSimulator.setImageResource(R.drawable.maisonronde_c1_shaded_brown_v1);
                break;
            case "SHADED GREEN Round Roof":
                imgSimulator.setImageResource(R.drawable.maisonronde_b1_shaded_green_v1_0);
                break;
            case "SHADED RED Round Roof":
                imgSimulator.setImageResource(R.drawable.maisonronde_a1_shaded_red_v1);
                break;



            case "EBONY BLACK Traditional Roof":
                imgSimulator.setImageResource(R.drawable.onduvilla_3d_traditional_house_h3_ebony_black_v2_tm_2016);
                break;
            case "TERRACOTA Traditional Roof":
                imgSimulator.setImageResource(R.drawable.onduvilla_3d_traditional_house_g3_terracotta_v2_tm_2016);
                break;
            case "FOREST GREEN Traditional Roof":
                imgSimulator.setImageResource(R.drawable.onduvilla_3d_traditional_house_f3_forest_green_v2_tm_2016);
                break;
            case "CLASSIC RED Traditional Roof":
                imgSimulator.setImageResource(R.drawable.onduvilla_3d_traditional_house_e3_classic_red_v1_tm_2016);
                break;
            case "ANTHRACITE BLACK Traditional Roof":
                imgSimulator.setImageResource(R.drawable.onduvilla_3d_traditional_house_d3_anthracite_black_v1_tm_2016);
                break;
            case "SHADED BROWN Traditional Roof":
                imgSimulator.setImageResource(R.drawable.onduvilla_3d_traditional_house_c3_shaded_brown_v1_tm_2016);
                break;
            case "SHADED GREEN Traditional Roof":
                imgSimulator.setImageResource(R.drawable.onduvilla_3d_traditional_house_b3_shaded_green_v1_tm_2016);
                break;
            case "SHADED RED Traditional Roof":
                imgSimulator.setImageResource(R.drawable.onduvilla_3d_traditional_house_a3_shaded_red_v1_tm_2016);
                break;
        }
    }
    public void simulator(View v){
        switch (v.getId()){
            case R.id.btnEbonyBlack:
//                Toast.makeText(SimulatorAtapActivity.this, "EBONY BLACK "+imgChooser , Toast.LENGTH_LONG).show();
                setImageSimulator("EBONY BLACK "+imgChooser);
                break;
            case R.id.btnTerracota:
                setImageSimulator("TERRACOTA "+imgChooser);
                break;
            case R.id.btnForestGreen:
                setImageSimulator("FOREST GREEN "+imgChooser);
                break;
            case R.id.btnRedClassic:
                setImageSimulator("CLASSIC RED "+imgChooser);
                break;
            case R.id.btnAntraciteBlack:
                setImageSimulator("ANTHRACITE BLACK "+imgChooser);
                break;
            case R.id.btnShadedBrown:
                setImageSimulator("SHADED BROWN "+imgChooser);
                break;
            case R.id.btnShadedRed:
                setImageSimulator("SHADED RED "+imgChooser);
                break;
            case R.id.btnShadedGreen:
                setImageSimulator("SHADED GREEN "+imgChooser);
                break;
        }
    }






    //----------------------------------------------------------------------------------------------


    public void setColorSpinner(String produk){
        switch (produk){
            case "ONDUVILA":
                adapterSpnWarna = ArrayAdapter.createFromResource(this,
                        R.array.pilih_warna_onduvila_array, android.R.layout.simple_spinner_item);
                spnWarna.setEnabled(true);
                break;
            case "ONDULINE":
                adapterSpnWarna = ArrayAdapter.createFromResource(this,
                        R.array.pilih_warna_onduline_array, android.R.layout.simple_spinner_item);
                spnWarna.setEnabled(true);
                break;
            case "BARDULINE":
                spnWarna.setEnabled(false);
                break;
            case "BITULINE":
                spnWarna.setEnabled(false);
                break;
        }
        adapterSpnWarna.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnWarna.setAdapter(adapterSpnWarna);
    }

    public void setRoofTypeSpinner(String produk){
        switch (produk){
            case "ONDUVILA":
                adapterSpnAtap = ArrayAdapter.createFromResource(this,
                        R.array.pilih_atap_onduvila_array, android.R.layout.simple_spinner_item);
                spnAtap.setEnabled(true);
                break;
            case "ONDULINE":
                spnAtap.setEnabled(false);
                break;
            case "BARDULINE":
                spnAtap.setEnabled(false);
                break;
            case "BITULINE":
                spnAtap.setEnabled(false);;
                break;
            case " - Pilih Produk - ":
                LinearColorButton.setVisibility(View.GONE);
                break;
        }
        adapterSpnAtap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAtap.setAdapter(adapterSpnAtap);
    }

}
