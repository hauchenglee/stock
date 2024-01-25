package com.stock.main.controller;

import com.stock.main.config.ReturnBean;
import com.stock.main.model.Stock;
import com.stock.main.service.StockService;
import com.stock.main.util.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping(value = "/stock/today")
    public ReturnBean<Boolean> saveStockToday(HttpServletRequest request) throws Exception {
        stockService.saveStockToday();
        return ReturnBean.success(true);
    }

    @PostMapping(value = "/stock/history")
    public ReturnBean<Boolean> saveStockHistory(HttpServletRequest request, @RequestBody String jsonString) throws Exception {
        String date1 = JsonUtils.readValueByFieldName(jsonString, "date1");
        String date2 = JsonUtils.readValueByFieldName(jsonString, "date2");
        String stockCode = JsonUtils.readValueByFieldName(jsonString, "code");
        new Thread(() -> {
            try {
                stockService.saveStockHistory(date1, date2, stockCode);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();

        return ReturnBean.success(true);
    }

    @PostMapping(value = "/stock/csv")
    public ReturnBean<Boolean> saveCsvHistoryStock(HttpServletRequest request, @RequestBody String jsonString) throws Exception {
        stockService.saveCsvHistory();
        return ReturnBean.success(true);
    }

    @GetMapping(value = "/stock/all")
    public ReturnBean<Stock> getStockByCode(HttpServletRequest request) throws Exception {
        List<Stock> list = stockService.getAllStock();
        return ReturnBean.success(list);
    }

    @GetMapping(value = "/stock")
    public ReturnBean<Stock> getStockByCode(HttpServletRequest request,
                                            @RequestParam(value = "code") String code) throws Exception {
        Stock stock = stockService.getStockByCode(code);
        return ReturnBean.success(stock);
    }

    @GetMapping(value = "/stock/exchange")
    public ReturnBean<Stock> getStockByDateAndCode(HttpServletRequest request,
                                                   @RequestParam(value = "date1") String date1,
                                                   @RequestParam(value = "date2") String date2,
                                                   @RequestParam(value = "code") String code) throws Exception {
        Stock stock = stockService.getStockByDateAndCode(date1, date2, code);
        return ReturnBean.success(stock);
    }


}
