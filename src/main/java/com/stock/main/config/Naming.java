package com.stock.main.config;

import com.stock.main.util.Constants;

// https://www.baeldung.com/spring-data-mongodb-collection-name
public class Naming {
    public String fix(String name) {
        return Constants.stockNoCollectionName;
    }
}
