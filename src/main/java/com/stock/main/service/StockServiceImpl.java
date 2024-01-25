package com.stock.main.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.stock.main.config.CustomerException;
import com.stock.main.model.Exchange;
import com.stock.main.model.Stock;
import com.stock.main.model.StockMetadata;
import com.stock.main.repository.ExchangeRepository;
import com.stock.main.repository.StockRepository;
import com.stock.main.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class StockServiceImpl implements StockService {

    private final Logger log = Logger.getLogger(this.getClass().getName());

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Override
    public void test() throws Exception {
        try {

        } catch (Exception e) {
            System.out.println("exception: " + e);
        }

        System.out.println("end");
    }

    @Override
    public List<Stock> getAllStock() {
        return stockRepository.findAll();
    }

    @Override
    public Stock getStockByDateAndCode(String date1, String date2, String code) throws Exception {
        Stock stock = getStockByCode(code);
        List<Exchange> exchangeList = exchangeRepository.findExchangeByDateAndCode(date1, date2, code);
        stock.setExchange(exchangeList);
        return stock;
    }

    @Override
    public Stock getStockByCode(String stockCode) throws Exception {
        List<Stock> stockList = stockRepository.findByCode(stockCode);
        // case size = 1 正确
        // case size = 0 找不到数据
        // case size >= 2 异常 有多的数据
        if (stockList == null) {
            throw new CustomerException.DataIsNull("getStockByCode is null");
        } else if (stockList.stream().findFirst().isEmpty()) {
            throw new CustomerException.DataNotFound("getStockByCode is empty");
        } else if (stockList.size() >= 2) {
            throw new CustomerException.DataDuplicate("getStockByCode found more than 2 stock");
        } else {
            return stockList.stream().findFirst().get();
        }
    }

    @Override
    public void saveStockToday() throws Exception {
        // 取得当日交易行情
        String jsonString = HttpRequestUtils.sendGet(Constants.url_STOCK_DAY_ALL);
        JsonNode jsonNode = JsonUtils.readTree(jsonString);
        List<LinkedHashMap<String, Object>> list = JsonUtils.arrayNodeToHashMap((ArrayNode) jsonNode);
        List<StockMetadata> metadataList = MapUtils.convertToPojoList(list, StockMetadata.class);

        // 存成 metadata
        for (StockMetadata metadata : metadataList) {
            Exchange exchange = new Exchange();
            exchange.setDate(DatetimeUtils.getSimpleDateFormatDate(new Date()));
            exchange.setTrade_volume(metadata.TradeVolume);
            exchange.setTrade_value(metadata.TradeValue);
            exchange.setOpening_price(metadata.OpeningPrice);
            exchange.setHighest_price(metadata.HighestPrice);
            exchange.setLowest_price(metadata.LowestPrice);
            exchange.setClosing_price(metadata.ClosingPrice);
            exchange.setPrice_change(metadata.Change);
            exchange.setTransaction(metadata.Transaction);

            try {
                Stock oldStock = getStockByCode(metadata.getCode());
                exchange.setStock(oldStock);
                saveExchange(exchange);
            } catch (CustomerException.DataNotFound e) {
                Stock newStock = new Stock();
                newStock.setCode(metadata.getCode());
                newStock.setName(metadata.getName());
                newStock.setIs_history_exist("true");
                exchange.setStock(newStock);
                saveStock(newStock);
                saveExchange(exchange);
            }
        }
    }

    @Override
    public void saveStockHistory(String oldDateString, String newDateString, String stockCode) throws Exception {
        // 设置交易日期
        String searchDate;

        // 初始化 Calendar
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();

        // 设计新日期
        Date newDate = DatetimeUtils.parserSimpleDateFormatDate(newDateString);
        calendar.setTime(newDate);
        calendar.add(Calendar.DATE, -1);
        System.out.println(newDate);

        // 设计旧日期
        Date oldDate = DatetimeUtils.parserSimpleDateFormatDate(oldDateString);

        // 由新日期往旧日期 for loop
        while (calendar.getTime().after(oldDate) || calendar.getTime().equals(oldDate)) {
            searchDate = formatter.format(calendar.getTime());

            // 确认系统是否已有数据
            // 如果之前以查询过的股票，跳过查询
            boolean duplicateResult = checkDuplicateExchange(searchDate, stockCode);
            if (duplicateResult) {
                continue;
            }

            // 取得 stockCode
            System.out.println("search2 " + searchDate);
            System.out.println("stockCode2 " + stockCode);
            ArrayList<JsonNode> jsonNodeList = getSTOCK_DAY_DATA(searchDate, stockCode);
            saveSTOCK_DAY(jsonNodeList, stockCode);

            calendar.add(Calendar.DAY_OF_MONTH, -1); // 日期减1天
        }
    }

    /**
     * step
     * v 1.取得json
     * v 2.依股票代號取得系統的stock
     * v 3.判斷該股票代號是否下市（是否在官方有數據）
     * 如果下市，将栏位设为 false 让之后能跳过查询
     * 如果没有下市，则继续后续行为
     * v 4.取json的data栏位值
     * 5.将json data栏位值转换成List<String>
     * 6.分别设置stock, exchange
     * 7.save
     *
     * @param searchDate
     * @param stockCode
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<JsonNode> getSTOCK_DAY_DATA(String searchDate, String stockCode) throws Exception {
        // 延后执行request，避免被锁
        Thread.sleep(10000); // 10s
        String jsonString = HttpRequestUtils.sendGet(Constants.url_STOCK_BY_DAY + "?date=" + searchDate + "&stockNo=" + stockCode);
        if (StringUtils.isNullOrEmpty(jsonString)) {
            throw new Exception("STOCK_BY_DAY jsonString is null or empty!");
        }

        JsonNode jsonNodeTWSE = JsonUtils.readTree(jsonString);

        // 确认官方是否已有数据
        // 如果官方没有数据或找不到数据，将栏位设为 false 让之后能跳过查询
        boolean isNoExist = checkNoMatchingProfile(jsonNodeTWSE);
        if (isNoExist) {
            // 取得系统的stock by code
            Stock oldStock = getStockByCode(stockCode);
            oldStock.setIs_history_exist("false");
            saveStock(oldStock);
            return new ArrayList<>();
        } else {
            // 取得股票data
            ArrayNode arrayNodeData = (ArrayNode) jsonNodeTWSE.get("data");
            return JsonUtils.arrayNodeToJsonNodeList(arrayNodeData);
        }
    }

    /**
     * step
     * 1.取得json
     * 2.依股票代號取得系統的stock
     * 3.判斷該股票代號是否下市（是否在官方有數據）
     * 如果下市，将栏位设为 false 让之后能跳过查询
     * 如果没有下市，则继续后续行为
     * 4.取json的data栏位值
     * v 5.将json data栏位值转换成List<String>
     * v 6.分别设置stock, exchange
     * v 7.save
     *
     * @param jsonNodeList
     * @param stockCode
     * @throws Exception
     */
    @Override
    public void saveSTOCK_DAY(ArrayList<JsonNode> jsonNodeList, String stockCode) throws Exception {
        // 取得系统的stock by code
        Stock oldStock = getStockByCode(stockCode);

        for (JsonNode jsonNode : jsonNodeList) {
            List<String> resultList = JsonUtils.convertToListString(jsonNode.toString());

            Exchange exchange = new Exchange();
            String mingGuo = resultList.get(0);
            String xiYuan = DatetimeUtils.transferMinguoDateToADDate(mingGuo);
            exchange.setDate(xiYuan);
            exchange.setTrade_volume(resultList.get(1));
            exchange.setTrade_value(resultList.get(2));
            exchange.setOpening_price(resultList.get(3));
            exchange.setHighest_price(resultList.get(4));
            exchange.setLowest_price(resultList.get(5));
            exchange.setClosing_price(resultList.get(6));
            exchange.setPrice_change(resultList.get(7));
            exchange.setTransaction(resultList.get(8));
            exchange.setStock(oldStock);

            Constants.stockNoCollectionName = stockCode;
            saveExchange(exchange);
        }
    }

    @Override
    public void saveCsvHistory() throws FileNotFoundException, ParseException {
        File[] files = FileUtils.MultipleFileReader(Constants.stock_history_linux, ".csv");
        for (File file : files) {
            Stock stock = new Stock();
            List<List<String>> csvList = CsvUtils.csvReader(file);
            for (List<String> resultList : csvList) {
                stock.setCode(resultList.get(1));
                stock.setIs_history_exist("true");
                Exchange exchange = new Exchange();
                exchange.setDate(resultList.get(0).replace("-", ""));
                exchange.setTrade_volume(resultList.get(2));
                exchange.setTrade_value(resultList.get(3));
                exchange.setOpening_price(resultList.get(4));
                exchange.setHighest_price(resultList.get(5));
                exchange.setLowest_price(resultList.get(6));
                exchange.setClosing_price(resultList.get(7));
                exchange.setPrice_change(resultList.get(8));
                exchange.setTransaction(resultList.get(9));
                exchange.setVolume(resultList.get(10));
                exchange.setBuyAmount(resultList.get(11));
                exchange.setSellAmount(resultList.get(12));
                exchange.setMarginPurchaseBuy(resultList.get(13));
                exchange.setMarginPurchaseCashRepayment(resultList.get(14));
                exchange.setMarginPurchaseLimit(resultList.get(15));
                exchange.setMarginPurchaseSell(resultList.get(16));
                exchange.setMarginPurchaseTodayBalance(resultList.get(17));
                exchange.setMarginPurchaseYesterdayBalance(resultList.get(18));
                exchange.setOffsetLoanAndShort(resultList.get(19));
                exchange.setShortSaleBuy(resultList.get(20));
                exchange.setShortSaleCashRepayment(resultList.get(21));
                exchange.setShortSaleLimit(resultList.get(22));
                exchange.setShortSaleSell(resultList.get(23));
                exchange.setShortSaleTodayBalance(resultList.get(24));
                exchange.setShortSaleYesterdayBalance(resultList.get(25));
                exchange.setDealer_Hedging_buy(resultList.get(26));
                exchange.setDealer_self_buy(resultList.get(27));
                exchange.setForeign_Investor_buy(resultList.get(28));
                exchange.setInvestment_Trust_buy(resultList.get(29));
                exchange.setDealer_Hedging_sell(resultList.get(30));
                exchange.setDealer_self_sell(resultList.get(31));
                exchange.setForeign_Investor_sell(resultList.get(32));
                exchange.setInvestment_Trust_sell(resultList.get(33));
                exchange.setStock(stock);
                saveStock(stock);
                saveExchange(exchange);
            }
        }
    }

    @Override
    public void saveStock(Stock stock) {
        stockRepository.save(stock);
    }

    @Override
    public void saveExchange(Exchange exchange) {
        exchangeRepository.save(exchange);
    }

    private boolean checkDuplicateExchange(String searchDate, String stockCode) throws Exception {
        List<Exchange> exchangeList = exchangeRepository.findExchangeByDateAndCode(searchDate, searchDate, stockCode);
        if (exchangeList.size() == 1) {
            return true;
        } else if (exchangeList.isEmpty()) {
            return false;
        } else {
            throw new CustomerException.DataDuplicate();
        }
    }

    private boolean checkNoMatchingProfile(JsonNode jsonNode) {
        String value = jsonNode.get("stat").asText();
        return value.equals(Constants.noMatchingProfileMsg);
    }
}
