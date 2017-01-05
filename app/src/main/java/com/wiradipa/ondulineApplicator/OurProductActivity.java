package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class OurProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_product);
    }

    public void onClickOurProduct(View v){
        Intent i;
        switch (v.getId()) {
            case R.id.btnOnduvila:
                Toast.makeText(OurProductActivity.this, "btnOnduvila", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnOnduline:
                Toast.makeText(OurProductActivity.this, "btnOnduline", Toast.LENGTH_LONG).show();
                i = new Intent(this, MenuProductActivity.class);
                startActivity(i);
                break;
            case R.id.btnBarduline:
                Toast.makeText(OurProductActivity.this, "btnBarduline", Toast.LENGTH_LONG).show();
                //i = new Intent(this, productActivity.class);
                //startActivity(i);
                break;
            case R.id.btnBituline:
                Toast.makeText(OurProductActivity.this, "btnBituline", Toast.LENGTH_LONG).show();
                //i = new Intent(this, PoinActivity.class);
                //startActivity(i);
                break;
        }
    }
}
