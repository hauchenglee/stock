package com.stock.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Entity(name = "exchange")
@Table(name = "exchange")
public class Exchange {

    @Id
    @UuidGenerator
    @Column(name = "exchange_id")
    private UUID exchange_id;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    @JsonIgnore
    private Stock stock;

    @Column(name = "date")
    private String date; // 日期

    @Column(name = "trade_volume")
    private String trade_volume; // 成交股數

    @Column(name = "trade_value")
    private String trade_value; // 成交金額

    @Column(name = "opening_price")
    private String opening_price; // 開盤價

    @Column(name = "highest_price")
    private String highest_price; // 最高價

    @Column(name = "lowest_price")
    private String lowest_price; // 最低價

    @Column(name = "closing_price")
    private String closing_price; // 收盤價

    @Column(name = "price_change")
    private String price_change; // 漲跌價差 spread

    @Column(name = "transaction")
    private String transaction; // 成交筆數 Trading_turnover

    @Column(name = "volume")
    private String Volume; // 成交量

    @Column(name = "buy_amount")
    private String BuyAmount; // 买入量

    @Column(name = "sell_amount")
    private String SellAmount; // 卖出量

    @Column(name = "margin_purchase_buy")
    private String MarginPurchaseBuy; // 融资买入量

    @Column(name = "margin_purchase_cash_repayment")
    private String MarginPurchaseCashRepayment; // 融资现金还款量

    @Column(name = "margin_purchase_limit")
    private String MarginPurchaseLimit; // 融资买入限额

    @Column(name = "margin_purchase_sell")
    private String MarginPurchaseSell; // 融资卖出量

    @Column(name = "margin_purchase_today_balance")
    private String MarginPurchaseTodayBalance; // 融资今日余额

    @Column(name = "margin_purchase_yesterday_balance")
    private String MarginPurchaseYesterdayBalance; // 融资昨日余额

    @Column(name = "offset_loan_and_short")
    private String OffsetLoanAndShort; // 融券偿还卖出量

    @Column(name = "short_sale_buy")
    private String ShortSaleBuy; // 融券卖出买入量

    @Column(name = "short_sale_cash_repayment")
    private String ShortSaleCashRepayment; // 融券现金还款量

    @Column(name = "short_sale_limit")
    private String ShortSaleLimit; // 融券卖出限额

    @Column(name = "short_sale_sell")
    private String ShortSaleSell; // 融券卖出量

    @Column(name = "short_sale_today_balance")
    private String ShortSaleTodayBalance; // 融券今日余额

    @Column(name = "short_sale_yesterday_balance")
    private String ShortSaleYesterdayBalance; // 融券昨日余额

    @Column(name = "dealer_hedging_buy")
    private String Dealer_Hedging_buy; // 经纪人对冲买入量

    @Column(name = "dealer_self_buy")
    private String Dealer_self_buy; // 经纪人自营买入量

    @Column(name = "foreign_investor_buy")
    private String Foreign_Investor_buy; // 外资买入量

    @Column(name = "investment_trust_buy")
    private String Investment_Trust_buy; // 投信买入量

    @Column(name = "dealer_hedging_sell")
    private String Dealer_Hedging_sell; // 经纪人对冲卖出量

    @Column(name = "dealer_self_sell")
    private String Dealer_self_sell; // 经纪人自营卖出量

    @Column(name = "foreign_investor_sell")
    private String Foreign_Investor_sell; // 外资卖出量

    @Column(name = "investment_trust_sell")
    private String Investment_Trust_sell; // 投信卖出量
}
