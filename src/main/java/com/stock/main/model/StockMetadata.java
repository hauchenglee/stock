package com.stock.main.model;

import lombok.Data;

@Data
public class StockMetadata {
    public String Code;

    public String Name;

    public String Date; // 日期

    public String TradeVolume; // 成交股數

    public String TradeValue; // 成交金額

    public String OpeningPrice; // 開盤價

    public String HighestPrice; // 最高價

    public String LowestPrice; // 最低價

    public String ClosingPrice; // 收盤價

    public String Change; // 漲跌價差

    public String Transaction; // 成交筆數
}
