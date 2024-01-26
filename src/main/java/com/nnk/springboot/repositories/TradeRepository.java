package com.nnk.springboot.repositories;

import com.nnk.springboot.model.TradeModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TradeRepository extends JpaRepository<TradeModel, Integer> {
}
