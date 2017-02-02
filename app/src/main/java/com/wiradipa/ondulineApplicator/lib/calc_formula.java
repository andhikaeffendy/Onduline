package com.wiradipa.ondulineApplicator.lib;

/**
 * Created by sentanu on 1/19/2017.
 */

public class calc_formula {


    ///onduline : stat = 1, onduvile : stat = 2
    public double sumOfRoof(int stat, int tilt, double luas){

        double hasil=0;

        if(stat==1 && (tilt==15 || tilt>15)){
            hasil=luas/1.56;
        }else if (stat==1 && (tilt<15 || tilt>10) || tilt==10){
            hasil=luas/1.5;
        }else if (stat==1 && tilt < 10){
            hasil=luas/1.23;
        }else if (stat==2 && (tilt==15 || tilt>15) ){
            hasil=luas/0.31;
        }else {
            hasil=0;
        }

        return hasil;
    }
    public double sumOfNok(int statNok, int panjangBubungan, int panjangJuringluar, int panjangJuringDalam){

        double hasil=0;

        if (statNok==1){
            hasil=(panjangBubungan+panjangJuringDalam+panjangJuringluar)/0.775;
        }else {

            hasil=(panjangBubungan+panjangJuringluar)/0.775;
        }
        return hasil;
    }

    public double luas(double tilt){

        double hasil=112.56/Math.cos(Math.toRadians(tilt));
        return hasil;
    }

//    jenis rangka 1==kayu 2==baja , 1==onduline 2==onduvilla
    public double sumOfScrup(int frameType, int roofType, int tilt, int sumOfRoof){

        double hasil= 0;

        if(frameType==1 & roofType==1 & (tilt >=15)){

            hasil = sumOfRoof*19;

        }else if(frameType==1 & roofType==1 & tilt>=10 & tilt <15){

            hasil = sumOfRoof*18;

        }else if(frameType==1 & roofType==1 & tilt<10){

            hasil = sumOfRoof*19;

        }else if(frameType==2 & roofType==1 & (tilt >=15)){

            hasil = sumOfRoof*11;

        }else if(frameType==2 & roofType==1 & tilt>=10 & tilt <15){

            hasil = sumOfRoof*14;

        }else if(frameType==2 & roofType==1 & tilt<10){

            hasil = sumOfRoof*11;

        }else {

            hasil = sumOfRoof*5;

        }

        return hasil;
    }


}
