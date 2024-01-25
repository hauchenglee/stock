package com.stock.main.repository;

import com.stock.main.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, UUID> {

    String findExchangeByDateAndCodeSQL = "select e.* from stock s join exchange e on s.stock_id = e.stock_id " +
            "where (e.date between :date1 and :date2) and s.code = :stockCode";

    String findExchangeByDateSQL = "select e.* from stock s join exchange e on s.stock_id = e.stock_id " +
            "where (e.date between :date1 and :date2)";

    @Query(value = findExchangeByDateAndCodeSQL, nativeQuery = true)
    List<Exchange> findExchangeByDateAndCode(
            @Param("date1") String date1,
            @Param("date2") String date2,
            @Param("stockCode") String stockCode);

    @Query(value = findExchangeByDateSQL, nativeQuery = true)
    List<Exchange> findExchangeByDate(String date1, String date2);
}
