package com.nnk.springboot.repositories;

import com.nnk.springboot.model.BidListModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BidListRepository extends JpaRepository<BidListModel, Integer> {

}
