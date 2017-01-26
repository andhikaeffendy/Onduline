package com.wiradipa.ondulineApplicator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wiradipa.ondulineApplicator.lib.calc_formula;

public class CalculatorActivity extends AppCompatActivity {


    String pil;
    TextView txtMenuProductName, txtLuasAtap,txtJumlahAtap;
    EditText edittextTiltAngle;
    Spinner spnJenisRangka, spnJenisAtap;
    ArrayAdapter<CharSequence> adapterspnJenisRangka, adapterspnJenisAtap;

    calc_formula formula;
    int statRoof;   // digunakan untuk mendefinisikan onduline ==1 atau onduvila ==2
    double luasAtap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");
        txtMenuProductName = (TextView)findViewById(R.id.txtMenuProductName);
        txtLuasAtap = (TextView)findViewById(R.id.txtLuasAtap);
        txtJumlahAtap = (TextView)findViewById(R.id.txtJumlahAtap);
        edittextTiltAngle = (EditText)findViewById(R.id.edittextTiltAngle);
        formula = new calc_formula();
        spnJenisAtap = (Spinner)findViewById(R.id.spnJenisAtap);
        spnJenisRangka = (Spinner)findViewById(R.id.spnJenisRangka);

        txtMenuProductName.setText(pil + "®");



        // Create an ArrayAdapter using the string array and a default spinner layout
        adapterspnJenisAtap = ArrayAdapter.createFromResource(this, R.array.pilih_jenis_atap_array, android.R.layout.simple_spinner_item);
        adapterspnJenisRangka = ArrayAdapter.createFromResource(this, R.array.pilih_jenis_rangka_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapterspnJenisAtap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterspnJenisRangka.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spnJenisAtap.setAdapter(adapterspnJenisAtap);
        spnJenisRangka.setAdapter(adapterspnJenisRangka);

        edittextTiltAngle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getSumOfRoof(s);
            }
        });

    }

    public void getSumOfRoof(Editable s){

        int jumlahAtap;

        //cek memilih onduline atau onduvilla
        if(spnJenisAtap.getSelectedItem().toString().equals("ONDULINE")){
            statRoof=1;
        }else {
            statRoof=2;
        }


        //perhitungan sesuai dengan pilihan user
        if(s.toString().isEmpty()){
            txtLuasAtap.setText("");
            txtJumlahAtap.setText("Jumlah Atap adalah \n...");
        }else if (statRoof==2 && Integer.parseInt(s.toString())<15 ){
            txtJumlahAtap.setText("Onduvilla minimal 15 derajat");
        }else if(Integer.parseInt(s.toString())<5){
            txtLuasAtap.setText("");
            txtJumlahAtap.setText("Jumlah Atap adalah \n...");
        }else {
            luasAtap = formula.luas(Double.parseDouble(s.toString()));
            txtLuasAtap.setText((int) Math.ceil(luasAtap)+ "");

            jumlahAtap=(int) Math.ceil(formula.sumOfRoof(statRoof,Integer.parseInt(edittextTiltAngle.getText().toString()),luasAtap));
            txtJumlahAtap.setText("Jumlah Atap " + spnJenisAtap.getSelectedItem().toString() + "\n" + jumlahAtap + " Lembar" );
            //Toast.makeText(SimulatorAtapActivity.this, spnProduk.getSelectedItem().toString() , Toast.LENGTH_LONG).show();
            //setColorSpinner(spnProduk.getSelectedItem().toString());
        }

    }
}
