package com.wiradipa.ondulineApplicator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MyProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_project);
    }

    public void onClickMyProject(View v){

        Intent i;
        switch (v.getId()) {
            case R.id.btnNewProject:
//                Toast.makeText(this, "btnNewProject", Toast.LENGTH_LONG).show();
                i = new Intent(this, NewProjectActivity.class);
                startActivity(i);
                break;
        }
    }
}
