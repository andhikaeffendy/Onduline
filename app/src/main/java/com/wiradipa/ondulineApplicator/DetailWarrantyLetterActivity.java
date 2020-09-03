package com.wiradipa.ondulineApplicator;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.wiradipa.ondulineApplicator.lib.ApiWeb;

import org.json.JSONException;
import org.json.JSONObject;


public class DetailWarrantyLetterActivity extends AppCompatActivity {

    String resultUrl, noGaransi, namaProyek, lokasiProyek, pemilikProyek, luasAtap
            , alamat, aplikatorData, produk, batchNumber, otherProductStatus, otherProduct, screwStatus
            , otherScrew, namaAplikator, company, director, alamatPerusahaan, kemiringanAtap, jarakGording
            , zigZag, jumlahSekrup, overlapRabung, jumlahSekrupRabung;

    TextView mNoGaransi, mNamaProyek, mLokasiProyek, mPemilikProyek, mLuasAtap, mAlamat
            , mAplikatorData, mProduk, mBatchNumber, mOtherProductStatus, mOtherProduct, mScrewStatus
            , mOtherScrew, mNamaAplikator, mCompany, mDirector, mAlamatPerusahaan, mKemiringanAtap, mJarakGording
            , mZigZag, mJumlahSekrup, mOverlapRabung, mJumlahSekrupRabung;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_warranty_letter);

        mContext = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            resultUrl = extras.getString("url");
        }

        if (!isNetworkAvailable()){
            popupNoInternet();
        }

        initialitation();
        getWarrantyLetters mGetWarrantyLetters = new getWarrantyLetters();
        mGetWarrantyLetters.execute();

    }

    private void initialitation(){
        mNoGaransi = (TextView) findViewById(R.id.tv_nomor_garansi);
        mNamaAplikator = (TextView) findViewById(R.id.tv_nama_aplikator);
        mNamaProyek = (TextView) findViewById(R.id.tv_nama_proyek);
        mLokasiProyek = (TextView) findViewById(R.id.tv_area_projek);
        mPemilikProyek = (TextView) findViewById(R.id.tv_project_owner);
        mLuasAtap = (TextView) findViewById(R.id.tv_luas_atap);
        mAlamat = (TextView) findViewById(R.id.tv_alamat);
        mAplikatorData = (TextView) findViewById(R.id.tv_data_aplikator);
        mProduk = (TextView) findViewById(R.id.tv_produk);
        mBatchNumber = (TextView) findViewById(R.id.tv_batch_number);
        mOtherProductStatus = (TextView) findViewById(R.id.tv_other_product_status);
        mOtherProduct = (TextView) findViewById(R.id.tv_other_product);
        mScrewStatus = (TextView) findViewById(R.id.tv_screw_status);
        mOtherScrew = (TextView) findViewById(R.id.tv_other_screw);
        mCompany = (TextView) findViewById(R.id.tv_nama_perusahaan);
        mDirector = (TextView) findViewById(R.id.tv_director);
        mAlamatPerusahaan = (TextView) findViewById(R.id.tv_alamat_perusahaan);
        mKemiringanAtap = (TextView) findViewById(R.id.tv_kemiringan_atap);
        mJarakGording = (TextView) findViewById(R.id.tv_jarak);
        mZigZag = (TextView) findViewById(R.id.tv_zigzag);
        mJumlahSekrup = (TextView) findViewById(R.id.tv_jumlah_sekrup);
        mOverlapRabung = (TextView) findViewById(R.id.tv_overlap_rabung);
        mJumlahSekrupRabung = (TextView) findViewById(R.id.tv_jumlah_sekrup_perRabung);
    }

    @SuppressLint("StaticFieldLeak")
    public class getWarrantyLetters extends AsyncTask<Void, Void, Boolean>{

        ApiWeb mApiWeb;
        private String url = resultUrl;
        private String errorMessage = "Koneksi Error";
        private ProgressDialog pg;

        getWarrantyLetters(){
            mApiWeb = new ApiWeb();
            pg = new ProgressDialog(mContext);
            pg.setTitle("Loading");
            pg.setMessage("Tunggu sebentar");
            pg.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            String completeUrl = mApiWeb.GetHttp(mApiWeb.getApiLetterr()+url);
            Log.d("DEBUB : ", completeUrl);

            if (completeUrl == null){
                return false;
            }

            try {
                JSONObject jsonObject = new JSONObject(completeUrl);
                if (jsonObject.getString("approval").equals("1")){
                    noGaransi = jsonObject.getString("letter_number");
                    namaProyek = jsonObject.getString("project_name");
                    pemilikProyek = jsonObject.getString("project_owner");
                    produk = jsonObject.getString("product");
                    lokasiProyek = jsonObject.getString("project_location");
                    luasAtap = jsonObject.getString("roof_size");
                    company = jsonObject.getString("company");
                    director = jsonObject.getString("director");
                    alamat = jsonObject.getString("address");
                    aplikatorData = jsonObject.getString("applicator_data");
                    alamatPerusahaan = jsonObject.getString("company_address");
                    batchNumber = jsonObject.getString("batch_number");
                    otherProductStatus = jsonObject.getString("other_product_status");
                    otherProduct = jsonObject.getString("other_product");
                    screwStatus = jsonObject.getString("screw_status");
                    otherScrew = jsonObject.getString("other_screw");
                    namaAplikator = jsonObject.getString("applicator_name");
                    kemiringanAtap = jsonObject.getString("roof_slope");
                    jarakGording = jsonObject.getString("gording_space");
                    jumlahSekrupRabung = jsonObject.getString("screw_q_rabung");
                    jumlahSekrup = jsonObject.getString("screw_q_sheet");
                    zigZag = jsonObject.getString("zigzag_status");
                    overlapRabung = jsonObject.getString("rabung_overlap");

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
                            builder.setMessage("Surat Garansi Belum di Approve !!!");
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

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            pg.dismiss();

            mNoGaransi.setText(noGaransi);
            mNamaProyek.setText(namaProyek);
            mPemilikProyek.setText(pemilikProyek);
            mProduk.setText(produk);
            mLokasiProyek.setText(lokasiProyek);
            mLuasAtap.setText(luasAtap + "M2");
            mCompany.setText(company);
            mDirector.setText(director);
            mAlamat.setText(alamat);
            mAplikatorData.setText(aplikatorData);
            mAlamatPerusahaan.setText(alamatPerusahaan);
            mBatchNumber.setText(batchNumber);
            mOtherProductStatus.setText(otherProductStatus);
            mOtherProduct.setText(otherProduct);
            mScrewStatus.setText(screwStatus);
            mOtherScrew.setText(otherScrew);
            mNamaAplikator.setText(namaAplikator);
            mKemiringanAtap.setText(kemiringanAtap + "°");
            mJarakGording.setText(jarakGording + " CM");
            mJumlahSekrupRabung.setText(jumlahSekrupRabung + " PCS");
            mJumlahSekrup.setText(jumlahSekrup + " PCS");
            mZigZag.setText(zigZag);
            mOverlapRabung.setText(overlapRabung + " CM");
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
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        int pid = android.os.Process.myPid();
                        android.os.Process.killProcess(pid);
                        System.exit(0);
                        finish();
                    }
                }).create().show();
    }
}
