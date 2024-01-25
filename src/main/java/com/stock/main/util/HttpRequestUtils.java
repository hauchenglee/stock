package com.stock.main.util;

import okhttp3.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

public class HttpRequestUtils {

    private final static String USER_AGENT = "Mozilla/5.0";

    // HTTP POST request
    public static String sendPost(String url, String postParams) throws Exception {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, postParams);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/json")
                .build();

        String responseStr = null;

        Call call = client.newCall(request);

        Response response = call.execute();
        responseStr = response.body().string();
        response.body().close();

        return responseStr;
    }

    // HTTP GET request
    public static String sendGet(String url) throws Exception {

        URL obj = new URL(url);
        if ("https".equalsIgnoreCase(obj.getProtocol())) {
            SslUtils.ignoreSsl();
        }

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        BufferedReader in = null;
        if (responseCode == 200) {
            if ("gzip".equals(con.getContentEncoding())) {
                in = new BufferedReader(new InputStreamReader(new GZIPInputStream(con.getInputStream())));
            } else {
                in = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            }
        }

        String inputLine;
        StringBuilder response = new StringBuilder();
        if (in != null) {
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }

        return response.toString();
    }

    public static String sendGetByReferer(String url, String referer) throws Exception {

        URL obj = new URL(url);
        if ("https".equalsIgnoreCase(obj.getProtocol())) {
            SslUtils.ignoreSsl();
        }

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Referer", referer);

        int responseCode = con.getResponseCode();

        BufferedReader in = null;
        if (responseCode == 200) {
            if ("gzip".equals(con.getContentEncoding())) {
                in = new BufferedReader(new InputStreamReader(new GZIPInputStream(con.getInputStream())));
            } else {
                in = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            }
        }

        String inputLine;
        StringBuilder response = new StringBuilder();
        if (in != null) {
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }

        return response.toString();
    }

    // HTTP POST request
    public static String sendPostByToken(String url, String postParams, String token) throws Exception {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        RequestBody body = RequestBody.create(mediaType, postParams);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", token)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        String responseStr = null;

        Call call = client.newCall(request);
        Response response = call.execute();
        responseStr = response.body().string();
        response.body().close();

        return responseStr;
    }

    public static String sendPostByOpenAI(String url, String postParams, String apiKey) throws Exception {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, postParams);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("content-type", "application/json")
                .build();

        String responseStr = null;

        Call call = client.newCall(request);
        Response response = call.execute();
        responseStr = response.body().string();
        response.body().close();

        return responseStr;
    }

    // HTTP GET token request
    public static String sendGetByToken(String url, String token) throws Exception {
        URL obj = new URL(url);
        if ("https".equalsIgnoreCase(obj.getProtocol())) {
            SslUtils.ignoreSsl();
        }

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Authorization", token);

        int responseCode = con.getResponseCode();

        BufferedReader in = null;
        if (responseCode == 200) {
            if ("gzip".equals(con.getContentEncoding())) {
                in = new BufferedReader(new InputStreamReader(new GZIPInputStream(con.getInputStream())));
            } else {
                in = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            }
        }

        String inputLine;
        StringBuilder response = new StringBuilder();
        if (in != null) {
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }

        return response.toString();
    }
}
