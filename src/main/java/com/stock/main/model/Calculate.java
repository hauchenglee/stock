package com.stock.main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Entity(name = "calculate")
@Table(name = "calculate")
public class Calculate {

    @Id
    @UuidGenerator
    @Column(name = "exchange_id")
    private UUID calculate_id;

    @Column(name = "sma5")
    private Double sma5;

    @Column(name = "sma10")
    private Double sma10;

    @Column(name = "sma20")
    private Double sma20;

    @Column(name = "sma60")
    private Double sma60;

    @Column(name = "sma120")
    private Double sma120;

    @Column(name = "sma240")
    private Double sma240;
}
