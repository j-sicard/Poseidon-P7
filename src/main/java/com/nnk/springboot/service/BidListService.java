package com.nnk.springboot.service;

import com.nnk.springboot.model.BidListModel;

import java.util.List;
import java.util.Optional;

public interface BidListService {
    public List<BidListModel> getAllBidLists();
    public void saveBidList(BidListModel bidList);
    public void deleteBidList(BidListModel bidList);
    public Optional<BidListModel> getbyid(Integer id);
}
