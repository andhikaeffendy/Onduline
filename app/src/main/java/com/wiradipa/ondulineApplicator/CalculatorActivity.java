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
    TextView txtMenuProductName, txtLuasAtap,txtJumlahAtap, txtresultSumOfNok, txtLastSumOfScrup, txtLastSumOfNok, txtLastSumOfRoof, txtSumOfScrup;
    EditText edittextTiltAngle, etPanjangBubungan, etPanjangJuraiLuar, etPanjangJuraiDalam, scrupOndulineForRoof, scrupOndulineForBubungan;
    Spinner spnJenisRangka, spnJenisAtap, spnNokOnduline;
    ArrayAdapter<CharSequence> adapterspnJenisRangka, adapterspnJenisAtap, adapterspnNokOnduline;

    calc_formula formula;
    private int statRoof, statFrame;   // digunakan untuk mendefinisikan onduline ==1 atau onduvila ==2
    private double luasAtap, lastSumOfNok, lastSumOfRoof, lastSumOfScrup, lastsumOfBubunganOnduline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");
        txtMenuProductName = (TextView)findViewById(R.id.txtMenuProductName);
        txtLuasAtap = (TextView)findViewById(R.id.txtLuasAtap);
        txtJumlahAtap = (TextView)findViewById(R.id.txtJumlahAtap);
        txtresultSumOfNok = (TextView)findViewById(R.id.txtresultSumOfNok);

        txtLastSumOfRoof = (TextView)findViewById(R.id.txtLastSumOfRoof);
        txtLastSumOfNok = (TextView)findViewById(R.id.txtLastSumOfNok);
        txtLastSumOfScrup = (TextView)findViewById(R.id.txtLastSumOfScrup);

        txtSumOfScrup = (TextView)findViewById(R.id.txtSumOfScrup);

        etPanjangBubungan = (EditText)findViewById(R.id.etPanjangBubungan);
        etPanjangJuraiLuar = (EditText)findViewById(R.id.etPanjangJuraiLuar);
        etPanjangJuraiDalam = (EditText)findViewById(R.id.etPanjangJuraiDalam);
        scrupOndulineForRoof = (EditText)findViewById(R.id.scrupOndulineForRoof);
        scrupOndulineForBubungan = (EditText)findViewById(R.id.scrupOndulineForBubungan);
        edittextTiltAngle = (EditText)findViewById(R.id.edittextTiltAngle);
        formula = new calc_formula();
        spnJenisAtap = (Spinner)findViewById(R.id.spnJenisAtap);
        spnJenisRangka = (Spinner)findViewById(R.id.spnJenisRangka);
        spnNokOnduline = (Spinner)findViewById(R.id.spnNokOnduline);

        txtMenuProductName.setText(pil + "®");



        // Create an ArrayAdapter using the string array and a default spinner layout
        adapterspnJenisAtap = ArrayAdapter.createFromResource(this, R.array.pilih_jenis_atap_array, android.R.layout.simple_spinner_item);
        adapterspnJenisRangka = ArrayAdapter.createFromResource(this, R.array.pilih_jenis_rangka_array, android.R.layout.simple_spinner_item);
        adapterspnNokOnduline = ArrayAdapter.createFromResource(this, R.array.yes_or_no_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapterspnJenisAtap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterspnJenisRangka.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterspnNokOnduline.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spnJenisAtap.setAdapter(adapterspnJenisAtap);
        spnJenisRangka.setAdapter(adapterspnJenisRangka);
        spnNokOnduline.setAdapter(adapterspnNokOnduline);

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

    public void onClickcalculator(View v){
        switch (v.getId()){
            case R.id.btnSumOfNok:
                getSumOfNok();
                lastsumOfBubunganOnduline = formula.sumOfNokOndulineBubungan(statFrame,(int) Math.ceil(lastSumOfNok));
                scrupOndulineForBubungan.setText(lastsumOfBubunganOnduline+"");
                break;
            case R.id.btnSumOfScrup:
                txtSumOfScrup.setText((lastsumOfBubunganOnduline + lastSumOfScrup) + " skrup");
                txtLastSumOfScrup.setText((int) (lastsumOfBubunganOnduline + lastSumOfScrup) + "");

        }
    }

    public void getSumOfRoof(Editable s){

        int jumlahAtap;

        //cek memilih onduline atau onduvilla
        if(spnJenisAtap.getSelectedItem().toString().equals("ONDULINE")){
            statRoof=1;
        }else {
            statRoof=2;
        }
        //cek frame kayu dan besi
        if(spnJenisRangka.getSelectedItem().toString().equals("RANGKA KAYU")){
            statFrame=1;
        }else {
            statFrame=2;
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

            lastSumOfRoof = formula.sumOfRoof(statRoof,Integer.parseInt(edittextTiltAngle.getText().toString()),luasAtap);
            lastSumOfScrup = formula.sumOfScrup(statFrame,statRoof,Integer.parseInt(edittextTiltAngle.getText().toString()),(int) Math.ceil(lastSumOfRoof));
            lastsumOfBubunganOnduline = formula.sumOfNokOndulineBubungan(statFrame,(int) Math.ceil(lastSumOfNok));

            jumlahAtap=(int) Math.ceil(lastSumOfRoof);
            txtJumlahAtap.setText("Jumlah Atap " + spnJenisAtap.getSelectedItem().toString() + "\n" + jumlahAtap + " Lembar" );
            txtLastSumOfRoof.setText(jumlahAtap + "" );


            scrupOndulineForRoof.setText(lastSumOfScrup+"");
            scrupOndulineForBubungan.setText(lastsumOfBubunganOnduline+"");
            //Toast.makeText(SimulatorAtapActivity.this, spnProduk.getSelectedItem().toString() , Toast.LENGTH_LONG).show();
            //setColorSpinner(spnProduk.getSelectedItem().toString());
        }
    }

    public void getSumOfNok(){


        int statNok, panjangBubungan, panjangJuraiLuar, panjangJuraiDalam;
        panjangBubungan=Integer.parseInt(etPanjangBubungan.getText().toString());
        panjangJuraiLuar=Integer.parseInt(etPanjangJuraiLuar.getText().toString());
        panjangJuraiDalam=Integer.parseInt(etPanjangJuraiDalam.getText().toString());

        if (spnNokOnduline.getSelectedItem().toString().equals("ya")){
            statNok=1;
        }else {
            statNok=2;
        }

        lastSumOfNok  = formula.sumOfNok(statNok,panjangBubungan,panjangJuraiLuar,panjangJuraiDalam);
        txtresultSumOfNok.setText(Math.ceil(formula.sumOfNok(statNok,panjangBubungan,panjangJuraiLuar,panjangJuraiDalam)) + " lembar");
        txtLastSumOfNok.setText((int) Math.ceil(formula.sumOfNok(statNok,panjangBubungan,panjangJuraiLuar,panjangJuraiDalam)) + "");

    }
}
