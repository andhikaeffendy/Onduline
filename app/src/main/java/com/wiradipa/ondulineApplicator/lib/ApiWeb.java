package com.wiradipa.ondulineApplicator.lib;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
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


public class ApiWeb {

    //	private String url = "http://onduline-mobile.wiradipa.com";
//	private String apiurl = "http://onduline-mobile.wiradipa.com/api/";
    private String url = "http://128.199.142.101";
    private String apiurl = "http://128.199.142.101/api/";
    private int responseCode = 200;

    public ApiWeb() {

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


    public int getResponseCode() {
        return responseCode;
    }

    public boolean serverConnected() {
        return responseCode == 200;
    }

    /**
     * Fungsi login untuk apk
     */
    public String Login(String username, String password) {
        try {
            String url = apiurl + "users/sign_in";
            String params = "username=" + URLEncoder.encode(username, "UTF-8") + "&password="
                    + URLEncoder.encode(password, "UTF-8");
            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi register untuk retailer
     * belum ada retailer type
     */
    public String RegisterRetailer(String retailer_type, String user_type, String username, String password, String password_comfirmation, String email, String shop_name, String shop_address, String states_id, String city_id, String hp_no, String birth_date, String distributor_name, String owner_name, String id_no, String id_no_type, String phone) {
        try {
            String url = apiurl + "users/create";

            String params = "retailer_type=" + URLEncoder.encode(retailer_type, "UTF-8")
                    + "&user_type=" + URLEncoder.encode(user_type, "UTF-8")
                    + " &username=" + URLEncoder.encode(username, "UTF-8")
                    + "&password=" + URLEncoder.encode(password, "UTF-8")
                    + "&password_comfirmation=" + URLEncoder.encode(password_comfirmation, "UTF-8")
                    + "&email=" + URLEncoder.encode(email, "UTF-8")
                    + "&shop_name=" + URLEncoder.encode(shop_name, "UTF-8")
                    + "&shop_address=" + URLEncoder.encode(shop_address, "UTF-8")
                    + "&states_id=" + URLEncoder.encode(states_id, "UTF-8")
                    + "&city_id=" + URLEncoder.encode(city_id, "UTF-8")
                    + "&phone=" + URLEncoder.encode(phone, "UTF-8")
                    + "&hp_no=" + URLEncoder.encode(hp_no, "UTF-8")
                    + "&birth_date=" + URLEncoder.encode(birth_date, "UTF-8")
                    + "&distributor_name=" + URLEncoder.encode(distributor_name, "UTF-8")
                    + "&owner_name=" + URLEncoder.encode(owner_name, "UTF-8")
                    + "&id_no=" + URLEncoder.encode(id_no, "UTF-8")
                    + "&id_no_type=" + URLEncoder.encode(id_no_type, "UTF-8");

            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi register untuk retailer
     * belum ada retailer type
     */   //user_type, 			username, 		password, 		password_confirmation, 		  email, 		name, 		 address, 		 states_id, 	   city_id, 	   hp_no, 		 birth_date, 		id_no, 		  id_no_type
    public String RegisterIndividu(String user_type, String username, String password, String password_comfirmation, String email, String name, String address, String states_id, String city_id, String hp_no, String birth_date, String id_no, String id_no_type, String occupation, String company_name) {
        try {
            String url = apiurl + "users/create";

            String params = "user_type=" + URLEncoder.encode(user_type, "UTF-8")
                    + " &username=" + URLEncoder.encode(username, "UTF-8")
                    + "&password=" + URLEncoder.encode(password, "UTF-8")
                    + "&password_comfirmation=" + URLEncoder.encode(password_comfirmation, "UTF-8")
                    + "&email=" + URLEncoder.encode(email, "UTF-8")
                    + "&name=" + URLEncoder.encode(name, "UTF-8")
                    + "&address=" + URLEncoder.encode(address, "UTF-8")
                    + "&company_name=" + URLEncoder.encode(company_name, "UTF-8")
                    + "&states_id=" + URLEncoder.encode(states_id, "UTF-8")
                    + "&city_id=" + URLEncoder.encode(city_id, "UTF-8")
                    + "&hp_no=" + URLEncoder.encode(hp_no, "UTF-8")
                    + "&birth_date=" + URLEncoder.encode(birth_date, "UTF-8")
                    + "&id_no=" + URLEncoder.encode(id_no, "UTF-8")
                    + "&occupation=" + URLEncoder.encode(occupation, "UTF-8")
                    + "&id_no_type=" + URLEncoder.encode(id_no_type, "UTF-8");


            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi register untuk aplikator
     */
    public String RegisterApplicator(String user_type, String username, String password, String password_comfirmation, String email, String name, String address, String states_id, String city_id, String hp_no, String company_name, String birth_date, String association_name, String id_no, String id_no_type) {
        try {
            String url = apiurl + "users/create";
            String params =
                    "&user_type=" + URLEncoder.encode(user_type, "UTF-8")
                            + " &username=" + URLEncoder.encode(username, "UTF-8")
                            + "&password=" + URLEncoder.encode(password, "UTF-8")
                            + "&password_comfirmation=" + URLEncoder.encode(password_comfirmation, "UTF-8")
                            + "&email=" + URLEncoder.encode(email, "UTF-8")
                            + "&name=" + URLEncoder.encode(name, "UTF-8")
                            + "&address=" + URLEncoder.encode(address, "UTF-8")
                            + "&states_id=" + URLEncoder.encode(states_id, "UTF-8")
                            + "&city_id=" + URLEncoder.encode(city_id, "UTF-8")
                            + "&hp_no=" + URLEncoder.encode(hp_no, "UTF-8")
                            + "&birth_date=" + URLEncoder.encode(birth_date, "UTF-8")
                            + "&company_name=" + URLEncoder.encode(company_name, "UTF-8")
                            + "&association_name=" + URLEncoder.encode(association_name, "UTF-8")
                            + "&id_no=" + URLEncoder.encode(id_no, "UTF-8")
                            + "&id_no_type=" + URLEncoder.encode(id_no_type, "UTF-8");


            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi activity untuk apk
     */
    public String Activation(String activation_code, String activation_email) {
        try {
            String url = apiurl + "users/activation_code";

            String params = "activation_code=" + URLEncoder.encode(activation_code, "UTF-8") + "&email=" + URLEncoder.encode(activation_email, "UTF-8");

            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi activity untuk apk
     */
    public String ResendActivatiionCode(String email, String hp_no) {
        try {
            String url = apiurl + "users/resend_activation_code";

            String params = "email=" + URLEncoder.encode(email, "UTF-8") + "&hp_no=" + URLEncoder.encode(hp_no, "UTF-8");

            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi activity untuk apk
     */
    public String OrderSuvenir(String token, String souvenir_orders_attributes) {
        try {
            String url = apiurl + "user_souvenir_orders";

            String params = "token=" + URLEncoder.encode(token, "UTF-8") +
                    "&user_souvenir_order=" + URLEncoder.encode(souvenir_orders_attributes, "UTF-8");
//			"&souvenir_orders_attributes="+ URLEncoder.encode(souvenir_orders_attributes, "UTF-8");

//			user_souvenir_order

            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi activity untuk apk
     */
    public String OrderBrosur(String token, String brosur_orders_attributes) {
        try {
            String url = apiurl + "user_brochure_orders";

            String params = "token=" + URLEncoder.encode(token, "UTF-8") +
                    "&user_brochure_order=" + URLEncoder.encode(brosur_orders_attributes, "UTF-8");
//			"&souvenir_orders_attributes="+ URLEncoder.encode(souvenir_orders_attributes, "UTF-8");

//			user_souvenir_order

            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi activity untuk apk
     */
    public String OrderProduct(String token, String product_id, String color_id, String total_nok, String total_screw, String total_least, String etc) {
        try {
            String url = apiurl + "product_orders";

            String params = "token=" + URLEncoder.encode(token, "UTF-8") +
                    "&product_id=" + URLEncoder.encode(product_id, "UTF-8") +
                    "&color_id=" + URLEncoder.encode(color_id, "UTF-8") +
                    "&total_nok=" + URLEncoder.encode(total_nok, "UTF-8") +
                    "&total_screw=" + URLEncoder.encode(total_screw, "UTF-8") +
                    "&total_least=" + URLEncoder.encode(total_least, "UTF-8") +
                    "&etc=" + URLEncoder.encode(etc, "UTF-8");
//			"&souvenir_orders_attributes="+ URLEncoder.encode(souvenir_orders_attributes, "UTF-8");

//			user_souvenir_order

            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi technical support instalation guide untuk apk
     */                                        //installation
    public String TechnicalSupportInstalationGuide(String token, String detail, String support_type) {
        try {
            String url = apiurl + "supports";
            String params = "token=" + URLEncoder.encode(token, "UTF-8")
                    + "&detail=" + URLEncoder.encode(detail, "UTF-8")
                    + "&support_type=" + URLEncoder.encode(support_type, "UTF-8");
            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi technical support instalation guide complain untuk apk
     */                                //installation_complaint
    public String TechnicalSupportInstalationGuideComplain(String token, String detail, String support_type) {
        try {
            String url = apiurl + "supports";
            String params = "token=" + URLEncoder.encode(token, "UTF-8")
                    + "&detail=" + URLEncoder.encode(detail, "UTF-8")
                    + "&support_type=" + URLEncoder.encode(support_type, "UTF-8");
            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi technical support product complain untuk apk
     */                                        //product_complaint
    public String TechnicalSupportProductComplain(String token, String detail, String support_type) {
        try {
            String url = apiurl + "supports";
            String params = "token=" + URLEncoder.encode(token, "UTF-8")
                    + "&detail=" + URLEncoder.encode(detail, "UTF-8")
                    + "&support_type=" + URLEncoder.encode(support_type, "UTF-8");
            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }


    // pengambilan anam product api/products
    //product_id, order_date, address, color_id, amount

    /**
     * Fungsi OrderApplicator untuk apk
     */                                        //product_complaint
    public String Order(String token, String receipt, String order_date, String distributor, String store_name, String color_id, String city_id, String states_id, String onduvilla_amount, String onduline_amount, String onduclair_amount) {
        String url = apiurl + "orders";

//		return PostOrderFile(url, receipt,token,product_id,order_date,address,distributor,store_name, color_id,amount,city_id,states_id);
        return PostOrderFile(url, receipt, token, order_date, distributor, store_name, color_id, city_id, states_id, onduvilla_amount, onduline_amount, onduclair_amount);
    }

    /**
     * Fungsi OrderApplicator untuk apk
     */
    public String NewProject(String token, String receipt, String project_type, String address, String order_date, String city_id, String states_id, String product_id, String color_id, String roof_width) {
        String url = apiurl + "projects";

        return PostProjectFile(url, receipt, token, project_type, address, order_date, city_id, states_id, product_id, color_id, roof_width);
    }

    /**
     * Fungsi OrderApplicator untuk apk
     */
    public String inputProjectInformation(String token, String project_name, String roof_type, String address, String city_id, String states_id, String roof_width) {

        try {
            String url = apiurl + "project_infos";
            String params = "token=" + URLEncoder.encode(token, "UTF-8")
                    + "&name=" + URLEncoder.encode(project_name, "UTF-8")
                    + "&product_id=" + URLEncoder.encode(roof_type, "UTF-8")
                    + "&city_id=" + URLEncoder.encode(city_id, "UTF-8")
                    + "&state_id=" + URLEncoder.encode(states_id, "UTF-8")
                    + "&address=" + URLEncoder.encode(address, "UTF-8")
                    + "&roof_width=" + URLEncoder.encode(roof_width, "UTF-8");
            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Fungsi technical support supervisi proyek untuk apk
     */                                        //product_complaint
    public String TechnicalSupportSupervisiProject(String token, String Nama, String Address, String Phone, String OwnerName, String Detail) {
        try {
            String url = apiurl + "project_supervises";
            String params = "token=" + URLEncoder.encode(token, "UTF-8")
                    + "&name=" + URLEncoder.encode(Nama, "UTF-8")
                    + "&address=" + URLEncoder.encode(Address, "UTF-8")
                    + "&hp_no=" + URLEncoder.encode(Phone, "UTF-8")
                    + "&owner_name=" + URLEncoder.encode(OwnerName, "UTF-8")
                    + "&detail=" + URLEncoder.encode(Detail, "UTF-8");
            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi contact us untuk apk
     */                                        //product_complaint
    public String contactUs(String token, String subject, String detail) {
        try {
            String url = apiurl + "feedbacks";
            String params = "token=" + URLEncoder.encode(token, "UTF-8")
                    + "&subject=" + URLEncoder.encode(subject, "UTF-8")
                    + "&detail=" + URLEncoder.encode(detail, "UTF-8");
            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi technical support supervisi proyek untuk apk
     */
    public String TechnicalSupportTrainingRequest(String token, String Phone, String date, String totalPeserta, String stateId, String cityId, String Address, String keterangan) {
        try {
            String url = apiurl + "training_requests";
            String params = "token=" + URLEncoder.encode(token, "UTF-8")
                    + "&hp_no=" + URLEncoder.encode(Phone, "UTF-8")
                    + "&request_date=" + URLEncoder.encode(date, "UTF-8")
                    + "&total_participant=" + URLEncoder.encode(totalPeserta, "UTF-8")// ini minta ke ka Odit untuk parameter"nya.
                    + "&state_id=" + URLEncoder.encode(stateId, "UTF-8")
                    + "&city_id=" + URLEncoder.encode(cityId, "UTF-8")
                    + "&address=" + URLEncoder.encode(Address, "UTF-8")
                    + "&detail=" + URLEncoder.encode(keterangan, "UTF-8");// ini minta ke ka Odit untuk parameter"nya.
            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi technical support supervisi proyek untuk apk
     */
    public String ForgotPassword(String hp_no) {
        try {
            String url = apiurl + "users/forgot_password";
            String params = "hp_no=" + URLEncoder.encode(hp_no, "UTF-8");
            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi get point untuk apk
     */
    public String getPoint(String token) {
        try {
            String url = apiurl + "users/get_point";
            String params = "token=" + URLEncoder.encode(token, "UTF-8");
            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fungsi technical support supervisi proyek untuk apk
     */
    public String GetproductColors(String product_id) {
        String url = apiurl + "colors/?product_id=" + product_id;
        return GetHttp(url);
    }
//GetproductColors
//	public String GetFile (long id, String file){
//		String url = apiurl+"product_locations/"+id+"/upload_photo";
//		return PostFile(url, imei, file);
//	}

    public String GetCities(String state_id) {
        String url = apiurl + "cities?state_id=" + state_id;
        return GetHttp(url);
    }

    public String GetStates() {
        String url = apiurl + "states";
        return GetHttp(url);
    }

    public String Getsouvenirs() {
        String url = apiurl + "souvenirs";
        return GetHttp(url);
    }

    public String Getbrochures() {
        String url = apiurl + "brochures";
        return GetHttp(url);
    }

    public String Getproducts() {
        String url = apiurl + "products";
        return GetHttp(url);
    }

    public String GetProjectTypes() {
        String url = apiurl + "project_types";
        return GetHttp(url);
    }

    public String GetProjetc(String token) {
        String url = apiurl + "projects?token=" + token;
        return GetHttp(url);
    }

    public String GetProjectDetil(String id, String token) {
        String url = apiurl + "projects/" + id + "?token=" + token;
        return GetHttp(url);
    }

    public String GetOrders(String token) {
//		http://onduline-mobile.wiradipa.com/api/orders?token=H_op4Hi7uyMUf78n_T4Y
        String url = apiurl + "orders?token=" + token;
        return GetHttp(url);
    }

    public String GetOrderDetil(String id, String token) {
        String url = apiurl + "orders/" + id + "?token=" + token;
        return GetHttp(url);
    }
//	public String ResendActivatiionCode(String token, int id){
//		String url = apiurl+"resend_activation_code";
//		return GetHttp(url);
//	}

    public String GetShippings(String token) {
        String url = apiurl + "order_items/?auth_token=" + token;
        return GetHttp(url);
    }

    public String submitOrder(String token, String order) {
        String url = apiurl + "users/sign_in";
        String params = "auth_token=" + token + "&order=" + order;
        return PostHttp(url, params);
    }

    public String GetStates(String token) {
        String url = apiurl + "states?auth_token=" + token;
        return GetHttp(url);
    }

    public String GetDistricts(String token) {
        String url = apiurl + "districts/?auth_token=" + token;
        return GetHttp(url);
    }

    public String GetSubDistricts(String token) {
        String url = apiurl + "sub_districts/?auth_token=" + token;
        return GetHttp(url);
    }

    public String GetAddresses(String token) {
        String url = apiurl + "user_addresses/?auth_token=" + token;
        return GetHttp(url);
    }

    //																								retailler			applicator			dikosongin
    public String PostOrderFile(String complete_url, String file, String token, String order_date, String distributor, String store_name, String color_id, String city_id, String states_id, String onduvilla_amount, String onduline_amount, String onduclair_amount) {
        String boundary = "*****";
        File selectedFile = new File(file);
        String contenttype = "image/png";
        if (file.substring(file.length() - 3).compareToIgnoreCase("png") != 0)
            contenttype = "image/jpeg";
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.setCharset(MIME.UTF8_CHARSET);
        entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        entityBuilder.addBinaryBody("receipt", selectedFile, ContentType.create(contenttype), selectedFile.getName());

        /** disamain sama order parameter */
        entityBuilder.addTextBody("token", token);
        entityBuilder.addTextBody("order_date", order_date);
        entityBuilder.addTextBody("store_name", store_name);
        entityBuilder.addTextBody("distributor_name", distributor);
        entityBuilder.addTextBody("color_id", color_id);
        entityBuilder.addTextBody("city_id", city_id);
        entityBuilder.addTextBody("state_id", states_id);
        entityBuilder.addTextBody("onduvilla_amount", onduvilla_amount);
        entityBuilder.addTextBody("onduline_amount", onduline_amount);
        entityBuilder.addTextBody("onduclair_amount", onduclair_amount);

        entityBuilder.setBoundary(boundary);
        HttpEntity entity = entityBuilder.build();
        try {
            URL url = new URL(complete_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);//Allow Inputs
            conn.setDoOutput(true);//Allow Outputs
            conn.setUseCaches(false);//Don't use a cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            entity.writeTo(conn.getOutputStream());

            System.out.println("Response Code :" + conn.getResponseCode());
            System.out.println("Response Message :" + conn.getResponseMessage());

            responseCode = conn.getResponseCode();

            if (conn.getResponseCode() == 200) {

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

//	public String PostOrderFile (String complete_url, String file, String token,String product_id,String order_date,String address,String distributor,String store_name,String color_id,String amount,String city_id,String states_id) {
//		String boundary = "*****";
//		File selectedFile = new File(file);
//		String contenttype="image/png";
//		if(file.substring(file.length()-3).compareToIgnoreCase("png")!=0)contenttype="image/jpeg";
//		MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
//		entityBuilder.setCharset(MIME.UTF8_CHARSET);
//		entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//		entityBuilder.addBinaryBody("receipt", selectedFile, ContentType.create(contenttype), selectedFile.getName());
//
//		/** disamain sama order parameter */
//		entityBuilder.addTextBody("token", token);
//		entityBuilder.addTextBody("product_id", product_id);
//		entityBuilder.addTextBody("order_date", order_date);
//		entityBuilder.addTextBody("address", address);
//		entityBuilder.addTextBody("store_name", store_name);
//		entityBuilder.addTextBody("distributor_name", distributor);
//		entityBuilder.addTextBody("color_id", color_id);
//		entityBuilder.addTextBody("amount", amount);
//		entityBuilder.addTextBody("city_id", city_id);
//		entityBuilder.addTextBody("state_id", states_id);
//
//		entityBuilder.setBoundary(boundary);
//		HttpEntity entity = entityBuilder.build();
//		try {
//			System.out.println("Getting Data From :" + complete_url);
//			URL url = new URL(complete_url);
//			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//			conn.setRequestMethod("POST");
//			conn.setDoInput(true);//Allow Inputs
//			conn.setDoOutput(true);//Allow Outputs
//			conn.setUseCaches(false);//Don't use a cached Copy
//			conn.setRequestMethod("POST");
//			conn.setRequestProperty("Connection", "Keep-Alive");
//			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
//			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//
//			entity.writeTo(conn.getOutputStream());
//
//			System.out.println("Response Code :" + conn.getResponseCode());
//			System.out.println("Response Message :" + conn.getResponseMessage());
//
//			responseCode = conn.getResponseCode();
//
//			if(conn.getResponseCode()==200) {
//
//				InputStream inputStream = new BufferedInputStream(conn.getInputStream());
//
//				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//
//				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//				StringBuilder stringBuilder = new StringBuilder();
//
//				String bufferedStrChunk = null;
//
//				while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
//					stringBuilder.append(bufferedStrChunk);
//				}
//
//				System.out.println("Response Data :" + stringBuilder.toString());
//
//				return stringBuilder.toString();
//			}
//
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (ProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//
//		return null;
//	}

    public String PostProjectFile(String complete_url, String file, String token, String project_type, String address, String order_date, String city_id, String states_id, String product_id, String color_id, String roof_width) {
        String boundary = "*****";
        File selectedFile = new File(file);
        String contenttype = "image/png";
        if (file.substring(file.length() - 3).compareToIgnoreCase("png") != 0)
            contenttype = "image/jpeg";
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.setCharset(MIME.UTF8_CHARSET);
        entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        entityBuilder.addBinaryBody("receipt", selectedFile, ContentType.create(contenttype), selectedFile.getName());

//		complete_url, String file,String token,String project_type ,String address ,String order_date ,String
// 		city_id ,String states_id ,String product_id ,String color_id ,String roof_width
        /** disamain sama order parameter */
        entityBuilder.addTextBody("token", token);
        entityBuilder.addTextBody("product_id", product_id);
        entityBuilder.addTextBody("order_date", order_date);
        entityBuilder.addTextBody("address", address);
        entityBuilder.addTextBody("project_type_id", project_type);
        entityBuilder.addTextBody("color_id", color_id);
        entityBuilder.addTextBody("roof_width", roof_width);
        entityBuilder.addTextBody("city_id", city_id);
        entityBuilder.addTextBody("state_id", states_id);

        entityBuilder.setBoundary(boundary);
        HttpEntity entity = entityBuilder.build();
        try {
            System.out.println("Getting Data From :" + complete_url);
            URL url = new URL(complete_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);//Allow Inputs
            conn.setDoOutput(true);//Allow Outputs
            conn.setUseCaches(false);//Don't use a cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            entity.writeTo(conn.getOutputStream());

            System.out.println("Response Code :" + conn.getResponseCode());
            System.out.println("Response Message :" + conn.getResponseMessage());

            responseCode = conn.getResponseCode();

            if (conn.getResponseCode() == 200) {

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


    public String PostAddress(String token, String label, String address, int state_id,
                              int district_id, int subdistrict_id, double latitude, double longitude) {
        String url = apiurl + "user_addresses";
        try {
            String params = "auth_token=" + token +
                    "&address_label=" + URLEncoder.encode(label, "UTF-8") +
                    "&address=" + URLEncoder.encode(address, "UTF-8") +
                    "&state_id=" + state_id + "&district_id=" + district_id +
                    "&sub_district_id=" + subdistrict_id + "&latitude=" + latitude +
                    "&longitude=" + longitude;
            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }

    public String UpdateAddress(String token, int id, String label, String address, int state_id,
                                int district_id, int subdistrict_id, double latitude, double longitude) {
        String url = apiurl + "user_addresses/update";
        try {
            String params = "auth_token=" + token + "&id=" + id +
                    "&address_label=" + URLEncoder.encode(label, "UTF-8") +
                    "&address=" + URLEncoder.encode(address, "UTF-8") +
                    "&state_id=" + state_id + "&district_id=" + district_id +
                    "&sub_district_id=" + subdistrict_id + "&latitude=" + latitude +
                    "&longitude=" + longitude;
            return PostHttp(url, params);
        } catch (UnsupportedEncodingException e) {
            responseCode = 500;
            e.printStackTrace();
        }
        return null;
    }

    public String PostTariff(String token, String shipment) {
        String url = apiurl + "user_shipments";
        String params = "auth_token=" + token + "&shipment=" + shipment;
        return PostHttp(url, params);
    }

    public String PostOrder(String token, String order) {
        String url = apiurl + "orders";
        String params = "auth_token=" + token + "&order=" + order;
        return PostHttp(url, params);
    }

    public String GetHttp(String complete_url) {
        try {
            System.out.println("Getting Data From :" + complete_url);
            URL url = new URL(complete_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            System.out.println("Response Code :" + conn.getResponseCode());
            System.out.println("Response Message :" + conn.getResponseMessage());
            responseCode = conn.getResponseCode();
            if (responseCode == 200) {

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

    public String PostHttp(String complete_url, String complete_params) {
        try {
            System.out.println("Getting Data From :" + complete_url);
            URL url = new URL(complete_url);
            System.out.println("Getting Data From  complete_params:" + complete_params);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(complete_params);
            out.close();


            System.out.println("Response Code :" + conn.getResponseCode());
            System.out.println("Response Message :" + conn.getResponseMessage());

            responseCode = conn.getResponseCode();
            if (responseCode == 200) {

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