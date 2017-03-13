package com.wiradipa.ondulineApplicator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.wiradipa.ondulineApplicator.lib.AppSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetilViewActivity extends AppCompatActivity {


    private TextView txtMenuProductName;
    protected ListView lv;
    protected ListAdapter adapter;
    SimpleAdapter Adapter;
    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> mylist;
    String[] Gbr;
    private Context context;
    private AppSession session;
    private String pil, pilView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_view);

        context = this;
        session = new AppSession(context);

        Bundle extras = getIntent().getExtras();
        pil = extras.getString("pil");
        pilView = extras.getString("pilDetilView");

        lv = (ListView)findViewById(R.id.lv_detailView);
        txtMenuProductName = (TextView)findViewById(R.id.txtMenuProductName);

        if(pil.equals("BARDOLINE")){
            txtMenuProductName.setText(pil + "® PRO");
        }else {
            txtMenuProductName.setText(pil + "®");
        }

        setDetil(pilView);
//        Gbr = new String[] {
//                Integer.toString(R.drawable.onduvilla_detil_description)
//        };

        mylist = new ArrayList<HashMap<String,String>>();

        for (int i = 0; i < Gbr.length; i++){
            map = new HashMap<String, String>();
            map.put("gbr", Gbr[i]);
            mylist.add(map);
        }

        Adapter = new SimpleAdapter(this, mylist, R.layout.list_detil_product,
                new String[] {"gbr"}, new int[] {R.id.img_detil});
        lv.setAdapter(Adapter);
    }

    public void setDetil(String pilView){

        switch (pilView){
//            onduvilla
            case "ONDUVILLADESCRIPTION":
                Gbr = new String[] {
                        Integer.toString(R.drawable.onduvilla_detil_description)
                };
                break;
            case "ONDUVILLASPECIFICATION":
                Gbr = new String[] {
                        Integer.toString(R.drawable.onduvilla_detil_specification1),
                        Integer.toString(R.drawable.onduvilla_detil_specification3)
                };
                break;
            case "ONDUVILLACOLORSELECTION":
                Gbr = new String[] {
                        Integer.toString(R.drawable.onduvilla_detil_color1),
                        Integer.toString(R.drawable.onduvilla_detil_color2)
                };
                break;
            case "ONDUVILLAADVANTAGES":
                Gbr = new String[] {
                        Integer.toString(R.drawable.onduvilla_detil_advanatges)
                };
                break;
            case "ONDUVILLAACCESSORIES":
                Gbr = new String[] {
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris1),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris2),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris3),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris4),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris5),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris6),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris7),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris8),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris9),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris10),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris11),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris12),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris13),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris14),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris15),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris16),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris17),
                };
                break;
            case "ONDUVILLAINSTALATIONGUIDE":

                txtMenuProductName.setText("Panduan Pemasangan "+pil + "®");
                Gbr = new String[] {
                        Integer.toString(R.drawable.instalation_guide_onduvilla_1),
                        Integer.toString(R.drawable.instalation_guide_onduvilla_2),
                        Integer.toString(R.drawable.instalation_guide_onduvilla_3),
                        Integer.toString(R.drawable.instalation_guide_onduvilla_4),
                        Integer.toString(R.drawable.instalation_guide_onduvilla_5),
                        Integer.toString(R.drawable.instalation_guide_onduvilla_6),
                        Integer.toString(R.drawable.instalation_guide_onduvilla_7)
                };
                break;
            case "ONDUVILLASUSTAINABILIT":
                Gbr = new String[] {
                        Integer.toString(R.drawable.onduvilla_detil_sustainability)
                };
                break;
