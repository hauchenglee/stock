package com.stock.main.repository;

import com.stock.main.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {

    List<Stock> findByCode(String code);

    /**
     * 多表查询
     * https://matthung0807.blogspot.com/2020/11/spring-data-jpa-jpql-join.html
     * https://codejava.net/frameworks/spring/jpa-join-query-for-like-search-examples
     * https://roytuts.com/spring-boot-data-jpa-left-right-inner-and-cross-join-examples/
     */
}
