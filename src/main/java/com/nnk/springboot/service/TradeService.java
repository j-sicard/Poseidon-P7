package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;

import java.util.List;

public interface TradeService {
    public List<Trade> getAllTrades();

    public void deleteTrade(Integer tradeId);

    public void saveTrade(Trade trade);
}
