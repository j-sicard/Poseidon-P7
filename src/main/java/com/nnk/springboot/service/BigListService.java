package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;

import java.util.List;
import java.util.Optional;

public interface BigListService {
    public List<BidList> getAllBidLists();
    public void saveBidList(BidList bidList);
    public void deleteBidList(BidList bidList);
    public Boolean checkIfExist(Integer id);
    public Optional<BidList> getbyid(Integer id);
}
