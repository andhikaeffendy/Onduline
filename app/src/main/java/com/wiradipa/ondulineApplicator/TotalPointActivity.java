package com.wiradipa.ondulineApplicator;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wiradipa.ondulineApplicator.lib.ApiWeb;
import com.wiradipa.ondulineApplicator.lib.AppSession;

import org.json.JSONException;
import org.json.JSONObject;

public class TotalPointActivity extends AppCompatActivity {

    private Context context;
    private AppSession session;
    private String token, usertype, retailer_type;
    private UpdatePoinTask updatePoinTask;
    private TextView txt_totalpoin, txt_NameUser;
    private ImageView img_bonusTable;
    private int idImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_point);


        context = this;
        session = new AppSession(context);
        session.checkSession();
        token=session.getToken();
        usertype = session.getUSERTYPE();
        retailer_type = session.getRetailerType();

        txt_totalpoin = (TextView)findViewById(R.id.txt_totalpoin);
        txt_NameUser = (TextView)findViewById(R.id.txt_NameUser);
        img_bonusTable = (ImageView) findViewById(R.id.img_bonusTable);


//        get poin
        updatePoinTask = new UpdatePoinTask();
        updatePoinTask.execute((Void)null);


        txt_totalpoin.setText(session.getPoin()+" Poin");
        txt_NameUser.setText("Halo "+session.getName());
        setBonusTable(usertype);
        System.out.println("usertype : "+session.getUSERTYPE());
        System.out.println("usertype : "+session.getRetailerType());

        img_bonusTable.setImageResource(idImage);
    }

    public void onClickTotalPoin(View v){
        Intent i;
        switch (v.getId()){
            case R.id.btn_upload_surat_jalan_baru:

                i = new Intent(this, AddNewProjectAndOrderActivity.class);
                i.putExtra("pil", "order retailer");
                startActivity(i);
                break;
            case R.id.btn_reward_program:
//                Toast.makeText(this, "btnProgram", Toast.LENGTH_LONG).show();
                if (session.getUSERTYPE().equals("applicator")){
//                    i = new Intent(this, ListProgram.class);

                    i = new Intent(this, DetilViewActivity.class);
                    i.putExtra("pil","Program");
                    i.putExtra("pilDetilView","ondulucky baja ringan");
                    startActivity(i);
                }else {
                    i = new Intent(this, DetilViewActivity.class);
                    i.putExtra("pil","Program");
                    i.putExtra("pilDetilView","toko");
                    startActivity(i);
                }

                break;
            case R.id.btn_total_surat_jalan:
                i = new Intent(this, TotalOrderActivity.class);
                i.putExtra("pilListView","order");
                i.putExtra("pil",session.getUSERTYPE());
                startActivity(i);

                break;
        }
    }

    public void setBonusTable(String usertype){
        switch (usertype){
            case "applicator":
                idImage = R.drawable.program_ondulucky_tukang_baja_ringan1;
                break;
            case "retailer":

                if ("Toko Bahan Bangunan / Toko Tradisional".equals(retailer_type)){
                    idImage = R.drawable.tabel_hadiah_toko_bahan_bangunan;
                }else if ("Toko Baja Ringan / Depo keramik".equals(retailer_type)){
                    idImage = R.drawable.program_ondulucky_toko_baja_ringan1;
                }else if ("Supermarket Bahan Bangunan".equals(retailer_type)){
                    idImage = R.drawable.program_ondulucky_toko_supermarket_mo1;
                }

                break;
            default:
                idImage = R.drawable.program_ondulucky_tukang_baja_ringan1;
                break;
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UpdatePoinTask extends AsyncTask<Void, Void, Boolean> {

        private ApiWeb apiWeb;
        private String userPoint;
        private ProgressDialog pg;

        UpdatePoinTask() {
            apiWeb = new ApiWeb();
            pg = new ProgressDialog(context);
            pg.setTitle("Ambil Data");
            pg.setMessage("Ambil Data");
            pg.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            String result = apiWeb.getPoint(token);
            if(result==null){
                return false;
            }
            try {
                JSONObject json = new JSONObject(result);
                String status = json.getString("status");
                if(status.compareToIgnoreCase("success")==0){
                    userPoint = json.getString("total_point");

                    return true;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            pg.dismiss();
            session.setPoin(userPoint);
//            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
