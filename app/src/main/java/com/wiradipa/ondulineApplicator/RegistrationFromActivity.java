package com.wiradipa.ondulineApplicator;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class RegistrationFromActivity extends AppCompatActivity {

    String pil, gender;

    String user_type, username, password, email, shop_name, shop_address, states_id, city_id, hp_no, birth_date, distribution_name, owner_name, id_no, id_no_type, retailer_type, name, address, company_name, association_name;

    private Calendar calendar;
    private int year, month, day;

    TextView txtisianBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();

        txtisianBirth=(TextView)findViewById(R.id.txtisianBirth);
        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        //setContentView(R.layout.activity_registration_form_tukang_bangunan);
    }

    public void onClickRegistrationForm(View v){
        Intent i;
        switch (v.getId()){
            case R.id.btnSubmitRegistrationForm:
                i = new Intent(this, VerificationPageActivity.class);
                startActivity(i);
                break;
        }
    }


    //function to set layout(produk, brosur, souvenir)
    public void setLayout(){
        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");
        switch (pil) {
            case "tukang bangunan":
                setContentView(R.layout.activity_registration_form_tukang_bangunan);
                break;
            case "retailer tradisional":
                setContentView(R.layout.activity_registration_form_retailer_tradisional);
                break;
        }
    }



    /** Radio Button */
    public void onRBSignupClicked(View v){
        // Is the button now checked?
        boolean checked = ((RadioButton) v).isChecked();

        // Check which radio button was clicked
        switch(v.getId()) {
            case R.id.rbKTP:
                if (checked)
                    gender="KTP";
                break;
            case R.id.rbSIM:
                if (checked)
                    gender="SIM";;
                break;
            default:
                gender="";
                break;
        }

    }



    /** set date :D */
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    private void showDate(int year, int month, int day) {
        txtisianBirth.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };


}
