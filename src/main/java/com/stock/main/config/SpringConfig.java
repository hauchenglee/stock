package com.stock.main.config;

import com.stock.main.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
//https://www.baeldung.com/spring-scheduled-tasks
public class SpringConfig {

    @Autowired
    private StockService stockService;

    @Scheduled(cron = "${cron.expression}", zone = "${cron.zone}")
    public void scheduleFixedRateTask() throws Exception {
        stockService.saveStockToday();
    }
}
