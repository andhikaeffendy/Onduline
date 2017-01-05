package com.wiradipa.ondulineApplicator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SimulatorAtapActivity extends AppCompatActivity {

    Spinner spnProduk, spnAtap, spnWarna;
    ArrayAdapter<CharSequence> adapterSpnProduk, adapterSpnAtap, adapterSpnWarna;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator_atap);

        spnProduk = (Spinner)findViewById(R.id.spnProduk);
        spnAtap = (Spinner)findViewById(R.id.spnAtap);
        spnWarna = (Spinner)findViewById(R.id.spnWarna);

        // Create an ArrayAdapter using the string array and a default spinner layout
        adapterSpnProduk = ArrayAdapter.createFromResource(this,
                R.array.pilih_produk_onduline_array, android.R.layout.simple_spinner_item);
        adapterSpnAtap = ArrayAdapter.createFromResource(this,
                R.array.pilih_atap_onduline_array, android.R.layout.simple_spinner_item);
        adapterSpnWarna = ArrayAdapter.createFromResource(this,
                R.array.pilih_warna_onduline_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpnProduk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSpnAtap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSpnWarna.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spnProduk.setAdapter(adapterSpnProduk);
        spnAtap.setAdapter(adapterSpnAtap);
        spnWarna.setAdapter(adapterSpnWarna);
    }
}
