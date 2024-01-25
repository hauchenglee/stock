package com.stock.main.service;

import com.stock.main.model.BollingBandsResult;

import java.util.List;

public interface CalculateService {
    List<Double> calculateSMA(List<Double> prices, int dayCount);

    BollingBandsResult calculateBollingBands(List<Double> data, int dayCount, double K);
}
