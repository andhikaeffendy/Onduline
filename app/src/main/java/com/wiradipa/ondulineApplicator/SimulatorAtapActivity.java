package com.wiradipa.ondulineApplicator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class SimulatorAtapActivity extends AppCompatActivity {

    Spinner spnProduk, spnAtap, spnWarna;
    ArrayAdapter<CharSequence> adapterSpnProduk, adapterSpnAtap, adapterSpnWarna;
    ImageView imgSimulator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator_atap);

        imgSimulator = (ImageView)findViewById(R.id.imgSimulator);
        spnProduk = (Spinner)findViewById(R.id.spnProdukSimulatorAtap);
        spnAtap = (Spinner)findViewById(R.id.spnAtapSimulatorAtap);
        spnWarna = (Spinner)findViewById(R.id.spnWarnaSimulatorAtap);

        spnAtap.setEnabled(false);
        spnWarna.setEnabled(false);

        // Create an ArrayAdapter using the string array and a default spinner layout
        adapterSpnProduk = ArrayAdapter.createFromResource(this, R.array.pilih_produk_array, android.R.layout.simple_spinner_item);
        adapterSpnAtap = ArrayAdapter.createFromResource(this, R.array.pilih_atap_onduline_array, android.R.layout.simple_spinner_item);
        adapterSpnWarna = ArrayAdapter.createFromResource(this, R.array.pilih_warna_onduline_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapterSpnProduk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSpnAtap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSpnWarna.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spnProduk.setAdapter(adapterSpnProduk);
        spnAtap.setAdapter(adapterSpnAtap);
        spnWarna.setAdapter(adapterSpnWarna);

        spnProduk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(SimulatorAtapActivity.this, spnProduk.getSelectedItem().toString() , Toast.LENGTH_LONG).show();
                setColorSpinner(spnProduk.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnWarna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setImageSimulator(spnWarna.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

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
                //imgSimulator.setImageDrawable(getResources().getDrawable(R.drawable.odl_brown));
                //imgSimulator.setBackgroundResource(R.drawable.odl_brown);
                break;
            case "BLACK":
                imgSimulator.setImageResource(R.drawable.odl_black);
                //imgSimulator.setImageDrawable(getResources().getDrawable(R.drawable.odl_black));
                //imgSimulator.setBackgroundResource(R.drawable.odl_black);
                break;
            case "RED":
                imgSimulator.setImageResource(R.drawable.odl_red);
                //imgSimulator.setImageDrawable(getResources().getDrawable(R.drawable.odl_red));
                //imgSimulator.setBackgroundResource(R.drawable.odl_red);
                break;
            case "GREEN":
                imgSimulator.setImageResource(R.drawable.odl_green);
                //imgSimulator.setImageDrawable(getResources().getDrawable(R.drawable.odl_green));
                //imgSimulator.setBackgroundResource(R.drawable.odl_green);
                break;
        }
    }

}
