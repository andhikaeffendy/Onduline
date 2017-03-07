package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class OurProductActivity extends AppCompatActivity {

    private String stat;
    RelativeLayout rv_bituline, rv_barduline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_product);

        rv_barduline = (RelativeLayout)findViewById(R.id.rv_barduline);
        rv_bituline = (RelativeLayout)findViewById(R.id.rv_bituline);

        Bundle extras = getIntent().getExtras();
        stat = extras.getString("stat");

//        if (stat.equals("instalation guide")){
//            rv_bituline.setVisibility(View.GONE);
//        }
    }

    public void onClickOurProduct(View v){
        Intent i;

        switch (v.getId()) {
            case R.id.btnOnduvila:
//                Toast.makeText(OurProductActivity.this, "btnOnduvila", Toast.LENGTH_LONG).show();

                if (stat.equals("instalation guide")){

//                    disini tempat menuju ke instalation guide activity
                    i = new Intent(this, DetilViewActivity.class);

                    i.putExtra("pilDetilView","ONDUVILLAINSTALATIONGUIDE");
                    i.putExtra("pil","ONDUVILLA");
                    startActivity(i);
                }else {
                    i = new Intent(this, MenuProductActivity.class);
                    i.putExtra("pil","ONDUVILLA");
                    startActivity(i);
                }

                break;
            case R.id.btnOnduline:
//                Toast.makeText(OurProductActivity.this, "btnOnduline", Toast.LENGTH_LONG).show();

                if (stat.equals("instalation guide")){

//                    disini tempat menuju ke instalation guide activity
                    i = new Intent(this, ListOndulineInstalationGuide.class);
                    i.putExtra("pil","ONDULINE");

//                    i.putExtra("pilDetilView","ONDULINEINSTALATIONGUIDE");
//                    i.putExtra("pil","ONDULINE");
                    startActivity(i);
                }else {
                    i = new Intent(this, MenuProductActivity.class);
                    i.putExtra("pil","ONDULINE");
                    startActivity(i);
                }

                break;
            case R.id.btnBarduline:
//                Toast.makeText(OurProductActivity.this, "btnBarduline", Toast.LENGTH_LONG).show();

                if (stat.equals("instalation guide")){

//                    disini tempat menuju ke instalation guide activity
//                    disini tempat menuju ke instalation guide activity


                    i = new Intent(this, ListOndulineInstalationGuide.class);
                    i.putExtra("pil","BARDOLINE");

//                    i = new Intent(this, DetilViewActivity.class);
//
//                    i.putExtra("pilDetilView","BARDOLINEINSTALATIONGUIDE");
//                    i.putExtra("pil","BARDOLINE");;
                    startActivity(i);
                }else {
                    i = new Intent(this, MenuDetailActivity.class);
                    i.putExtra("pil","BARDOLINE");
                    startActivity(i);
                }

//                i = new Intent(this, MenuDetailActivity.class);
//                i.putExtra("pil","BARDOLINE");
//                startActivity(i);

                break;
            case R.id.btnBituline:
//                Toast.makeText(OurProductActivity.this, "btnBituline", Toast.LENGTH_LONG).show();


//                i = new Intent(this, MenuDetailActivity.class);
//                i.putExtra("pil","BITULINE");
//                startActivity(i);



                if (stat.equals("instalation guide")){

//                    disini tempat menuju ke instalation guide activity
//                    disini tempat menuju ke instalation guide activity
                    i = new Intent(this, DetilViewActivity.class);

                    i.putExtra("pilDetilView","BITULINEINSTALATIONGUIDE");
                    i.putExtra("pil","BITULINE");;
                    startActivity(i);
                }else {
                    i = new Intent(this, MenuDetailActivity.class);
                    i.putExtra("pil","BITULINE");
                    startActivity(i);
                }




//                if (stat.equals("instalation guide")){
//
////                    disini tempat menuju ke instalation guide activity
//                    i = new Intent(this, MenuProductActivity.class);
//
//                    i.putExtra("pilDetilView","BITULINEINSTALATIONGUIDE");
//                    i.putExtra("pil","BITULINE");
//                    startActivity(i);
//                }else {
//                    i = new Intent(this, MenuDetailActivity.class);
//                    i.putExtra("pil","BITULINE");
//                    startActivity(i);
//                }

                break;
        }
    }
}
