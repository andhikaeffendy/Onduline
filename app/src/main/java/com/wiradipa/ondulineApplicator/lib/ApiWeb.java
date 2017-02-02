package com.wiradipa.ondulineApplicator.lib;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class ApiWeb {

	private String url = "http://onduline-mobile.wiradipa.com";
	private String apiurl = "http://onduline-mobile.wiradipa.com/api/";
	private int responseCode =200;

	public ApiWeb(){

//		try {
//			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
//
//				@Override
//				public boolean verify(String s, SSLSession sslSession) {
//					return true;
//				}
//
//			});
//			SSLContext sc = SSLContext.getInstance("TLS");
//			sc.init(null, trustAllCerts, new java.security.SecureRandom());
//			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//		} catch (KeyManagementException e) {
//			e.printStackTrace();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
	}

//	TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
//		public X509Certificate[] getAcceptedIssuers() {
//			return null;
//		}
//
//		@Override
//		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
//			// Not implemented
//		}
//
//		@Override
//		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
//			// Not implemented
//		}
//	} };

	public int getResponseCode(){
		return responseCode;
	}

	public boolean serverConnected(){
		return responseCode==200;
	}

	/** Fungsi login untuk apk*/
	public String Login(String username, String password){
		try {
			String url = apiurl+"users/sign_in";
			String params = "username="+ URLEncoder.encode(username, "UTF-8")+"&password="
					+ URLEncoder.encode(password, "UTF-8");
			return PostHttp(url, params);
		} catch (UnsupportedEncodingException e) {
			responseCode = 500;
			e.printStackTrace();
		}
		return null;
	}

	/** Fungsi register untuk retailer
	 * belum ada retailer type */
	public String RegisterRetailer(String user_type,String username, String password,String password_comfirmation, String email, String shop_name, String shop_address, String states_id, String city_id, String hp_no, String birth_date, String distribution_name, String owner_name, String id_no, String id_no_type){
		try {
			String url = apiurl+"users/create";

			String params = "&user_type="+ URLEncoder.encode(user_type, "UTF-8")+" &username="+ URLEncoder.encode( username, "UTF-8")+ "&password="+ URLEncoder.encode(password, "UTF-8")+ "&password_comfirmation="+ URLEncoder.encode(password_comfirmation, "UTF-8")
					+ "&email="+ URLEncoder.encode(email, "UTF-8")+"&shop_name="+ URLEncoder.encode(shop_name, "UTF-8")+"&shop_address="+ URLEncoder.encode(shop_address, "UTF-8")
					+ "&states_id="+ URLEncoder.encode(states_id, "UTF-8") + "&city_id="+ URLEncoder.encode(city_id, "UTF-8")+"&hp_no="+ URLEncoder.encode(hp_no, "UTF-8")
					+ "&birth_date="+ URLEncoder.encode(birth_date, "UTF-8") + "&distribution_name="+ URLEncoder.encode(distribution_name, "UTF-8") + "&owner_name="+ URLEncoder.encode(owner_name, "UTF-8")
					+ "&id_no="+ URLEncoder.encode(id_no, "UTF-8") + "&id_no_type="+ URLEncoder.encode(id_no_type, "UTF-8");


			return PostHttp(url, params);
		} catch (UnsupportedEncodingException e) {
			responseCode = 500;
			e.printStackTrace();
		}
		return null;
	}

	/** Fungsi register untuk aplikator*/
	public String RegisterApplicator(String user_type,String username, String password, String password_comfirmation, String email, String name, String address, String states_id, String city_id, String hp_no, String company_name, String birth_date, String association_name, String id_no, String id_no_type){
		try {
			String url = apiurl+"users/create";
			String params =
					"&user_type="+ URLEncoder.encode(user_type, "UTF-8")+" &username="+ URLEncoder.encode( username, "UTF-8")+ "&password="+ URLEncoder.encode(password, "UTF-8")+ "&password_comfirmation="+ URLEncoder.encode(password_comfirmation, "UTF-8")
							+ "&email="+ URLEncoder.encode(email, "UTF-8")+"&name="+ URLEncoder.encode(name, "UTF-8")+"&address="+ URLEncoder.encode(address, "UTF-8")
							+ "&states_id="+ URLEncoder.encode(states_id, "UTF-8") + "&city_id="+ URLEncoder.encode(city_id, "UTF-8")+"&hp_no="+ URLEncoder.encode(hp_no, "UTF-8")
							+ "&birth_date="+ URLEncoder.encode(birth_date, "UTF-8") + "&company_name="+ URLEncoder.encode(company_name, "UTF-8") + "&association_name="+ URLEncoder.encode(association_name, "UTF-8")
							+ "&id_no="+ URLEncoder.encode(id_no, "UTF-8") + "&id_no_type="+ URLEncoder.encode(id_no_type, "UTF-8");



			return PostHttp(url, params);
		} catch (UnsupportedEncodingException e) {
			responseCode = 500;
			e.printStackTrace();
		}
		return null;
	}

	/** Fungsi activity untuk apk*/
	public String Activation(String activation_code,String activation_email){
		try {
			String url = apiurl+"users/activation_code";

			String params ="activation_code="+ URLEncoder.encode(activation_code, "UTF-8")+"&email="+ URLEncoder.encode(activation_email, "UTF-8");

			return PostHttp(url, params);
		} catch (UnsupportedEncodingException e) {
			responseCode = 500;
			e.printStackTrace();
		}
		return null;
	}

	/** Fungsi technical support instalation guide untuk apk*/										//installation
	public String TechnicalSupportInstalationGuide(String token, String detail, String support_type){
		try {
			String url = apiurl+"supports";
			String params = "token="+ URLEncoder.encode(token, "UTF-8")
					+"&detail=" + URLEncoder.encode(detail, "UTF-8")
					+"&support_type=" + URLEncoder.encode(support_type, "UTF-8");
			return PostHttp(url, params);
		} catch (UnsupportedEncodingException e) {
			responseCode = 500;
			e.printStackTrace();
		}
		return null;
	}

	/** Fungsi technical support instalation guide complain untuk apk*/								//installation_complaint
	public String TechnicalSupportInstalationGuideComplain(String token, String detail, String support_type){
		try {
			String url = apiurl+"supports";
			String params = "token="+ URLEncoder.encode(token, "UTF-8")
					+"&detail=" + URLEncoder.encode(detail, "UTF-8")
					+"&support_type=" + URLEncoder.encode(support_type, "UTF-8");
			return PostHttp(url, params);
		} catch (UnsupportedEncodingException e) {
			responseCode = 500;
			e.printStackTrace();
		}
		return null;
	}

	/** Fungsi technical support product complain untuk apk*/										//product_complaint
	public String TechnicalSupportProductComplain(String token, String detail, String support_type){
		try {
			String url = apiurl+"supports";
			String params = "token="+ URLEncoder.encode(token, "UTF-8")
					+"&detail=" + URLEncoder.encode(detail, "UTF-8")
					+"&support_type=" + URLEncoder.encode(support_type, "UTF-8");
			return PostHttp(url, params);
		} catch (UnsupportedEncodingException e) {
			responseCode = 500;
			e.printStackTrace();
		}
		return null;
	}
	/** Fungsi technical support product complain untuk apk*/										//product_complaint
	public String TechnicalSupportSupervisiProject(String token, String Nama, String Address, String Phone, String OwnerName, String Detail){
		try {
			String url = apiurl+"project_supervises";
			String params = "token="+ URLEncoder.encode(token, "UTF-8")
					+"&name=" + URLEncoder.encode(Nama, "UTF-8")
					+"&address=" + URLEncoder.encode(Address, "UTF-8")
					+"&hp_no=" + URLEncoder.encode(Phone, "UTF-8")
					+"&owner_name=" + URLEncoder.encode(OwnerName, "UTF-8")
					+"&detail=" + URLEncoder.encode(Detail, "UTF-8");
			return PostHttp(url, params);
		} catch (UnsupportedEncodingException e) {
			responseCode = 500;
			e.printStackTrace();
		}
		return null;
	}


	public String GetCities(){
		String url = apiurl+"cities";
		return GetHttp(url);
	}

	public String GetStates(){
		String url = apiurl+"states";
		return GetHttp(url);
	}

	public String GetShipping(String token, int id){
		String url = apiurl+"order_items/"+id+"?auth_token="+token;
		return GetHttp(url);
	}

	public String GetShippings(String token){
		String url = apiurl+"order_items/?auth_token="+token;
		return GetHttp(url);
	}

	public String submitOrder(String token, String order){
		String url = apiurl+"users/sign_in";
		String params = "auth_token="+ token +"&order=" + order;
		return PostHttp(url, params);
	}

	public String GetStates(String token){
		String url = apiurl+"states?auth_token="+token;
		return GetHttp(url);
	}

	public String GetDistricts(String token){
		String url = apiurl+"districts/?auth_token="+token;
		return GetHttp(url);
	}

	public String GetSubDistricts(String token){
		String url = apiurl+"sub_districts/?auth_token="+token;
		return GetHttp(url);
	}

	public String GetAddresses(String token){
		String url = apiurl+"user_addresses/?auth_token="+token;
		return GetHttp(url);
	}

	public String PostAddress(String token, String label, String address, int state_id,
							  int district_id, int subdistrict_id, double latitude, double longitude){
		String url = apiurl+"user_addresses";
		try {
			String params = "auth_token="+ token +
					"&address_label=" + URLEncoder.encode(label, "UTF-8") +
					"&address=" + URLEncoder.encode(address, "UTF-8") +
					"&state_id=" + state_id + "&district_id=" + district_id +
					"&sub_district_id=" + subdistrict_id + "&latitude="+ latitude +
					"&longitude=" +longitude ;
			return PostHttp(url, params);
		} catch (UnsupportedEncodingException e) {
			responseCode = 500;
			e.printStackTrace();
		}
		return null;
	}

	public String UpdateAddress(String token, int id, String label, String address, int state_id,
								int district_id, int subdistrict_id, double latitude, double longitude){
		String url = apiurl+"user_addresses/update";
		try {
			String params = "auth_token="+ token + "&id=" + id +
					"&address_label=" + URLEncoder.encode(label, "UTF-8") +
					"&address=" + URLEncoder.encode(address, "UTF-8") +
					"&state_id=" + state_id + "&district_id=" + district_id +
					"&sub_district_id=" + subdistrict_id + "&latitude="+ latitude +
					"&longitude=" +longitude ;
			return PostHttp(url, params);
		} catch (UnsupportedEncodingException e) {
			responseCode = 500;
			e.printStackTrace();
		}
		return null;
	}

	public String PostTariff(String token, String shipment){
		String url = apiurl+"user_shipments";
		String params = "auth_token="+ token + "&shipment=" + shipment;
		return PostHttp(url, params);
	}

	public String PostOrder(String token, String order){
		String url = apiurl+"orders";
		String params = "auth_token="+ token + "&order=" + order;
		return PostHttp(url, params);
	}

	public String GetHttp (String complete_url) {
		try {
			System.out.println("Getting Data From :" + complete_url);
			URL url = new URL(complete_url);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");

			System.out.println("Response Code :" + conn.getResponseCode());
			System.out.println("Response Message :" + conn.getResponseMessage());
			responseCode = conn.getResponseCode();
			if(responseCode==200) {

				InputStream inputStream = new BufferedInputStream(conn.getInputStream());

				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

				StringBuilder stringBuilder = new StringBuilder();

				String bufferedStrChunk = null;

				while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
					stringBuilder.append(bufferedStrChunk);
				}

				System.out.println("Response Data :" + stringBuilder.toString());

				return stringBuilder.toString();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String PostHttp (String complete_url, String complete_params) {
		try {
			System.out.println("Getting Data From :" + complete_url);
			URL url = new URL(complete_url);
			System.out.println("Getting Data From  complete_params:" + complete_params);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			PrintWriter out = new PrintWriter(conn.getOutputStream());
			out.print(complete_params);
			out.close();


			System.out.println("Response Code :" + conn.getResponseCode());
			System.out.println("Response Message :" + conn.getResponseMessage());

			responseCode = conn.getResponseCode();
			if(responseCode==200) {

				InputStream inputStream = new BufferedInputStream(conn.getInputStream());

				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

				StringBuilder stringBuilder = new StringBuilder();

				String bufferedStrChunk = null;

				while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
					stringBuilder.append(bufferedStrChunk);
				}

				System.out.println("Response Data :" + stringBuilder.toString());

				return stringBuilder.toString();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}