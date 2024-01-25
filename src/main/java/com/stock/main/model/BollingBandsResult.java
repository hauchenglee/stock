package com.stock.main.model;

import lombok.Data;

import java.util.List;

@Data
public class BollingBandsResult {
    public List<Double> MiddleBands;
    public List<Double> UpperBands;
    public List<Double> LowerBands;
}
