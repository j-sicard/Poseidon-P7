package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;

import java.util.List;
import java.util.Optional;

public interface TradeService {
    public List<Trade> getAllTrades();

    public void deleteTrade(Trade trade);

    public void saveTrade(Trade trade);

    public Optional<Trade> getById(Integer id);
}
