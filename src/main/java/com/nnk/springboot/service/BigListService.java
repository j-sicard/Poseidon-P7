package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;

import java.util.List;

public interface BigListService {
    public List<BidList> getAllBidLists();
    public void saveBidList(BidList bidList);
    public void deleteBidListById(Integer bidListId);
    public void getBidListById(Integer bidListId);

}