//            onduline

            case "ONDULINEDESCRIPTION":
                Gbr = new String[] {
                        Integer.toString(R.drawable.onduline_detil_description)
                };
                break;
            case "ONDULINESPECIFICATION":
                Gbr = new String[] {
                        Integer.toString(R.drawable.onduline_detil_spesification)
                };
                break;
            case "ONDULINECOLORSELECTION":
                Gbr = new String[] {
                        Integer.toString(R.drawable.onduline_detil_color)
                };
                break;
            case "ONDULINEADVANTAGES":
                Gbr = new String[] {
                        Integer.toString(R.drawable.onduline_detil_advantages),
                        Integer.toString(R.drawable.onduline_detil_advantages2),
                        Integer.toString(R.drawable.onduline_detil_advantages3)
                };
                break;
            case "ONDULINEACCESSORIES":
                Gbr = new String[] {
                        Integer.toString(R.drawable.onduline_detil_aksesoris1),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris2),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris3),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris4),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris5),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris6),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris7),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris8),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris9),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris10),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris11),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris12),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris13),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris14),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris15),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris16),
                        Integer.toString(R.drawable.onduvilla_detil_aksesoris17),
                };
                break;
            case "ONDULINEINSTALATIONGUIDE5-10":
                txtMenuProductName.setText(pil);
                Gbr = new String[] {
                        Integer.toString(R.drawable.instalation_guide_onduline_5_10_1),
                        Integer.toString(R.drawable.instalation_guide_onduline_5_10_2),
                        Integer.toString(R.drawable.instalation_guide_onduline_5_10_3),
                        Integer.toString(R.drawable.instalation_guide_onduline_10_15_5)
                };
                break;
            case "ONDULINEINSTALATIONGUIDE10-15":
                txtMenuProductName.setText(pil);
                Gbr = new String[] {
                        Integer.toString(R.drawable.instalation_guide_onduline_10_15_1),
                        Integer.toString(R.drawable.instalation_guide_onduline_10_15_2),
                        Integer.toString(R.drawable.instalation_guide_onduline_10_15_3),
                        Integer.toString(R.drawable.instalation_guide_onduline_10_15_4),
                        Integer.toString(R.drawable.instalation_guide_onduline_nok),
                        Integer.toString(R.drawable.instalation_guide_onduline_10_15_5)
                };
                break;
            case "ONDULINEINSTALATIONGUIDE15":
                txtMenuProductName.setText(pil);
                Gbr = new String[] {
                        Integer.toString(R.drawable.intsalation_guide_onduline_15_1),
                        Integer.toString(R.drawable.intsalation_guide_onduline_15_2),
                        Integer.toString(R.drawable.intsalation_guide_onduline_15_3),
                        Integer.toString(R.drawable.intsalation_guide_onduline_15_4),
                        Integer.toString(R.drawable.instalation_guide_onduline_nok),
                        Integer.toString(R.drawable.intsalation_guide_onduline_15_5),
                };
                break;
            case "ONDULINESUSTAINABILIT":
                Gbr = new String[] {
                        Integer.toString(R.drawable.onduvilla_detil_sustainability)
                };
                break;
//            bardoline

            case "BARDOLINEDESCRIPTION":
                Gbr = new String[] {
                        Integer.toString(R.drawable.bardoline_detil_description)
                };
                break;
            case "BARDOLINESPECIFICATION":
                Gbr = new String[] {
                        Integer.toString(R.drawable.bardoline_detil_spesification)
                };
                break;
            case "BARDOLINECOLORSELECTION":
                Gbr = new String[] {
                        Integer.toString(R.drawable.bardoline_detil_color)
                };
                break;
            case "BARDOLINEADVANTAGES":
                Gbr = new String[] {
                        Integer.toString(R.drawable.bardoline_detil_advantages)
                };
                break;
            case "BARDOLINEACCESSORIES":
                Gbr = new String[] {
                        Integer.toString(R.drawable.onduvilla_detil_description)
                };
                break;
            case "BARDOLINEINSTALATIONGUIDETYPEBEAVER":

                txtMenuProductName.setText(pil);
                Gbr = new String[] {
                        Integer.toString(R.drawable.instalation_guide_bardoline_2),
                        Integer.toString(R.drawable.instalation_guide_bardoline_3),
                        Integer.toString(R.drawable.instalation_guide_bardoline_4),
                        Integer.toString(R.drawable.instalation_guide_bardoline_5),
                        Integer.toString(R.drawable.instalation_guide_bardoline_6),
                        Integer.toString(R.drawable.instalation_guide_bardoline_7),
                        Integer.toString(R.drawable.instalation_guide_bardoline_8),
                        Integer.toString(R.drawable.instalation_guide_bardoline_9),
                        Integer.toString(R.drawable.instalation_guide_bardoline_10),
                        Integer.toString(R.drawable.instalation_guide_bardoline_11)
                };
                break;
            case "BARDOLINEINSTALATIONGUIDETYPERECTANGULAR":
                txtMenuProductName.setText(pil);
                Gbr = new String[] {
                        Integer.toString(R.drawable.instalation_guide_bardoline_12),
                        Integer.toString(R.drawable.instalation_guide_bardoline_13),
                        Integer.toString(R.drawable.instalation_guide_bardoline_14),
                        Integer.toString(R.drawable.instalation_guide_bardoline_15),
                        Integer.toString(R.drawable.instalation_guide_bardoline_16),
                        Integer.toString(R.drawable.instalation_guide_bardoline_17),
                        Integer.toString(R.drawable.instalation_guide_bardoline_18),
                        Integer.toString(R.drawable.instalation_guide_bardoline_19),
                        Integer.toString(R.drawable.instalation_guide_bardoline_20),
                        Integer.toString(R.drawable.instalation_guide_bardoline_21)
                };
                break;
            case "BARDOLINESUSTAINABILIT":
                Gbr = new String[] {
                        Integer.toString(R.drawable.onduvilla_detil_description)
                };
                break;
