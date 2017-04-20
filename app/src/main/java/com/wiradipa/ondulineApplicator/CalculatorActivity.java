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

//    Spinner bagian atas
    Spinner spnJenisRangka, spnJenisAtap;

    //    EditText TextView Onduline / Onduvilla Sheet
    EditText et_pitchAngle, et_roofArea;
    TextView txtJumlahAtap,txtLastSumOfRoof;

    //    EditText TextView Onduline Ridges
    EditText et_ridgeLength, et_hipLength;
    TextView txtresultSumOfNok,txtLastSumOfRidges;

    //    EditText TextView Onduline Screws
    TextView et_screwsForRoof, et_screwsForRidge;
    TextView txtSumOfScrup,txtLastSumOfScrews;

    TextView txt_sumOFRoof;

    //      TextView Resume
    TextView txt_resumeSumRoof, txt_resumeSumRidges, txt_resumeSumScrews;

   ArrayAdapter<CharSequence> adapterspnJenisRangka, adapterspnJenisAtap, adapterspnNokOnduline;

    calc_formula formula;
    private int statRoof, statFrame;   // digunakan untuk mendefinisikan onduline ==1 atau onduvila ==2
    private double luasAtap, lastSumOfNok, lastSumOfRoof, lastSumOfScrup, lastsumOfBubunganOnduline;



    private int roofArea, pitchAngle, ridgeLength, hipLength;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        formula = new calc_formula();


        //    EditText TextView Onduline / Onduvilla Sheet
        et_pitchAngle= (EditText) findViewById(R.id.et_pitchAngle);
        et_roofArea= (EditText) findViewById(R.id.et_roofArea);
        txtJumlahAtap= (TextView) findViewById(R.id.txtJumlahAtap);
        txtLastSumOfRoof= (TextView) findViewById(R.id.txtLastSumOfRoof);

        //    EditText TextView Onduline Ridges
        et_ridgeLength= (EditText) findViewById(R.id.et_ridgeLength);
        et_hipLength= (EditText) findViewById(R.id.et_hipLength);
        txtresultSumOfNok= (TextView) findViewById(R.id.txtresultSumOfNok);
        txtLastSumOfRidges= (TextView) findViewById(R.id.txtLastSumOfRidges);

        //    EditText TextView Onduline Screws
        et_screwsForRoof= (TextView) findViewById(R.id.et_screwsForRoof);
        et_screwsForRidge= (TextView) findViewById(R.id.et_screwsForRidge);
        txtSumOfScrup= (TextView) findViewById(R.id.txtSumOfScrup);
        txtLastSumOfScrews= (TextView) findViewById(R.id.txtLastSumOfScrews);

        //      TextView Resume
        txt_resumeSumRoof= (TextView) findViewById(R.id.txt_resumeSumRoof);
        txt_resumeSumRidges= (TextView) findViewById(R.id.txt_resumeSumRidges);
        txt_resumeSumScrews= (TextView) findViewById(R.id.txt_resumeSumRoof);

        txt_sumOFRoof = (TextView)findViewById(R.id.txt_sumOFRoof);

        spnJenisAtap = (Spinner)findViewById(R.id.spnJenisAtap);
        spnJenisRangka = (Spinner)findViewById(R.id.spnJenisRangka);

        // Create an ArrayAdapter using the string array and a default spinner layout
        adapterspnJenisAtap = ArrayAdapter.createFromResource(this, R.array.pilih_jenis_atap_array, android.R.layout.simple_spinner_item);
        adapterspnJenisRangka = ArrayAdapter.createFromResource(this, R.array.pilih_jenis_rangka_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapterspnJenisAtap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterspnJenisRangka.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spnJenisAtap.setAdapter(adapterspnJenisAtap);
        spnJenisRangka.setAdapter(adapterspnJenisRangka);

//        et_roofArea.setText("0");
//        et_pitchAngle.setText("0");
//        et_ridgeLength.setText("0");
//        et_hipLength.setText("0");
        txt_sumOFRoof.setText("JUMLAH ATAP ONDULINE®");

        roofArea    = 0;
        pitchAngle  = 0;
        ridgeLength = 0;
        hipLength   = 0;


        spnJenisAtap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSumOfRoofForPitchAngle(et_pitchAngle.getText());


//            txt_sumOFRoof.setText("JUMLAH ATAP ONDULINE®");
//        Toast.makeText(CalculatorActivity.this, et_pitchAngle.getText().toString(), Toast.LENGTH_LONG).show();
                if (id==0){
                    txt_sumOFRoof.setText("JUMLAH ATAP ONDULINE®");
                    txt_resumeSumRoof.setText(getString(R.string.calc_sumOFRoofOduline));
//                    txt_resumeSumRidges.setText(getString(R.string.calc_sumOFRidgeOduline));
                }else {
                    txt_sumOFRoof.setText("JUMLAH ATAP ONDUVILLA®");
                    txt_resumeSumRoof.setText(getString(R.string.calc_sumOFRoofOnduvilla));
//                    txt_resumeSumRidges.setText(getString(R.string.calc_sumOFRidgeOnduvilla));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnJenisRangka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSumOfRoofForPitchAngle(et_pitchAngle.getText());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        et_pitchAngle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getSumOfRoofForPitchAngle(s);
            }
        });
        et_roofArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getSumOfRoofForRoofArea(s);
            }
        });


        et_ridgeLength.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getSumOfRidgesForRidgesLength(s);
            }
        });
        et_hipLength.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getSumOfRidgesForHipLength(s);
            }
        });

    }

    public void refreshFormula(){

        //cek memilih onduline atau onduvilla
        if(spnJenisAtap.getSelectedItem().toString().equals("ONDULINE®")){
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


//        F ; sumOfRoof
        double totalSheet =formula.sumOfRoof(statRoof,pitchAngle,roofArea);
//        Toast.makeText(CalculatorActivity.this, Integer.parseInt(et_pitchAngle.getText().toString()) + "" , Toast.LENGTH_LONG).show();
        txtJumlahAtap.setText((int)Math.ceil(totalSheet) + " Lembar");
        txtLastSumOfRoof.setText((int)Math.ceil(totalSheet) + " Lembar");

//        F ; sumOfRidges
        double totalRidges = formula.sumOfRidges(ridgeLength,hipLength);
        txtresultSumOfNok.setText((int)Math.ceil(totalRidges) + " Lembar");
        txtLastSumOfRidges.setText((int)Math.ceil(totalRidges) + " Lembar");

//        F ; sumOfScrewsForRoof
        double sumOfScrewsForRoof = formula.sumOfScrewsForRoof(statFrame,statRoof,pitchAngle,(int) Math.ceil(totalSheet));
        et_screwsForRoof.setText(sumOfScrewsForRoof + "");

//        F ; sumOfScrewsForRidge
        double sumOfScrewsForRidge = formula.sumOfScrewsForRidge((int) Math.ceil(totalRidges));
        et_screwsForRidge.setText(sumOfScrewsForRidge + "");

//        F ; sumOfScrews
        int totalScrews = (int)Math.ceil(formula.sumOfScrews(sumOfScrewsForRoof,sumOfScrewsForRidge));
        txtSumOfScrup.setText(totalScrews + " Buah");
        txtLastSumOfScrews.setText(totalScrews + " Buah");


        //perhitungan sesuai dengan pilihan user
        if(et_pitchAngle.getText().toString().isEmpty()){
            pitchAngle=0;
            txtJumlahAtap.setText("Jumlah Atap adalah \n...");
        }else if (statRoof==2 && Integer.parseInt(et_pitchAngle.getText().toString())<15 ){
            txtJumlahAtap.setText("Onduvilla minimal 15 derajat");
            txtSumOfScrup.setText("Onduvilla minimal 15 derajat");
            txtLastSumOfRoof.setText("0");
            txtLastSumOfScrews.setText("0");
            et_screwsForRoof.setText("0");
        }else if(Integer.parseInt(et_pitchAngle.getText().toString())<5){
            txtJumlahAtap.setText("Jumlah Atap adalah \n...");
        }else {
            pitchAngle = Integer.parseInt(et_pitchAngle.getText().toString());
//            refreshFormula();
        }

    }


    public void getSumOfRoofForPitchAngle(Editable s){

        int jumlahAtap;

        //cek memilih onduline atau onduvilla
        if(spnJenisAtap.getSelectedItem().toString().equals("ONDULINE®")){
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

//        Toast.makeText(CalculatorActivity.this, "statRoof : " + statRoof , Toast.LENGTH_LONG).show();

        //perhitungan sesuai dengan pilihan user
        if(et_pitchAngle.getText().toString().isEmpty()){
            pitchAngle=0;
            txtJumlahAtap.setText("Jumlah Atap adalah \n...");
        }else if (statRoof==2 && Integer.parseInt(et_pitchAngle.getText().toString())<15 ){
            txtJumlahAtap.setText("Onduvilla minimal 15 derajat");
            txtSumOfScrup.setText("Onduvilla minimal 15 derajat");
            txtLastSumOfRoof.setText("0");
            txtLastSumOfScrews.setText("0");
            et_screwsForRoof.setText("0");
        }else if(Integer.parseInt(et_pitchAngle.getText().toString())<5){
            txtJumlahAtap.setText("Jumlah Atap adalah \n...");
        }else {
            pitchAngle = Integer.parseInt(et_pitchAngle.getText().toString());
            refreshFormula();
        }
    }
    public void getSumOfRoofForRoofArea(Editable s){

        //perhitungan sesuai dengan pilihan user
        if(s.toString().isEmpty()){
            roofArea = 0;
        }else {
            roofArea = Integer.parseInt(et_roofArea.getText().toString());
        }
        refreshFormula();
    }
    public void getSumOfRidgesForRidgesLength(Editable s){

        //perhitungan sesuai dengan pilihan user
        if(s.toString().isEmpty()){
            ridgeLength = 0;
        }else {
            ridgeLength = Integer.parseInt(et_ridgeLength.getText().toString());
        }
        refreshFormula();
    }
    public void getSumOfRidgesForHipLength(Editable s){

        //perhitungan sesuai dengan pilihan user
        if(s.toString().isEmpty()){
            hipLength = 0 ;
        }else {
            hipLength = Integer.parseInt(et_hipLength.getText().toString());
        }
        refreshFormula();
    }

}
