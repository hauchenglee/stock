package com.stock.main.repository;

import com.stock.main.model.Calculate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CalculateRepository extends JpaRepository<Calculate, UUID> {


}
