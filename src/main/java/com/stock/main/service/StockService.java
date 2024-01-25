package com.stock.main.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stock.main.model.Exchange;
import com.stock.main.model.Stock;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StockService {

    void test() throws Exception;

    List<Stock> getAllStock();

    Stock getStockByCode(String stockCode) throws Exception;

    Stock getStockByDateAndCode(String date1, String date2, String code) throws Exception;

    void saveStockToday() throws Exception;

    void saveStockHistory(String oldDateString, String newDateString, String stockCode) throws Exception;

    ArrayList<JsonNode> getSTOCK_DAY_DATA(String searchDate, String stockCode) throws Exception;

    void saveSTOCK_DAY(ArrayList<JsonNode> jsonNodeList, String stockCode) throws Exception;

    void saveCsvHistory() throws FileNotFoundException, ParseException;

    void saveStock(Stock stock);

    void saveExchange(Exchange exchange);
}
