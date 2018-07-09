package com.wiradipa.ondulineApplicator.GridView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import com.wiradipa.ondulineApplicator.R;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class GuaranteRequest extends AppCompatActivity {

    ArrayList<String> filepath = new ArrayList<>();
    GridView gridView;
    private int STORAGE_PERMISSION_CODE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guarante_request);

        gridView = (GridView) findViewById(R.id.grid_view);
        ImageButton upload = (ImageButton) findViewById(R.id.upload_gambar);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReadStorageAllowed())
                    Toast.makeText(GuaranteRequest.this, "You already have the permission", Toast.LENGTH_SHORT).show();
                filepath.clear();

                FilePickerBuilder.getInstance().setMaxCount(5).setSelectedFiles(filepath).setActivityTheme(R.style.AppTheme).
                        pickPhoto(GuaranteRequest.this);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case FilePickerConst.REQUEST_CODE:
                if (resultCode==RESULT_OK && data != null)
                {
                    filepath = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_PHOTOS);
                    Scapecraft scapecraft;
                    ArrayList<Scapecraft> scapecrafts = new ArrayList<>();

                    try
                    {
                        for (String path:filepath){
                            scapecraft = new Scapecraft();
                            scapecraft.setName(path.substring(path.lastIndexOf("/")+1));

                            scapecraft.setUri(Uri.fromFile(new File(path)));
                            scapecrafts.add(scapecraft);
                        }

                        gridView.setAdapter(new CustomAdapter(this, scapecrafts));
                        Toast.makeText(GuaranteRequest.this, "Total = " + String.valueOf(scapecrafts.size()), Toast.LENGTH_SHORT).show();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
        }
    }

    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == STORAGE_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }
}
