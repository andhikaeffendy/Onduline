package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DokumenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokumen);
    }

    public void onClickDokumenTeknis(View v){
        Intent i;
        switch (v.getId()){
            case R.id.download_data_proyek_green_certification: //v
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://onduvilla.co.id/sites/onduvilla_id/files/green_certification.pdf"));
                startActivity(i);
                break;
            case R.id.download_data_proyek_green_certification_onduvilla: //v
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://onduvilla.co.id/sites/onduvilla_id/files/green_certification.pdf"));
                startActivity(i);
                break;
            case R.id.download_data_proyek_green_certification_bituline: //v
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://onduvilla.co.id/sites/onduvilla_id/files/green_certification.pdf"));
                startActivity(i);
                break;
            case R.id.download_data_proyek_tds_onduline: //v
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://id.onduline.com/sites/onduline_id/files/rks_onduline.pdf"));
                startActivity(i);
                break;
            case R.id.download_data_proyek_rks_onduline: //v
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://id.onduline.com/sites/onduline_id/files/rks_onduline_0.pdf"));
                startActivity(i);
                break;
            case R.id.download_data_proyek_sertifikat_sni: //v
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://id.onduline.com/sites/onduline_id/files/sertifikat_sni.pdf"));
                startActivity(i);
                break;
            case R.id.download_data_proyek_sni_pemasangan: //v
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://id.onduline.com/sites/onduline_id/files/sni_pemasangan_7711-2-2012_0.pdf"));
                startActivity(i);
                break;
            case R.id.download_data_proyek_sound_test_onduline: //v
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://onduvilla.co.id/sites/onduvilla_id/files/sound_test.pdf"));
                startActivity(i);
                break;
            case R.id.download_data_proyek_sound_test: //v
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://onduvilla.co.id/sites/onduvilla_id/files/sound_test.pdf"));
                startActivity(i);
                break;
            case R.id.download_data_proyek_wind_testing_onduline: //v
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://id.onduline.com/sites/onduline_id/files/wind_testing_onduline.pdf"));
                startActivity(i);
                break;
            case R.id.download_data_proyek_rks_onduvilla: //v
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://onduvilla.co.id/sites/onduvilla_id/files/rks_onduvilla.pdf"));
                startActivity(i);
                break;
            case R.id.download_data_proyek_wind_testing_onduvilla: //v
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://onduvilla.co.id/sites/onduvilla_id/files/wind_testing_onduvilla.pdf"));
                startActivity(i);
                break;
            case R.id.download_data_proyek_tds_onduvilla: //v
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://onduvilla.co.id/sites/onduvilla_id/files/tds_onduvilla.pdf"));
                startActivity(i);
                break;
            case R.id.download_data_proyek_technical_data_onduvilla_pphr_3d: //v
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://onduvilla.co.id/sites/onduvilla_id/files/technical_data_onduvilla_pphr_3d.pdf"));
                startActivity(i);
                break;
            case R.id.download_data_proyek_tds_bituline: //v
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://id.onduline.com/sites/onduline_id/files/tds_bituline.pdf"));
                startActivity(i);
                break;
            case R.id.download_data_proyek_tds_bardoline_beaver: //v
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://id.onduline.com/sites/onduline_id/files/tds_bardoline_beaver.pdf"));
                startActivity(i);
                break;
            case R.id.download_data_proyek_tds_bardoline_rectangular: // v
                i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://id.onduline.com/sites/onduline_id/files/tds_bardoline_rectangular.pdf"));
                startActivity(i);
                break;
        }
    }
}
