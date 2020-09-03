package com.wiradipa.ondulineApplicator;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.wiradipa.ondulineApplicator.lib.ApiWeb;
import com.wiradipa.ondulineApplicator.lib.AppSession;

import java.util.ArrayList;

public class DetailSupportLetterActivity extends AppCompatActivity {

    String resultUrl, noSurat, namaProyek, pokja, product, projectArea, fileUrl,
            company, nameDirector, position, alamat, idcustomer;

    TextView mNomorSurat, mNamaProyek, mPokja, mProduct, mProjectArea,
            mCompany, mNameDirector, mPosition, mAlamat;
//    TextView mFileUrl;

    private Context mContext;
    AppSession mAppSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_support_letter);

        mContext = this;
        mAppSession = new AppSession(mContext);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            resultUrl = extras.getString("url");
        }

        if (!isNetworkAvailable()){
            popupNoInternet();
        }

        initialitation();
        getDetailSupportLetter getDetailSupportLetter = new getDetailSupportLetter();
        getDetailSupportLetter.execute();
    }

    private void initialitation() {
        mNomorSurat = (TextView) findViewById(R.id.tv_nomor_surat);
        mNamaProyek = (TextView) findViewById(R.id.tv_nama_proyek);
        mPokja = (TextView) findViewById(R.id.tv_pokja);
        mProduct = (TextView) findViewById(R.id.tv_produk);
        mProjectArea = (TextView) findViewById(R.id.tv_area_projek);
//        mFileUrl = (TextView) findViewById(R.id.tv_lampiran);
        mCompany = (TextView) findViewById(R.id.tv_nama_perusahaan);
        mNameDirector = (TextView) findViewById(R.id.tv_nama_penerima);
        mPosition = (TextView) findViewById(R.id.tv_jabatan_penerima);
        mAlamat = (TextView) findViewById(R.id.tv_alamat_perusahaan);
    }

    @SuppressLint("StaticFieldLeak")
    public class getDetailSupportLetter extends AsyncTask<Void, Void, Boolean>{

        ApiWeb mApiWeb;
        private String url = resultUrl;
        private String errorMessage = "Koneksi Error";
        private ProgressDialog pg;

        getDetailSupportLetter(){
            mApiWeb = new ApiWeb();
            pg = new ProgressDialog(mContext);
            pg.setTitle("Loading");
            pg.setMessage("Tunggu sebentar");
            pg.show();
        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            String completeUrl = mApiWeb.GetHttp(mApiWeb.getApiLetterr()+url);
            Log.d("DEBUG : ", mApiWeb.getApiLetterr() + url);

            if (completeUrl == null){
                return false;
            }
            try {
                JSONObject jsonObject = new JSONObject(completeUrl);
//                JSONArray jsonArray = jsonObject.getJSONArray("product");
                if (jsonObject.getString("approval").equals("1")) {
                    noSurat = jsonObject.getString("letter_number");
                    namaProyek = jsonObject.getString("project_name");
                    pokja = jsonObject.getString("pokja");

//                ArrayList<String> index = new ArrayList<String>();
//                for (int i=0; i<jsonArray.length(); i++) {
//                    index.add(jsonArray.getString(i));
//                }

                    product = jsonObject.getString("product");

                    projectArea = jsonObject.getString("project_area");
//                fileUrl = jsonObject.getString("file_url");
                    idcustomer = jsonObject.getString("id_customer");
                    company = jsonObject.getString("company");
                    nameDirector = jsonObject.getString("name");
                    position = jsonObject.getString("position");
                    alamat = jsonObject.getString("address");

                    return true;
                } else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("Detail Surat ");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    System.exit(0);
                                    finish();
                                }
                            });
                            builder.setMessage("Surat Dukungan Belum di Approve !!!");
                            AlertDialog alert1 = builder.create();
                            alert1.show();
                        }
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            pg.dismiss();
            mNomorSurat.setText(noSurat);
            mNamaProyek.setText(namaProyek);
            mPokja.setText(pokja);
            mProduct.setText(product);
            mProjectArea.setText(projectArea);

//          membuat Link untuk lampiran
//            if (fileUrl.equals("") || fileUrl == null || fileUrl.equals("/files/original/missing.png")){
//                mFileUrl.setText("-");
//            } else{
//                final StringBuilder file = new StringBuilder();
//                file.append("<a href = '" + mApiWeb.getApiLetterr()+fileUrl + "'> download </a>");
//                mFileUrl.setText(Html.fromHtml(file.toString()));
//                mFileUrl.setClickable(true);
//                mFileUrl.setMovementMethod(LinkMovementMethod.getInstance());
//
//                mFileUrl.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setData(Uri.parse(String.valueOf(file)));
//                        startActivity(intent);
//                    }
//                });
//            }
//          membuat link untuk lampiran

            mCompany.setText(company);
            mNameDirector.setText(nameDirector);
            mPosition.setText(position);
            mAlamat.setText(alamat);
        }
    }

    // cek ada internet apa gak..
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void popupNoInternet() {
        new AlertDialog.Builder(this)
                .setTitle("Tidak Ada Koneksi Internet!")
                .setMessage("Periksa koneksi internet anda")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        int pid = android.os.Process.myPid();
                        android.os.Process.killProcess(pid);
                        System.exit(0);
                        finish();
                    }
                }).create().show();
    }

}