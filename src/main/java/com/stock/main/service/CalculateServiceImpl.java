package com.stock.main.service;

import com.stock.main.model.BollingBandsResult;
import com.stock.main.model.Calculate;
import com.stock.main.repository.CalculateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CalculateServiceImpl implements CalculateService{

    @Autowired
    private CalculateRepository calculateRepository;

    @Override
    public List<Double> calculateSMA(List<Double> prices, int dayCount) {
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < prices.size(); i++) {
            if (i < dayCount) {
                result.add(Double.NaN);
                continue;
            }
            double sum = 0;
            for (int j = 0; j < dayCount; j++) {
                if (prices.get(i - j) != null) {
                    sum += prices.get(i - j);
                }
            }
            result.add(sum / dayCount);
            System.out.println("result: " + sum / dayCount);
        }
        return result;
    }

    @Override
    public BollingBandsResult calculateBollingBands(List<Double> data, int dayCount, double K) {
        BollingBandsResult result = new BollingBandsResult();
        result.MiddleBands = new ArrayList<>();
        result.UpperBands = new ArrayList<>();
        result.LowerBands = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            if (i >= dayCount) {
                List<Double> ClosingPrices = data.subList(i - dayCount, i);
                double MiddleBand = ClosingPrices.stream().mapToDouble(Double::doubleValue).sum() / dayCount;
                result.MiddleBands.add(MiddleBand);

                double sumSquaredDiff = ClosingPrices.stream()
                        .mapToDouble(price -> Math.pow(price - MiddleBand, 2))
                        .sum();
                double StandardDeviation = Math.sqrt(sumSquaredDiff);

                result.UpperBands.add(MiddleBand + K * StandardDeviation);
                result.LowerBands.add(MiddleBand - K * StandardDeviation);
            } else {
                result.MiddleBands.add(Double.NaN);
                result.UpperBands.add(Double.NaN);
                result.LowerBands.add(Double.NaN);
            }
        }
        return result;
    }

    public void saveStock(Calculate calculate) {
        calculateRepository.save(calculate);
    }
}
