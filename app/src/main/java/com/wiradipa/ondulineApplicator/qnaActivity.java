package com.wiradipa.ondulineApplicator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wiradipa.ondulineApplicator.lib.AppSession;

public class qnaActivity extends AppCompatActivity {



    private Context context;
    private AppSession session;
    private String usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        context = this;
        session = new AppSession(context);
        session.checkSession();
        usertype=session.getUSERTYPE();
        setLayoutqna(usertype);

    }

    //function to set layout(aplikator or retailer)
    public void setLayoutqna(String pil){
//        Bundle extras = getIntent().getExtras();
//        pil = extras.getString("pil");
        switch (pil) {
            case "applicator":
                setContentView(R.layout.activity_qna_applicator);
                break;
            case "retailer":
                setContentView(R.layout.activity_qna_retailler);
                break;
            case "individu":
                setContentView(R.layout.activity_qna_applicator);
                break;
        }
    }

}
