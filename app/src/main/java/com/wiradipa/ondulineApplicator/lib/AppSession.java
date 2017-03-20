package com.wiradipa.ondulineApplicator.lib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.wiradipa.ondulineApplicator.LoginActivity;

/**
 * Created by emrekabir on 5/23/16.
 */
public class AppSession {
    public final String INTRO = "intro";

    public final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    public final String SHAREDKEY = "ONDULINEMOBILE";
    public final String USERNAMEKEY = "username";
    public final String TOKENKEY = "token";
    public final String EMAILKEY = "email";
    public final String NAMEKEY = "name";
    public final String USERID = "userid";
    public final String USERTYPE = "user_type";
    public final String AVATAR = "avatar";
    public final String POIN = "poin";
    public final String RETAILERTYPE = "retailer_type";
    public final String SHOPNAME = "shop_name";
    public final String EMAILFORM = "email_form";
    public final String HPNOFORM = "hp_no_form";

    public final String DEVICEID = "device_id";
    public final String REGISTRATIONID = "registration_id";
    public final String SENTREG = "send_reg";


    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public AppSession(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHAREDKEY, Context.MODE_PRIVATE);
    }

    public void clearSession(){
        editor = sharedPreferences.edit();
        editor.clear().apply();
    }

    public void login(long userid, String username, String usertype, String token, String shop_name, String name, String email, String retailertype, String poin){
        editor = sharedPreferences.edit();
        editor.putString(USERNAMEKEY, username);
        editor.putString(SHOPNAME, shop_name);
        editor.putString(NAMEKEY, name);
        editor.putString(EMAILKEY,email);
        editor.putString(TOKENKEY,token);
        editor.putString(USERTYPE, usertype);
        editor.putString(POIN, poin);
        editor.putLong(USERID, userid);
        editor.putString(RETAILERTYPE, retailertype);
        editor.apply();
    }
    public void intro(String intro){
        editor = sharedPreferences.edit();
        editor.putString(INTRO, intro);
        editor.apply();
    }
    public void setPoin(String poin){
        editor = sharedPreferences.edit();
        editor.putString(POIN, poin);
        editor.apply();
    }
    public void login(long userid, String username, String usertype, String token, String name, String email,String poin){
        editor = sharedPreferences.edit();
        editor.putString(USERNAMEKEY, username);
        editor.putString(NAMEKEY, name);
        editor.putString(EMAILKEY,email);
        editor.putString(TOKENKEY,token);
        editor.putString(USERTYPE, usertype);
        editor.putString(POIN, poin);
        editor.putLong(USERID, userid);
        editor.apply();
    }
    public boolean is_login(){
        return (sharedPreferences.getString(TOKENKEY, null)!=null) ;
    }
    public void setFirstTimeLaunch(String isFirstTime) {
        editor = sharedPreferences.edit();
        editor.putString(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.apply();
    }

    public String isFirstTimeLaunch() {
        return sharedPreferences.getString(IS_FIRST_TIME_LAUNCH, "true");
    }


    public void checkSession(){
        if(!is_login())logout();
    }

    public void logout(){
        clearSession();
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
        ((Activity) context).finish();
    }

    public String getToken(){
        return sharedPreferences.getString(TOKENKEY,null);
    }
    public String getPoin(){
        return sharedPreferences.getString(POIN,null);
    }
    public String getIntro(){
        return sharedPreferences.getString(INTRO,"0");
    }

    public String getEmailForm(){
        return sharedPreferences.getString(EMAILFORM,"null");
    }
    public void setEmailForm(String email){
        editor = sharedPreferences.edit();
        editor.putString(EMAILFORM, email);
        editor.apply();
    }
    public String getHpNoForm(){
        return sharedPreferences.getString(HPNOFORM,"null");
    }
    public void setHpNoForm(String hp_no){
        editor = sharedPreferences.edit();
        editor.putString(HPNOFORM, hp_no);
        editor.apply();
    }

    public void setRegId (String regId){
        editor = sharedPreferences.edit();
        editor.putString(REGISTRATIONID, regId);
        editor.apply();
    }

    public void setDeviceId(String deviceId){
        editor = sharedPreferences.edit();
        editor.putString(DEVICEID, deviceId);
        editor.apply();
    }

    public String getDeviceId(){
        return sharedPreferences.getString(DEVICEID,null);
    }

    public String getRegId(){
        return sharedPreferences.getString(REGISTRATIONID,null);
    }

    public void setSentReg(boolean sentReg){
        editor = sharedPreferences.edit();
        editor.putBoolean(SENTREG, sentReg);
        editor.apply();
    }

    public boolean getSentReg(){
        return sharedPreferences.getBoolean(SENTREG,false);
    }

    public String getShopName(){
        return sharedPreferences.getString(SHOPNAME, "N/A");
    }
    public String getName(){
        return sharedPreferences.getString(NAMEKEY, "N/A");
    }
    public String getRetailerType(){
        return sharedPreferences.getString(RETAILERTYPE, "N/A");
    }

    public String getUSERTYPE() {
        return sharedPreferences.getString(USERTYPE, "N/A");
    }

    public String getUserName() {
        return sharedPreferences.getString(USERNAMEKEY, "N/A");
    }

    public String getEmail(){
        return sharedPreferences.getString(EMAILKEY, "N/A");
    }

    public long getUserid(){
        return sharedPreferences.getLong(USERID, 0);
    }

    public String getAvatar(){
        return sharedPreferences.getString(AVATAR,"");
    }

}
