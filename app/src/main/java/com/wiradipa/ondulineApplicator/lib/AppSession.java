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
    public final String SHAREDKEY = "ONDULINEMOBILE";
    public final String USERNAMEKEY = "username";
    public final String TOKENKEY = "token";
    public final String EMAILKEY = "email";
    public final String NAMEKEY = "name";
    public final String USERID = "userid";
    public final String AVATAR = "avatar";

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

    public void login(long userid, String username, String token, String name, String email,
                      String avatar){
        editor = sharedPreferences.edit();
        editor.putString(USERNAMEKEY, username);
        editor.putString(NAMEKEY, name);
        editor.putString(EMAILKEY,email);
        editor.putString(TOKENKEY,token);
        editor.putLong(USERID, userid);
        editor.putString(AVATAR, avatar);
        editor.apply();
    }

    public boolean is_login(){
        return (sharedPreferences.getString(TOKENKEY, null)!=null) ;
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

    public String getName(){
        return sharedPreferences.getString(NAMEKEY, "N/A");
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
