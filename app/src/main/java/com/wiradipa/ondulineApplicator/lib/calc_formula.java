package com.wiradipa.ondulineApplicator.lib;

/**
 * Created by sentanu on 1/19/2017.
 */

public class calc_formula {


    ///onduline : stat = 1, onduvile : stat = 2
    public double sumOfRoof(int statRoof, int tilt, int luas){

        double hasil=0;

        if(statRoof==1 && (tilt==15 || tilt>15)){
            hasil=luasCosRadian(luas,tilt)/1.539;
//            System.out.println(luas + " / 1.539 = " + hasil );
        }else if (statRoof==1 && (tilt<15 || tilt>10) || tilt==10){
            hasil=luasCosRadian(luas,tilt)/1.539;
//            System.out.println(luas + " / 1.539 = " + hasil );
        }else if (statRoof==1 && tilt < 10){
            hasil=luasCosRadian(luas,tilt)/1.277;
//            System.out.println(luas + " / 1.277 = " + hasil );
        }else if (statRoof==2 && (tilt==15 || tilt>15) ){
            hasil=luasCosRadian(luas,tilt)/0.3088;
//            System.out.println(luas + " / 0.3088 = " + hasil );
        }else {
            hasil=0;
        }
//        System.out.println("kemiringan :" + tilt + " luas : " + luas + " hasil : " + hasil);
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
    public double sumOfRidges(double ridgeLength, double hipLength){

        double hasil=(ridgeLength+hipLength)*1.25;
//        System.out.println("("+ridgeLength+ " + " + hipLength + ") * 1.25 = " + hasil);
        return hasil;
    }

    public double sumOfScrews(double sumOfScrewsForRoof, double sumOfScrewsForRidge){

        double hasil=sumOfScrewsForRoof + sumOfScrewsForRidge;
        return hasil;
    }

    public double luasCosRadian(double roofArea,double tilt){

        double hasil=roofArea/Math.cos(Math.toRadians(tilt));
        return hasil;
    }

//    jenis rangka 1==kayu 2==baja , 1==onduline 2==onduvilla
    public double sumOfScrewsForRoof(int frameType, int roofType, int tilt, int sumOfRoof){

        double hasil= 0;

        if(frameType==1 & roofType==1 & (tilt >=15)){

            hasil = sumOfRoof*19;

        }else if(frameType==1 & roofType==1 & tilt>=10 & tilt <15){

            hasil = sumOfRoof*18;

        }else if(frameType==1 & roofType==1 & tilt<10){

            hasil = sumOfRoof*20;

        }else if(frameType==2 & roofType==1 & (tilt >=15)){

            hasil = sumOfRoof*11;

        }else if(frameType==2 & roofType==1 & tilt>=10 & tilt <15){

            hasil = sumOfRoof*14;

        }else if(frameType==2 & roofType==1 & tilt<10){

            hasil = sumOfRoof*20;

        }else {

            hasil = sumOfRoof*5;

        }

        return hasil;
    }

    public double sumOfScrewsForRidge(int sumOfRidges){

        double hasil= sumOfRidges*8;

        return hasil;
    }

    //    jenis rangka 1==kayu 2==baja
    public double sumOfNokOndulineBubungan(int frametype, int sumOfNok){

        double hasil = 0;

        if(frametype==1){
            hasil = 16 * sumOfNok;
        }else {
            hasil = 8 * sumOfNok;
        }

        return hasil;
    }

}
