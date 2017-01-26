package com.wiradipa.ondulineApplicator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuDetailActivity extends AppCompatActivity {


    String pil;
    //Button btnDetaiPDescriptionProduct;
    TextView txtMenuProductName, txtDescriptionProduct;
    LinearLayout LinearDescription, LinearProductSpecification, LinearColorSelection, LinearAdvantages,LinearAccessories,LinearInstalationGuide,LinearSubtainability;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");
        chooselayout(pil);
        txtMenuProductName = (TextView)findViewById(R.id.txtMenuProductName);
        txtDescriptionProduct = (TextView)findViewById(R.id.txtDescriptionProduct);

        //btnDetaiPDescriptionProduct =(Button)findViewById(R.id.btnDetaiPDescriptionProduct);

        LinearDescription = (LinearLayout) findViewById(R.id.LinearDescription);
        LinearProductSpecification = (LinearLayout)findViewById(R.id.LinearProductSpecification);
        LinearColorSelection = (LinearLayout)findViewById(R.id.LinearColorSelection);
        LinearAdvantages = (LinearLayout)findViewById(R.id.LinearAdvantages);
        LinearAccessories = (LinearLayout)findViewById(R.id.LinearAccessories);
        LinearInstalationGuide =(LinearLayout)findViewById(R.id.LinearInstalationGuide);
        LinearSubtainability =(LinearLayout)findViewById(R.id.LinearSubtainability);

        CloseAllInformation();
        txtMenuProductName.setText(pil + "®");//semua isi di set sesuai pilihan misla milih onduvilla
    }

    public void ProductDetail(View v){

        switch (v.getId()){
            case R.id.btnDetaiPDescriptionProduct:
                CloseAllInformation();
                LinearDescription.setVisibility(View.VISIBLE);
                break;
            case R.id.btnDetailProductSpecification:
                CloseAllInformation();
                LinearProductSpecification.setVisibility(View.VISIBLE);
                break;
            case R.id.btnDetailColorSelection:
                CloseAllInformation();
                LinearColorSelection.setVisibility(View.VISIBLE);
                break;
            case R.id.btnDetailAdvantages:
                CloseAllInformation();
                LinearAdvantages.setVisibility(View.VISIBLE);
                break;
            case R.id.btnDetailAccessories:
                CloseAllInformation();
                LinearAccessories.setVisibility(View.VISIBLE);
                break;
            case R.id.btnDetailInstalationGuide:
                CloseAllInformation();
                LinearInstalationGuide.setVisibility(View.VISIBLE);
                break;
            case R.id.btnDetailSubtainability:
                CloseAllInformation();
                LinearSubtainability.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void CloseAllInformation(){
        LinearDescription.setVisibility(View.GONE);
        LinearProductSpecification.setVisibility(View.GONE);
        LinearColorSelection.setVisibility(View.GONE);
        LinearAdvantages.setVisibility(View.GONE);
        LinearAccessories.setVisibility(View.GONE);
        LinearInstalationGuide.setVisibility(View.GONE);
        LinearSubtainability.setVisibility(View.GONE);
    }

    public void chooselayout(String pil){
        switch (pil){
            case "ONDUVILA":
                setContentView(R.layout.activity_menu_detail_onduvilla);
                break;
            case "ONDULINE":
                setContentView(R.layout.activity_menu_detail_onduline);
                break;
            case "BARDULINE":
                setContentView(R.layout.activity_menu_detail_barduline);
                break;
            case "BITULINE":
                setContentView(R.layout.activity_menu_detail_bituline);
                break;
            default:
                setContentView(R.layout.activity_menu_detail_onduvilla);
                break;
        }
    }
}
