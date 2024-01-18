package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    TradeRepository tradeRepository;

    public List<Trade> getAllTrades(){
        return tradeRepository.findAll();
    }

    public void deleteTrade(Trade trade){
        tradeRepository.delete(trade);
    }

    public void saveTrade(Trade trade){
        tradeRepository.save(trade);
    }

    public Optional<Trade> getById(Integer id){
        return tradeRepository.findById(id);
    }

}
