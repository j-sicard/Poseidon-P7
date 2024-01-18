package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BigListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BigListServiceImpl implements BigListService {

    @Autowired
    BidListRepository bidListRepository;

    public List<BidList> getAllBidLists(){
       return bidListRepository.findAll();
    }

    public void saveBidList(BidList bidList){
        bidListRepository.save(bidList);
    }

    public void deleteBidList(BidList bidList){
        bidListRepository.delete(bidList);
    }

    public Boolean checkIfExist(Integer id){
      return bidListRepository.existsById(id);
    }

    public Optional<BidList> getbyid(Integer id){
        return bidListRepository.findById(id);
    }

}
