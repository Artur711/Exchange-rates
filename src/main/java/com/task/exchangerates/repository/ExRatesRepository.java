package com.task.exchangerates.repository;

import com.task.exchangerates.model.entity.Call;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExRatesRepository extends JpaRepository<Call, Long> {
}