//            bituline

            case "BITULINEDESCRIPTION":
                Gbr = new String[] {
                        Integer.toString(R.drawable.bituline_detil_description)
                };
                break;
            case "BITULINESPECIFICATION":
                Gbr = new String[] {
                        Integer.toString(R.drawable.bituline_detil_specification)
                };
                break;
            case "BITULINECOLORSELECTION":
                Gbr = new String[] {
                        Integer.toString(R.drawable.bituline_detil_color)
                };
                break;
            case "BITULINEADVANTAGES":
                Gbr = new String[] {
                        Integer.toString(R.drawable.bituline_detil_advantages)
                };
                break;
            case "BITULINEACCESSORIES":
                Gbr = new String[] {
                        Integer.toString(R.drawable.onduvilla_detil_description)
                };
                break;
            case "BITULINEINSTALATIONGUIDE":
                Gbr = new String[] {
                        Integer.toString(R.drawable.instalation_guide_bituline)
                };
                break;
            case "BITULINESUSTAINABILIT":
                Gbr = new String[] {
                        Integer.toString(R.drawable.onduvilla_detil_description)
                };
                break;

//            menu program

            case "toko":

                String retailertype = session.getRetailerType();

                if (retailertype.equals("Toko Bahan Bangunan / Toko Tradisional")){

                    Gbr = new String[] {
                            Integer.toString(R.drawable.program_ondulucky_toko_bahan_bangunan_0),
                            Integer.toString(R.drawable.program_ondulucky_toko_bahan_bangunan_1),
                            Integer.toString(R.drawable.program_ondulucky_toko_bahan_bangunan_2),
                            Integer.toString(R.drawable.program_ondulucky_toko_bahan_bangunan_3),
                            Integer.toString(R.drawable.program_ondulucky_toko_bahan_bangunan_4)
                    };

                }else if (retailertype.equals("Toko Baja Ringan / Depo keramik")){

                    Gbr = new String[] {
                            Integer.toString(R.drawable.program_ondulucky_toko_baja_ringan0),
                            Integer.toString(R.drawable.program_ondulucky_toko_baja_ringan1),
                            Integer.toString(R.drawable.program_ondulucky_toko_baja_ringan2),
                            Integer.toString(R.drawable.program_ondulucky_toko_baja_ringan3),
                            Integer.toString(R.drawable.program_ondulucky_toko_baja_ringan4)
                    };

                }else if (retailertype.equals("Supermarket Bahan Bangunan")){

                    Gbr = new String[] {
                            Integer.toString(R.drawable.program_ondulucky_toko_supermarket_mo0),
                            Integer.toString(R.drawable.program_ondulucky_toko_supermarket_mo1),
                            Integer.toString(R.drawable.program_ondulucky_toko_supermarket_mo2),
                            Integer.toString(R.drawable.program_ondulucky_toko_supermarket_mo3),
                            Integer.toString(R.drawable.program_ondulucky_toko_supermarket_mo4)
                    };

                }

                txtMenuProductName.setText(pil);
                break;
            case "ondulucky baja ringan":
                Gbr = new String[] {
                        Integer.toString(R.drawable.program_ondulucky_tukang_baja_ringan0),
                        Integer.toString(R.drawable.program_ondulucky_tukang_baja_ringan1),
                        Integer.toString(R.drawable.program_ondulucky_tukang_baja_ringan2),
                        Integer.toString(R.drawable.program_ondulucky_tukang_baja_ringan3),
                        Integer.toString(R.drawable.program_ondulucky_tukang_baja_ringan4)
                };
                txtMenuProductName.setText(pil);
                break;
            case "kontes foto proyek":
                Gbr = new String[] {
                        Integer.toString(R.drawable.program_kontes_foto_0),
                        Integer.toString(R.drawable.program_kontes_foto_1),
                        Integer.toString(R.drawable.program_kontes_foto_2)
                };
                txtMenuProductName.setText(pil);
                break;
        }

    }
}
