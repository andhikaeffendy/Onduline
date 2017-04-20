package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DetilOndulineGreenRoofAwardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_onduline_green_roof_award);
    }

    public void onClickOgra(View v){
        Intent i;
        switch (v.getId()){
            case R.id.btn_ogra_download_formulir_pendaftaran:
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://onduvilla.co.id/sites/onduvilla_id/files/formulir_pendaftaran.pdf"));
                startActivity(i);
                break;
            case R.id.btn_ogra_download_panduan_sayembara:
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://onduvilla.co.id/sites/onduvilla_id/files/syarat_ketentuan.pdf"));
                startActivity(i);
                break;
        }
    }
}
