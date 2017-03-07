package com.wiradipa.ondulineApplicator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuDetailActivity extends AppCompatActivity {


    Context context;
    String pil;
    //Button btnDetaiPDescriptionProduct;
    TextView txtMenuProductName, txtDescriptionProduct;
    LinearLayout LinearDescription, LinearProductSpecification, LinearColorSelection, LinearAdvantages,LinearAccessories,LinearInstalationGuide,LinearSubtainability;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context=this;
        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");
        chooselayout(pil);
//        txtDescriptionProduct = (TextView)findViewById(R.id.txtDescriptionProduct);

        //btnDetaiPDescriptionProduct =(Button)findViewById(R.id.btnDetaiPDescriptionProduct);
//
//        LinearDescription = (LinearLayout) findViewById(R.id.LinearDescription);
//        LinearProductSpecification = (LinearLayout)findViewById(R.id.LinearProductSpecification);
//        LinearColorSelection = (LinearLayout)findViewById(R.id.LinearColorSelection);
//        LinearAdvantages = (LinearLayout)findViewById(R.id.LinearAdvantages);
//        LinearAccessories = (LinearLayout)findViewById(R.id.LinearAccessories);
//        LinearInstalationGuide =(LinearLayout)findViewById(R.id.LinearInstalationGuide);
//        LinearSubtainability =(LinearLayout)findViewById(R.id.LinearSubtainability);

//        CloseAllInformation();
//        txtMenuProductName.setText(pil + "®");//semua isi di set sesuai pilihan misla milih onduvilla
    }

    public void ProductDetail(View v){

        Intent i;
        i = new Intent(this, DetilViewActivity.class);
        switch (v.getId()){
            case R.id.btnDetaiPDescriptionProduct:
//                CloseAllInformation();
//                LinearDescription.setVisibility(View.VISIBLE);
                i.putExtra("pil",pil );
                i.putExtra("pilDetilView",pil + "DESCRIPTION");
                break;
            case R.id.btnDetailProductSpecification:
//                CloseAllInformation();
//                LinearProductSpecification.setVisibility(View.VISIBLE);
                i.putExtra("pil",pil );
                i.putExtra("pilDetilView",pil + "SPECIFICATION");
                break;
            case R.id.btnDetailColorSelection:
//                CloseAllInformation();
//                LinearColorSelection.setVisibility(View.VISIBLE);
                i.putExtra("pil",pil );
                i.putExtra("pilDetilView",pil + "COLORSELECTION");
                break;
            case R.id.btnDetailAdvantages:
//                CloseAllInformation();
//                LinearAdvantages.setVisibility(View.VISIBLE);
                i.putExtra("pil",pil );
                i.putExtra("pilDetilView",pil + "ADVANTAGES");
                break;
            case R.id.btnDetailAccessories:
//                CloseAllInformation();
//                LinearAccessories.setVisibility(View.VISIBLE);
                i.putExtra("pil",pil );
                i.putExtra("pilDetilView",pil + "ACCESSORIES");
                break;
            case R.id.btnDetailInstalationGuide:
//                CloseAllInformation();
//                LinearInstalationGuide.setVisibility(View.VISIBLE);
                i.putExtra("pil",pil );
                i.putExtra("pilDetilView",pil + "INSTALATIONGUIDE");
                break;
            case R.id.btnDetailSubtainability:
//                CloseAllInformation();
//                LinearSubtainability.setVisibility(View.VISIBLE);
                i.putExtra("pil",pil );
                i.putExtra("pilDetilView",pil + "SUSTAINABILIT");
                break;
        }

        startActivity(i);
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
            case "ONDUVILLA":
                setContentView(R.layout.activity_menu_detail_onduvilla);
                txtMenuProductName = (TextView)findViewById(R.id.txtMenuProductName);
                txtMenuProductName.setText(pil + "®");
                break;
            case "ONDULINE":
                setContentView(R.layout.activity_menu_detail_onduline);
                txtMenuProductName = (TextView)findViewById(R.id.txtMenuProductName);
                txtMenuProductName.setText(pil + "®");
                break;
            case "BARDOLINE":
                setContentView(R.layout.activity_menu_detail_bardoline);
                txtMenuProductName = (TextView)findViewById(R.id.txtMenuProductName);
                txtMenuProductName.setText(pil + "® PRO");
                break;
            case "BITULINE":
                setContentView(R.layout.activity_menu_detail_bituline);
                txtMenuProductName = (TextView)findViewById(R.id.txtMenuProductName);
                txtMenuProductName.setText(pil + "®");
                break;
            default:
                setContentView(R.layout.activity_menu_detail_onduvilla);
                txtMenuProductName = (TextView)findViewById(R.id.txtMenuProductName);
                txtMenuProductName.setText(pil + "®");
                break;
        }
    }
}
