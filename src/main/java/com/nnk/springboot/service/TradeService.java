package com.nnk.springboot.service;

import com.nnk.springboot.model.TradeModel;

import java.util.List;
import java.util.Optional;

public interface TradeService {
    public List<TradeModel> getAllTrades();

    public void deleteTrade(TradeModel trade);

    public void saveTrade(TradeModel trade);

    public Optional<TradeModel> getById(Integer id);
}
