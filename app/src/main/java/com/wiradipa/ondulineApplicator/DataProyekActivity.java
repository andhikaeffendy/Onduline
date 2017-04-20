package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DataProyekActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_proyek);
    }

    public void onClickDataProyek(View v){
        Intent i;
        switch (v.getId()){
            case R.id.rl_dokumenTeknis:

                i = new Intent(this, DokumenActivity.class);
                startActivity(i);

                break;
            case R.id.rl_inputInformasiProyek:

                i = new Intent(this, InputInformasiProyekActivity.class);
                startActivity(i);

                break;
        }
    }
}
