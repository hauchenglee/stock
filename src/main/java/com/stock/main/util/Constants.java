package com.stock.main.util;

public class Constants {

    // HTTP type
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";
    public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded;charset=UTF-8";
    public static final String CONTENT_TYPE_PNG = "image/png";
    public static final String CONTENT_TYPE_MIXED = "multipart/mixed ";
    public static final String CONTENT_TYPE_ALTERNATIVE = "multipart/alternative";
    public static final String CONTENT_TYPE_RELATED = "multipart/related";
    public static final String CONTENT_TYPE_FORM_DATA = "multipart/form-data";
    public static final String CONTENT_TYPE_EXCEL_XLS = "application/vnd.ms-excel";
    public static final String CONTENT_TYPE_EXCEL_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    // security
    public static final String SECRET = "SECRET_KEY";
    public static final long EXPIRATION_TIME = 900_000; // 15 mins
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    // set which url can access server without check security by jwt
    public static final String[] MATCHERS_URL = {
            "/*", // any request
            "/authenticate",
            "/login",
            "/register",
    };
    // url
    public static final String coindeskAPI = "https://api.coindesk.com/v1/bpi/currentprice.json";
    // mail
    public static String MAIL_STARTTLS = "";
    public static String MAIL_HOST = "";
    public static String MAIL_PORT = "";
    public static String MAIL_USER_NAME = "";
    public static String MAIL_FROM = "";
    public static String MAIL_PASSWORD = "";

    // stock
    public static String stockNoCollectionName = "";
    public static String noMatchingProfileMsg = "很抱歉，沒有符合條件的資料!";
    public static String url_STOCK_DAY_ALL = "https://openapi.twse.com.tw/v1/exchangeReport/STOCK_DAY_ALL";
    public static String url_STOCK_BY_DAY = "https://www.twse.com.tw/rwd/zh/afterTrading/STOCK_DAY";

    // file
    public static String stock_history_pc = "./src/main/resources/stock_history/StockHistory";
    public static String stock_history_linux = "/home/stock_history/";
}
