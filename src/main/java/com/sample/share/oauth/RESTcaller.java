package com.sample.share.oauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RESTcaller {
	
	public static boolean checkValidity(Token token) throws IOException {
		String url = "https://kapi.kakao.com/v1/user/access_token_info";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", "Bearer "+token.getAccess_token());
		con.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		int http_code = con.getResponseCode();
		if(http_code != 200) {
			return false;
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		StringBuffer sb = new StringBuffer();
		String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        System.out.println(sb);
        //sb = {"id":930592143,"expiresInMillis":7198373,"appId":232495}
        //이 정보 필요함?
		return true;
	}
	
	public static Token updateToken(Token token) throws IOException, JSONException {
		String url = "https://kauth.kakao.com/oauth/token";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		
		//내 rest api key
		String client_id = "04508ced113435af63e4b06ce58d2f20";
		//String client_id = "";
		String params = "grant_type=refresh_token&client_id="+client_id+"&refresh_token="+token.getRefreshToken();
		OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
		writer.write(params);
		writer.flush();
		
		int http_code = con.getResponseCode();
		
		Token new_token = null;
		if(http_code == 200) {
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			 
			StringBuffer sb = new StringBuffer();
			String line = null;
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	        }
	        //System.out.println(sb);
	        JSONObject json_obj = new JSONObject(sb.toString());
	        Map<String, String> map = new HashMap<>();
			Iterator<String> iter = json_obj.keys();
			while(iter.hasNext()) {
				String key = iter.next();
				map.put(key, json_obj.get(key).toString());
			}
			
	        new_token = new Token(map);
	        if(new_token.getRefreshToken() == null)
				new_token.setRefreshToken(token.getRefreshToken());
	        new_token.addToken();
	        token.removeToken();
		}
		return new_token;
		//return tokens;
	}

	public static String getUserInfo(Token token) throws Exception {
		String url = "https://kapi.kakao.com/v2/user/me";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", "Bearer "+token.getAccess_token());

		int http_code = con.getResponseCode();
		if(http_code != 200) {
			throw new Exception();
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		StringBuffer sb = new StringBuffer();
		String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        System.out.println(sb);
        JSONObject json_obj = new JSONObject(sb.toString());
		return json_obj.get("id").toString();
	}
}
