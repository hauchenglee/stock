package com.stock.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "stock")
@Table(name = "stock")
// jakarta 是正確的
// javax 在 repository 會出現 not found bean
public class Stock {

    @Id
    @UuidGenerator
    @Column(name = "stock_id")
    private UUID stock_id;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    @Transient // 不被持久化，也不捞取出来
//    @Getter(AccessLevel.NONE)
//    @Setter(AccessLevel.NONE)
    private List<Exchange> exchange = new ArrayList<>();

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "is_history_exist")
    @JsonIgnore
    private String is_history_exist;

    // No setter, only a getter which returns an immutable collection
    // source from: https://stackoverflow.com/questions/30643796/java-spring-data-query-with-onetomany-relation-returns-no-result
//    public List<Exchange> getAddresses() {
//        return Collections.unmodifiableList(this.exchange);
//    }
//
//    public void addExchange(Exchange exchange) {
//        exchange.setStock(this);
//        this.exchange.add(exchange);
//    }
}
