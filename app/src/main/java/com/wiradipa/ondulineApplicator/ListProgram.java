package com.wiradipa.ondulineApplicator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wiradipa.ondulineApplicator.lib.AppSession;

/**
 * Created by sentanu on 2/13/2017.
 */

public class ListProgram extends AppCompatActivity {


    private Context context;
    private AppSession session;
    private String pil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_program);

        context = this;
        session = new AppSession(context);
        session.checkSession();
        pil=session.getUSERTYPE();
    }

    public void onClickListProgram(View v){
        Intent i;
        switch (v.getId()) {
            case R.id.btnListProgram_ondulucky:


                i = new Intent(this, DetilViewActivity.class);
                i.putExtra("pil","Program");
                i.putExtra("pilDetilView","ondulucky baja ringan");

//                i = new Intent(this, ProgramActivity.class);
//                i.putExtra("pil","ondulucky");
                startActivity(i);
                break;
            case R.id.btnListProgram_PhotoContest:

//                i = new Intent(this, DetilViewActivity.class);
//                i.putExtra("pil","Program");
//                i.putExtra("pilDetilView","kontes foto proyek");

                i = new Intent(this, ProgramActivity.class);
                i.putExtra("pil","photoContest");
                startActivity(i);
                break;
        }
    }
}