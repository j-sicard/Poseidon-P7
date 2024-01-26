package com.nnk.springboot.service.impl;

import com.nnk.springboot.model.TradeModel;
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

    public List<TradeModel> getAllTrades(){
        return tradeRepository.findAll();
    }

    public void deleteTrade(TradeModel trade){
        tradeRepository.delete(trade);
    }

    public void saveTrade(TradeModel trade){
        tradeRepository.save(trade);
    }

    public Optional<TradeModel> getById(Integer id){
        return tradeRepository.findById(id);
    }

}
