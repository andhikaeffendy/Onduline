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


}
